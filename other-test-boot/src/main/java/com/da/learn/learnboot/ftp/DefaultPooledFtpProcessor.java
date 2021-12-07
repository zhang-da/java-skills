package com.da.learn.learnboot.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.pool2.ObjectPool;
import org.springframework.util.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class DefaultPooledFtpProcessor implements PooledFtpProcessor<FTPClient> {

    private final FtpProperties ftpProperties;

    //连接池初始化标志
    private boolean hasInit = false;
    //连接池
    private ObjectPool<FTPClient> ftpClientPool;

    public DefaultPooledFtpProcessor(FtpProperties ftpProperties) {
        this.ftpProperties = ftpProperties;
    }

    @Override
    public void destroy() {
        if (null != ftpClientPool) {
            ftpClientPool.close();
            log.info("销毁ftp客户端连接池。。。");
        }
    }

    @Override
    public void setPool(ObjectPool<FTPClient> ftpClientPool) {
        this.ftpClientPool = ftpClientPool;
    }

    public void setHasInit(boolean hasInit) {
        this.hasInit = hasInit;
    }

    @Override
    public boolean uploadFile(String path, String fileName, String originFileName) {
        boolean flag = false;
        try {
            InputStream inputStream = new FileInputStream(originFileName);
            flag = uploadFile(path, fileName, inputStream);
        } catch (Exception e) {
            log.error("上传文件出错！{}", e);
        }
        return flag;
    }

    @Override
    public boolean uploadFile(String path, String fileName, InputStream inputStream) throws IOException {
        boolean flag = false;
        FTPClient ftpClient = getFtpClient();
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            createDirectory(path, ftpClient);
            ftpClient.changeWorkingDirectory(path);
            ftpClient.storeFile(fileName, inputStream);
            flag = true;
        } catch (Exception e) {
            log.error("上传文件出错！:{}", e);
        } finally {
            if (null != inputStream) inputStream.close();
            releaseFtpClient(ftpClient);
        }
        return flag;
    }

    @Override
    public boolean downloadFile(String path, String fileName, String localPath) {
        boolean flag = false;
        OutputStream outputStream = null;
        FTPClient ftpClient = getFtpClient();
        try {
            ftpClient.changeWorkingDirectory(path);
            FTPFile[] files = ftpClient.listFiles(fileName);
            if (files != null && files.length > 0) {
                File localFile = new File(localPath + "/" + fileName);
                outputStream = new FileOutputStream(localFile);
                ftpClient.retrieveFile(fileName, outputStream);
                outputStream.close();
            }
            flag = true;
        } catch (IOException e) {
            log.error("下载文件出错！{}", e);
        } finally {
            releaseFtpClient(ftpClient);
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("关闭输出流出错！{}", e);
                }
            }
        }
        return flag;
    }

    @Override
    public InputStream downloadFile(String path, String fileName) {
        FTPClient ftpClient = getFtpClient();
        try {
            ftpClient.changeWorkingDirectory(path);
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                if (fileName.equalsIgnoreCase(file.getName())) {
                    return ftpClient.retrieveFileStream(file.getName());
                }
            }
        } catch (IOException e) {
            log.error("下载文件出错！{}", e);
        } finally {
            releaseFtpClient(ftpClient);
        }
        return null;
    }

    @Override
    public boolean deleteFile(String path, String fileName) {
        boolean flag = false;
        FTPClient ftpClient = getFtpClient();
        try {
            ftpClient.changeWorkingDirectory(path);
            ftpClient.deleteFile(fileName);
            flag = true;
        } catch (IOException e) {
            log.error("删除文件出错！{}", e);
        } finally {
            releaseFtpClient(ftpClient);
        }
        return flag;
    }

    @Override
    public boolean createDirectory(String remote) throws IOException {
        FTPClient ftpClient = getFtpClient();
        try {
            return createDirectory(remote, ftpClient);
        } finally {
            releaseFtpClient(ftpClient);
        }
    }

    @Override
    public boolean existFile(String path) throws IOException {
        FTPClient ftpClient = getFtpClient();
        try {
            return existFile(path, ftpClient);
        } finally {
            releaseFtpClient(ftpClient);
        }
    }

    private boolean createDirectory(String remote, FTPClient ftpClient) throws IOException {
        return ftpClient.makeDirectory(remote);
    }

    private boolean existFile(String path, FTPClient ftpClient) throws IOException {
        boolean flag = false;
        FTPFile[] files = ftpClient.listFiles(path);
        if (files.length > 0) {
            flag = true;
        }
        return flag;
    }


    /**
     * 按行读取FTP文件
     *
     * @param remoteFilePath ftp路径
     */
    public List<String> readFileByLine(String remoteFilePath) throws IOException {
        FTPClient ftpClient = getFtpClient();
        try (InputStream inputStream = ftpClient.retrieveFileStream(encodingPath(remoteFilePath));
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .map(StringUtils::trimToEmpty)
                    .filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toList());
        } finally {
            ftpClient.completePendingCommand();
            releaseFtpClient(ftpClient);
        }
    }

    @Override
    public List<String> listFileNames(String remotePath) throws IOException {
        FTPFile[] files = listFtpFiles(remotePath);
        if (files == null || files.length == 0) {
            return new ArrayList<>();
        }
        return Arrays.stream(files).filter(Objects::nonNull).map(FTPFile::getName).collect(Collectors.toList());
    }

    /**
     * 获取指定路径下的ftp文件
     *
     * @param remotePath 指定路径
     */
    private FTPFile[] listFtpFiles(String remotePath) throws IOException {
        FTPClient ftpClient = getFtpClient();
        try {
            return ftpClient.listFiles(encodingPath(remotePath + "/"), file -> file != null && file.getSize() > 0);
        } finally {
            releaseFtpClient(ftpClient);
        }
    }

    /**
     * 获取编码后的文件路径
     */
    private String encodingPath(String path) throws UnsupportedEncodingException {
        //在FTP协议中，规定文件名编码格式为ISO-8859-1，所以目录名或文件名需要转码
        return new String(path.replaceAll("//", "/").getBytes(ftpProperties.getControlEncoding()), ftpProperties.getPathEncoding());
    }

    /**
     * 获取ftp客户端
     */
    private FTPClient getFtpClient() {
        checkFtpClientPoolAvailable();
        FTPClient ftpClient = null;
        Exception exception = null;
        //获取连接，做多尝试n次
        try {
            for (int i = 0; i < ftpProperties.getRetryCount(); i++) {
                ftpClient = ftpClientPool.borrowObject();
                ftpClient.enterLocalPassiveMode();//设置为被动模式
                ftpClient.changeWorkingDirectory("/");
                break;
            }
        } catch (Exception e) {
            log.error("无法在连接池中获取ftp客户端！{}", e);
            exception = e;
        }
        if (null == ftpClient) {
            throw new RuntimeException("无法在连接池中获取ftp客户端", exception);
        }
        System.out.println("=========test=========" + ftpClient);
        return ftpClient;
    }

    /**
     * 释放ftp客户端
     *
     * @param ftpClient
     */
    private void releaseFtpClient(FTPClient ftpClient) {
        if (null != ftpClient) {
            try {
                //从ftp连接池中移除ftp客户端
                ftpClientPool.returnObject(ftpClient);
            } catch (Exception e) {
                try {
                    //判断客户端是否可用
                    if (ftpClient.isAvailable()) {
                        //销毁连接
                        ftpClient.disconnect();
                    }
                } catch (IOException ex) {
                    log.error("销毁ftp连接失败！{}", e);
                }
                log.error("从ftp连接池移除ftp客户端失败！{}", e);
            }
        }
    }


    /**
     * 检查ftp连接池是否可用
     */
    private void checkFtpClientPoolAvailable() {
        Assert.state(hasInit, "ftp未启用或连接失败！");
    }

}
