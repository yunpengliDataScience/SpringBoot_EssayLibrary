package com.library.essay.reports.servlets;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;

import com.library.essay.reports.beans.AuthorEssayCountBean;
import com.library.essay.reports.dataSource.ChartReportDataSource;
import com.library.essay.reports.dynamic.utils.DynamicBarChartCustomizer;
import com.library.essay.reports.dynamic.utils.Templates;
import com.library.essay.services.EssayService;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class DynamicChartReportServlet implements HttpRequestHandler {

  @Autowired
  private EssayService essayService;

  private JasperReportBuilder buildReport() {

    JasperReportBuilder jasperReportBuilder = null;

    FontBuilder boldFont = stl.fontArialBold().setFontSize(12);

    TextColumnBuilder<String> authorColumn = col.column("Author", "author", type.stringType());
    TextColumnBuilder<Long> essayCountColumn =
        col.column("Essay Count", "essayCount", type.longType());


    jasperReportBuilder = report().setTemplate(Templates.reportTemplate)
        .columns(authorColumn, essayCountColumn).title(Templates.createTitleComponent("BarChart"))
        .summary(cht.barChart().setTitle("Author and Essay Count Bar Chart").setTitleFont(boldFont)
            .setCategory(authorColumn).series(cht.serie(essayCountColumn))
            .setCategoryAxisFormat(cht.axisFormat().setLabel("Author"))
            .addCustomizer(new DynamicBarChartCustomizer()))  //Add horizontal bar
        .pageFooter(Templates.footerComponent).setDataSource(createDataSource());

    return jasperReportBuilder;
  }

  private JRDataSource createDataSource() {

    List<AuthorEssayCountBean> chartReportList = new ArrayList<AuthorEssayCountBean>();

    chartReportList = essayService.countEssayByAuthor();

    ChartReportDataSource barChartReportDataSource =
        new ChartReportDataSource("Author and Essay Count Chart Report", chartReportList);

    return barChartReportDataSource;
  }

  @Override
  public void handleRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/pdf");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies.

    JasperReportBuilder jasperReportBuilder = this.buildReport();
    try {
      jasperReportBuilder.toPdf(response.getOutputStream());
    } catch (DRException e) {
      e.printStackTrace();
    }

  }

}
