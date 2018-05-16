package app.gokada.qulinr.screens.home;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.gokada.qulinr.R;
import app.gokada.qulinr.app_core.models.TimeModel;
import app.gokada.qulinr.databinding.TimeListItemBinding;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.Holder> {

    private List<TimeModel> models;
    private OnTimeSelected onTimeSelected;

    public TimeAdapter(List<TimeModel> models){
        this.models = models;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TimeListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.time_list_item, parent, false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final TimeModel model = models.get(position);
        holder.bind(model);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTimeSelected != null){
                    onTimeSelected.onTimeSelected(model);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setOnTimeSelectedListener(OnTimeSelected onTimeSelected){
        this.onTimeSelected = onTimeSelected;
    }

    interface OnTimeSelected{
        void onTimeSelected(TimeModel model);
    }
    public class Holder extends RecyclerView.ViewHolder {

        private TimeListItemBinding binding;

        public Holder(TimeListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TimeModel model){
            binding.setTime(model);
        }

    }

}
