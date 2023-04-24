package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FcfsAlgorithmTest {

    @Test
    public void fcfsAlgorithm() {
        List<Process> processes = new ArrayList<>();
        Process p1 = new Process("P1", 0, 30, 1);
        Process p2 = new Process("P3", 6, 9, 2);
        Process p3 = new Process("P2", 3, 18, 3);

        processes.add(p1);
        processes.add(p2);
        processes.add(p3);

//        FcfsAlgorithm fcfsAlgorithm = new FcfsAlgorithm();
//        fcfsAlgorithm.schedule(processes, 3);
    }

}