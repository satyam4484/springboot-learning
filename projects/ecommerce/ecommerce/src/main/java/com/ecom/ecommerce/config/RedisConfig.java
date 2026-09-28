package com.ecom.ecommerce.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory connectionFactory) {

        GenericJacksonJsonRedisSerializer jsonSerializer =
                GenericJacksonJsonRedisSerializer.builder()
                        .build();

        RedisSerializationContext.SerializationPair<Object> serializationPair =
                RedisSerializationContext.SerializationPair
                        .fromSerializer(jsonSerializer);

        RedisCacheConfiguration productConfig =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10))
                        .serializeValuesWith(serializationPair);

        RedisCacheConfiguration categoryConfig =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(30))
                        .serializeValuesWith(serializationPair);

        return RedisCacheManager.builder(connectionFactory)
                .withCacheConfiguration("products", productConfig)
                .withCacheConfiguration("productList", productConfig)
                .withCacheConfiguration("categories", categoryConfig)
                .build();
    }
}