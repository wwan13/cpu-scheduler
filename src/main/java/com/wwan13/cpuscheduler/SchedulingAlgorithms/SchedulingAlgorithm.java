package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;

import java.util.List;

/**
 * SchedulingAlgorithm Interface
 * 스케줄링 알고리즘들을 정의한 인터페이스
 */
public interface SchedulingAlgorithm {

    public ResponseDto schedule(List<Process> processes, Integer timeSlice);

    public double getAWT(List<Process> processes);

    public double getATT(List<Process> processes);

    public double getART(List<Process> processes);

}
