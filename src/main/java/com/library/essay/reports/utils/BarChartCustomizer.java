package com.library.essay.reports.utils;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

public class BarChartCustomizer implements JRChartCustomizer {

  @Override
  public void customize(JFreeChart jfchart, JRChart jrchart) {
    CategoryPlot plot = (CategoryPlot) jfchart.getPlot();
    // Set at what value you like the line, its color and size of stroke
    ValueMarker vm = new ValueMarker(2, Color.GRAY, new BasicStroke(2.0F));
    // add marker to plot
    plot.addRangeMarker(vm);
  }

}
