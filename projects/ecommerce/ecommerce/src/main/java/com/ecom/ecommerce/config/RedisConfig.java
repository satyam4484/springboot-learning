package com.ecom.ecommerce.config;

import java.time.Duration;

import com.ecom.ecommerce.dto.CategoryDto;
import com.ecom.ecommerce.dto.ProductDto;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory connectionFactory) {

        JacksonJsonRedisSerializer<ProductDto> productSerializer =
                new JacksonJsonRedisSerializer<>(ProductDto.class);

        JacksonJsonRedisSerializer<CategoryDto> categorySerializer =
                new JacksonJsonRedisSerializer<>(CategoryDto.class);

        RedisCacheConfiguration productConfig =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(productSerializer)
                        );

        RedisCacheConfiguration categoryConfig =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(30))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(categorySerializer)
                        );

        return RedisCacheManager.builder(connectionFactory)
                .withCacheConfiguration(
                        "products",
                        productConfig
                )
                .withCacheConfiguration(
                        "categories",
                        categoryConfig
                )
                .withCacheConfiguration(
                        "productList",
                        productConfig
                )
                .build();
    }
}