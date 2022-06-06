package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<firebasemodel> firebasemodelArrayList;
    public static final String TITLE = "com.example.MultiScreenApp.extra";
    public static final String CONTENT = "com.example.MultiScreenApp.extra.NAME";

    public MyAdapter(Context context, ArrayList<firebasemodel> firebasemodelArrayList) {
        this.context = context;
        this.firebasemodelArrayList = firebasemodelArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notes_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        firebasemodel model = firebasemodelArrayList.get(position);

        holder.notetitle.setText(model.getTitle());
        holder.notecontent.setText(model.getContent());

//        ----------------------------------------------------------------------------------------------------------------------------

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                this will open the details of the note
                Intent intent = new Intent(view.getContext(), EditNoteActivity.class);
                firebasemodel firebasemodel = new firebasemodel();
                intent.putExtra(TITLE, firebasemodelArrayList.get(holder.getBindingAdapterPosition()).getTitle());
                intent.putExtra(CONTENT, firebasemodelArrayList.get(holder.getBindingAdapterPosition()).getContent());
                view.getContext().startActivity(intent);
//                intent.putExtra("noteId", docId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return firebasemodelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView  notetitle, notecontent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notecontent = itemView.findViewById(R.id.notecontent);
            notetitle = itemView.findViewById(R.id.notetitle);
        }
    }
}
