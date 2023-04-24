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

        // TODO
        // 프로세스별 반환시간, 프로세스별 대기시간 구하기

        for (ScheduledData scheduledData : scheduledResult) {

        }

        ResponseDto responseDto = new ResponseDto();
        responseDto.setAlgorithmType("FCFS");

        return responseDto;
    }

    @Override
    public double getAWT(List<Process> processes) {
        return 0;
    }

    @Override
    public double getATT(List<Process> processes) {
        return 0;
    }

    @Override
    public double getART(List<Process> processes) {
        return 0;
    }
}
