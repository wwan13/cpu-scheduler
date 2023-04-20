package com.wwan13.cpuscheduler.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @GetMapping
    public String indexPage(HttpServletRequest request) {
        return "index";
    }

}
