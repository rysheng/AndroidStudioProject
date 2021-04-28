package com.example.dogapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.dogapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private List<DogBreed> mainList;
    private Context context;
    private ItemClickListener listener;
    private final int SHOW_MENU=1;
    private final int HIDE_MENU=2;
    public MyAdapter(List<DogBreed> list){
        this.mainList=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=(View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dog,parent,false);
        MyViewHolder vh=new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(mainList.get(position).getName());
        holder.tvLife.setText(mainList.get(position).getLifeSpan());
        holder.tvOrigin.setText(mainList.get(position).getOrigin());
//        Picasso.with(context).load(mainList.get(position).getUrl()).into(holder.imgAva);
        Glide.with(context)
                .load(mainList.get(position).getUrl())
                .into(holder.imgAva);

    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView tvName,tvLife,tvOrigin;
        public ImageView imgAva;
        public LinearLayout llMain;
        public MyViewHolder(View v) {
            super(v);
            view=v;
            context=v.getContext();
            llMain=view.findViewById(R.id.ll_main);
            tvName=view.findViewById(R.id.tv_name);
            tvLife=view.findViewById(R.id.tv_life_span);
            tvOrigin=view.findViewById(R.id.tv_origin);
            imgAva=view.findViewById(R.id.img_ava);
            llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onClick(getAdapterPosition());
                    }
                }
            });
        }
    }
    public void setOnItemClick(ItemClickListener listener){
        this.listener=listener;
    }
    public void showMenu(int pos){
        for(int i=0;i<mainList.size();++i){
            mainList.get(i).setShowMenu(false);
        }
        mainList.get(pos).setShowMenu(true);
        notifyDataSetChanged();
    }
    public boolean isMenuShown(){
        for(int i=0;i<mainList.size();++i){
            if(mainList.get(i).isShowMenu()){
                return true;
            }
        }
        return false;
    }
    public void closeMenu(){
        for(int i=0;i<mainList.size();++i){
            mainList.get(i).setShowMenu(false);
        }
    }
}