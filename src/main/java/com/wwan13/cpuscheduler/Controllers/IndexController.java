package com.wwan13.cpuscheduler.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/***
 * IndexController Class
 * Client 의 요청을 처리하는 클래스
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String scheduler() {
        return "index";
    }

}
