package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FcfsAlgorithmTest {

    public List<Process> makeTestProcesses() {
        Process p1 = new Process("P1", 0, 30, 1);
        Process p2 = new Process("P3", 6, 9, 2);
        Process p3 = new Process("P2", 3, 18, 3);

        List<Process> processes = Arrays.asList(p1, p2, p3);

        return processes;
    }

    @Test
    public void fcfsAlgorithm() {
        List<Process> processes = makeTestProcesses();

        FcfsAlgorithm fcfsAlgorithm = new FcfsAlgorithm(processes);
        ResponseDto response = fcfsAlgorithm.schedule();

        assertThat(response.getAlgorithmType()).isEqualTo("FCFS");
        assertThat(response.getAWT()).isEqualTo(23);
        assertThat(response.getATT()).isEqualTo(42);
    }

}