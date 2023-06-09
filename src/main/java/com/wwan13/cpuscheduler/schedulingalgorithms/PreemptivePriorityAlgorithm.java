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
@NoArgsConstructor
@AllArgsConstructor
public class PreemptivePriorityAlgorithm implements SchedulingAlgorithm {

    private List<Process> processes;

    @Override
    public ResponseDto schedule() {

        Integer currentTime = 0;
        List<ScheduledData> scheduledResult = new ArrayList<>();

        List<Process> readyQueue = this.getReadyQueue();

        while(!readyQueue.isEmpty()) {

            Optional<Process> optionalProcess = this.getFirstPriorityProcess(readyQueue, currentTime);
            Integer nextProcessArriveTime = getNextProcessArriveTime(currentTime);

            if (optionalProcess.isEmpty()) {
                currentTime += 1;
                continue;
            } 
            
            Process currentProcess = optionalProcess.get();
            readyQueue.remove(currentProcess);

            ScheduledData scheduledData;
            if (nextProcessArriveTime > currentTime && nextProcessArriveTime != 0xffff) {

                Integer thisServiceTime = nextProcessArriveTime - currentTime;
                currentProcess.setServiceTime(currentProcess.getServiceTime() - thisServiceTime);
                if (currentProcess.getServiceTime() > 0) {
                    readyQueue.add(currentProcess);
                }

                scheduledData = ScheduledData.builder()
                        .process(currentProcess)
                        .startAt(currentTime)
                        .endAt(nextProcessArriveTime)
                        .build();

                currentTime += thisServiceTime;

            } else {

                scheduledData = ScheduledData.builder()
                        .process(currentProcess)
                        .startAt(currentTime)
                        .endAt(currentTime + currentProcess.getServiceTime())
                        .build();

                currentTime += currentProcess.getServiceTime();

            }

            scheduledResult.add(scheduledData);

        }

        this.calculatePerProcesses(scheduledResult);

        // 결과 반환을 위한 DTO
        ResponseDto responseDto = ResponseDto.builder()
                .algorithmType("PreemptivePriority")
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

    private Integer getNextProcessArriveTime(Integer currentProcessArriveTime) {
        return this.processes.stream()
                .sorted(Comparator.comparing(Process::getArrivalTime))
                .map(Process::getArrivalTime)
                .filter(a -> a > currentProcessArriveTime)
                .findFirst()
                .orElse(0xffff);
    }

    private Optional<Process> getFirstPriorityProcess(List<Process> readyQueue, Integer currentTime) {

        return readyQueue.stream()
                .filter(a -> a.getArrivalTime() <= currentTime)
                .min(Comparator.comparing(Process::getPriority));

    }

    private void calculatePerProcesses(List<ScheduledData> scheduledResult) {

        // 반환시간, 대기시간 계산
        for (ScheduledData scheduledData : scheduledResult) {
            Process process = scheduledData.getProcess();
            process.setTurnAroundTime(scheduledData.getEndAt() - process.getArrivalTime());

            if (process.getResponseTime() == null) {
                process.setResponseTime((scheduledData.getStartAt() + 1) - process.getArrivalTime());
            }
        }

        // 대기시간 계산 (반환시간 - 전체 서비스 시간)
        for (Process p : this.processes) {
            Integer waitTime = scheduledResult.stream()
                    .filter(a -> a.getProcess().equals(p))
                    .mapToInt(a -> a.getEndAt() - a.getStartAt())
                    .sum();

            p.setWaitTime(p.getTurnAroundTime() - waitTime);
        }
    }


}
