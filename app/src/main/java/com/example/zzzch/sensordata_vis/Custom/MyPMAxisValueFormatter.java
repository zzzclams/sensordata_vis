package com.example.zzzch.sensordata_vis.Custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class MyPMAxisValueFormatter implements IAxisValueFormatter
{
    private DecimalFormat PM_Format;

    public MyPMAxisValueFormatter(){
        PM_Format = new DecimalFormat("##0.0");
    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return PM_Format.format(value) + "ug/m\u00B3";
    }
}
