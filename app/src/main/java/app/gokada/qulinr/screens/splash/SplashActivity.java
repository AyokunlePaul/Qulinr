package app.gokada.qulinr.screens.splash;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import app.gokada.qulinr.R;
import app.gokada.qulinr.app_core.view.CoreActivity;
import app.gokada.qulinr.databinding.ActivitySplashBinding;

public class SplashActivity extends CoreActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }
}
