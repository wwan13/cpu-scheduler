package com.wwan13.cpuscheduler.Processes;

public class Process {

    private String processId;

    private Integer arrivalTime;

    private Integer serviceTime;

    private Integer priority;

    public Process(String processId, Integer arrivalTime, Integer serviceTime, Integer priority) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.priority = priority;
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
}
