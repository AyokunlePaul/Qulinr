package app.gokada.qulinr.screens.splash.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import app.gokada.qulinr.QulinrApplication;
import app.gokada.qulinr.R;
import app.gokada.qulinr.app_core.api.models.FullTimeTableResponse;
import app.gokada.qulinr.app_core.api.models.TimeTableResponse;
import app.gokada.qulinr.app_core.dagger.components.QulinrMainComponent;
import app.gokada.qulinr.app_core.view.CoreActivity;
import app.gokada.qulinr.databinding.ActivitySplashBinding;
import app.gokada.qulinr.global.Day;
import app.gokada.qulinr.screens.home.HomeActivity;
import app.gokada.qulinr.screens.splash.viewmodel.SplashVM;

public class SplashActivity extends CoreActivity {

    private boolean hasFetchedTimetable = false;
    private boolean hasFetchedFullTimetable = false;

    private ActivitySplashBinding binding;
    private QulinrMainComponent component;

    private SplashVM.OnTimetableRetrievedCallback timetableRetrievedCallback;
    private SplashVM.OnFullTimeTableRetrievedCallback fullTimeTableRetrievedCallback;

    @Inject
    SplashVM splashVM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = QulinrApplication.get(this).getComponent();
        component.inject(this);

        initBinding();

        initCallback();

        if (splashVM.getCachedFoodType() == null){
            getTimetableForToday();
        } else if (splashVM.getCachedFoodType().equals("dinner")){
            getTimetableForToday();
        } else {
            binding.setEnable(true);
        }

        if (splashVM.getCurrentDay().equals(Day.DAY.SUNDAY)){
            splashVM.cacheFoodType("breakfast");
        }

        overridePendingTransition(android.R.transition.slide_left, android.R.transition.slide_right);
    }

    private void initBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setOnClickListener(new SplashActivityViewClickListener());
    }

    private void initCallback(){
        timetableRetrievedCallback = new SplashVM.OnTimetableRetrievedCallback() {
            @Override
            public void onTimetableRetrieved(TimeTableResponse response) {
                hasFetchedTimetable = true;
                splashVM.cacheTimetable(response);
                getFullTimeTable();
            }

            @Override
            public void onErrorOccurred(Throwable throwable) {
                Log.e("TIME TABLE TODAY", throwable.getLocalizedMessage() != null ? throwable.getLocalizedMessage() : "NULL");
                hasFetchedTimetable = false;
                binding.loadingLink.getRoot().setVisibility(View.GONE);
                final Snackbar snackbar = Snackbar.make(binding.getRoot(), "An error has occurred", Snackbar.LENGTH_INDEFINITE);
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

                TextView text = layout.findViewById(android.support.design.R.id.snackbar_text);
                text.setTextColor(getResources().getColor(android.R.color.white));
                text.setEllipsize(TextUtils.TruncateAt.END);

                snackbar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getTimetableForToday();
                        snackbar.dismiss();
                    }
                });

                showDialog(throwable);

                snackbar.setActionTextColor(getResources().getColor(R.color.white));
                snackbar.show();
            }
        };

        fullTimeTableRetrievedCallback = new SplashVM.OnFullTimeTableRetrievedCallback() {
            @Override
            public void onFullTimeTableRetrieved(FullTimeTableResponse response) {
                splashVM.cacheFullTimeTable(response);
                hasFetchedFullTimetable = true;
                binding.loadingLink.getRoot().setVisibility(View.GONE);
                binding.setEnable(hasFetchedTimetable && hasFetchedFullTimetable);
            }

            @Override
            public void onErrorOccurred(Throwable throwable) {
                Log.e("FULL TIME TABLE", throwable.getLocalizedMessage() != null ? throwable.getLocalizedMessage() : "NULL");
                hasFetchedFullTimetable = false;
                binding.loadingLink.getRoot().setVisibility(View.GONE);
                final Snackbar snackbar = Snackbar.make(binding.getRoot(), "An error has occurred", Snackbar.LENGTH_INDEFINITE);
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

                TextView text = layout.findViewById(android.support.design.R.id.snackbar_text);
                text.setTextColor(getResources().getColor(android.R.color.white));
                text.setEllipsize(TextUtils.TruncateAt.END);

                snackbar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFullTimeTable();
                        snackbar.dismiss();
                    }
                });

                showDialog(throwable);

                snackbar.setActionTextColor(getResources().getColor(R.color.white));
                snackbar.show();
            }
        };
    }

    private void showDialog(Throwable throwable){
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setMessage(throwable.getLocalizedMessage());
        builder.setCancelable(false);
        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void getTimetableForToday(){
        binding.loadingLink.getRoot().setVisibility(View.VISIBLE);
        splashVM.getTimetableForToday(timetableRetrievedCallback);
    }

    private void getFullTimeTable(){
        binding.loadingLink.getRoot().setVisibility(View.VISIBLE);
        splashVM.getFullTimetable(fullTimeTableRetrievedCallback);
    }

    public class SplashActivityViewClickListener{
        public void onProceedClicked(View view){
//            if (!(hasFetchedTimetable && hasFetchedFullTimetable)){
//
//                return;
//            }
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

}
