package me.jaden.sc.scmerger.controller;

import me.jaden.sc.scmerger.services.PayloadUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class PayloadUplaodController {

    @Autowired
    PayloadUploadService payloadUploadService;

    @PostMapping("/payload/upload")
    @ResponseBody
    public void upload(@RequestParam("csvFile") MultipartFile csvFile, @RequestParam("xmlFile") MultipartFile xmlFile){

        try{
            File csvF = multipartFileToFile(csvFile);
            File xmlF = multipartFileToFile(xmlFile);
            payloadUploadService.mergeData(csvF, xmlF);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private File multipartFileToFile(MultipartFile source)throws IOException {
        File f = new File(source.getOriginalFilename());
        source.transferTo(f);
        return f;
    }
}
