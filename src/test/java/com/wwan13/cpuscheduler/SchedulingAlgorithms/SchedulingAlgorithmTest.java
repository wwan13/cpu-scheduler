package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SchedulingAlgorithmTest {

    public List<Process> testProcesses() {
        return Arrays.asList(
                Process.builder().processId("P0").arrivalTime(0).serviceTime(7).priority(3).build(),
                Process.builder().processId("P1").arrivalTime(2).serviceTime(4).priority(2).build(),
                Process.builder().processId("P2").arrivalTime(3).serviceTime(1).priority(4).build(),
                Process.builder().processId("P3").arrivalTime(6).serviceTime(3).priority(1).build()
        );
    }

}
