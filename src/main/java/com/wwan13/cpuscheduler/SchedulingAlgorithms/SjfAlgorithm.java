package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;
import com.wwan13.cpuscheduler.Processes.ScheduledData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SJF 알고르즘
 */
public class SjfAlgorithm implements SchedulingAlgorithm{

    private List<Process> processes;

    public SjfAlgorithm(List<Process> processes) {
        this.processes = processes;
    }

    @Override
    public ResponseDto schedule() {

        Integer currentTime = 0;                                        // 현재 시간을 저장하는 변수
        List<ScheduledData> scheduledResult = new ArrayList<>();        // 스줄링 결과를 단계별로 저장할 리스트

        // 가장 먼저 스케줄링될 프로세스 (도착 시간이 가장 빠르고, 서비스 시간이 가장 작은) 선택
        Process firstArrivalProcess = this.processes.stream()
                .sorted(Comparator.comparing(Process::getArrivalTime)
                        .thenComparing(Process::getServiceTime))
                .findFirst()
                .orElseThrow(() -> new NullPointerException());

        // 두 번째로 스케줄링 될 프로세스들을 서비스 시간(같다면 먼저 도착한 것 부터)으로 정렬
        List<Process> sortedProcesses = this.processes.stream()
                .filter(a -> !a.equals(firstArrivalProcess))
                .sorted(Comparator.comparing(Process::getServiceTime)
                        .thenComparing(Process::getArrivalTime))
                .collect(Collectors.toList());

        // 첫 번째 프로세스를 큐의 가장 앞에 넣음
        sortedProcesses.add(0, firstArrivalProcess);

        for (Process p: sortedProcesses) {
            System.out.println(p.getProcessId());
            System.out.println(p.getServiceTime());
        }

        // 모든 프로세스가 스케줄링 완료될 때 까지 반복
        while(!sortedProcesses.isEmpty()) {

            // 도착 시간으로 정렬 하였기 때문에, 가장 먼저 도착한 프로세스를 가져오고, 기존 리스트에서 삭제
            Process currentProcess = sortedProcesses.remove(0);

            // 어느 프로세스가 언제부터 언제까지 스케줄링 되었는지 계산 후 리스트에 넣음
            ScheduledData scheduledData = new ScheduledData(currentProcess, currentTime, currentTime + currentProcess.getServiceTime());
            scheduledResult.add(scheduledData);

            // 현재 시간 동기화
            currentTime += currentProcess.getServiceTime();
        }

        return null;

    }

    @Override
    public double getAWT() {
        return 0;
    }

    @Override
    public double getATT() {
        return 0;
    }

    @Override
    public double getART() {
        return 0;
    }
}
