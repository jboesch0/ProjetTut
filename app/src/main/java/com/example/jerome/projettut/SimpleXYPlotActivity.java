package com.example.jerome.projettut;

import android.app.Activity;
import android.os.Bundle;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SimpleXYPlotActivity extends Activity
{

    private XYPlot plot;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphique_puissance);
        plot = (XYPlot) findViewById(R.id.plot);
        ArrayList<Double> listDouble = (ArrayList<Double>) getIntent().getSerializableExtra("ARR");
        Double [] listPuiss = new Double[listDouble.size()];

        for (int i = 0; i < listDouble.size(); i++){
            listPuiss[i] = listDouble.get(i).doubleValue();
        }

        XYSeries series1 = new SimpleXYSeries(Arrays.asList(listPuiss),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Puissance");

        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.getLinePaint().setLinearText(false);
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_labels_2);

        plot.addSeries(series1, series1Format);
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 3);
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);

    }
}