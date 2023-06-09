package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Commons.Att;
import com.wwan13.cpuscheduler.Commons.Awt;
import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;
import com.wwan13.cpuscheduler.Processes.ScheduledData;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SrtAlgorithm implements SchedulingAlgorithm{

    private List<Process> processes;
    private Integer timeSlice;

    @Override
    public ResponseDto schedule() {

        Integer currentTime = 0;
        List<ScheduledData> scheduledResult = new ArrayList<>();

        List<Process> readyQueue = this.getReadyQueue();

        while(!readyQueue.isEmpty()) {

            Optional<Process> optionalCurrentProcess = this.getShortestRemainingProcess(readyQueue, currentTime);
            Process currentProcess;
            if(optionalCurrentProcess.isEmpty()) {
                currentTime += 1;
                continue;
            }
            currentProcess = optionalCurrentProcess.get();
            readyQueue.remove(currentProcess);

            if (currentProcess.getArrivalTime() > currentTime) {
                currentTime = currentProcess.getArrivalTime();
            }

            Integer startTime = currentTime;
            Integer endTime;

            if (currentProcess.getServiceTime() > this.timeSlice) {
                endTime = currentTime + this.getTimeSlice();
                currentProcess.setServiceTime(currentProcess.getServiceTime() - this.timeSlice);
                readyQueue.add(currentProcess);
                currentTime += this.timeSlice;
            } else {
                endTime = currentTime + currentProcess.getServiceTime();
                currentTime += currentProcess.getServiceTime();
            }

            ScheduledData scheduledData = ScheduledData.builder()
                    .process(currentProcess)
                    .startAt(startTime)
                    .endAt(endTime)
                    .build();
            scheduledResult.add(scheduledData);
        }

        this.calculatePerProcesses(scheduledResult);

        ResponseDto responseDto = ResponseDto.builder()
                .algorithmType("SRT")
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

    private Optional<Process> getShortestRemainingProcess(List<Process> readyQueue, Integer currentTime) {
        return readyQueue.stream()
                .filter((p) -> currentTime >= p.getArrivalTime())
                .min(Comparator.comparing(Process::getServiceTime));
    }

    private void calculatePerProcesses(List<ScheduledData> scheduledResult) {
        for (ScheduledData scheduledData : scheduledResult) {
            Process process = scheduledData.getProcess();
            Integer serviceTime = scheduledData.getEndAt() - scheduledData.getStartAt();

            process.setTurnAroundTime(scheduledData.getEndAt() - process.getArrivalTime());
            process.setWaitTime(scheduledData.getStartAt() - serviceTime);
        }
    }

}
