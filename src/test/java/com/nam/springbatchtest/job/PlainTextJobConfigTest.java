package com.nam.springbatchtest.job;

import com.nam.springbatchtest.BatchTestConfig;
import com.nam.springbatchtest.core.domain.PlainText;
import com.nam.springbatchtest.core.repository.PlainTextRepository;
import com.nam.springbatchtest.core.repository.ResultTextRespository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.IntStream;


@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {PlainTextJobConfig.class, BatchTestConfig.class})
public class PlainTextJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PlainTextRepository plainTextRepository;

    @Autowired
    private ResultTextRespository resultTextRespository;
    
    // 각각의 수행 다음에 데이터를 삭제
    @AfterEach
    public void tearDown() {
        plainTextRepository.deleteAll();
        resultTextRespository.deleteAll();
    }

    @Test
    public void success_givenNoPlainText() throws Exception {
        // given
        // no plainText
        // when
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        Assertions.assertEquals(resultTextRespository.count(), 0);
    }

    @Test
    public void success_givenPlainText() throws Exception {
        // given
    givenPlainTexts(12);
        // when
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        Assertions.assertEquals(resultTextRespository.count(), 12);
    }

    private void givenPlainTexts(Integer count) {
        IntStream.range(0, count)
                .forEach(
                    num -> plainTextRepository.save(new PlainText(null, "text" + num)));
    }
}
