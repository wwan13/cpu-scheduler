package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;
import com.wwan13.cpuscheduler.Processes.ScheduledData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FCFS 알고리즘
 */
public class FcfsAlgorithm implements SchedulingAlgorithm{

    private List<Process> processes;

    public FcfsAlgorithm(List<Process> processes) {
        this.processes = processes;
    }

    @Override
    public ResponseDto schedule() {

        Integer currentTime = 0;
        List<ScheduledData> scheduledResult = new ArrayList<>();

        List<Process> sortedProcesses = this.processes.stream()
                .sorted(Comparator.comparing(Process::getArrivalTime))
                .collect(Collectors.toList());

        while(!sortedProcesses.isEmpty()) {
            Process currentProcess = sortedProcesses.remove(0);
            ScheduledData scheduledData = new ScheduledData(currentProcess, currentTime, currentTime + currentProcess.getServiceTime());
            scheduledResult.add(scheduledData);
            currentTime += currentProcess.getServiceTime();
        }

        for (ScheduledData scheduledData : scheduledResult) {
            Process process = scheduledData.getProcess();

            process.setWaitTime(scheduledData.getStartAt() - process.getArrivalTime());
            process.setTurnAroundTime(scheduledData.getEndAt() - process.getArrivalTime());
        }

        ResponseDto responseDto = new ResponseDto();
        responseDto.setAlgorithmType("FCFS");

        return responseDto;
    }

    @Override
    public double getAWT() {

        Integer sumOfWaitTimes = this.processes.stream()
                .map(Process::getWaitTime)
                .reduce(0, (n1, n2) -> n1 + n2);

        Integer AWT = sumOfWaitTimes / this.processes.size();

        return AWT;

    }

    @Override
    public double getATT() {

        Integer sumOfTurnAroundTimes = this.processes.stream()
                .map(Process::getTurnAroundTime)
                .reduce(0, (n1, n2) -> n1 + n2);

        Integer ATT = sumOfTurnAroundTimes / this.processes.size();

        return ATT;

    }

    @Override
    public double getART() {
        return 0;
    }
}
