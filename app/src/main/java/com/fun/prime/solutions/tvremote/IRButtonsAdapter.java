package com.fun.prime.solutions.tvremote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fun.prime.solutions.tvremote.codes.IRButton;

import java.util.List;

public class IRButtonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IRButton> list;
    private Context c;
    private ClickListener listener;

    public IRButtonsAdapter(List<IRButton> list, Context c) {
        this.list = list;
        this.c = c;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(c).inflate(R.layout.listitem_irbutton,parent,false));
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

    class ViewHolder extends RecyclerView.ViewHolder{
        Button btnIr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnIr = itemView.findViewById(R.id.btnIr);
        }

        public void bindData(final IRButton irButton) {
            btnIr.setText(irButton.getDisplay());
            btnIr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onButtonClick(irButton);
                    }
                }
            });
        }
    }

    public interface ClickListener{
        void onButtonClick(IRButton irButton);
    }

    public void setClickListener(ClickListener listener){
        this.listener = listener;
    }

}
