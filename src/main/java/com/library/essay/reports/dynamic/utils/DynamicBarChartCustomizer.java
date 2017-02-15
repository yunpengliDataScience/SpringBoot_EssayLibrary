package com.library.essay.reports.dynamic.utils;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;

import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.chart.DRIChartCustomizer;

public class DynamicBarChartCustomizer implements DRIChartCustomizer {

  @Override
  public void customize(JFreeChart jfchart, ReportParameters reportParameters) {
    CategoryPlot plot = (CategoryPlot) jfchart.getPlot();
    ValueMarker vm = new ValueMarker(2, Color.GRAY, new BasicStroke(2.0F));
    plot.addRangeMarker(vm);

  }

}
