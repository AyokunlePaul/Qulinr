package app.gokada.qulinr.screens.timetable.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import app.gokada.qulinr.R;
import app.gokada.qulinr.app_core.api.models.FullTimeTableResponse;
import app.gokada.qulinr.app_core.api.models.TimeTableResponse;
import app.gokada.qulinr.databinding.LayoutTimeTableItemBinding;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableHolder> {

    private FullTimeTableResponse responses;
    private LayoutInflater inflater;

    public TimetableAdapter(FullTimeTableResponse responses){
        this.responses = responses;
    }

    @NonNull
    @Override
    public TimetableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        LayoutTimeTableItemBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.layout_time_table_item, parent, false);
        return new TimetableHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TimetableHolder holder, int position) {
        TimeTableResponse response = getCurrentResponse(position);
        holder.bind(getDay(position), response);
    }

    private TimeTableResponse getCurrentResponse(int position){
        switch (position){
            default:
            case 0:
                return responses.getSunday();
            case 1:
                return responses.getMonday();
            case 2:
                return responses.getTuesday();
            case 3:
                return responses.getWednesday();
            case 4:
                return responses.getThursday();
            case 5:
                return responses.getFriday();
            case 6:
                return responses.getSaturday();
        }
    }

    private String getDay(int position){
        switch (position){
            default:
            case 0:
                return "Sun";
            case 1:
                return "Mon";
            case 2:
                return "Tue";
            case 3:
                return "Wed";
            case 4:
                return "Thur";
            case 5:
                return "Fri";
            case 6:
                return "Sat";
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class TimetableHolder extends RecyclerView.ViewHolder {

        private LayoutTimeTableItemBinding binding;

        public TimetableHolder(LayoutTimeTableItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String day, TimeTableResponse response){
            binding.setDay(day);
            binding.setTimetable(response);
        }
    }

}
