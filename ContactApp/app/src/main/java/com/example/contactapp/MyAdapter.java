package com.example.contactapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {
    private static ArrayList<Contact>  mConctact;
    private static ArrayList<Contact>  movelistALl;
    private static Context mContext;
    private static int position;

    public MyAdapter(ArrayList<Contact> mConctact, Context mContext) {
        this.mConctact = mConctact;
        this.mContext = mContext;
        this.movelistALl = new ArrayList<>(mConctact);

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(mConctact.get(position).getName());
        holder.tvEmail.setText(mConctact.get(position).getEmail());
    }


    @Override
    public int getItemCount() {
        return mConctact.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Contact> filtered = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filtered.addAll(movelistALl);
            }
            else{
                for(Contact contact: movelistALl){
                    if(contact.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filtered.add(contact);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filtered;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mConctact.clear();
            mConctact.addAll((Collection<? extends Contact>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        private TextView tvName, tvEmail;
        private LinearLayout item_contact, lncontact;
        private ImageView call_img, sms_img, infor_img;
        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            tvEmail = view.findViewById(R.id.tv_email);
            tvName = view.findViewById(R.id.tv_name);
            lncontact = view.findViewById(R.id.ln_contact);
            call_img = view.findViewById(R.id.phone_img);
            sms_img = view.findViewById(R.id.sms_img);
            infor_img = view.findViewById(R.id.info_img);
            item_contact = view.findViewById(R.id.item_contact);
            position = -1;
            lncontact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(item_contact.getVisibility()==View.GONE){
                        item_contact.setVisibility(View.VISIBLE);
                    }
                    else item_contact.setVisibility(View.GONE);
                }
            });

            call_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String phone = mConctact.get(position).getPhone();
                    Intent intent = new Intent(Intent.ACTION_DIAL,
                            Uri.fromParts("tel", phone, null));
                    mContext.startActivity(intent);
                }
            });

            sms_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String nameContact = mConctact.get(position).getPhone();
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.fromParts("sms", nameContact, null));
                    mContext.startActivity(intent);
                }
            });
            infor_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Contact contact = mConctact.get(position);
                    Intent intent= new Intent(mContext,Show_infor.class);
                    intent.putExtra("position", position);
                    intent.putExtra("id", contact.getId());
                    intent.putExtra("name", contact.getName());
                    intent.putExtra("phone", contact.getPhone());
                    intent.putExtra("email", contact.getEmail());
                    intent.putExtra("infor", contact.getInfor());
                    ((Activity) mContext).startActivityForResult(intent, 456);
                }
            });

        }

    }
}
