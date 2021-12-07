package com.da.learn.learnboot.ftp;

import cn.hutool.extra.ftp.Ftp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/testFtp")
public class TestController {

    @Autowired
    private FtpProcessor ftpProcessor;

    @GetMapping("test")
    public void test(@RequestParam(value = "method", required = false) String method) throws Exception {
        switch (method) {
            case "uploadFile1":
                uploadFile1();
                break;
            case "uploadFile2":
                uploadFile2();
                break;
            case "downloadFile1":
                downloadFile1();
                break;
            case "downloadFile2":
                downloadFile2();
                break;
            case "deleteFile":
                deleteFile();
                break;
            case "createDirectory":
                createDirectory();
                break;
            case "existFile":
                existFile();
                break;
            case "readFileByLine":
                readFileByLine();
                break;
            case "retrieveFileNames":
                listFileNames();
                break;
            default:
                listFileNames();
        }
    }

    private void uploadFile1() {
        ftpProcessor.uploadFile("/ftp", "test1.txt", "D:\\Temp\\test1.txt");
    }

    private void uploadFile2() throws Exception {
        ftpProcessor.uploadFile("/ftp/test", "test222.txt", new FileInputStream(new File("D:\\Temp\\test2.txt")));
    }

    private void downloadFile1() {
        ftpProcessor.downloadFile("/ftp", "download1", "D:\\Temp");
    }

    private void downloadFile2() throws Exception{
        InputStream input = null;
        try {
            input = ftpProcessor.downloadFile("/ftp", "download2");
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println(reader.lines()
                    .map(StringUtils::trimToEmpty)
                    .filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toList()));
        } finally {
            if (input != null) {
                input.close();
            }
        }

    }

    private void deleteFile() throws Exception {
        ftpProcessor.deleteFile("/ftp", "forDelete");
    }

    private void createDirectory() throws Exception {
        System.out.println(ftpProcessor.createDirectory("/ftp/nextDir"));
        System.out.println(ftpProcessor.createDirectory("/ftp/nextDir2"));
        System.out.println(ftpProcessor.createDirectory("/ftp/nextDir2"));
    }

    private void existFile() throws Exception {
        System.out.println(ftpProcessor.existFile("/ftp/nextDir"));
        System.out.println(ftpProcessor.existFile("/ftp/nextDir/"));
        System.out.println(ftpProcessor.existFile("/ftp/download1"));
        System.out.println(ftpProcessor.existFile("/ftp/download1/"));
    }

    private void readFileByLine() throws Exception {
        System.out.println(ftpProcessor.readFileByLine("/ftp/download1"));
    }

    private void listFileNames() throws Exception {
        System.out.println(ftpProcessor.listFileNames("/ftp"));
    }

}
