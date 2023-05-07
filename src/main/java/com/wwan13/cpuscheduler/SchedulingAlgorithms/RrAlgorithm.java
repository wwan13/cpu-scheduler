package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;
import com.wwan13.cpuscheduler.Processes.ScheduledData;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Round Robin 알고리즘
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RrAlgorithm implements SchedulingAlgorithm{

    private List<Process> processes;
    private Integer timeSlice;

    @Override
    public ResponseDto schedule() {

        Integer currentTime = 0;                                        // 현재 시간을 저장하는 변수
        List<ScheduledData> scheduledResult = new ArrayList<>();        // 스줄링 결과를 단계별로 저장할 리스트

        // ready queue 초기값
        List<Process> readyQueue = this.getReadyQueue();

        // 모든 프로세스가 스케줄링 완료될 때 까지 반복
        while(!readyQueue.isEmpty()) {

            // 도착 시간으로 정렬 하였기 때문에, 가장 먼저 도착한 프로세스를 가져오고, 기존 리스트에서 삭제
            Process currentProcess = readyQueue.remove(0);

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

            // 어느 프로세스가 언제부터 언제까지 스케줄링 되었는지 계산 후 리스트에 넣음
            ScheduledData scheduledData = ScheduledData.builder()
                    .process(currentProcess)
                    .startAt(startTime)
                    .endAt(endTime)
                    .build();
            scheduledResult.add(scheduledData);
        }

        this.calculatePerProcesses(scheduledResult);

        return null;
    }


    /**
     * 초기 ReadyQueue 를 반환하는 메서드
     * 도착 시간 기준 정렬해 반환
     * @return
     */
    private List<Process> getReadyQueue() {
        return this.processes.stream()
                .sorted(Comparator.comparing(Process::getArrivalTime))
                .collect(Collectors.toList());
    }

    /**
     * 프로세스별 대기시간, 반환시간, 응답시간을 계산하는 메서드
     * @param scheduledResult
     */
    private void calculatePerProcesses(List<ScheduledData> scheduledResult) {
        for (ScheduledData scheduledData : scheduledResult) {

            System.out.println(scheduledData.getProcess().getProcessId());
            System.out.println(scheduledData.getStartAt());
            System.out.println(scheduledData.getEndAt() + "\n");
//            Process process = scheduledData.getProcess();
//            process.setWaitTime(scheduledData.getStartAt() - process.getArrivalTime());
//            process.setTurnAroundTime(scheduledData.getEndAt() - process.getArrivalTime());
        }
    }

}
