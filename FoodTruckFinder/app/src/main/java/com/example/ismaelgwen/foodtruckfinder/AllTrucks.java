package com.example.ismaelgwen.foodtruckfinder;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class AllTrucks extends AppCompatActivity {


    ListView lv;
    Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trucks);

       int [] prgmImages={R.drawable.ic_menu_camera};
        String[] prgmNameList={"Placeholder"};

        context=this;

        lv=(ListView) findViewById(R.id.truckList);
        lv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));


    }

}
