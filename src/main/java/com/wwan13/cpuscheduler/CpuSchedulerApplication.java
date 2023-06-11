package com.wwan13.cpuscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CpuSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CpuSchedulerApplication.class, args);
		System.out.println();
		System.out.println("--------[프로젝트 실행 방법]--------");
		System.out.println("1. 브라우저 (크롬, 엣지, 사파리 등) 접속");
		System.out.println("2. 주소창에 localhost:8080 입력 후 이동");
		System.out.println("-------------------------------");
		System.out.println();

	}

}
