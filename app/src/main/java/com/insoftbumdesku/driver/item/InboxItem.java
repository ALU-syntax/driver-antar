package com.insoftbumdesku.driver.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.insoftbumdesku.driver.R;
import com.insoftbumdesku.driver.activity.DetailActivity;
import com.insoftbumdesku.driver.models.NotifModel;

import java.util.List;


public class InboxItem extends RecyclerView.Adapter<InboxItem.ItemRowHolder> {

    private List<NotifModel> dataList;
    private Context context;

    public InboxItem(Context context, List<NotifModel> dataList) {
        this.dataList = dataList;
        this.context = context;
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inbox, parent, false);
        return new ItemRowHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        holder.judulnotif.setText(dataList.get(position).getJudul());
        holder.isinotif.setText(dataList.get(position).getIsi());
        holder.tanggalnotif.setText(dataList.get(position).getTanggal());
        holder.selengkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, DetailActivity.class);
                in.putExtra("judulnotifintent", dataList.get(position).getJudul());
                in.putExtra("isinotifintent", dataList.get(position).getIsi());
                in.putExtra("tanggalnotifintent", dataList.get(position).getTanggal());
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView judulnotif, isinotif, tanggalnotif, selengkapnya;
        RelativeLayout rootLayout;
        ItemRowHolder(View itemView) {
            super(itemView);
            judulnotif = itemView.findViewById(R.id.judulnotif);
            isinotif = itemView.findViewById(R.id.isinotif);
            tanggalnotif = itemView.findViewById(R.id.tanggalnotif);
            selengkapnya = itemView.findViewById(R.id.selengkapnya);
            rootLayout = itemView.findViewById(R.id.rootLayout);

        }
    }


}
