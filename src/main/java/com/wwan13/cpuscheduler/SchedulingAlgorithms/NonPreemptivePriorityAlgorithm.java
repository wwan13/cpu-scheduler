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
 * 비선점 Priority 알고리즘
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NonPreemptivePriorityAlgorithm implements SchedulingAlgorithm {

    private List<Process> processes;

    /**
     * 비선점 Priority 스케줄링을 실행하는 메서드
     * @return
     */
    @Override
    public ResponseDto schedule() {

        Integer currentTime = 0;
        List<ScheduledData> scheduledResult = new ArrayList<>();

        List<Process> readyQueue = this.getReadyQueue();

        while (!readyQueue.isEmpty()) {

            // 도착 시간으로 정렬 하였기 때문에, 가장 먼저 도착한 프로세스를 가져오고, 기존 리스트에서 삭제
            Process currentProcess = readyQueue.remove(0);

            // 어느 프로세스가 언제부터 언제까지 스케줄링 되었는지 계산 후 리스트에 넣음
            ScheduledData scheduledData = ScheduledData.builder()
                    .process(currentProcess)
                    .startAt(currentTime)
                    .endAt(currentTime + currentProcess.getServiceTime())
                    .build();
            scheduledResult.add(scheduledData);

            // 현재 시간 동기화
            currentTime += currentProcess.getServiceTime();

        }

        // 프로세스별 반환시간, 응답시간 계산
        this.calculatePerProcesses(scheduledResult);

        // 결과 반환을 위한 DTO
        ResponseDto responseDto = ResponseDto.builder()
                .algorithmType("NonPreemptivePriority")
                .processes(this.processes)
                .scheduledDataList(scheduledResult)
                .AWT(Awt.calculate(this.processes))
                .ATT(Att.calculate(this.processes))
                .build();

        return responseDto;
    }

    /**
     * ReadyQueue 의 초기값을 반환하는 메서드
     * 가장 먼저 도착하는 프로세스 + 우선 순위를 기준으로 정렬
     * @return
     */
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
        for (ScheduledData scheduledData : scheduledResult) {
            Process process = scheduledData.getProcess();
            process.setWaitTime(scheduledData.getStartAt() - process.getArrivalTime());
            process.setTurnAroundTime(scheduledData.getEndAt() - process.getArrivalTime());
        }
    }
}
