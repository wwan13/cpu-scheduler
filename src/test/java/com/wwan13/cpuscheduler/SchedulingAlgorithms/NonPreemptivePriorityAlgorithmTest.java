package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NonPreemptivePriorityAlgorithmTest extends SchedulingAlgorithmTest {

    @Test
    public void NonPreemptivePriorityAlgorithm() {

        List<Process> testProcesses = this.testProcesses();

        SchedulingAlgorithm schedulingAlgorithm = new NonPreemptivePriorityAlgorithm(testProcesses);

        ResponseDto response = schedulingAlgorithm.schedule();

        assertThat(response.getAlgorithmType()).isEqualTo("NonPreemptivePriority");

        // 스케줄링 결과 테스트
        assertThat(response.getScheduledDataList().get(0).getStartAt()).isEqualTo(0);
        assertThat(response.getScheduledDataList().get(0).getEndAt()).isEqualTo(7);

        assertThat(response.getScheduledDataList().get(1).getStartAt()).isEqualTo(7);
        assertThat(response.getScheduledDataList().get(1).getEndAt()).isEqualTo(10);

        assertThat(response.getScheduledDataList().get(2).getStartAt()).isEqualTo(10);
        assertThat(response.getScheduledDataList().get(2).getEndAt()).isEqualTo(14);

        assertThat(response.getScheduledDataList().get(3).getStartAt()).isEqualTo(14);
        assertThat(response.getScheduledDataList().get(3).getEndAt()).isEqualTo(15);

        // 프로세스 별 대기시간, 반환시간, 응답시간 테스트
        assertThat(response.getProcesses().get(0).getWaitTime()).isEqualTo(0);
        assertThat(response.getProcesses().get(0).getTurnAroundTime()).isEqualTo(7);
        assertThat(response.getProcesses().get(0).getResponseTime()).isEqualTo(1);

        assertThat(response.getProcesses().get(1).getWaitTime()).isEqualTo(8);
        assertThat(response.getProcesses().get(1).getTurnAroundTime()).isEqualTo(12);
        assertThat(response.getProcesses().get(1).getResponseTime()).isEqualTo(9);

        assertThat(response.getProcesses().get(2).getWaitTime()).isEqualTo(11);
        assertThat(response.getProcesses().get(2).getTurnAroundTime()).isEqualTo(12);
        assertThat(response.getProcesses().get(2).getResponseTime()).isEqualTo(12);

        assertThat(response.getProcesses().get(3).getWaitTime()).isEqualTo(1);
        assertThat(response.getProcesses().get(3).getTurnAroundTime()).isEqualTo(4);
        assertThat(response.getProcesses().get(3).getResponseTime()).isEqualTo(2);

    }

}