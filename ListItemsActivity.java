package com.muwuprojects.mfish;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class ListItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        setTitle("Select a practice");
        ListView lv = (ListView) findViewById(R.id.list_view);


        setupActionBar();

        String names = "Check in,Body scan,Pleasantness or not,Mood scan,Five hindrances,Five aggregates,Seven factors of awakening,Craving,Clinging,Cessation,Calming";
        List<String> itemArray_text = Arrays.asList(names.split(","));
        lv.setAdapter(new TextListAdaptor(this, itemArray_text));
        
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setSelector(R.drawable.selector);

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
