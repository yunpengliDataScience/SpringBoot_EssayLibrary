package com.library.essay.reports.resource;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;

import org.apache.log4j.Logger;

import com.library.essay.reports.dataSource.JasperReportAbstractDataSource;

/**
 * This is used with ReportLink to generate reports. It implements IResource.
 */
public class JasperReportGenerationResource<T> {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(JasperReportGenerationResource.class);

  private String reportTemplateFileFullPath;
  private String subReportDirectory;
  private String swapFileDir;

  private String contentType;
  private JasperReportAbstractDataSource<T> dataSource;

  public JasperReportGenerationResource(String reportTemplateFileFullPath,
      String subReportDirectory, String swapFileDir, String contentType,
      JasperReportAbstractDataSource<T> dataSource) {

    this.reportTemplateFileFullPath = reportTemplateFileFullPath;
    this.subReportDirectory = subReportDirectory;
    this.swapFileDir = swapFileDir;
    this.contentType = contentType;

    this.dataSource = dataSource;
  }

  protected JRAbstractExporter getExporter() {

    JRAbstractExporter exporter = null;
    if ("text/html".equals(contentType)) {
      exporter = new JRXhtmlExporter();
      //exporter = new HtmlExporter();
    } else {
      // Default is pdf
      exporter = new JRPdfExporter();
    }

    return exporter;
  }

  public void generateReport(OutputStream outputStream) {

    try {

      long start = System.currentTimeMillis();

      JasperPrint print = getFilledJasperPrint();
      JRAbstractExporter exporter = getExporter();

      // Deprecated
      // exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
      // exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);

      exporter.setExporterInput(new SimpleExporterInput(print));
      exporter.setExporterOutput(new OutputStreamExporterOutput() {

        @Override
        public OutputStream getOutputStream() {

          return outputStream;
        }

        @Override
        public void close() {
          
        }
      });

      System.out.println("Start exporting report...");
      exporter.exportReport();

      long end = System.currentTimeMillis();

      System.out.println("Generating report takes time : " + ((end - start) / 1000) + " seconds.");
      logger.info("Generating report takes time : " + ((end - start) / 1000) + " seconds.");

    } catch (JRException e) {
      e.printStackTrace();
      logger.error(e.getMessage(), e);
    }

  }

  private JasperPrint getFilledJasperPrint() throws JRException {

    File templateFile = new File(reportTemplateFileFullPath);

    JasperPrint print = null;
    JasperDesign design;
    JasperReport report = null;

    try {

      design = JRXmlLoader.load(templateFile);

      report = JasperCompileManager.compileReport(design);

      Map<String, Object> params = new HashMap<String, Object>();
      params.put("SUBREPORT_DIR", this.subReportDirectory + "/");

      // Use file swap and JRSwapFileVirtualizer to improve big report
      // generation performance
      // and reduce chance of out-of-memory error.
      JRSwapFile swapFile = new JRSwapFile(swapFileDir, 2048, 1024);
      JRSwapFileVirtualizer virtualizer = new JRSwapFileVirtualizer(30, swapFile, true);

      params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

      System.out.println("Start filling report...");
      print = JasperFillManager.fillReport(report, params, dataSource);

    } catch (JRException e) {
      logger.error(e.getMessage(), e);
    }
    return print;
  }
}
