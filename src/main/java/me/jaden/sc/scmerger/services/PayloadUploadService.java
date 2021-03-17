package me.jaden.sc.scmerger.services;

import java.io.File;

public interface PayloadUploadService {

    void mergeData(File csvFile, File xmlFile);

    String getRepositoryPath();

}
