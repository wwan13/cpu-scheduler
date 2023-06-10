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
        // 반환시간 대기시간 계산
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
                    .filter(a -> a.getProcess().equals(p)) // 이 프로세스의 스케줄링 결과만 가져와
                    .mapToInt(a -> a.getEndAt() - a.getStartAt()) // 서비스 시간 계산 후
                    .sum(); // 모두 더한 값을 반환

            p.setWaitTime(p.getTurnAroundTime() - waitTime);
        }
    }

}
