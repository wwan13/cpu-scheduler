package com.wwan13.cpuscheduler.schedulingalgorithms;

import com.wwan13.cpuscheduler.commons.Att;
import com.wwan13.cpuscheduler.commons.Awt;
import com.wwan13.cpuscheduler.processes.Process;
import com.wwan13.cpuscheduler.processes.ResponseDto;
import com.wwan13.cpuscheduler.processes.ScheduledData;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HrnAlgorithm implements SchedulingAlgorithm {

    private List<Process> processes;

    @Override
    public ResponseDto schedule() {

        Integer currentTime = 0;
        List<ScheduledData> scheduledResult = new ArrayList<>();

        List<Process> readyQueue = this.getReadyQueue();

        while(!readyQueue.isEmpty()) {

            Optional<Process> optionalCurrentProcess = this.getHighestResponseRatioProcess(readyQueue, currentTime);

            if (optionalCurrentProcess.isEmpty()) {
                currentTime += 1;
                continue;
            }

            Process currentProcess = currentProcess = optionalCurrentProcess.get();
            readyQueue.remove(currentProcess);

            ScheduledData scheduledData = ScheduledData.builder()
                    .process(currentProcess)
                    .startAt(currentTime)
                    .endAt(currentTime + currentProcess.getServiceTime())
                    .build();
            scheduledResult.add(scheduledData);

            currentTime += currentProcess.getServiceTime();
        }

        this.calculatePerProcesses(scheduledResult);

        ResponseDto responseDto = ResponseDto.builder()
                .algorithmType("HRN")
                .processes(this.processes)
                .scheduledDataList(scheduledResult)
                .AWT(Awt.calculate(this.processes))
                .ATT(Att.calculate(this.processes))
                .build();

        return responseDto;
    }

    private List<Process> getReadyQueue() {
        return this.processes.stream()
                .sorted(Comparator.comparing(Process::getArrivalTime))
                .collect(Collectors.toList());
    }

    private Optional<Process> getHighestResponseRatioProcess(List<Process> readyQueue, Integer currentTime) {
        return readyQueue.stream()
                .filter((p) -> currentTime >= p.getArrivalTime())
                .max(Comparator.comparing((p) -> {
                    Integer waitTime = currentTime - p.getArrivalTime();
                    return (waitTime + p.getServiceTime()) / p.getServiceTime();
                }));
    }

    private void calculatePerProcesses(List<ScheduledData> scheduledResult) {
        for (ScheduledData scheduledData : scheduledResult) {
            Process process = scheduledData.getProcess();
            process.setWaitTime(scheduledData.getStartAt() - process.getArrivalTime());
            process.setTurnAroundTime(scheduledData.getEndAt() - process.getArrivalTime());
            process.setResponseTime((scheduledData.getStartAt() + 1) - process.getArrivalTime());
        }
    }

}
