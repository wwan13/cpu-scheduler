package com.wwan13.cpuscheduler.schedulingalgorithms;

import com.wwan13.cpuscheduler.processes.Process;
import com.wwan13.cpuscheduler.processes.ResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PreemptivePriorityAlgorithmTest extends SchedulingAlgorithmTest{

    @Test
    public void preemptivePriority() {
        List<Process> testProcesses = this.testProcesses();

        SchedulingAlgorithm schedulingAlgorithm = new PreemptivePriorityAlgorithm(testProcesses);

        ResponseDto response = schedulingAlgorithm.schedule();

        assertThat(response.getAlgorithmType()).isEqualTo("PreemptivePriority");

        // 스케줄링 결과 테스트
        assertThat(response.getScheduledDataList().get(0).getProcess().getProcessId()).isEqualTo("P0");
        assertThat(response.getScheduledDataList().get(0).getStartAt()).isEqualTo(0);
        assertThat(response.getScheduledDataList().get(0).getEndAt()).isEqualTo(2);

        assertThat(response.getScheduledDataList().get(1).getProcess().getProcessId()).isEqualTo("P1");
        assertThat(response.getScheduledDataList().get(1).getStartAt()).isEqualTo(2);
        assertThat(response.getScheduledDataList().get(1).getEndAt()).isEqualTo(3);

        assertThat(response.getScheduledDataList().get(2).getProcess().getProcessId()).isEqualTo("P1");
        assertThat(response.getScheduledDataList().get(2).getStartAt()).isEqualTo(3);
        assertThat(response.getScheduledDataList().get(2).getEndAt()).isEqualTo(6);

        assertThat(response.getScheduledDataList().get(3).getProcess().getProcessId()).isEqualTo("P3");
        assertThat(response.getScheduledDataList().get(3).getStartAt()).isEqualTo(6);
        assertThat(response.getScheduledDataList().get(3).getEndAt()).isEqualTo(9);

        assertThat(response.getScheduledDataList().get(4).getProcess().getProcessId()).isEqualTo("P0");
        assertThat(response.getScheduledDataList().get(4).getStartAt()).isEqualTo(9);
        assertThat(response.getScheduledDataList().get(4).getEndAt()).isEqualTo(14);

        assertThat(response.getScheduledDataList().get(5).getProcess().getProcessId()).isEqualTo("P2");
        assertThat(response.getScheduledDataList().get(5).getStartAt()).isEqualTo(14);
        assertThat(response.getScheduledDataList().get(5).getEndAt()).isEqualTo(15);

        // 프로세스 별 대기시간, 반환시간, 응답시간 테스트
        assertThat(response.getProcesses().get(0).getWaitTime()).isEqualTo(7);
        assertThat(response.getProcesses().get(0).getTurnAroundTime()).isEqualTo(14);
        assertThat(response.getProcesses().get(0).getResponseTime()).isEqualTo(1);

        assertThat(response.getProcesses().get(1).getWaitTime()).isEqualTo(0);
        assertThat(response.getProcesses().get(1).getTurnAroundTime()).isEqualTo(4);
        assertThat(response.getProcesses().get(1).getResponseTime()).isEqualTo(1);

        assertThat(response.getProcesses().get(2).getWaitTime()).isEqualTo(11);
        assertThat(response.getProcesses().get(2).getTurnAroundTime()).isEqualTo(12);
        assertThat(response.getProcesses().get(2).getResponseTime()).isEqualTo(12);

        assertThat(response.getProcesses().get(3).getWaitTime()).isEqualTo(0);
        assertThat(response.getProcesses().get(3).getTurnAroundTime()).isEqualTo(3);
        assertThat(response.getProcesses().get(3).getResponseTime()).isEqualTo(1);
    }

}