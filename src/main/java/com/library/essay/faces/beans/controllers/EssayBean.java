package com.library.essay.faces.beans.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.library.essay.persistence.entities.Essay;
import com.library.essay.services.EssayService;

//This is the managed bean name that will be refered in xhtml pages. It acts as
//a controller.

@Component("essayBean")
@Scope("session")
public class EssayBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private EssayService essayService;

	private Essay essay;

	public EssayBean() {
		essay = new Essay();
	}

	public List<Essay> getEssays() {

		logger.debug("getEssays() is called!");
		return essayService.getEssays();
	}

	public String showEssayAdmin() {

		FacesContext fc = FacesContext.getCurrentInstance();

		String essayIdStr = this.getEssayId(fc);
		long essayId = Long.parseLong(essayIdStr);

		this.essay = essayService.getEssay(essayId);

		return "essay";
	}

	public String showEssay() {

		FacesContext fc = FacesContext.getCurrentInstance();

		String essayIdStr = this.getEssayId(fc);
		long essayId = Long.parseLong(essayIdStr);

		this.essay = essayService.getEssay(essayId);

		return "essay2";
	}

	// get value from "f:param"
	private String getEssayId(FacesContext fc) {

		// Use FacesContext to get the parameters passed from <f:param>
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

		return params.get("essayId");

	}

	public String createNewEssay() {
		this.essay = new Essay();

		return "essay";
	}

	public void initNewEssay() {
		this.essay = new Essay();
	}

	public String saveEssay() {

		essay = essayService.saveOrUpdate(essay);

		return "essay";
	}

	// TODO
	public void validateAndSaveEssay(ActionEvent ae) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		String message;
		FacesMessage.Severity severity;
		InputText titleInput = (InputText) ae.getComponent().findComponent("title");

		if (essay.getTitle() != null && StringUtils.trimToNull(essay.getTitle()) != null) {
			message = "Archived.";
			severity = FacesMessage.SEVERITY_INFO;
			requestContext.addCallbackParam("titleValid", true);
			titleInput.setValid(true);

		} else {
			message = "Title is required!";
			severity = FacesMessage.SEVERITY_ERROR;
			requestContext.addCallbackParam("titleValid", false);
			titleInput.setValid(false);
		}
		FacesMessage msg = new FacesMessage(severity, message, null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String deleteEssay() {

		essayService.delete(essay);
		this.essay = new Essay();

		return "essay";
	}

	public EssayService getEssayService() {
		return essayService;
	}

	public void setEssayService(EssayService essayService) {
		this.essayService = essayService;
	}

	public Essay getEssay() {
		return essay;
	}

	public void setEssay(Essay essay) {
		this.essay = essay;
	}

}
