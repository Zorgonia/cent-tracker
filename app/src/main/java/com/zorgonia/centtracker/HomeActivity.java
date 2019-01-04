package com.zorgonia.centtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * The home activity for the app
 */
public class HomeActivity extends AppCompatActivity {
    ArrayList<String> centList;
    CentStorage centStorage;
    ArrayList<TextView> counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        centStorage = new CentStorage();

        counts = new ArrayList<>();
        TextView temp = findViewById(R.id.countmt);
        counts.add(temp);
        temp = findViewById(R.id.countmo);
        counts.add(temp);
        temp = findViewById(R.id.countz);
        counts.add(temp);
        temp = findViewById(R.id.countpo);
        counts.add(temp);
        temp = findViewById(R.id.countpt);
        counts.add(temp);

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
                if (type == 1) {
                    centStorage.increment(position);
                } else if (type == -1) {
                    if (!centStorage.decrement(position)) {
                        makeInvalidToast(position);
                    }
                }
                makeToast(position, centStorage.getElementAt(position));
                updateText(position);
            }
        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Make a toast saying there's count count at position position
     * @param position the position to say
     * @param count the count at said position
     */
    private void makeToast(int position, int count) {
        Toast.makeText(this, "Position " + Integer.toString(position) + " has a count of " + Integer.toString(count), Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast to the user that the count is already 0 at position position
     * @param position the position which count is 0
     */
    private void makeInvalidToast(int position) {
        Toast.makeText(this, "Cannot decrement element in position " + Integer.toString(position), Toast.LENGTH_SHORT).show();
    }

    /**
     * Update the text for the chart at position position
     * @param position the position to update the text at
     */
    public void updateText(int position) {
        counts.get(position).setText(String.format("%s", centStorage.getElementAt(position)));

    }
}
