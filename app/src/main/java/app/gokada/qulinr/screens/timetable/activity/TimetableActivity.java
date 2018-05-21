package app.gokada.qulinr.screens.timetable.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import javax.inject.Inject;

import app.gokada.qulinr.QulinrApplication;
import app.gokada.qulinr.R;
import app.gokada.qulinr.app_core.api.models.FullTimeTableResponse;
import app.gokada.qulinr.app_core.dagger.components.QulinrMainComponent;
import app.gokada.qulinr.app_core.view.CoreActivity;
import app.gokada.qulinr.databinding.ActivityTimetableBinding;
import app.gokada.qulinr.screens.timetable.adapter.TimetableAdapter;
import app.gokada.qulinr.screens.timetable.viewmodel.TimetableVM;

public class TimetableActivity extends CoreActivity {

    private ActivityTimetableBinding binding;
    private QulinrMainComponent component;

    @Inject
    TimetableVM viewModel;

    private RecyclerView.OnScrollListener listener;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = QulinrApplication.get(this).getComponent();
        component.inject(this);

        initBinding();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timetable);
        binding.toolbarLink.setOnClickListener(new TimetableActivityClickListener());
        binding.setOnClickListener(new TimetableActivityClickListener());
        binding.setVisible(false);
        initScrollListener();
        setupRecyclerView();
    }

    private void initScrollListener(){
        manager = new LinearLayoutManager(this);
        listener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (manager.findLastVisibleItemPosition() == 6 && dy > 0){
                    binding.setVisible(true);
                } else {
                    binding.setVisible(false);
                }
            }
        };
    }

    private void setupRecyclerView(){
        TimetableAdapter adapter = new TimetableAdapter(getCachedFullTimeTableResponse());
        binding.timetable.setAdapter(adapter);
        binding.timetable.setLayoutManager(manager);
        binding.timetable.addOnScrollListener(listener);
        binding.timetable.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
    }

    private FullTimeTableResponse getCachedFullTimeTableResponse(){
        return viewModel.getCachedFullTimeTableResponse();
    }

    public class TimetableActivityClickListener{
        public void onBackPressed(View view){
            finish();
        }

        public void onScrollToTopPressed(View view){
            manager.scrollToPosition(0);
        }
    }

}
