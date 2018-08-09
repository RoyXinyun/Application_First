package com.example.dell.lbstest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.library.bubbleview.BubbleTextView;

import java.util.ArrayList;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> msgList = new ArrayList<>();

    public MsgAdapter(List<Msg> msgList){
        this.msgList = msgList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_left;
        BubbleTextView text_left;
        LinearLayout layout_right;
        BubbleTextView text_right;
        public ViewHolder(View itemView) {
            super(itemView);
            layout_left = (LinearLayout)itemView.findViewById(R.id.layout_left);
            text_left = (BubbleTextView)itemView.findViewById(R.id.text_left);
            layout_right = (LinearLayout)itemView.findViewById(R.id.layout_right);
            text_right = (BubbleTextView)itemView.findViewById(R.id.text_right);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        if(msg.getType() == Msg.TYPE_RECEIVE){
            holder.layout_left.setVisibility(View.VISIBLE);
            holder.layout_right.setVisibility(View.GONE);
            holder.text_left.setText(msg.getContent());
        }
        else if(msg.getType() == Msg.TYPE_SEND){
            holder.layout_right.setVisibility(View.VISIBLE);
            holder.layout_left.setVisibility(View.GONE);
            holder.text_right.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
