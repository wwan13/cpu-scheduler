package com.wwan13.cpuscheduler.Controllers;

import com.wwan13.cpuscheduler.Processes.ResponseDto;
import com.wwan13.cpuscheduler.SchedulingAlgorithms.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.wwan13.cpuscheduler.Processes.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public Map schedule(@RequestParam Map<String, String> data) {

        List<Process> processes = this.getProcesses(data);
        SchedulingAlgorithm schedulingAlgorithm = this.getSchedulingAlgorithm(data, processes);

        ResponseDto responseDto = schedulingAlgorithm.schedule();

        Map<String, Object> response = new HashMap<>();
        response.put("response", responseDto);

        return response;
    }

    public List<Process> getProcesses(Map<String, String> data) {

        int loopValue = (data.size() - 2) / 4;
        List<Process> result = new ArrayList<>();

        System.out.println(data);
        System.out.println(data.get("datas[1][PID]"));
        System.out.println(data.get("datas[1][arrivalTime]"));
        System.out.println(data.get("datas[1][serviceTime]"));
        System.out.println(data.get("datas[1][priority]"));

        for(int i=0; i < loopValue; i++) {
            String hashMapKey = "datas[" + Integer.toString(i) + "]";
            Process newProcess = Process.builder()
                    .processId(data.get(hashMapKey + "[PID]"))
                    .arrivalTime(Integer.valueOf(data.get(hashMapKey + "[arrivalTime]")))
                    .serviceTime(Integer.valueOf(data.get(hashMapKey + "[serviceTime]")))
                    .priority(Integer.valueOf(data.get(hashMapKey + "[priority]")))
                    .build();
            result.add(newProcess);
        }

        return result;
    }

    private SchedulingAlgorithm getSchedulingAlgorithm(Map<String, String> data, List<Process> processes) {

        String algorithmName = data.get("algorithmType").toString();
        Integer timeSlice = Integer.valueOf(data.get("timeSlice"));
        SchedulingAlgorithm schedulingAlgorithm = null;

        switch (algorithmName) {
            case "FCFS":
                schedulingAlgorithm = new FcfsAlgorithm(processes);
                break;
            case "SJF":
                schedulingAlgorithm = new SjfAlgorithm(processes);
                break;
            case "npPriority":
                schedulingAlgorithm = new NonPreemptivePriorityAlgorithm(processes);
                break;
            case "pPriority":
                schedulingAlgorithm = new PreemptivePriorityAlgorithm(processes);
                break;
            case "RR":
                schedulingAlgorithm = new RrAlgorithm(processes, timeSlice);
                break;
            case "SRT":
                schedulingAlgorithm = new SrtAlgorithm(processes, timeSlice);
                break;
            case "HRN":
                schedulingAlgorithm = new HrnAlgorithm(processes);
                break;
        }

        return schedulingAlgorithm;
    }

}
