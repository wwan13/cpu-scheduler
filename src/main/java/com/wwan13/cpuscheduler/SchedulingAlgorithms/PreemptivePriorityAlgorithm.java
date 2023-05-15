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
import java.util.stream.Collectors;

/**
 * 선점 우선순위 알고리즘
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreemptivePriorityAlgorithm implements SchedulingAlgorithm {

    private List<Process> processes;

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
            Integer nextProcessArrivalTime;

            if (!readyQueue.isEmpty()) {
                nextProcessArrivalTime = readyQueue.get(0).getArrivalTime();
            } else {
                nextProcessArrivalTime = 0;
            }

            // 프로세스의 도착시간이 지금 현재 시간보다 빠르다면 프로세스의 도착시간으로 현재 시간을 변경
            if (currentProcess.getArrivalTime() > currentTime) {
                currentTime = currentProcess.getArrivalTime();
            }

            Integer startTime = currentTime;
            Integer endTime;

            if (nextProcessArrivalTime > currentTime) {
                Integer thisServiceTime = nextProcessArrivalTime - currentTime;
                currentProcess.setServiceTime(currentProcess.getServiceTime() - thisServiceTime);
                readyQueue.add(currentProcess);
                endTime = currentTime + thisServiceTime;
                currentTime += thisServiceTime;
            } else {
                endTime = currentProcess.getServiceTime();
                currentTime += currentProcess.getServiceTime();
            }

            // 어느 프로세스가 언제부터 언제까지 스케줄링 되었는지 계산 후 리스트에 넣음
            ScheduledData scheduledData = ScheduledData.builder()
                    .process(currentProcess)
                    .startAt(startTime)
                    .endAt(endTime)
                    .build();
            scheduledResult.add(scheduledData);

            currentTime += currentProcess.getServiceTime();
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

        Process firstScheduledProcess = this.getFirstScheduledProcess();

        List<Process> readyQueue = this.processes.stream()
                .filter(a -> !a.equals(firstScheduledProcess))
                .sorted(Comparator.comparing(Process::getPriority)
                        .thenComparing(Process::getArrivalTime))
                .collect(Collectors.toList());

        readyQueue.add(0, firstScheduledProcess);

        return readyQueue;

    }

    /**
     * 가장 먼저 스케줄링되는 프로세스를 반환하는 메서드
     * 도착 시간이 가장 빠르고, 도착 시간이 같다면 우선순위가 더 높은 것을 반환
     * @return Process
     */
    private Process getFirstScheduledProcess() {
        return this.processes.stream()
                .sorted(Comparator.comparing(Process::getArrivalTime)
                        .thenComparing(Process::getPriority))
                .findFirst()
                .orElseThrow(() -> new NullPointerException());
    }

    /**
     * 프로세스별 대기시간, 반환시간, 응답시간을 계산하는 메서드
     * @param scheduledResult
     */
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
                    .filter(a -> a.getProcess().equals(p)) // 이 프로세스의 스케줄링 결과만 가져와
                    .mapToInt(a -> a.getEndAt() - a.getStartAt()) // 서비스 시간 계산 후
                    .sum(); // 모두 더한 값을 반환

            p.setWaitTime(p.getTurnAroundTime() - waitTime);
        }
    }


}
