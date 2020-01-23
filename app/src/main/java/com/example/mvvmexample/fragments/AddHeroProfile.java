package com.example.mvvmexample.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mvvmexample.MainActivity;
import com.example.mvvmexample.R;
import com.example.mvvmexample.db.SQLiteHelper;
import com.example.mvvmexample.models.Hero;
import static com.example.mvvmexample.Utils.*;


public class AddHeroProfile extends Fragment {
    public static final String TAG = AddHeroProfile.class.getSimpleName();
    EditText name, realname, firstAppearance, createdby, publisher;
    ImageView heroProfileImage;
    Button submitbtn;
    boolean update;
    Hero hero;
    String imgPath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public AddHeroProfile(Hero hero) {
        this.hero = hero;
        update = true;
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
       // getActivity().getMenuInflater().inflate(R.menu.details_fragment_menu,menu);
       menu.clear();
       MainActivity mainActivity = (MainActivity) getActivity();
       ActionBar actionBar = mainActivity.getSupportActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "onPrepareOptionsMenu: "+actionBar);
        super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public AddHeroProfile() {
        this.update = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_hero_details, null);
        name = view.findViewById(R.id.et_name);
        submitbtn = view.findViewById(R.id.submit);
        realname = view.findViewById(R.id.et_realname);
        createdby = view.findViewById(R.id.et_created_by);
        firstAppearance = view.findViewById(R.id.et_firstapp);
        publisher = view.findViewById(R.id.et_publisher);
        heroProfileImage = view.findViewById(R.id.upload_image);
        heroProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_PICK);
                i.setType("image/*");
               startActivityForResult(i,1);
                Log.d(TAG, "onClick: ");
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });
        if (update) {
            name.setText(hero.getName());
            realname.setText(hero.getRealName());
            createdby.setText(hero.getCreatedBy());
            publisher.setText(hero.getPublisher());
            firstAppearance.setText(hero.getFirstAppearance());
            //name.setText(hero.getName());
        }
        return view;
    }

    public void onSubmit() {
        String s = "";
        Log.d(TAG, "onSubmit: ");
        Log.d(NAME, name.getText().toString());
        if (name.getText().toString().equals("")) {
            s += getString(R.string.hero_name) + "\t";
        }
        if (realname.getText().toString().equals("")) {
            s += getString(R.string.realname) + "\t";
        }
        if (publisher.getText().toString().equals("")) {
            s += getString(R.string.publisher) + "\t";
        }
        if (createdby.getText().toString().equals("")) {
            s += getString(R.string.createdBy);

        }
        if (s.equals("")) {
            s = "data successfully uploaded";
            createData();
            closeFragment();
        } else {
            s += " invalid fields";
        }
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    public void createData() {
        SQLiteHelper helper = new SQLiteHelper(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name.getText().toString().trim());
        values.put(REALNAME, realname.getText().toString().trim());
        values.put(CREATEDBY, createdby.getText().toString().trim());
        values.put(FIRST_APP, firstAppearance.getText().toString().trim());
        values.put(PUBLISHER, publisher.getText().toString().trim());
        values.put(URL, imgPath);
        if (update) {
            db.update(TABLE_NAME, values, "id=?",
                    new String[]{String.valueOf(
                            hero.getId())});
        } else {
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    @Override
    public String toString() {
        return AddHeroProfile.class.getSimpleName();
    }

    public void closeFragment() {
        Log.d(TAG, "count" + getFragmentManager().getBackStackEntryCount());
        getFragmentManager().popBackStack();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "onActivityResult: " + requestCode + "result "+ requestCode );
        super.onActivityResult(requestCode, resultCode, data);
      if (data!=null) {
          Glide.with(this).load(data.getData())
                  .placeholder(R.drawable.defaultprofile)
                  .into(heroProfileImage);
          imgPath= data.getDataString();
          Log.d(TAG, "onActivityResult: "+data.getDataString());

      }
        Log.d(TAG, "onActivityResult: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity mainActivity = (MainActivity) getActivity();
        ActionBar actionBar = mainActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }
}
