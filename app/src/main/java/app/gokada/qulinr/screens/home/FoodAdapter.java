package app.gokada.qulinr.screens.home;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.gokada.qulinr.R;
import app.gokada.qulinr.databinding.FoodListItemBinding;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.Holder> {

    private String[] models;
    private OnFoodSelected onFoodSelected;

    public FoodAdapter(String... models){
        this.models = models;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.food_list_item, parent, false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final String food = models[position];
        holder.bind(food);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFoodSelected != null){
                    onFoodSelected.onFoodSelected(food);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.length;
    }

    public void setOnTimeSelectedListener(OnFoodSelected onFoodSelected){
        this.onFoodSelected = onFoodSelected;
    }

    interface OnFoodSelected {
        void onFoodSelected(String model);
    }
    public class Holder extends RecyclerView.ViewHolder {

        private FoodListItemBinding binding;

        public Holder(FoodListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String model){
            binding.setFood(model);
        }

    }

}
