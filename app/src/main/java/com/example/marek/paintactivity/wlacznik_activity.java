package com.example.marek.paintactivity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;



public class wlacznik_activity extends ActionBarActivity {
    ImageView img;
    int b = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlacznik_activity);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wlacznik_activity, menu);
        img =(ImageView)findViewById(R.id.imageView);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void on(View view) {
        if (Bluetooth.connectedThread != null) {
            Bluetooth.connectedThread.write(1);
        };
    }

    public void wlacznik(View view) {

        if (Bluetooth.connectedThread != null) {
            Bluetooth.connectedThread.write(1);
        }
            if(b==0){
                img.setImageResource(numerObrazu[0]);

            }
            if(b==1) {
                img.setImageResource(numerObrazu[1]);

            }
            b++;
            if(b>=2)b=0;



    }
    public Integer[] numerObrazu={
            R.mipmap.off,
            R.mipmap.on,
    };
}
