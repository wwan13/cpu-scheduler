package com.wwan13.cpuscheduler.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping(value = "/", produces = "application/json")
    @ResponseBody
    public Map schedule(@RequestParam Map<String, Object> payload) {
        Map<String, Object> map = new HashMap<>();
        map.put("test", "test");

        System.out.println("payload = " + payload);
        return map;
    }

}
