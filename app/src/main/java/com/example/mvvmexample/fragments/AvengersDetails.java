package com.example.mvvmexample.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmexample.MyAdapter;
import com.example.mvvmexample.R;
import com.example.mvvmexample.db.SQLiteHelper;
import com.example.mvvmexample.listerners.IClicklistener;
import com.example.mvvmexample.models.Hero;
import com.example.mvvmexample.repos.HerosRepo;
import com.example.mvvmexample.viewModels.AvangersDetailsViewModels;

import java.util.ArrayList;
import java.util.List;

import static com.example.mvvmexample.Utils.*;


public class AvengersDetails extends Fragment implements IClicklistener {
    RecyclerView recyclerView;
    List<Hero> heroes = new ArrayList<>();
    ProgressBar progressBar;
    MyAdapter adapter;
    HerosRepo repo ;
    private AvangersDetailsViewModels detailsViewModels;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag, null);
        recyclerView = view.findViewById(R.id.detail_rv);
        progressBar =view.findViewById(R.id.progress_circular);
        repo = HerosRepo.getInstance();
        getAlldetails();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter = new MyAdapter(repo.getHeroes().getValue(), getActivity(), this);
        recyclerView.setAdapter(adapter);

        detailsViewModels = ViewModelProviders.of(this)
                .get(AvangersDetailsViewModels.class);
        detailsViewModels.init();
        detailsViewModels.getMutableLiveDataHeros()
                .observe(this, new Observer<List<Hero>>() {
                    @Override
                    public void onChanged(List<Hero> heroes) {
                        adapter.notifyDataSetChanged();
//                        recyclerView.smoothScrollToPosition(repo.getHeroes().getValue().size()-1);
                    }
                });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAlldetails();
    }

    public void getAlldetails() {
        heroes.clear();
        SQLiteHelper helper = new SQLiteHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();
        String cols[] = {ID, NAME, REALNAME, FIRST_APP, CREATEDBY, PUBLISHER, URL};
        Cursor cursor = db.query(TABLE_NAME, cols, null, null, null, null, null);
        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
              Hero h =   new Hero(cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(NAME)),
                        cursor.getString(cursor.getColumnIndex(REALNAME)),
                        cursor.getString(cursor.getColumnIndex(FIRST_APP)),
                        cursor.getString(cursor.getColumnIndex(CREATEDBY)),
                        cursor.getString(cursor.getColumnIndex(PUBLISHER)),
                        cursor.getString(cursor.getColumnIndex(URL)));
                heroes.add(h);
                cursor.moveToNext();
            }
            repo.setHeroes(heroes);
        } else {
            Toast.makeText(getActivity(), "no data in db" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String toString() {
        return AvengersDetails.class.getSimpleName();
    }

    @Override
    public void onclick(Hero hero, int flag) {
        if (flag == UPDATE) {
            AddHeroProfile addHeroProfile = new AddHeroProfile(hero);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, addHeroProfile)
                    .addToBackStack("update")
                    .commit();
        } else if (flag == DELETE) {
            delete(hero.getId());
            getAlldetails();
            recyclerView.getAdapter().notifyDataSetChanged();
        }

    }

    public void delete(int id) {
        SQLiteHelper helper = new SQLiteHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{"" + id});
        db.close();
    }
    private void showProgessBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }
}
