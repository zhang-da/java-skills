package com.da.utils.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FtpClientPooledObjectFactory implements PooledObjectFactory<FTPClient> {

    private static final Logger logger = LoggerFactory.getLogger(FtpClientPooledObjectFactory.class);

    private final FtpProperties ftpProperties;

    public FtpClientPooledObjectFactory(FtpProperties ftpProperties) {
        this.ftpProperties = ftpProperties;
    }


    @Override
    public PooledObject<FTPClient> makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpProperties.getIp(), Integer.parseInt(ftpProperties.getPort()));
            ftpClient.login(ftpProperties.getUsername(), ftpProperties.getPassword());
            logger.info("连接ftp服务返回码：{}", ftpClient.getReplyCode());
            ftpClient.setBufferSize(ftpProperties.getBufferSize());
            ftpClient.setControlEncoding(ftpProperties.getControlEncoding());
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            return new DefaultPooledObject<>(ftpClient);
        } catch (Exception e) {
            if (ftpClient.isAvailable()) {
                ftpClient.disconnect();
            }
            ftpClient = null;
            logger.error("建立ftp连接失败！{}", e.getMessage());
            throw new Exception("建立ftp连接失败！{}", e);
        }
    }

    @Override
    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = getObject(p);
        if (null != ftpClient && ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
        ftpClient = null;
    }

    @Override
    public boolean validateObject(PooledObject<FTPClient> p) {
        FTPClient ftpClient = getObject(p);
        if (null == ftpClient || !ftpClient.isConnected()) {
            return false;
        }
        try {
            ftpClient.changeWorkingDirectory("/");
            return true;
        } catch (IOException e) {
            logger.error("验证ftp连接失败！{}", e.getMessage());
            return false;
        }
    }

    @Override
    public void activateObject(PooledObject<FTPClient> p) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<FTPClient> p) throws Exception {

    }

    private FTPClient getObject(PooledObject<FTPClient> pooledObject) {
        if (null == pooledObject || null == pooledObject.getObject()) {
            return null;
        }
        return pooledObject.getObject();
    }
}
