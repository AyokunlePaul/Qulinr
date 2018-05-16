package app.gokada.qulinr.screens.splash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Toast;

import app.gokada.qulinr.R;
import app.gokada.qulinr.app_core.view.CoreActivity;
import app.gokada.qulinr.databinding.ActivitySplashBinding;
import app.gokada.qulinr.screens.home.HomeActivity;

public class SplashActivity extends CoreActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();

        overridePendingTransition(android.R.transition.slide_left, android.R.transition.slide_right);
    }

    private void initBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setOnClickListener(new SplashActivityViewClickListener());
    }

    public class SplashActivityViewClickListener{
        public void onProceedClicked(View view){
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

}
