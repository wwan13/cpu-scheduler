package com.wwan13.cpuscheduler.processes;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    private String processId;       // 프로세스 id

    private Integer arrivalTime;    // 도착시간

    private Integer serviceTime;    // 서비스 시간

    private Integer priority;       // 우선순위

    private Integer waitTime;       // 대기시간

    private Integer responseTime;   // 응답시간

    private Integer turnAroundTime; // 반환시간

}
