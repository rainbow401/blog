package com.blog.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "auth")
@Component
public class AuthProperties {

    private Long exp;

    private String secret;

    private String issuer;

    private List<String> excludePath;

    private List<String> includePath;
}
