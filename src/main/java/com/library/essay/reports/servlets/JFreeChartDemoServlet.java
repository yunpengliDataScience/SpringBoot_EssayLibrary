package com.library.essay.reports.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;

import com.library.essay.reports.beans.AuthorEssayCountBean;
import com.library.essay.services.EssayService;

public class JFreeChartDemoServlet implements HttpRequestHandler {

  @Autowired
  private EssayService essayService;

  @Override
  public void handleRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    OutputStream out = response.getOutputStream();

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();


    List<AuthorEssayCountBean> chartReportList = new ArrayList<AuthorEssayCountBean>();

    chartReportList = essayService.countEssayByAuthor();
    for (AuthorEssayCountBean authorEssayCountBean : chartReportList) {
      long value = authorEssayCountBean.getEssayCount();
      String category = authorEssayCountBean.getAuthor();

      // Author1 Author2 Author3
      // count1 count2 count3

      dataset.addValue(value, "", category);
    }

    JFreeChart jfchart = ChartFactory.createBarChart("Author and Essay Count Bar Chart", "Author",
        "Essay Count", dataset, PlotOrientation.VERTICAL, true, true, false);

    // Add horizontal value bar
    CategoryPlot plot = (CategoryPlot) jfchart.getPlot();
    ValueMarker vm = new ValueMarker(2, Color.GRAY, new BasicStroke(2.0F));
    plot.addRangeMarker(vm);

    response.setContentType("image/png");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies.

    ChartUtilities.writeChartAsPNG(out, jfchart, 800, 600);

    out.close();
  }


}
