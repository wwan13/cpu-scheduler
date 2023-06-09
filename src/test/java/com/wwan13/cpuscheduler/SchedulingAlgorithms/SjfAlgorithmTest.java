package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SjfAlgorithmTest extends SchedulingAlgorithmTest {

    @Test
    public void sjfAlgorithm() {

        List<Process> processes = this.testProcesses();

        SchedulingAlgorithm schedulingAlgorithm = new SjfAlgorithm(processes);
        ResponseDto response = schedulingAlgorithm.schedule();

        assertThat(response.getAlgorithmType()).isEqualTo("SJF");

        // 스케줄링 결과 테스트
        assertThat(response.getScheduledDataList().get(0).getStartAt()).isEqualTo(0);
        assertThat(response.getScheduledDataList().get(0).getEndAt()).isEqualTo(7);

        assertThat(response.getScheduledDataList().get(1).getStartAt()).isEqualTo(7);
        assertThat(response.getScheduledDataList().get(1).getEndAt()).isEqualTo(8);

        assertThat(response.getScheduledDataList().get(2).getStartAt()).isEqualTo(8);
        assertThat(response.getScheduledDataList().get(2).getEndAt()).isEqualTo(11);

        assertThat(response.getScheduledDataList().get(3).getStartAt()).isEqualTo(11);
        assertThat(response.getScheduledDataList().get(3).getEndAt()).isEqualTo(15);

        // 프로세스 별 대기시간, 반환시간, 응답시간 테스트
        assertThat(response.getProcesses().get(0).getWaitTime()).isEqualTo(0);
        assertThat(response.getProcesses().get(0).getTurnAroundTime()).isEqualTo(7);
        assertThat(response.getProcesses().get(0).getResponseTime()).isEqualTo(1);

        assertThat(response.getProcesses().get(1).getWaitTime()).isEqualTo(9);
        assertThat(response.getProcesses().get(1).getTurnAroundTime()).isEqualTo(13);
        assertThat(response.getProcesses().get(1).getResponseTime()).isEqualTo(10);

        assertThat(response.getProcesses().get(2).getWaitTime()).isEqualTo(4);
        assertThat(response.getProcesses().get(2).getTurnAroundTime()).isEqualTo(5);
        assertThat(response.getProcesses().get(2).getResponseTime()).isEqualTo(5);

        assertThat(response.getProcesses().get(3).getWaitTime()).isEqualTo(2);
        assertThat(response.getProcesses().get(3).getTurnAroundTime()).isEqualTo(5);
        assertThat(response.getProcesses().get(3).getResponseTime()).isEqualTo(3);

    }

    @Test
    public void sjfAlgorithm_sameTimeArrive_differentServiceTIme() {

        List<Process> processes = Arrays.asList(
                Process.builder().processId("P1").arrivalTime(0).serviceTime(30).priority(1).build(),
                Process.builder().processId("P3").arrivalTime(0).serviceTime(9).priority(1).build(),
                Process.builder().processId("P2").arrivalTime(3).serviceTime(18).priority(1).build()
        );

        SchedulingAlgorithm schedulingAlgorithm = new SjfAlgorithm(processes);
        ResponseDto responseDto = schedulingAlgorithm.schedule();

        Assertions.assertThat(responseDto.getScheduledDataList().get(0).getProcess())
                .isEqualTo(processes.get(1));
    }

}