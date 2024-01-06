package com.vn.controller.errors;

import jakarta.servlet.RequestDispatcher;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ErrorPageController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    @Autowired
    public ErrorPageController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(Model model, WebRequest webRequest, HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        Map<String, Object> errorAttributes = getErrorAttributes(webRequest);
        model.addAttribute("errorCode", status.value());
        model.addAttribute("status", status.getReasonPhrase());
        model.addAttribute("errorMessage", errorAttributes.get("message"));
        return "view/error/lkh";
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    private Map<String, Object> getErrorAttributes(WebRequest webRequest) {
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.MESSAGE);
        return errorAttributes.getErrorAttributes(webRequest, options);
    }
}