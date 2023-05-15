package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PreemptivePriorityAlgorithmTest {

    public List<Process> testProcesses() {
        return Arrays.asList(
                Process.builder().processId("P1").arrivalTime(0).serviceTime(30).priority(3).build(),
                Process.builder().processId("P3").arrivalTime(6).serviceTime(9).priority(1).build(),
                Process.builder().processId("P2").arrivalTime(3).serviceTime(18).priority(2).build()
        );
    }

    @Test
    public void preemptivePriority() {
        List<Process> testProcesses = this.testProcesses();

        SchedulingAlgorithm schedulingAlgorithm = new PreemptivePriorityAlgorithm(testProcesses);

        schedulingAlgorithm.schedule();
    }

}