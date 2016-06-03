package com.example.ismaelgwen.foodtruckfinder;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AllTrucks extends AppCompatActivity {


    ListView lv;
    Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trucks);

       int [] prgmImages={R.drawable.ic_menu_camera, R.drawable.download, R.drawable.compass};
        String[] prgmNameList={"Placeholder", "Example 2", "Example 3"};

        context=this;

        lv=(ListView) findViewById(R.id.truckList);
        lv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(AllTrucks.this, TruckDetail.class);
                startActivity(i);
            }
        });


    }

}
