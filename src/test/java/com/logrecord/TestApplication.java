package com.logrecord;

import com.logrecord.annotation.LogRecordAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yeyu
 * @since 2022-05-11 14:44
 */
@SpringBootApplication(scanBasePackages = "com.logrecord")
@RestController
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class,args);
    }

    @PostMapping("/test")
    @LogRecordAnnotation(
            bizNo = "#param.bizNo",
            content = "测试#{#oldData.name}",
            serviceKey = "TEST",
            needOld = true
    )
    public void logTest(@RequestBody TestParam param) {
        System.out.println(param);
    }
}
