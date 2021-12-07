package com.da.learn.learnboot.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FtpProcessor {

    /**
     * 上传文件
     *
     * @param path           ftp服务器保存地址
     * @param fileName       上传到ftp的文件名
     * @param originFileName 等待上传的文件名（绝对地址或路径）
     */
    boolean uploadFile(String path, String fileName, String originFileName);

    /**
     * 上传文件
     *
     * @param path        ftp服务器保存地址
     * @param fileName    上传到ftp的文件名
     * @param inputStream 文件流
     */
    boolean uploadFile(String path, String fileName, InputStream inputStream) throws IOException;

    /**
     * 下载文件
     *
     * @param path      ftp服务器文件路径
     * @param fileName  文件名称
     * @param localPath 下载后的路径
     */
    boolean downloadFile(String path, String fileName, String localPath);

    /**
     * 下载文件
     *
     * @param path     ftp服务器文件路径
     * @param fileName 文件名称
     * @return 文件流
     */
    // TODO: 2021/7/28 不好用，有bug
    InputStream downloadFile(String path, String fileName);

    /**
     * 删除文件
     *
     * @param path     ftp文件路径
     * @param fileName 文件名
     */
    boolean deleteFile(String path, String fileName);

    /**
     * 创建多层目录，如果ftp服务器已存在该目录，则不创建，如果没有，则创建
     *
     * @param remote 创建的目录
     */
    boolean createDirectory(String remote) throws IOException;

    /**
     * 判断ftp服务器的文件是否存在(路径暂时不支持)
     *
     * @param path
     */
    boolean existFile(String path) throws IOException;


    /**
     * 按行读取FTP文件
     *
     * @param remoteFilePath ftp路径
     */
    List<String> readFileByLine(String remoteFilePath) throws IOException;

    /**
     * 获取指定ftp路径下的文件名称
     *
     * @param remotePath 指定ftp路径
     */
    List<String> listFileNames(String remotePath) throws IOException;

}
