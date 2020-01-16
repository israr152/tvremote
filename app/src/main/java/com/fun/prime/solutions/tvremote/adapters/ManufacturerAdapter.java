package com.fun.prime.solutions.tvremote.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fun.prime.solutions.tvremote.R;
import com.fun.prime.solutions.tvremote.codes.Manufacturer;

import java.util.List;

public class ManufacturerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context c;
    private List<String> list;
    private ClickListener listener;

    public ManufacturerAdapter(Context c, List<String> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(c).inflate(R.layout.listitem_manufacturer,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMf;
        LinearLayout llMf;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMf = itemView.findViewById(R.id.tvMf);
            llMf = itemView.findViewById(R.id.llMf);
        }

        void bindData(final String name) {
            tvMf.setText(name);
            llMf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onManufactureClick(name);
                    }
                }
            });
        }
    }

    public interface ClickListener {
        void onManufactureClick(String mf);
    }

    public void setClickListener(ClickListener listener){
        this.listener = listener;
    }
}
