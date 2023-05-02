package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;

import java.util.List;

public class Awt {

    public static Double calculate(List<Process> processes) {
        Double AWT = processes.stream()
                .mapToInt(Process::getWaitTime)
                .average()
                .getAsDouble();

        return AWT;
    }

}
