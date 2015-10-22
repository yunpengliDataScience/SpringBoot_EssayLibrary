package com.library.essay.reports.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import com.library.essay.persistence.entities.Essay;
import com.library.essay.reports.dataSource.EssayReportDataSource;
import com.library.essay.reports.resource.JasperReportGenerationResource;
import com.library.essay.services.EssayService;

//The target bean name must match the HttpRequestHandlerServlet servlet-name as
//defined in web.xml.
@Component("reportServlet")
public class ReportServlet implements HttpRequestHandler {

	@Autowired
	private EssayService essayService;

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String essayIdParam = request.getParameter("essayId");

		System.out.println("essayId=" + essayIdParam);

		if (essayIdParam != null && !"".equals(essayIdParam)) {
			JasperReportGenerationResource<Essay> reportGenerationResource = buildJasperReportGenerationResource(
					request, essayIdParam);

			response.setContentType("application/pdf");
			response.setHeader("Cache-Control",
					"no-cache, no-store, must-revalidate"); // HTTP 1.1
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0
			response.setDateHeader("Expires", 0); // Proxies.

			reportGenerationResource.generateReport(response.getOutputStream());
		}
	}

	private JasperReportGenerationResource<Essay> buildJasperReportGenerationResource(
			HttpServletRequest request, String essayIdParam) {
		Essay essay = essayService.getEssay(Long.parseLong(essayIdParam));

		List<Essay> essayList = new ArrayList<Essay>();
		essayList.add(essay);

		EssayReportDataSource essayReportDataSource = new EssayReportDataSource(
				"Essay Report", essayList);

		ServletContext context = request.getSession().getServletContext();

		String reportDir = context.getRealPath("/reports/");
		String swapFileDir = context.getRealPath("/temp/fileSwap/");

		JasperReportGenerationResource<Essay> reportGenerationResource = new JasperReportGenerationResource<Essay>(
				reportDir + "/essayReport.jrxml", reportDir, swapFileDir,
				"application/pdf", essayReportDataSource);
		return reportGenerationResource;
	}

}
