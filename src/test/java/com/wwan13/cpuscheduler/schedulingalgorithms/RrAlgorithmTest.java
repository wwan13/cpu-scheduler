package com.wwan13.cpuscheduler.schedulingalgorithms;

import com.wwan13.cpuscheduler.processes.Process;
import com.wwan13.cpuscheduler.processes.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class RrAlgorithmTest {

    public List<Process> testProcesses() {
        return Arrays.asList(
                Process.builder().processId("P1").arrivalTime(0).serviceTime(30).priority(1).build(),
                Process.builder().processId("P3").arrivalTime(6).serviceTime(9).priority(1).build(),
                Process.builder().processId("P2").arrivalTime(3).serviceTime(18).priority(1).build()
        );
    }

    @Test
    public void RrAlgorithm() {
        List<Process> processes = testProcesses();

        SchedulingAlgorithm schedulingAlgorithm = new RrAlgorithm(processes, 10);
        ResponseDto response = schedulingAlgorithm.schedule();
    }

}