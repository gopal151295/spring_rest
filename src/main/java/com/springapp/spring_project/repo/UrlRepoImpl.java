package com.springapp.spring_project.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class UrlRepoImpl implements UrlRepo {

    private RedisTemplate<String, String> redisTemplate;
    private ValueOperations valueOperations;

    @Autowired
    public UrlRepoImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void saveUrl(String key, String value) {
        valueOperations.set(key, value);
        redisTemplate.expire(key, 604800,  TimeUnit.SECONDS);
    }

    @Override
    public List getAllUrls() {
        return null;
    }

    @Override
    public String getUrl(String key) {
        return null;
    }

    @Override
    public void updateUrl(String key) {

    }
}
