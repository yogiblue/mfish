package com.muwuprojects.mfish;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HelpActivity extends Activity {

    int which;
    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setTitle("About");

        Button myButton = (Button) findViewById(R.id.help_ok);
        TextView myText = (TextView) findViewById(R.id.help_text);


        Bundle b = getIntent().getExtras();

        which = b.getInt("which");
        //which =-1;

        myText.setText("");


        myButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        TextView myText = (TextView) findViewById(R.id.help_text);
        View areaView = (View) findViewById(android.R.id.content);

        myText.setWidth(80*areaView.getWidth()/100);

        if(hasFocus==true)
        {
            if(which==-1)
            {
                myText.setText("Welcome" +
                        "\n\nThis app basically a fish" +
                        "\n\nKeeping coming back or it will turn into bones. " +
                        "\n\nAny feedback is most welcome, so please do get in touch." +
                        "\n\nAll rights reserved, 2017. v1.01" +
                        "\n\n");
            }
            else if(which==2)
            {
                myText.setText("You called me from xml dude pup");
            }
            else
            {
                //messageObject myMessage = db.getMessageObject(which);
                //myText.setText(myMessage.getExtra());
                myText.setText("Get the extra bits and pieces - you know, the goodies");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.help, menu);
        return true;
    }

}
