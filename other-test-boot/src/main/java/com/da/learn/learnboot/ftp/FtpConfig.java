package com.da.learn.learnboot.ftp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
//@ConditionalOnClass({GenericObjectPool.class, FTPClient.class})
//@ConditionalOnProperty(prefix = "ftp", name = "isopen", havingValue = "true", matchIfMissing = true)
//@EnableConfigurationProperties(FtpProperties.class)
public class FtpConfig {

    private final FtpProperties ftpProperties = new FtpProperties();


    /**
     * 判断不存在业务Service时初始化默认Bean到Spring
     */
    @Bean(destroyMethod = "destroy")
//    @ConditionalOnMissingBean(FtpProcessor.class)
    public PooledFtpProcessor ftpProcessor() {
        log.info("执行创建默认处理器");
        return FtpProcessorGenerator.getDefaultPooledFtpProcessor(ftpProperties);
    }
}
