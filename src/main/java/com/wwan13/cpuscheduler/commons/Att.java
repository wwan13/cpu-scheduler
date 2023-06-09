package com.wwan13.cpuscheduler.commons;

import com.wwan13.cpuscheduler.processes.Process;

import java.util.List;

public class Att {

    public static Double calculate(List<Process> processes) {
        Double ATT = processes.stream()
                .mapToInt(Process::getTurnAroundTime)
                .average()
                .getAsDouble();

        return ATT;
    }
}
