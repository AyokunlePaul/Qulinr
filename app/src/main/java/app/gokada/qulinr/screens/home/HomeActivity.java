package app.gokada.qulinr.screens.home;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import app.gokada.qulinr.QulinrApplication;
import app.gokada.qulinr.R;
import app.gokada.qulinr.app_core.api.QulinrResponse;
import app.gokada.qulinr.app_core.api.models.CreateMenuRequest;
import app.gokada.qulinr.app_core.dagger.components.QulinrMainComponent;
import app.gokada.qulinr.app_core.manager.FoodTimerWorkRequest;
import app.gokada.qulinr.app_core.models.TimeModel;
import app.gokada.qulinr.app_core.view.CoreActivity;
import app.gokada.qulinr.databinding.ActivityHomeBinding;
import app.gokada.qulinr.databinding.LayoutTimeSelectorBinding;
import app.gokada.qulinr.service.QulinrStickyService;

public class HomeActivity extends CoreActivity {

    private ActivityHomeBinding binding;
    private QulinrMainComponent component;
    private TimeModel globalModel;

    private HomeActivityVM.OnMenuCreatedCallback menuCreatedCallback;
    private HomeActivityVM.OnSlackNotifiedCallback slackNotifiedCallback;

    @Inject
    HomeActivityVM viewModel;

    private Intent serviceIntent;
    private QulinrStickyService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = QulinrApplication.get(this).getComponent();
        component.inject(this);

        initBinding();
        initCallback();

        service = new QulinrStickyService();

        if (!isServiceRunning(service.getClass())){
            serviceIntent = new Intent(this, service.getClass());
            startService(serviceIntent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }

    private boolean isServiceRunning(Class<?> clazz){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)){
            if (clazz.getName().equals(service.service.getClassName())){
                return true;
            }
        }

        return false;
    }

    private void initBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.breakfastLink.setOnClickListener(new HomeActivityViewClickListeners());
        binding.completedLink.setOnClickListener(new HomeActivityViewClickListeners());

        globalModel = new TimeModel();
        globalModel.time = "1hr";
        globalModel.timeInMills = 3600000;
        binding.breakfastLink.setTime(globalModel);
    }

    private void scheduleWork(TimeModel model){
//        OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(FoodTimerWorkRequest.class);
//        builder.setInitialDelay(model.timeInMills, TimeUnit.MILLISECONDS);
//
//        WorkManager.getInstance().enqueue(builder.build());

        String toastMessage = String.format(Locale.ENGLISH,"%s %s", "Reminder set for ", model.time);
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        viewModel.cacheCounter(10000L);
        service.startCounter(10000L);
        Log.i("Worker", "Work successfully scheduled. ===== " + viewModel.getCachedLong());
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
                    Toast.makeText(HomeActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    scheduleWork(globalModel);
                    viewModel.cacheToken(response.getData().toString());

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
                    Toast.makeText(HomeActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    deleteAll();
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
        binding.breakfastLink.getRoot().setVisibility(View.VISIBLE);
    }

    private void showMoreTimeLayout(){
        hideAllViews();
        binding.additionalTime.getRoot().setVisibility(View.VISIBLE);
    }

    private void hideAllViews(){
        binding.breakfastLink.getRoot().setVisibility(View.GONE);
        binding.completedLink.getRoot().setVisibility(View.GONE);
        binding.additionalTime.getRoot().setVisibility(View.GONE);
    }

    private void deleteAll(){
        viewModel.deleteAll();
    }

    private void deleteWorkId(){
        viewModel.deleteWorkId();
    }

    private void showDialog(){
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
                        dialog.dismiss();
                    }
                });
                recyclerBinding.timeList.setAdapter(adapter);
                recyclerBinding.timeList.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));
            }
        });
        dialog.show();
    }

    public class HomeActivityViewClickListeners{
        public void onCreateMenuClicked(View view){
            CreateMenuRequest menuRequest = new CreateMenuRequest();
            menuRequest.setFoodMenu(binding.breakfastLink.foodMenu.getText().toString());
            menuRequest.setFoodType("breakfast");
            menuRequest.setTimeEstimate(binding.breakfastLink.timeEstimate.getText().toString());
            viewModel.createMenu(menuRequest, menuCreatedCallback);
        }
        public void onSelectTimeClicked(View view){
            showDialog();
        }
        public void onFoodIsReadyClicked(View view){
            viewModel.notifySlack(slackNotifiedCallback);
        }
        public void onINeedMoreTimeClicked(View view){
            showMoreTimeLayout();
        }
    }

}
