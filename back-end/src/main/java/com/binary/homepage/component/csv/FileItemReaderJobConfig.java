package com.binary.homepage.component.csv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileItemReaderJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CsvReader csvReader;
    private final CsvWriter csvWriter;

    @Bean
    public Job csvFileItemReaderJob() throws MalformedURLException {
        return jobBuilderFactory.get("csvFileItemReaderJob")
                .start(csvFileItemReaderStep())
                .build();
    }

    @Bean
    public Step csvFileItemReaderStep() throws MalformedURLException {
        return stepBuilderFactory.get("csvFileItemReaderStep")
                .<MemberCsvForm, MemberCsvForm>chunk(100)
                .reader(csvReader.csvFileItemReader())
                .writer(csvWriter)
                .allowStartIfComplete(false)
                .build();
    }

}
