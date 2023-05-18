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
public class PreemptivePriorityAlgorithm implements SchedulingAlgorithm {

    private List<Process> processes;

    @Override
    public ResponseDto schedule() {

        Integer currentTime = 0;
        List<ScheduledData> scheduledResult = new ArrayList<>();

        List<Process> readyQueue = this.getReadyQueue();

        while(!readyQueue.isEmpty()) {

            Optional<Process> optionalProcess = this.getFirstPriorityProcessAlreadyArrive(readyQueue, currentTime);
            Process process;
            Integer nextProcessArriveTime = getNextProcessArriveTime(currentTime);

            if (optionalProcess == null) {
                currentTime += 1;
                continue;
            } else {
                process = optionalProcess.get();
                readyQueue.remove(process);
            }

            if (process.getArrivalTime() > currentTime) {
                currentTime += process.getArrivalTime();
            }

            ScheduledData scheduledData;
            if (nextProcessArriveTime > currentTime && nextProcessArriveTime != 0xffff) {

                Integer thisServiceTime = nextProcessArriveTime - currentTime;
                process.setServiceTime(process.getServiceTime() - thisServiceTime);
                readyQueue.add(process);

                scheduledData = ScheduledData.builder()
                        .process(process)
                        .startAt(currentTime)
                        .endAt(nextProcessArriveTime)
                        .build();

                currentTime += thisServiceTime;

            } else {

                scheduledData = ScheduledData.builder()
                        .process(process)
                        .startAt(currentTime)
                        .endAt(currentTime + process.getServiceTime())
                        .build();

                currentTime += process.getServiceTime();

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

    private Optional<Process> getFirstPriorityProcessAlreadyArrive(List<Process> readyQueue, Integer currentTime) {

        return readyQueue.stream()
                .filter(a -> a.getArrivalTime() <= currentTime)
                .sorted(Comparator.comparing(Process::getPriority))
                .findFirst();

    }

    private void calculatePerProcesses(List<ScheduledData> scheduledResult) {

        // 반환시간 계산
        for (ScheduledData scheduledData : scheduledResult) {
            Process process = scheduledData.getProcess();
            process.setTurnAroundTime(scheduledData.getEndAt() - process.getArrivalTime());

            System.out.println(scheduledData.getStartAt());
            System.out.println(scheduledData.getEndAt());
            System.out.println();
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
