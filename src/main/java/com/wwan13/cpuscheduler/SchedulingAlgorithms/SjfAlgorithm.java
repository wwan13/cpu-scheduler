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
import java.util.stream.Stream;

/**
 * SJF 알고리즘
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SjfAlgorithm implements SchedulingAlgorithm{

    private List<Process> processes;

    /**
     * SFJ 스케줄링을 실행하는 메서드
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
        for (ScheduledData scheduledData : scheduledResult) {
            Process process = scheduledData.getProcess();
            process.setWaitTime(scheduledData.getStartAt() - process.getArrivalTime());
            process.setTurnAroundTime(scheduledData.getEndAt() - process.getArrivalTime());
        }

        // 결과 반환을 위한 DTO
        ResponseDto responseDto = ResponseDto.builder()
                .algorithmType("SJF")
                .processes(this.processes)
                .scheduledDataList(scheduledResult)
                .AWT(Awt.calculate(this.processes))
                .ATT(Att.calculate(this.processes))
                .build();

        return responseDto;

    }

    /**
     * ReadyQueue 의 초기값을 반환하는 메서드
     * 첫번쨰로 도착하는 프로세스 + 남은 메서드들을 서비스 시간 기준으로 정렬
     * @return
     */
    private List<Process> getReadyQueue() {

        // 가장 먼저 스케줄링될 프로세스
        Process firstScheduledProcess = this.getFirstScheduledProcess();

        List<Process> readyQueue = this.processes.stream()
                    .filter(a -> !a.equals(firstScheduledProcess))          // 첫번째로 스케줄링되는 프로세스는 제외
                    .sorted(Comparator.comparing(Process::getServiceTime)   // 서비스 시간 기준 정렬
                            .thenComparing(Process::getArrivalTime))        // 같다면 도착 시간 기준 정렬
                    .collect(Collectors.toList());

        readyQueue.add(0, firstScheduledProcess);

        return readyQueue;

    }

    /**
     * 가장 먼저 스케줄링되는 프로세스를 반환하는 메서드
     * 도착 시간이 가장 빠르고, 도착 시간이 같다면 서비스 시간이 더 작은 프로세스
     * @return Process
     */
    private Process getFirstScheduledProcess() {
        return this.processes.stream()
                .sorted(Comparator.comparing(Process::getArrivalTime)
                        .thenComparing(Process::getServiceTime))
                .findFirst()
                .orElseThrow(() -> new NullPointerException());
    }
}
