package com.example.ex11022_internalfiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {
    private EditText eT;
    private TextView tV;
    private final String FILENAME = "inttest.txt";

    /**
     * Initializes the activity, links UI components, and reads data from the file to display.
     *
     * @param savedInstanceState Saved instance state for restoring previous data if applicable.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eT = findViewById(R.id.eT);
        tV = findViewById(R.id.tV);

        String text = getTextFromFile();
        tV.setText(text);
    }

    /**
     * Handles the Save button click event. Appends the text from the EditText field to the file
     * and updates the TextView with the new content.
     *
     * @param view The view that triggered the method.
     */
    public void saveclick(View view) {
        try {
            FileOutputStream fOS = openFileOutput(FILENAME, MODE_APPEND);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);

            bW.write(eT.getText().toString());
            bW.close();
            oSW.close();
            fOS.close();

            tV.setText(getTextFromFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the content of the file and returns it as a string.
     *
     * @return The text content of the file.
     */
    private String getTextFromFile() {
        StringBuilder sB = new StringBuilder();
        try {
            FileInputStream fIS = openFileInput(FILENAME);
            InputStreamReader iSR = new InputStreamReader(fIS);
            BufferedReader bR = new BufferedReader(iSR);

            String line;
            while ((line = bR.readLine()) != null) {
                sB.append(line).append('\n');
            }
            bR.close();
            iSR.close();
            fIS.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sB.toString();
    }

    /**
     * Handles the Reset button click event. Clears the content of the file and resets the UI.
     *
     * @param view The view that triggered the method.
     */
    public void resetClick(View view) {
        try {
            FileOutputStream fOS = openFileOutput(FILENAME, MODE_PRIVATE);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);

            bW.write(""); // Clearing file content
            bW.close();
            oSW.close();
            fOS.close();

            eT.setText("");
            tV.setText("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the Exit button click event. Saves the current text and closes the application.
     *
     * @param view The view that triggered the method.
     */
    public void exitClick(View view) {
        saveclick(view);
        finish();
    }

    /**
     * Inflates the options menu.
     *
     * @param menu The options menu.
     * @return true if the menu is successfully created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles item selections from the options menu. If "credits" is selected, it opens the Credits activity.
     *
     * @param menuItem The selected menu item.
     * @return true if the action is handled successfully.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if ("credits".equals(menuItem.getTitle().toString())) {
            Intent si = new Intent(this, Credits.class);
            startActivity(si);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
