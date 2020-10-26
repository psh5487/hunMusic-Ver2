package com.music.push.configure;

import lombok.Getter;
import lombok.Setter;
import nl.martijndwars.webpush.PushService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.Security;

@Component
@ConfigurationProperties(prefix = "webpush")
@Getter
@Setter
public class PushConfigure {

    private String publicKey;
    private String privateKey;

    @Bean
    public PushService pushService() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        PushService pushService = new PushService();

        pushService.setPublicKey(publicKey);
        pushService.setPrivateKey(privateKey);
        return pushService;
    }
}
