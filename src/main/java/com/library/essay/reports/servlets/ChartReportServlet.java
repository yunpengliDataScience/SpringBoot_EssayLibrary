package com.library.essay.reports.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;

import com.library.essay.reports.beans.AuthorEssayCountBean;
import com.library.essay.reports.dataSource.ChartReportDataSource;
import com.library.essay.reports.resource.JasperReportGenerationResource;
import com.library.essay.services.EssayService;

public class ChartReportServlet implements HttpRequestHandler {

  @Autowired
  private EssayService essayService;

  @Override
  public void handleRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {


    JasperReportGenerationResource<AuthorEssayCountBean> reportGenerationResource =
        buildJasperReportGenerationResource(request);

    response.setContentType("application/pdf");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies.

    reportGenerationResource.generateReport(response.getOutputStream());

  }

  private JasperReportGenerationResource<AuthorEssayCountBean> buildJasperReportGenerationResource(
      HttpServletRequest request) {

    List<AuthorEssayCountBean> chartReportList = new ArrayList<AuthorEssayCountBean>();
    
    chartReportList = essayService.countEssayByAuthor();

    ChartReportDataSource barChartReportDataSource =
        new ChartReportDataSource("Essay Chart Report", chartReportList);

    ServletContext context = request.getSession().getServletContext();

    String reportDir = context.getRealPath("/reports/");
    String swapFileDir = context.getRealPath("/temp/fileSwap/");

    JasperReportGenerationResource<AuthorEssayCountBean> reportGenerationResource =
        new JasperReportGenerationResource<AuthorEssayCountBean>(reportDir + "/chartReport.jrxml",
            reportDir, swapFileDir, "application/pdf", barChartReportDataSource);
    return reportGenerationResource;
  }
}
