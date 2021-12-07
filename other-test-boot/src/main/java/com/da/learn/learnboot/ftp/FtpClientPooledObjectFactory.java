package com.da.learn.learnboot.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;

@Slf4j
public class FtpClientPooledObjectFactory implements PooledObjectFactory<FTPClient> {

    private FtpProperties ftpProperties;

    public FtpClientPooledObjectFactory(FtpProperties ftpProperties) {
        this.ftpProperties = ftpProperties;
    }


    @Override
    public PooledObject<FTPClient> makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpProperties.getIp(), Integer.valueOf(ftpProperties.getPort()));
            ftpClient.login(ftpProperties.getUsername(), ftpProperties.getPassword());
            log.info("连接ftp服务返回码：" + ftpClient.getReplyCode());
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
            log.error("建立ftp连接失败！", (Object) e.getStackTrace());
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
            log.error("验证ftp连接失败！{}", e);
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
