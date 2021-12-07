package com.da.utils.ftp;

import org.junit.Test;

import javax.annotation.Resource;

//@Configuration
////@ConditionalOnClass({GenericObjectPool.class, FTPClient.class})
////@ConditionalOnProperty(prefix = "ftp", name = "isopen", havingValue = "true", matchIfMissing = true)
//@EnableConfigurationProperties(HXFtpProperties.class)
public class FtpProcessorTest {

    private final XxFtpProperties xxFtpProperties = new XxFtpProperties();
//
//    @Autowired
//    public FtpConfig(HXFtpProperties hxFtpProperties) {
//        this.hxFtpProperties = hxFtpProperties;
//    }

//    @Bean(value = "xxFtpProcessor", destroyMethod = "destroy")
    public PooledFtpProcessor ftpProcessor() {
//        log.info("执行创建默认处理器");
        return FtpProcessorGenerator.getDefaultPooledFtpProcessor(xxFtpProperties);
    }



//    @Resource(name = "xxFtpProcessor")
//    private FtpProcessor ftpProcessor;

//    @Test
    public void test() throws Exception {
        PooledFtpProcessor ftpProcessor = FtpProcessorGenerator.getDefaultPooledFtpProcessor(xxFtpProperties);
        System.out.println(ftpProcessor.listFileNames("/"));
    }

}