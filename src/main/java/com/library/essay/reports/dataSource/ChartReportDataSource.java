package com.library.essay.reports.dataSource;

import java.util.List;

import com.library.essay.reports.beans.AuthorEssayCountBean;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ChartReportDataSource extends JasperReportAbstractDataSource<AuthorEssayCountBean> {

  public ChartReportDataSource(String reportTitle, List<AuthorEssayCountBean> reportObjects) {
    super(reportTitle, reportObjects);

  }

  @Override
  public Object getFieldValue(JRField jrField) throws JRException {
    Object value = null;

    String fieldName = jrField.getName();

    AuthorEssayCountBean authorEssayCountBean = this.getReportObjects().get(this.getIndex());

    if ("essayCount".equals(fieldName)) {
      value = authorEssayCountBean.getEssayCount();
    } else if ("author".equals(fieldName)) {
      value = authorEssayCountBean.getAuthor();
    }

    return value;
  }

}
