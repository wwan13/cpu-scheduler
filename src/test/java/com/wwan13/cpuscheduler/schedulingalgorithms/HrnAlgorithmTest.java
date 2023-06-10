package com.wwan13.cpuscheduler.schedulingalgorithms;

import com.wwan13.cpuscheduler.processes.Process;
import com.wwan13.cpuscheduler.processes.ResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HrnAlgorithmTest extends SchedulingAlgorithmTest {

    @Test
    public void hrnAlgorithm() {
        List<Process> processes = this.testProcesses();
        SchedulingAlgorithm schedulingAlgorithm = new HrnAlgorithm(processes);

        ResponseDto response = schedulingAlgorithm.schedule();

        assertThat(response.getAlgorithmType()).isEqualTo("HRN");

        // 스케줄링 결과 테스트
        assertThat(response.getScheduledDataList().get(0).getStartAt()).isEqualTo(0);
        assertThat(response.getScheduledDataList().get(0).getEndAt()).isEqualTo(7);

        assertThat(response.getScheduledDataList().get(1).getStartAt()).isEqualTo(7);
        assertThat(response.getScheduledDataList().get(1).getEndAt()).isEqualTo(8);

        assertThat(response.getScheduledDataList().get(2).getStartAt()).isEqualTo(8);
        assertThat(response.getScheduledDataList().get(2).getEndAt()).isEqualTo(12);

        assertThat(response.getScheduledDataList().get(3).getStartAt()).isEqualTo(12);
        assertThat(response.getScheduledDataList().get(3).getEndAt()).isEqualTo(15);

        // 프로세스 별 대기시간, 반환시간, 응답시간 테스트
        assertThat(response.getProcesses().get(0).getWaitTime()).isEqualTo(0);
        assertThat(response.getProcesses().get(0).getTurnAroundTime()).isEqualTo(7);
        assertThat(response.getProcesses().get(0).getResponseTime()).isEqualTo(1);

        assertThat(response.getProcesses().get(1).getWaitTime()).isEqualTo(6);
        assertThat(response.getProcesses().get(1).getTurnAroundTime()).isEqualTo(10);
        assertThat(response.getProcesses().get(1).getResponseTime()).isEqualTo(7);

        assertThat(response.getProcesses().get(2).getWaitTime()).isEqualTo(4);
        assertThat(response.getProcesses().get(2).getTurnAroundTime()).isEqualTo(5);
        assertThat(response.getProcesses().get(2).getResponseTime()).isEqualTo(5);

        assertThat(response.getProcesses().get(3).getWaitTime()).isEqualTo(6);
        assertThat(response.getProcesses().get(3).getTurnAroundTime()).isEqualTo(9);
        assertThat(response.getProcesses().get(3).getResponseTime()).isEqualTo(7);
    }

}