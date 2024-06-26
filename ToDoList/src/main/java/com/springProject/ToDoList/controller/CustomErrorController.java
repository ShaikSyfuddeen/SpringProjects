package com.springProject.ToDoList.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController{
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model theModel) {
		
		Object errorCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
		theModel.addAttribute("errorCode", errorCode.toString());
		if(errorMessage != null) {
			theModel.addAttribute("errorMessage", errorMessage.toString());
		}
		return "security/error";
	}
	
}
