package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SjfAlgorithmTest {

    public List<Process> testProcesses() {
        return Arrays.asList(
                Process.builder().processId("P1").arrivalTime(0).serviceTime(30).priority(1).build(),
                Process.builder().processId("P3").arrivalTime(6).serviceTime(9).priority(1).build(),
                Process.builder().processId("P2").arrivalTime(3).serviceTime(18).priority(1).build()
        );
    }

    @Test
    public void sjfAlgorithm() {

        List<Process> processes = testProcesses();

        SchedulingAlgorithm schedulingAlgorithm = new SjfAlgorithm(processes);
        ResponseDto responseDto = schedulingAlgorithm.schedule();


        Assertions.assertThat(responseDto.getAlgorithmType()).isEqualTo("SJF");
        Assertions.assertThat(responseDto.getScheduledDataList().get(0).getProcess())
                .isEqualTo(processes.get(0));

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