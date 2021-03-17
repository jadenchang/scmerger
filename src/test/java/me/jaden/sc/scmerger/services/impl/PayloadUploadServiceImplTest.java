package me.jaden.sc.scmerger.services.impl;

import me.jaden.sc.scmerger.services.PayloadUploadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.net.URL;

@SpringBootTest
class PayloadUploadServiceImplTest {

    @Autowired
    PayloadUploadService payloadUploadService;

    @Test
    public void runService()throws Exception{
        URL path = this.getClass().getResource("/AB-5014.xml");
        File xmlFile = new File(path.getPath());

        URL csvPath = this.getClass().getResource("/AB-5014.csv");
        File csvFile = new File(csvPath.getPath());

        payloadUploadService.mergeData(csvFile, xmlFile);

        Assertions.assertNotNull(xmlFile);
        Assertions.assertNotNull(csvFile);

        Assertions.assertNotNull(payloadUploadService);

        File f = new File(payloadUploadService.getRepositoryPath());
        Assertions.assertTrue(f.exists());
        Assertions.assertTrue(f.isDirectory());
        Assertions.assertTrue(f.canWrite());

        File[] files = f.listFiles();
        Assertions.assertEquals(files.length, 2);


    }

}