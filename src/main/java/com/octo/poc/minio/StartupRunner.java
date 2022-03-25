package com.octo.poc.minio;

import com.octo.poc.minio.service.MinIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {

    @Autowired
    private MinIOService minIOService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String fileName = "test.txt";
        minIOService.uploadToMinio(fileName);
        minIOService.downloadFromMinio(fileName);
    }
}
