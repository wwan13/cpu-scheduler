package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Commons.Att;
import com.wwan13.cpuscheduler.Commons.Awt;
import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;
import com.wwan13.cpuscheduler.Processes.ScheduledData;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * FCFS 알고리즘
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FcfsAlgorithm implements SchedulingAlgorithm{

    private List<Process> processes;

    /**
     * FCFS 스케줄링이 이뤄지는 메서드
     * @return ResponseDto
     */
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

        // 스케줄링 결과를 바탕으로 프로세스별 대기시간, 반환시간 계산
        this.calculatePerProcesses(scheduledResult);

        // 결과 반환을 위한 DTO
        ResponseDto responseDto = ResponseDto.builder()
                .algorithmType("FCFS")
                .processes(this.processes)
                .scheduledDataList(scheduledResult)
                .AWT(Awt.calculate(this.processes))
                .ATT(Att.calculate(this.processes))
                .build();

        return responseDto;
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
            Process process = scheduledData.getProcess();
            process.setWaitTime(scheduledData.getStartAt() - process.getArrivalTime());
            process.setTurnAroundTime(scheduledData.getEndAt() - process.getArrivalTime());
        }
    }
}
