package com.example.marek.paintactivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.camera2.params.BlackLevelPattern;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.bluetooth.BluetoothSocket;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Random;
import java.util.zip.CRC32;


public class realtime extends ActionBarActivity {

    private final Handler nHandler = new Handler();
    public Runnable nTimer2;
    public LineGraphSeries<DataPoint> nSeries2;
    public double graph2LastXValue = 100d;
    double plus_minus = 0.1;
    long czas = 1;
    static boolean nadawaj = true;
    public TextView read;
    public TextView read1;
    public Button start;
    boolean START_GRAPH = false;
    ProcessingFrame processing_Frame = new ProcessingFrame();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
    Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case Bluetooth.MESSAGE_READ:
                        if (Bluetooth.connectedThread != null)  {
                            processing_Frame.Konstruktor(msg.obj, msg.arg1);
                        }
                        ////////////////////ADC=12bit/////////////////////////
                        read1.setText(Boolean.toString(START_GRAPH));    //Double.toString(round(parse_bytes(),0)))round(rysuj.adc(rysuj.parse_bytes()),3));
                        read.setText(Integer.toString(processing_Frame.Error_count));

                        break;
                    case Bluetooth.DISCONECT:

                }
            }







        };
    }
*/

    public int convertByteToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < b.length; i++) {
            int n = (b[i] < 0 ? (int) b[i] + 256 : (int) b[i]) << (8 * i);
            value += n;
        }
        return value;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);//Hide title
        this.getWindow().setFlags(WindowManager.LayoutParams.
                FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//Hide Status bar
        setContentView(R.layout.activity_realtime);
        read = (TextView) this.findViewById(R.id.textView);
        read1 = (TextView) this.findViewById(R.id.textView2);
        start = (Button) this.findViewById(R.id.button8);
        GraphView graph2 = (GraphView) findViewById(R.id.graph);
        nSeries2 = new LineGraphSeries<DataPoint>();
        nSeries2.setColor(Color.RED);
        graph2.addSeries(nSeries2);
        graph2.getViewport().setScalable(true);
        graph2.getViewport().setScrollable(true);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(100);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setMinY(0);
        graph2.getViewport().setMaxY(3.3);
        graph2.setTitle("Rejestrator napięcia");
        graph2.setTitleColor(Color.BLACK);
        graph2.setTitleTextSize(50);
        graph2.getGridLabelRenderer().setPadding(60);
        graph2.getGridLabelRenderer().setGridColor(Color.RED);
        graph2.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.BOTH);


        //graph2.setShape(nSeries2.Shape.RECTANGLE);
        //graph2.setSize();
        graph2.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
        graph2.getGridLabelRenderer().setHorizontalAxisTitleTextSize(20);
        graph2.getGridLabelRenderer().setVerticalAxisTitleTextSize(20);
        graph2.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        graph2.getGridLabelRenderer().setGridColor(Color.BLACK);
        //Bluetooth.gethandler(mHandler);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        processing_Frame.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_realtime, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        nTimer2 = new Runnable() {
            @Override
            public void run() {

                if ((Bluetooth.connectedThread != null) & (START_GRAPH  == true)){
                        graph2LastXValue += 0.01d;

                }
                nSeries2.appendData(new DataPoint(graph2LastXValue,processing_Frame.point), true, 1000);

                nHandler.postDelayed(this, 1);
            }
        };
        nHandler.postDelayed(nTimer2, 10);
    }

    @Override
    public void onPause() {
        super.onPause();
        if((START_GRAPH  == true) & (Bluetooth.connectedThread != null)) {
             Bluetooth.connectedThread.write(2);
        }
        nHandler.removeCallbacks(nTimer2);
        processing_Frame.SET_THREAD(false);


    }


    public void plus(View view) {

        plus_minus = plus_minus + 0.1;
    }

    public void minus(View view) {
        plus_minus = plus_minus - 0.1;
        if (plus_minus <= 0) {
            plus_minus = 0.1;
        }
    }

    public void time_plus(View view) {
        czas = czas + 100;
        finish();
    }

    public void time_minus(View view) {
        czas = czas - 100;
        if (czas < 0) {
            czas = 1;
        }
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





    public void START(View view) throws InterruptedException {

        if (START_GRAPH  == true) {
            if (Bluetooth.connectedThread != null) {
                Bluetooth.connectedThread.write(2);
            }
            start.setText("START");

        }
        if (START_GRAPH == false) {
            start.setText("STOP");
            if (Bluetooth.connectedThread != null) {
                Bluetooth.connectedThread.write(2);
            }
        }
        START_GRAPH = !START_GRAPH;
    }

    public float round(double f, int places) {
        float temp = (float) (f * (Math.pow(10, places)));

        temp = (Math.round(temp));

        temp = temp / (int) (Math.pow(10, places));

        return temp;

    }
////////////////////////////////////////////////////////////////////////////////
}