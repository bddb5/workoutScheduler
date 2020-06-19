package com.schedule.workout.workoutScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class WorkoutSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkoutSchedulerApplication.class, args);
	}

}
