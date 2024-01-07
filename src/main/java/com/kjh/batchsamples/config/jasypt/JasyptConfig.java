package com.kjh.batchsamples.config.jasypt;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 서버 정보 암호화 하기위한 ConfigClass
 * 아래의 URL에서 encrypt decrypt 가능
 * https://www.devglan.com/online-tools/jasypt-online-encryption-decryption
 *
 * application-dev.yml 안에 있는 암호화된 DB 정보를 decrypt 하기 위해선 환경변수에서 값을 직접 입력받아야 합니다.
 */
@Configuration
@Slf4j
public class JasyptConfig {


    /**
     * 환경변수의 어떤 값을 키로 할것인가를 지정한다.
     * 예를들어 환경변수에 JASYPT_PASSWORD=1234 라고 저장되어있다면
     * 1234가 복호화 키이다.
     */
    private final String jasyptPassword = "JASYPT_PASSWORD";

    private final String jasyptAlgorithm = "PBEWithMD5AndDES";

    private final String keyHashingCount = "1000";

    private final String poolSize = "1";

    private final String providerName = "SunJCE";

    private final String saltGeneratorClassName = "org.jasypt.salt.RandomSaltGenerator";

    private final String ivGeneratorClassName = "org.jasypt.iv.NoIvGenerator";

    private final String stringOutputType = "base64";

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        log.info("jasyptStringEncryptor bean created");
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(System.getenv(jasyptPassword));
        config.setAlgorithm(jasyptAlgorithm);
        config.setKeyObtentionIterations(keyHashingCount);
        config.setPoolSize(poolSize);
        config.setProviderName(providerName);
        config.setSaltGeneratorClassName(saltGeneratorClassName);
        config.setIvGeneratorClassName(ivGeneratorClassName);
        config.setStringOutputType(stringOutputType);
        encryptor.setConfig(config);

        return encryptor;
    }

}
