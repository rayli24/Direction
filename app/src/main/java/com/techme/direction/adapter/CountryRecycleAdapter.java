package com.techme.direction.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techme.direction.Country;
import com.techme.direction.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CountryRecycleAdapter extends ListAdapter<Country,CountryRecycleAdapter.CountryViewHolder> {
    private onItemClickListener listener;

    public CountryRecycleAdapter() {
        super(Diff_Callback);
    }

    public static DiffUtil.ItemCallback<Country> Diff_Callback = new DiffUtil.ItemCallback<Country>() {
        @Override
        public boolean areItemsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
            return oldItem.getName().equals(newItem.getName()) && oldItem.getSelected() == newItem.getSelected();
        }
    };

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_country_recycle_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onClick(getCountry(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_recycle_view,parent,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = getCountry(position);
        holder.txtName.setText(country.getName());
    }
    public Country getCountry(int position){
        return getItem(position);
    }

    public interface onItemClickListener{
        void onClick(Country country);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

}
