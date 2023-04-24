package com.wwan13.cpuscheduler.Processes;

/**
 * ScheduledData Class
 * 스케줄링 결과를 담은 데이터 클래스
 */
public class ScheduledData {

    private Process process;    // 해당 프로세스

    private Integer startAt;    // 시작 시간

    private Integer endAt;      // 종료 시간

    public ScheduledData(Process process, Integer startAt, Integer endAt) {
        this.process = process;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Integer getStartAt() {
        return startAt;
    }

    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }

    public Integer getEndAt() {
        return endAt;
    }

    public void setEndAt(Integer endAt) {
        this.endAt = endAt;
    }
}
