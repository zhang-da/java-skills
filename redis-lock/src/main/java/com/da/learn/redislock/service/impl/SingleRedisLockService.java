package com.da.learn.redislock.service.impl;

import com.da.learn.redislock.service.RedisLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 单节点redis可用的分布式锁
 * 当使用集群redis时，可能会有漏洞
 */
@Service("SingleRedisLockService")
public class SingleRedisLockService implements RedisLockService {

    private static final String LOCK_KEY_PRE = "REDIS_LOCK_";

    private DefaultRedisScript<Long> script;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostConstruct
    private void init() {
        script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptText("" +
                "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end");
//        script.setScriptSource(new ResourceScriptSource(new
//                ClassPathResource("ipaccess.lua")));

    }


    @Override
    public boolean getLock(String lockName, String lockValue, long leaseTime, TimeUnit unit) {
        String key = LOCK_KEY_PRE.concat(lockName);
        return stringRedisTemplate.opsForValue().setIfAbsent(key, lockValue, leaseTime, unit);

//        long time = unit.toMillis(leaseTime);
//        Boolean setSuccess = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
//            JedisCommands command = (JedisCommands) connection.getNativeConnection();
//            String result = command.set(key, lockValue, "NX", "PX", time);
//            if (result == null) {
//                return false;
//            }
//            return "OK".equals(result);
//        });


//        Boolean aa = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
//            Object result = connection.execute("set", new byte[][]{
//                    SafeEncoder.encode("test_zd_key"), SafeEncoder.encode("haha"), SafeEncoder.encode("NX"),
//                    SafeEncoder.encode("PX"), Protocol.toByteArray(24 * 60 * 60 * 1000L)});
//            System.out.println(SafeEncoder.encode((byte[])result));
//            return true;
//        });

    }

    @Override
    public void unlock(String lockName, String lockValue) {
        String key = LOCK_KEY_PRE.concat(lockName);
        List<String> keys = new ArrayList<>();
        keys.add(key);
        stringRedisTemplate.execute(script, keys, lockValue);
    }
}
