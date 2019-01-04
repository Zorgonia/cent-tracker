package com.zorgonia.centtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<String> centList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        centList = new ArrayList<>();
        centList.add("-2");
        centList.add("-1");
        centList.add("0");
        centList.add("1");
        centList.add("2");
        RecyclerView rv = findViewById(R.id.CentRecycler);
        RecyclerAdapter adapter = new RecyclerAdapter(centList);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, int type) {
                makeToast(position, type);
            }
        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void makeToast(int toShow, int type) {
        Toast.makeText(this, Integer.toString(toShow) + " " +Integer.toString(type), Toast.LENGTH_SHORT).show();
    }
}
