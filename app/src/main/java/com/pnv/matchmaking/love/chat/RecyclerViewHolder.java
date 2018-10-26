package com.pnv.matchmaking.love.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnv.matchmaking.love.R;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
{
    public TextView text_view_user_name, text_view_time, text_view_content;

    private ItemClickListener itemClickListener;
    Context mContext;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        text_view_user_name = (TextView) itemView.findViewById(R.id.text_view_user_name);
        text_view_time = (TextView) itemView.findViewById(R.id.text_view_time);
        text_view_content = (TextView) itemView.findViewById(R.id.text_view_content);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}
