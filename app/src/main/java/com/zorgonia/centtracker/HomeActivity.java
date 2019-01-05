package com.zorgonia.centtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * The home activity for the app
 */
public class HomeActivity extends AppCompatActivity {
    ArrayList<String> centList;
    CentStorage centStorage;
    ArrayList<TextView> counts;
    TextView blurb;
    final String SAVE_FILE_NAME = "centsave.ser";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        centStorage = new CentStorage();
        loadFromFile(SAVE_FILE_NAME);


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
        blurb = findViewById(R.id.display);

        resetDisplay();

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
                //makeToast(position, centStorage.getElementAt(position));
                resetDisplay();
                saveToFile(SAVE_FILE_NAME);
            }
        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Make a toast saying there's count count at position position
     *
     * @param position the position to say
     * @param count    the count at said position
     */
    private void makeToast(int position, int count) {
        Toast.makeText(this, "Position " + Integer.toString(position) + " has a count of " + Integer.toString(count), Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast to the user that the count is already 0 at position position
     *
     * @param position the position which count is 0
     */
    private void makeInvalidToast(int position) {
        Toast.makeText(this, "Cannot decrement element in position " + Integer.toString(position), Toast.LENGTH_SHORT).show();
    }

    /**
     * Update the text for the chart at position position
     *
     * @param position the position to update the text at
     */
    public void updateText(int position) {
        counts.get(position).setText(String.format("%s", centStorage.getElementAt(position)));
        int centDiff = centStorage.totalCents();
        boolean check = false;
        if (centDiff < 0) {
            centDiff = -1 * centDiff;
            check = true;
        } else if (centDiff == 0) {
            blurb.setText(R.string.even);
            return;
        }
        int dollars = centDiff / 100;
        int cents = centDiff % 100;
        if (check) {
            if (cents < 10) {
                blurb.setText(String.format("You've saved $%s.0%s", dollars, cents));
            } else {
                blurb.setText(String.format("You've saved $%s.%s", dollars, cents));
            }
        } else {
            if (cents < 10) {
                blurb.setText(String.format("You've lost $%s.0%s", dollars, cents));
            } else {
                blurb.setText(String.format("You've lost $%s.%s", dollars, cents));
            }
        }
    }

    /**
     * Saves to a file with name fileName
     *
     * @param fileName the file to save to
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(centStorage);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                centStorage = (CentStorage) input.readObject();
                inputStream.close();
            }
            //resetDisplay();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        } finally {
            Log.e("login activity", centStorage.cents.toString());
        }
    }

    private void resetDisplay() {
        for (int i = 0; i != 5; i++) {
            updateText(i);
        }
    }
}
