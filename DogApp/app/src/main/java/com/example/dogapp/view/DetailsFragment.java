package com.example.dogapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.dogapp.R;

public class DetailsFragment extends Fragment {
    private View v;
    private FrameLayout flMain;
    private TextView tvName,tvLife,tvOrigin,tvBred,tvBreed,tvTemp;
    private ImageView imgAva;
    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_details, container, false);
        flMain=v.findViewById(R.id.fl_main);
        Bundle bundle=getArguments();
        String name=bundle.getString("name");
        if(name==null) name="Không rõ";
        String life=bundle.getString("life");
        if(life==null) life="Không rõ";
        String origin=bundle.getString("origin");
        if(origin==null) origin="Không rõ";
        String url=bundle.getString("url");
        String bred_for=bundle.getString("bred_for");
        if(bred_for==null) bred_for="Không rõ";
        String breed_group=bundle.getString("breed_group");
        if(breed_group==null) breed_group="Không rõ";
        String temperament=bundle.getString("temperament");
        if(temperament==null) temperament="Không rõ";
        tvName=(TextView)v.findViewById(R.id.tv_name);
        tvLife=(TextView)v.findViewById(R.id.tv_life_span);
        tvOrigin=(TextView)v.findViewById(R.id.tv_origin);
        tvBred=(TextView)v.findViewById(R.id.tv_bred_for);
        tvBreed=(TextView)v.findViewById(R.id.tv_breed_group);
        tvTemp=(TextView)v.findViewById(R.id.tv_temperament);
        imgAva=(ImageView) v.findViewById(R.id.img_ava);
        tvName.setText("Tên : "+name);
        tvLife.setText("Tuổi thọ : "+life);
        tvOrigin.setText("Xuất xứ : "+origin);
        tvBred.setText("Giống lai : "+bred_for);
        tvBreed.setText("Nhóm : "+breed_group);
        tvTemp.setText("Tính cách  : "+temperament);
        Glide.with(v.getContext()).load(url).into(imgAva);
        return v;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Navigation.findNavController(v).navigate(R.id.action_details_to_list);
        return super.onOptionsItemSelected(item);
    }
}