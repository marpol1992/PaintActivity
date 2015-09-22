package com.example.marek.paintactivity;

import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class PaintActivity extends ActionBarActivity
{
    MenuItem bluetoothStatusItem;
    boolean status;
    Handler lHandler;
    {
        lHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case Bluetooth.SUCCESS_CONNECT:
                        Toast.makeText(getApplicationContext(), "Connected:D", Toast.LENGTH_SHORT).show();
                        if (Bluetooth.connectedThread != null){
                            bluetoothStatusItem.setIcon(R.drawable.ic_bluetooth_connected_black_24dp);
                        }
                        break;
                    case Bluetooth.DISCONECT:
                        bluetoothStatusItem.setIcon(R.drawable.ic_bluetooth_disabled_black_24dp);
                        Toast.makeText(getApplicationContext(), "Disconnect:(", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, 1);
        ProcessingFrame.get_handler(lHandler);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


    }

  /*  @Override
    public void onResume() {
        super.onResume();

    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_paint, menu);
        bluetoothStatusItem = menu.findItem(R.id.status_connected);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()){
             //noinspection SimplifiableIfStatement
            case R.id.connect:{
                                startActivity(new Intent("android.intent.action.BT2"));
            // startActivity(new Intent(this,bluetooth.class));
            }
            case R.id.disconnect: {
                                Bluetooth.disconnect();
                status = true;
            //Bluetooth.ConnectThread.cancel();
             }
            case R.id.status_connected: {
                status = true;
            }

            default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void sin(View view) {
        Intent intent = new Intent(this,sinActivity.class);
        startActivity(intent);
    }

    public void realtime(View view) {
        Intent intent = new Intent(this,realtime.class);
        startActivity(intent);
    }

    public void wlacznik(View view) {
        Intent intent = new Intent(this,wlacznik_activity.class);
        startActivity(intent);
    }
}
