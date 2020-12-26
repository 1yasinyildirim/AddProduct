package com.yasin.addproduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CAdapter extends RecyclerView.Adapter<CAdapter.MyHold>
{
    Context context;
    List<ModClass> lists;

    public CAdapter(Context context, List<ModClass> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public CAdapter.MyHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_files,parent,false);

        return new MyHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CAdapter.MyHold holder, int position) {

        holder.textview.setText(lists.get(position).getImagename());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyHold extends RecyclerView.ViewHolder
    {
        TextView textview;
        public MyHold(@NonNull View itemView)
        {
            super(itemView);
            textview = itemView.findViewById(R.id.filename);
        }
    }
}