package com.example.zzzch.sensordata_vis.Custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class MyppbAxisValueFormatter implements IAxisValueFormatter
{
    private DecimalFormat ppb_Format;

    public MyppbAxisValueFormatter(){
        ppb_Format = new DecimalFormat("###,##0.0");
    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return ppb_Format.format(value) + "ppb";
    }
}