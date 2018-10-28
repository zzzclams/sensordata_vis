package com.example.zzzch.sensordata_vis.Custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class MyTEMAxisValueFormatter implements IAxisValueFormatter
{
    private DecimalFormat Tem_Format;

    public MyTEMAxisValueFormatter(){
        Tem_Format = new DecimalFormat("##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return Tem_Format.format(value) + "\u2103";
    }
}
