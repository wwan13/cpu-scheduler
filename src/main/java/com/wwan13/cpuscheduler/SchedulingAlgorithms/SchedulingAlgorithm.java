package com.wwan13.cpuscheduler.SchedulingAlgorithms;

import com.wwan13.cpuscheduler.Processes.Process;
import com.wwan13.cpuscheduler.Processes.ResponseDto;

import java.util.List;

public interface SchedulingAlgorithm {

    public ResponseDto schedule();

}
