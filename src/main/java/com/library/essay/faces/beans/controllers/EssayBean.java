package com.library.essay.faces.beans.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
//import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.library.essay.persistence.entities.Essay;
import com.library.essay.services.EssayService;

//This is the managed bean name that will be refered in xhtml pages. It acts as
//a controller.
//Note: It is javax.annotation.ManagedBean Not javax.faces.bean.ManagedBean!
@ManagedBean("essayBean")
@SessionScoped
public class EssayBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	// Use Spring @Autowired instead of JSF @ManagedProperty
	// @ManagedProperty(name = "essayService", value = "#{essayService}")
	@Autowired
	private EssayService essayService;

	private Essay essay;

	public EssayBean() {
		essay = new Essay();
	}

	public List<Essay> getEssays() {
		return essayService.getEssays();
	}

	public String showEssay() {

		FacesContext fc = FacesContext.getCurrentInstance();

		String essayIdStr = this.getEssayId(fc);
		long essayId = Long.parseLong(essayIdStr);

		this.essay = essayService.getEssay(essayId);

		return "essay";
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

	public String saveEssay() {

		essay = essayService.saveOrUpdate(essay);

		return "essay";
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
