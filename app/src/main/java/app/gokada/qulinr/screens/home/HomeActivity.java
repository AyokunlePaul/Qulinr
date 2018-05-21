package app.gokada.qulinr.screens.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;


import app.gokada.qulinr.QulinrApplication;
import app.gokada.qulinr.R;
import app.gokada.qulinr.app_core.api.QulinrResponse;
import app.gokada.qulinr.app_core.api.models.CreateMenuRequest;
import app.gokada.qulinr.app_core.api.models.TimeTableResponse;
import app.gokada.qulinr.app_core.dagger.components.QulinrMainComponent;
import app.gokada.qulinr.app_core.models.TimeModel;
import app.gokada.qulinr.app_core.view.CoreActivity;
import app.gokada.qulinr.databinding.ActivityHomeBinding;
import app.gokada.qulinr.databinding.LayoutTimeSelectorBinding;
import app.gokada.qulinr.receiver.NotificationPublisher;
import app.gokada.qulinr.screens.timetable.activity.TimetableActivity;

public class HomeActivity extends CoreActivity {

    private ActivityHomeBinding binding;
    private QulinrMainComponent component;
    private TimeModel globalModel;
    private String globalFood;

    private String foodType;

    private HomeActivityVM.OnMenuCreatedCallback menuCreatedCallback;
    private HomeActivityVM.OnSlackNotifiedCallback slackNotifiedCallback;

    @Inject
    HomeActivityVM viewModel;

    private TimeTableResponse timeTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = QulinrApplication.get(this).getComponent();
        component.inject(this);

        timeTable = viewModel.getCachedTimetable();

        initBinding();
        initCallback();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.breakfastLink.setOnClickListener(new HomeActivityViewClickListeners());
        binding.completedLink.setOnClickListener(new HomeActivityViewClickListeners());
        binding.additionalTime.setOnClickListeners(new HomeActivityViewClickListeners());

        globalModel = new TimeModel();
        globalModel.time = "1hr";
        globalModel.timeInMills = 3600000;
        binding.breakfastLink.setTime(globalModel);

        if (viewModel.getCachedToken() != null){
            foodType = viewModel.getCachedFoodType();
            Log.i("FOOD TYPE", " ========= " + foodType);
            showCompletedLayout();
            return;
        }

