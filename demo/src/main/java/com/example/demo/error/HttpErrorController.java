package com.example.demo.error;

import com.example.demo.controller.RestRetValue;
import groovy.util.logging.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class HttpErrorController implements ErrorController  {
    private final static String ERROR_PATH = "/error";
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(path = ERROR_PATH)
    public RestRetValue error(HttpServletRequest request, HttpServletResponse response)
    {
        log.info("访问/error" + "  错误代码："  + response.getStatus());
        RestRetValue restRetValue = new RestRetValue("1", "HttpErrorController error:"+response.getStatus(), null);
        return restRetValue;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
