package com.wwan13.cpuscheduler.SchedulingAlgorithms;

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
 * SJF 알고르즘
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SjfAlgorithm implements SchedulingAlgorithm{

    private List<Process> processes;

    @Override
    public ResponseDto schedule() {

        Integer currentTime = 0;                                        // 현재 시간을 저장하는 변수
        List<ScheduledData> scheduledResult = new ArrayList<>();        // 스줄링 결과를 단계별로 저장할 리스트

        // 가장 먼저 스케줄링될 프로세스
        Process firstScheduledProcess = this.getFirstScheduledProcess();

        // 두 번째로 스케줄링 될 프로세스들을 서비스 시간(같다면 먼저 도착한 것 부터)으로 정렬
        List<Process> sortedProcesses = this.processes.stream()
                .filter(a -> !a.equals(firstScheduledProcess))          // 첫번째로 스케줄링되는 프로세스는 제외
                .sorted(Comparator.comparing(Process::getServiceTime)   // 서비스 시간 기준 정렬
                        .thenComparing(Process::getArrivalTime))        // 같다면 도착 시간 기준 정렬
                .collect(Collectors.toList());

        // 첫 번째 프로세스를 큐의 가장 앞에 넣음
        sortedProcesses.add(0, firstScheduledProcess);

        // 모든 프로세스가 스케줄링 완료될 때 까지 반복
        while(!sortedProcesses.isEmpty()) {

            // 도착 시간으로 정렬 하였기 때문에, 가장 먼저 도착한 프로세스를 가져오고, 기존 리스트에서 삭제
            Process currentProcess = sortedProcesses.remove(0);

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

        return null;

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
