package com.nam.springbatchtest.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HouseJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean("houseJob")
    public Job houseJob(Step houseStep) {
        return jobBuilderFactory.get("houseJob")
                .incrementer(new RunIdIncrementer())
                .start(houseStep)
                .build();
    }

    @JobScope // job이 실행되는 동안에만 실행되게
    @Bean("houseStep")
    public Step houseStep(Tasklet tasklet) {
        return stepBuilderFactory.get("houseStep")
                .tasklet(tasklet)
                .build();
    }

    @StepScope // step이 살아있는 동안에만 실행
    @Bean
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("Spring Batch Start");
            return RepeatStatus.FINISHED;
        };
    }
}
