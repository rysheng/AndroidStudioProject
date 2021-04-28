package com.example.dogapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dogapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;

public class MyNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DogBreed> mainList;
    private List<DogBreed> allMainList;
    private Context context;
    private ItemClickListener listener;
    private final int SHOW_MENU=1;
    private final int HIDE_MENU=2;
    public MyNewAdapter(List<DogBreed> list){
        this.mainList=list;
        this.allMainList=new ArrayList<>();
        this.allMainList.addAll(list);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(viewType==SHOW_MENU){
            v=(View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dog_detail,parent,false);
            return new MenuViewHolder(v);
        }
        else{
            v=(View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_dog,parent,false);
            return new MyViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mainList.get(position).isShowMenu()) return SHOW_MENU;
        return HIDE_MENU;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            ((MyViewHolder)holder).tvName.setText(mainList.get(position).getName());
            ((MyViewHolder)holder).tvLife.setText(mainList.get(position).getLifeSpan());
            ((MyViewHolder)holder).tvOrigin.setText(mainList.get(position).getOrigin());
//        Picasso.with(context).load(mainList.get(position).getUrl()).into(holder.imgAva);
            Glide.with(context)
                    .load(mainList.get(position).getUrl())
                    .into(((MyViewHolder)holder).imgAva);
            ((MyViewHolder)holder).llMain.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showMenu(position);
                    return true;
                }
            });
        }
        if(holder instanceof MenuViewHolder){
            ((MenuViewHolder)holder).tvName.setText(mainList.get(position).getName());
            ((MenuViewHolder)holder).tvLife.setText(mainList.get(position).getLifeSpan());
            ((MenuViewHolder)holder).tvOrigin.setText(mainList.get(position).getOrigin());
            ((MenuViewHolder)holder).tvBredFor.setText(mainList.get(position).getBred_for());
            ((MenuViewHolder)holder).tvBreedGroup.setText(mainList.get(position).getBreed_group());
            ((MenuViewHolder)holder).tvTemp.setText(mainList.get(position).getTemperament());
        }
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }
    public void setOnItemClick(ItemClickListener listener){
        this.listener=listener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public TextView tvName,tvLife,tvOrigin;
        public ImageView imgAva;
        public CardView llMain;
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
    public class MenuViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView tvName,tvOrigin,tvLife,tvBredFor,tvBreedGroup,tvTemp;
        public MenuViewHolder(@NonNull View v) {
            super(v);
            view=v;
            context=v.getContext();
            tvName=v.findViewById(R.id.tv_name);
            tvLife=v.findViewById(R.id.tv_life_span);
            tvOrigin=v.findViewById(R.id.tv_origin);
            tvBredFor=v.findViewById(R.id.tv_bred_for);
            tvBreedGroup=v.findViewById(R.id.tv_breed_group);
            tvTemp=v.findViewById(R.id.tv_temperament);
        }
    }
    public void showMenu(int position){
        for(int i=0;i<mainList.size();++i){
            mainList.get(i).setShowMenu(false);
        }
        mainList.get(position).setShowMenu(true);
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
        notifyDataSetChanged();
    }

    // filter
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String text=charSequence.toString().toLowerCase().trim();
                if(text.isEmpty()){
                    mainList=allMainList;
                }
                else{
                    List<DogBreed> filteredList=new ArrayList<>();
                    for(DogBreed curDog:allMainList){
                        if(curDog.getName().toLowerCase().trim().contains(text)) {
                            filteredList.add(curDog);
                        }
                    }
                    mainList=filteredList;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=mainList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mainList= (List<DogBreed>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
