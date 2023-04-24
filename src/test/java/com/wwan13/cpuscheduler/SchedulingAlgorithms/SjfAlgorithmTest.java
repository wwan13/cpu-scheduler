package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SjfAlgorithmTest {

    public List<Process> testProcesses() {
        List<Process> processes = Arrays.asList(
                new Process("P1", 0, 30, 1),
                new Process("P3", 6, 9, 2),
                new Process("P2", 3, 18, 3)
        );
        return processes;
    }

    @Test
    public void sjfAlgorithm() {

        List<Process> processes = testProcesses();

        SchedulingAlgorithm schedulingAlgorithm = new SjfAlgorithm(processes);
        schedulingAlgorithm.schedule();

    }

    // 두 프로세스가 0에 도착 하는데 두 프로세스의 작업 시간이 다른 경우
    // p1 0, 10    p2 0, 4

}