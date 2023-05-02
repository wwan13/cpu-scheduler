package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;
import lombok.*;

import java.util.Comparator;
import java.util.List;

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
        return null;
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
}
