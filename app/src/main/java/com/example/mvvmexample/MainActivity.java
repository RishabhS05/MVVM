package com.example.mvvmexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mvvmexample.fragments.AddHeroProfile;
import com.example.mvvmexample.fragments.AvengersDetails;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AvengersDetails avengersDetails = new AvengersDetails();
        navigateToFragment(avengersDetails);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        switch (item.getItemId()){
            case R.id.menuAdd:
               AddHeroProfile addHeroProfile= new AddHeroProfile();
                navigateToFragment(addHeroProfile);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void navigateToFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .addToBackStack(fragment.toString()).commit();

    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
    }
}
