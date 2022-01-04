package com.loopin.ipos.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ZoneShopAdapter extends RecyclerView.Adapter<ZoneShopAdapter.ZSHolder> {
    @NonNull
    @Override
    public ZSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ZSHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ZSHolder extends RecyclerView.ViewHolder{
        public ZSHolder(View zsView){
            super(zsView);
        }
    }
}
