package com.antar.driver.item;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.antar.driver.R;
import com.antar.driver.models.BankModels;
import com.antar.driver.utils.Utility;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class BanklistItem extends RecyclerView.Adapter<BanklistItem.ItemRowHolder> {

    private ArrayList<BankModels> dataList;
    private Context mContext;
    private int rowLayout;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(BankModels item);
    }

    public BanklistItem(Context context, ArrayList<BankModels> dataList, int rowLayout, OnItemClickListener listener) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final BankModels singleItem = dataList.get(position);
        if (singleItem.getText().equals("Unlimited")) {
            holder.text.setText(singleItem.getText());
        } else {
//            Utility.currencyTXT(holder.text, singleItem.getText(), mContext);
            Utility.convertLocaleCurrencyTV(holder.text, mContext, singleItem.getText());
        }
        holder.text.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
                "fonts/NeoSansPro_Medium.ttf"));

        holder.bind(singleItem, listener);


    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text;
        LinearLayout background;

        ItemRowHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.rootLayout);
            text = itemView.findViewById(R.id.text);
        }

        void bind(final BankModels item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                    notifyDataSetChanged();
                }
            });


        }
    }


}
