package com.example.zzzch.sensordata_vis.Custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class MyppmAxisValueFormatter implements IAxisValueFormatter
{
    private DecimalFormat ppm_Format;

    public MyppmAxisValueFormatter(){
        ppm_Format = new DecimalFormat("###,##0.0");
    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return ppm_Format.format(value) + "ppm";
    }
}
