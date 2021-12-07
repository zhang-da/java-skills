package com.da.utils.ftp;

public class FtpProperties {
    private String ip = "localhost";
    private String port = "21";
    private String username = "ftp";
    private String password = "ftp";
    private Integer initialSize = 2;
    private String controlEncoding = "UTF-8";
    private Integer bufferSize = 4096;
    private Integer retryCount = 3;
    private String pathEncoding = "ISO-8859-1";

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public String getControlEncoding() {
        return controlEncoding;
    }

    public void setControlEncoding(String controlEncoding) {
        this.controlEncoding = controlEncoding;
    }

    public Integer getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public String getPathEncoding() {
        return pathEncoding;
    }

    public void setPathEncoding(String pathEncoding) {
        this.pathEncoding = pathEncoding;
    }
}
