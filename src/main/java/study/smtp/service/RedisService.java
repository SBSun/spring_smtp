package study.smtp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    public void setValues(String key, Object value, long expiration){
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expiration, TimeUnit.MILLISECONDS);
    }

    public Optional<Object> findByKey(String key){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Object value = valueOperations.get(key);

        if(value == null){
            return Optional.empty();
        }
        return Optional.of(value);
    }

    public void deleteByKey(String key){
        redisTemplate.delete(key);
    }
}
