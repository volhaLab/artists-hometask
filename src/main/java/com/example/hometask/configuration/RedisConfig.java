package com.example.hometask.configuration;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.example.hometask.remote.ItunesResponse;

@Configuration
public class RedisConfig {

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(RedisProperties redisProperties) {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                redisProperties.getHost(),
                redisProperties.getPort()
        );
        redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
        redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
        return redisStandaloneConfiguration;
    }

    @Bean
    public RedisTemplate<String, ItunesResponse> redisItunesTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, ItunesResponse> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, String> template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
