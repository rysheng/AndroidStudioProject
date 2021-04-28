package com.example.dogapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.model.DogBreed;
import com.example.dogapp.view.ListFragmentDirections;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.view.View.OnClickListener;
import static android.view.View.OnLongClickListener;

public class DogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static  Context mContext;
    private static  ArrayList<DogBreed> dogBreeds;
    private static ArrayList<DogBreed> mdogBreeds;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    public DogAdapter(ArrayList<DogBreed> dogBreeds, Context mContext){
        this.dogBreeds = dogBreeds;
        this.mContext = mContext;
        this.mdogBreeds = new ArrayList<>(dogBreeds);
    }
    @NonNull

    @Override
    public int getItemViewType(int position) {
        if(dogBreeds.get(position).isShowMenu()){
            return SHOW_MENU;
        }else{
            return HIDE_MENU;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(viewType==SHOW_MENU){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_item_detail, parent, false);
            return new MenuViewHolder(v);
        }else{
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_item, parent, false);
            return new MyViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            ((MyViewHolder) holder).name_dog.setText(dogBreeds.get(position).getName());
            ((MyViewHolder) holder).origin_dog.setText("Origin: " + ((dogBreeds.get(position).getOrigin() == null ||
                    (dogBreeds.get(position).getOrigin().toString().isEmpty()))
                    ? "Unknown" : dogBreeds.get(position).getOrigin()));
            ((MyViewHolder) holder).lifespan_dog.setText("Life_span: " + dogBreeds.get(position).getLifeSpan());
            Picasso.get().load(dogBreeds.get(position).getUrl()).placeholder(R.drawable.progress_animation).
                    resize(180, 180).centerCrop().into(((MyViewHolder) holder).img_dog);
            ((MyViewHolder) holder).dogs_item.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showMenu(position);
                    return true;
                }
            });
        }

        if(holder instanceof MenuViewHolder){
            //Menu Actions
            ((MenuViewHolder) holder).name_dog_detail.setText(dogBreeds.get(position).getName());
            ((MenuViewHolder) holder).bred_for_dog_detail.setText("Bred_for: " + dogBreeds.get(position).getBred_for());
            ((MenuViewHolder) holder).breed_group_dog_detail.setText("Breed_group: " + dogBreeds.get(position).getBreed_group());
            ((MenuViewHolder) holder).temperament_dog_detail.setText("Temperament: "+dogBreeds.get(position).getTemperament());
            ((MenuViewHolder) holder).dogs_item_detail.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    closeMenu();
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dogBreeds.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public View view;
        private LinearLayout dogs_item;
        private ImageView img_dog;
        private TextView name_dog;
        private TextView origin_dog;
        private TextView lifespan_dog;
        public MyViewHolder(View view){
            super(view);
            this.view = view;
            img_dog = view.findViewById(R.id.img_dog);
            name_dog = view.findViewById(R.id.name_dog);
            origin_dog = view.findViewById(R.id.origin_dog);
            lifespan_dog = view.findViewById(R.id.lifespan_dog);
            dogs_item = view.findViewById(R.id.dogs_item);
            dogs_item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavDirections action = ListFragmentDirections.actionListToDetails();
                    Navigation.findNavController(view).navigate(R.id.action_list_to_details);
                }
            });
        }
    }
    //Our menu view
    public class MenuViewHolder extends RecyclerView.ViewHolder{
        public View view;
        private LinearLayout dogs_item_detail;
        private TextView name_dog_detail;
        private TextView bred_for_dog_detail;
        private TextView breed_group_dog_detail;
        private TextView temperament_dog_detail;
        public MenuViewHolder(View view){
            super(view);
            this.view = view;
            name_dog_detail = view.findViewById(R.id.name_dog_detail);
            bred_for_dog_detail = view.findViewById(R.id.bred_for_dog_detail);
            breed_group_dog_detail = view.findViewById(R.id.breed_group_dog_detail);
            dogs_item_detail = view.findViewById(R.id.dogs_item_detail);
            temperament_dog_detail = view.findViewById(R.id.temperament_dog_detail);
        }
    }

    public void showMenu(int position) {
        for(int i=0; i<dogBreeds.size(); i++){
            dogBreeds.get(i).setShowMenu(false);
        }
        dogBreeds.get(position).setShowMenu(true);
        notifyDataSetChanged();
    }


    public boolean isMenuShown() {
        for(int i=0; i<dogBreeds.size(); i++){
            if(dogBreeds.get(i).isShowMenu()){
                return true;
            }
        }
        return false;
    }

    public void closeMenu() {
        for(int i=0; i<dogBreeds.size(); i++){
            dogBreeds.get(i).setShowMenu(false);
        }
        notifyDataSetChanged();
    }

}


