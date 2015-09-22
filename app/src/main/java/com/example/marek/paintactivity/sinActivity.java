package com.example.marek.paintactivity;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class sinActivity extends ActionBarActivity {
    GraphView graph;
    int i, tam = 200;
    double v = 0;
    DataPoint[] data= new DataPoint[tam] ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sin, menu);
        for(i = 0; i < tam; i++) {
            v += 0.3;
            data[i] = new DataPoint(i, Math.sin(v));
        }
        // GraphViewSeries seriesSeno = new GraphViewSeries("Seno", new GraphViewSeriesStyle(Color.BLUE, 3), data);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);
        graph = (GraphView) findViewById(R.id.graph);
       /* LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
                new DataPoint(5, 7),
                new DataPoint(20, 20)
        });*/

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(20);

// set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-1);
        graph.getViewport().setMaxY(1);
        // graph.getViewport().setBackgroundColor(Color.BLACK);
        graph.setTitle("Wykres czegos");
        graph.setTitleColor(Color.CYAN);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLUE);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLUE);
        graph.getGridLabelRenderer().setGridColor(Color.WHITE);

        graph.addSeries(series);
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
}
