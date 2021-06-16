package com.ty.wq.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Component
public class RedisUtils {

    private static RedisTemplate<String, Object> redisTemplate;

    @Resource
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    /**
     * 根据正则表达式，获取所有key值
     * @param patten
     * @return
     */
    public static Set<String> getAllKeys(String patten){
        return redisTemplate.keys(patten);
    }

    /**
     * 设置单个缓存
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     */
    public static void set(String key, Object value, long expire, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    /**
     *  普通缓存
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }


    /**
     * 按秒缓存
     * @param key
     * @param value
     * @param expire
     */
    public static void setSeconds(String key, Object value, long expire) {
        set(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * 按分钟缓存
     * @param key
     * @param value
     * @param expire
     */
    public static void setMinutes(String key, Object value, long expire) {
        set(key, value, expire, TimeUnit.MINUTES);
    }

    /**
     * 按小时缓存
     * @param key
     * @param value
     * @param expire
     */
    public static void setHours(String key, Object value, long expire) {
        set(key, value, expire, TimeUnit.HOURS);
    }

    /**
     * 通过key 获取value
     */

    public static Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public static boolean nSet(String key, Object value, long timeout, TimeUnit timeUnit){
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }

    /**
     * 设置过期时间
     * @param key
     * @param timeout
     * @param timeUnit
     */
    public static void setExpire(String key, long timeout, TimeUnit timeUnit){
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 获取过期时间
     * @param key
     * @return
     */
    public static Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断是否存在某个 key
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey == null ? false : hasKey;
    }

    /**
     * 删除某个 key
     * @param key
     */
    public static void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 给 value 自增1
     * @param key
     */
    public void increment(String key){
        redisTemplate.boundValueOps(key).increment(1);
    }

    /**
     * 给 value 减少1
     * @param key
     */
    public void decrement(String key){
        redisTemplate.boundValueOps(key).increment(-1);
    }


    /**
     * 添加
     * @param key
     * @param member
     * @param score
     */
    public static void zAdd(String key, Object member, double score) {
        redisTemplate.opsForZSet().add(key, member, score);
    }

    /**
     * 批量添加
     * @param key
     * @param typedTuples
     */
    public static void zmAdd(String key, Set<ZSetOperations.TypedTuple<Object>> typedTuples){
        if (typedTuples == null || typedTuples.size() == 0) {
            return;
        }
        redisTemplate.opsForZSet().add(key, typedTuples);
    }

    /**
     * 根据分数范围获取成员及分数
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double min, double max){
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * 根据分数范围获取成员
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<Object> zRangeByScore(String key, double min, double max){
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public static Set<Object> zReverseRangeByScore(String key, double min, double max){
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 删除所有
     * @param key
     */
    public static void zRemoveAllByScore(String key){
        redisTemplate.opsForZSet().removeRangeByScore(key,0,-1);
    }

    public static Set<Object> zGetReverseRange(String key, int start, int end){
        Set<Object> objects = redisTemplate.opsForZSet().reverseRange(key, start, end);
        return objects;
    }

    public static Set<Object> zGetRange(String key, int start, int end){
        Set<Object> objects = redisTemplate.opsForZSet().range(key, start, end);
        return objects;
    }

    public static Set<Object> zGetAllRange(String key){
        Set<Object> objects = redisTemplate.opsForZSet().range(key, 0,-1);
        return objects;
    }

    public static Double zGetScore(String key,Object member){
        return redisTemplate.opsForZSet().score(key, member);
    }

    /**
     * 判断zset中是否存在某个值
     * @param key
     * @param member
     * @return
     */
    public static boolean zExist(String key, Object member) {
        Long rank = redisTemplate.opsForZSet().rank(key, member);
        return rank != null;
    }

    /**
     * 删除某个成员
     * @param key
     * @param member
     */
    public static void zRem(String key, Object member) {
        redisTemplate.opsForZSet().remove(key, member);
    }

    public static Double zincrby(String key, Object member, double delta){
        return redisTemplate.opsForZSet().incrementScore(key, member, delta);
    }


    /*************************** opsForHash ************************************/

    public static void hSet(String hashKey, Object key, Object value) {
        redisTemplate.opsForHash().put(hashKey, key, value);
    }

    public static Object hGet(String hashKey, Object key) {
        return redisTemplate.opsForHash().get(hashKey, key);
    }

    public static List<Object> hGetAll(String key) {
        return redisTemplate.opsForHash().values(key);
    }


    /*************************** opsForList ************************************/

    public static void leftPush(String key, Object value){
        redisTemplate.opsForList().leftPush(key, value);
    }

    public static void rightPush(String key, Object value){
        redisTemplate.opsForList().rightPush(key, value);
    }

    public static Object rightPop(String key){
        return redisTemplate.opsForList().rightPop(key);
    }

}
