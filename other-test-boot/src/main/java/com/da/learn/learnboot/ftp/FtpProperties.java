package com.da.learn.learnboot.ftp;

import lombok.Data;

@Data
public class FtpProperties {
    private String  ip = "flyingzd.asuscomm.com";
    private String  port = "21";
    private String  username = "pi";
    private String  password = "ZhangDa@19881227";
    private Integer initialSize = 2;
    private String controlEncoding = "UTF-8";
    private Integer bufferSize = 4096;
    private Integer retryCount = 3;
    private String pathEncoding = "ISO-8859-1";
}
