package com.example.jerome.projettut;

import android.app.Activity;
import android.content.Intent;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple XYPlot
 */
public class SimpleXYPlotActivity extends Activity
{

    private XYPlot plot;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphique_puissance);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plot);

        ArrayList<Double> listDouble = (ArrayList<Double>) getIntent().getSerializableExtra("ARR");
        Double [] list = new Double[listDouble.size()];
        for (int i = 0; i < listDouble.size(); i++){
            list[i] = listDouble.get(i).doubleValue();
        }
         
        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(list),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Puissance");


        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_labels_2);


        //new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Uniform));


        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
        
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 3);


        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);

        // rotate domain labels 45 degrees to make them more compact horizontally:
        plot.getGraphWidget().setDomainLabelOrientation(-45);

    }
}