        showHelloLayout();
    }

    private void scheduleWork(TimeModel model){
        String toastMessage = String.format(Locale.ENGLISH,"%s %s", "Reminder set for ", model.time);
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        createAlarm(model.timeInMills);
    }

    private void createAlarm(long scanInterval){
        QulinrApplication application = QulinrApplication.get(this);

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, application.createMediaNotification());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager  = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + scanInterval, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + scanInterval, pendingIntent);
        }
    }

    private List<TimeModel> getModels(){
        List<TimeModel> models = new ArrayList<>();
        TimeModel first = new TimeModel();
        first.time = "30mins";
        first.timeInMills = 1800000;
        TimeModel second = new TimeModel();
        second.time = "1hr";
        second.timeInMills = 3600000;
        TimeModel third = new TimeModel();
        third.time = "1hr30mins";
        third.timeInMills = 5400000;
        TimeModel fourth = new TimeModel();
        fourth.time = "2hr";
        fourth.timeInMills = 7200000;

        models.add(first);
        models.add(second);
        models.add(third);
        models.add(fourth);

        return models;
    }

    private void initCallback(){
        menuCreatedCallback = new HomeActivityVM.OnMenuCreatedCallback() {
            @Override
            public void onMenuCreated(QulinrResponse response, Throwable throwable) {
                if (response == null){
                    showHelloLayout();
                    Toast.makeText(HomeActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    scheduleWork(globalModel);
                    viewModel.cacheToken(response.getData().toString());
                    viewModel.cacheFoodType(foodType);

                    Log.i("TOKEN", response.getData().toString());
                    Toast.makeText(HomeActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                    showCompletedLayout();
                }
            }
        };
        slackNotifiedCallback = new HomeActivityVM.OnSlackNotifiedCallback() {
            @Override
            public void onSlackNotified(QulinrResponse response, Throwable throwable) {
                if (response == null){
                    showCompletedLayout();
                    Toast.makeText(HomeActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    viewModel.cacheFoodType(foodType);
                    viewModel.deleteToken();
                    showHelloLayout();
                }
            }
        };
    }

    private void showCompletedLayout(){
        hideAllViews();
        binding.completedLink.getRoot().setVisibility(View.VISIBLE);
    }

    private void showHelloLayout(){
        hideAllViews();
        Log.i("Hello Layout(Breakfast)", timeTable.getBreakfast());
        Log.i("Hello Layout(Lunch)", timeTable.getLunch());
        Log.i("Hello Layout(Dinner)", timeTable.getDinner());
        binding.breakfastLink.getRoot().setVisibility(View.VISIBLE);
        if (viewModel.getCachedFoodType() == null || viewModel.getCachedFoodType().equals("dinner")){
            foodType = "breakfast";
            globalFood = timeTable.getBreakfast();
            binding.breakfastLink.setTimetable(globalFood);
            binding.breakfastLink.setFoodtype(foodType.toUpperCase() + "?");
//            Log.i("Hello Layout(Breakfast)", globalFood);
            return;
        }
        if (viewModel.getCachedFoodType().equals("breakfast")){
            foodType = "lunch";
            globalFood = timeTable.getLunch();
            binding.breakfastLink.setTimetable(globalFood);
            binding.breakfastLink.setFoodtype(foodType.toUpperCase() + "?");
//            Log.i("Hello Layout(Lunch)", globalFood);
            return;
        }
        if (viewModel.getCachedFoodType().equals("lunch")){
            foodType = "dinner";
            globalFood = timeTable.getDinner();
            binding.breakfastLink.setTimetable(globalFood);
            binding.breakfastLink.setFoodtype(foodType.toUpperCase() + "?");
//            Log.i("Hello Layout(Dinner)", globalFood);
        }
    }

    private void showMoreTimeLayout(){
        hideAllViews();
        binding.additionalTime.setTime(globalModel);
        binding.additionalTime.getRoot().setVisibility(View.VISIBLE);
    }

    private void showLoading(){
        hideAllViews();
        binding.loadingLink.getRoot().setVisibility(View.VISIBLE);
    }

    private void hideAllViews(){
        binding.breakfastLink.getRoot().setVisibility(View.GONE);
        binding.completedLink.getRoot().setVisibility(View.GONE);
        binding.additionalTime.getRoot().setVisibility(View.GONE);
        binding.loadingLink.getRoot().setVisibility(View.GONE);
    }

    private void showTimeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutTimeSelectorBinding recyclerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_time_selector, null, false);
        builder.setView(recyclerBinding.getRoot());
        builder.setTitle("Select Time");
        builder.setPositiveButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                TimeAdapter adapter = new TimeAdapter(getModels());
                adapter.setOnTimeSelectedListener(new TimeAdapter.OnTimeSelected() {
                    @Override
                    public void onTimeSelected(TimeModel model) {
                        globalModel = model;
                        binding.breakfastLink.setTime(globalModel);
                        binding.additionalTime.setTime(globalModel);
                        dialog.dismiss();
                    }
                });
                recyclerBinding.timeList.setAdapter(adapter);
                recyclerBinding.timeList.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));
            }
        });
        dialog.show();
    }

    private void showFoodSelectionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutTimeSelectorBinding recyclerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_time_selector, null, false);
        builder.setView(recyclerBinding.getRoot());
        builder.setTitle("Select Food");
        builder.setPositiveButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                FoodAdapter adapter = new FoodAdapter(viewModel.getListOfFoodFromFullTimeTableResponse());
                adapter.setOnTimeSelectedListener(new FoodAdapter.OnFoodSelected() {
                    @Override
                    public void onFoodSelected(String food) {
                        globalFood = food;
                        binding.breakfastLink.setTimetable(globalFood);
                        dialog.dismiss();
                    }
                });
                recyclerBinding.timeList.setAdapter(adapter);
                recyclerBinding.timeList.setLayoutManager(new GridLayoutManager(HomeActivity.this, 3));
            }
        });
        dialog.show();
    }

    public class HomeActivityViewClickListeners{
        public void onCreateMenuClicked(View view){
            showLoading();
            CreateMenuRequest menuRequest = new CreateMenuRequest();
            menuRequest.setFoodMenu(binding.breakfastLink.foodMenu.getText().toString());
            menuRequest.setFoodType(foodType);
            menuRequest.setTimeEstimate(binding.breakfastLink.timeEstimate.getText().toString());
            viewModel.createMenu(menuRequest, menuCreatedCallback);
        }
        public void onSelectTimeClicked(View view){
            showTimeDialog();
        }
        public void onFoodIsReadyClicked(View view){
            showLoading();
            viewModel.notifySlack(slackNotifiedCallback);
        }
        public void onINeedMoreTimeClicked(View view){
            showMoreTimeLayout();
        }
        public void onContinueCookingClicked(View view){
            createAlarm(globalModel.timeInMills);
            showCompletedLayout();
        }
        public void onShowCalenderClicked(View view){
            Intent intent = new Intent(HomeActivity.this, TimetableActivity.class);
            startActivity(intent);
        }
        public void onSelectOtherFoodSelected(View view){
            showFoodSelectionDialog();
        }
    }

}
