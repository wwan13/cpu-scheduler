package com.wwan13.cpuscheduler.Processes;

/**
 * Process Class
 */
public class Process {

    private String processId;       // 프로세스 id

    private Integer arrivalTime;    // 도착시간

    private Integer serviceTime;    // 서비스 시간

    private Integer priority;       // 우선순위

    private Integer waitTime;       // 대기시간

    private Integer responseTime;   // 응답시간

    private Integer turnAroundTime; // 반환시간

    public Process(String processId, Integer arrivalTime, Integer serviceTime, Integer priority) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.priority = priority;
        this.waitTime = null;
        this.responseTime = null;
        this.turnAroundTime = null;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public Integer getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Integer responseTime) {
        this.responseTime = responseTime;
    }

    public Integer getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(Integer turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }
}
