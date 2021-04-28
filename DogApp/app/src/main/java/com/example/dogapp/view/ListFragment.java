package com.example.dogapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dogapp.ItemClickListener;
import com.example.dogapp.MyNewAdapter;
import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;
import com.example.dogapp.viewmodel.DogsApiService;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListFragment extends Fragment {
    private RecyclerView rv_list;
    private ArrayList<DogBreed> mainList=new ArrayList<>();
    private MyNewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private View v;
    DogsApiService dogsApiService;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_list, container, false);
        setHasOptionsMenu(true);
        context=v.getContext();
        rv_list=(RecyclerView)v.findViewById(R.id.rv_list);
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipeLayout);
        rv_list.setLayoutManager(new GridLayoutManager(context,2));
        mainList = new ArrayList<DogBreed>();
        adapter = new MyNewAdapter(mainList);
        rv_list.setAdapter(adapter);
        dogsApiService=new DogsApiService();
        // update();
        adapter.setOnItemClick(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle=new Bundle();
                DogBreed curDog=mainList.get(position);
                bundle.putString("name",curDog.getName());
                bundle.putString("life",curDog.getLifeSpan());
                bundle.putString("origin",curDog.getOrigin());
                bundle.putString("url",curDog.getUrl());
                bundle.putString("bred_for",curDog.getBred_for());
                bundle.putString("breed_group",curDog.getBreed_group());
                bundle.putString("temperament",curDog.getTemperament());
                Navigation.findNavController(v).navigate(R.id.action_list_to_details,bundle);
            }
        });
        ItemTouchHelper.SimpleCallback touchHelperCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.background));
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(mainList.get(viewHolder.getAdapterPosition()).isShowMenu()==false){
                    adapter.showMenu(viewHolder.getAdapterPosition());
                }
                else{
                    adapter.closeMenu();
                }

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView=viewHolder.itemView;
                if (dX > 0) {
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX < 0) {
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(rv_list);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainList.clear();
                update();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search,menu);
        MenuItem mSearch=menu.findItem(R.id.searchBar);
        SearchView searchView=(SearchView)mSearch.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    public void update(){
        dogsApiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<DogBreed>>() {
                    @Override
                    public void onSuccess(ArrayList<DogBreed> value) {
                        for(DogBreed x:value){
                            mainList.add(x);
                        }
                        adapter=new MyNewAdapter(mainList);
                        rv_list.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}