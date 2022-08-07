package com.storehouse.utils;

import org.bouncycastle.util.test.Test;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * Redis通用操作工具类
 *
 * @author JanYork
 */
@Component
public class RedisUtils {
    /**
     * redis 操作模板
     */
    private final StringRedisTemplate redisTemplate;

    //连接redis


    public RedisUtils(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 非Spring注入配置RedisTemplate
     *
     * @param connectionFactory JedisConnectionFactory对象
     * @return RedisTemplate对象
     */
    public static RedisTemplate getRedisTemplate(JedisConnectionFactory connectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        //必须执行这个函数,初始化RedisTemplate*
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 非Spring注入配置StringRedisTemplate
     *
     * @return redisUtils设置连接模板后对象
     */
    public static RedisUtils initPool() {
        Properties properties = new Properties();
        InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("Redis.properties");
        try {
            properties.load(inputStream);
            System.out.println("读取Redis.properties成功");
        } catch (IOException e) {
            System.out.println("读取Redis.properties异常");
        }
        //通过jedisConnectionFactory配置redis连接池
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(properties.getProperty("redis1.ip"));
        jedisConnectionFactory.setPort(Integer.parseInt(properties.getProperty("redis1.port")));
        //设置最大等待时间
        jedisConnectionFactory.setTimeout(Integer.parseInt(properties.getProperty("redis.max.millis")));
        //设置阻塞
        jedisConnectionFactory.setUsePool(true);
        //创建StringRedisTemplate
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory);
        RedisUtils redisUtils = new RedisUtils(stringRedisTemplate);
        return redisUtils;
    }

    // ------------------ key 相关操作 ------------------

    /**
     * 删除key
     *
     * @param key key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys key集合
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 序列化key
     *
     * @param key key
     * @return 字节数组
     */
    public byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * 是否存在key
     *
     * @param key key
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     *
     * @param key     key
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 是否成功
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置过期日期
     *
     * @param key  key
     * @param date 过期日期
     * @return 是否成功
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 查找匹配的key
     *
     * @param pattern 匹配规则
     * @return 结果集合
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中
     *
     * @param key     key
     * @param dbIndex 目标DB
     * @return 是否成功
     */
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 移除 key 的过期时间，key 将持久保持
     *
     * @param key key
     * @return 是否成功
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 返回 key 的剩余的过期时间（指定单位）
     *
     * @param key  key
     * @param unit 时间单位
     * @return 过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间（s）
     *
     * @param key key
     * @return 过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 从当前数据库中随机返回一个 key
     *
     * @return 随机的 key
     */
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 修改 key 的名称
     *
     * @param oldKey 原 key 名称
     * @param newKey 新 key 名称
     */
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
     *
     * @param oldKey 原 key 名称
     * @param newKey 新 key 名称
     * @return 是否成功
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回 key 所储存的值的类型
     *
     * @param key key
     * @return 数据类型
     */
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    // ------------------ string 相关操作 ------------------

    /**
     * 设置指定 key 的值
     *
     * @param key   key
     * @param value 值
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取指定 key 的值
     *
     * @param key key
     * @return 值
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key   key
     * @param start 开始位置
     * @param end   结束位置
     * @return 值
     */
    public String getRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * @param key   key
     * @param value 值
     * @return 旧值
     */
    public String getAndSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
     *
     * @param key    key
     * @param offset 偏移量
     * @return 是否是1
     */
    public Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 批量获取
     *
     * @param keys key 集合
     * @return 值列表
     */
    public List<String> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
     *
     * @param key   key
     * @param value 值,true为1, false为0
     * @return 是否成功
     */
    public Boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     *
     * @param key     key
     * @param value   值
     * @param timeout 过期时间
     * @param unit    时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
     *                秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     */
    public void setEx(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key   key
     * @param value 值
     * @return 之前已经存在返回false, 不存在返回true
     */
    public Boolean setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param key    key
     * @param value  值
     * @param offset 从指定位置开始覆写
     */
    public void setRange(String key, String value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取字符串的长度
     *
     * @param key key
     * @return 长度
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 批量添加
     *
     * @param maps k-v Map
     */
    public void multiSet(Map<String, String> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     *
     * @param maps k-v Map
     * @return 之前已经存在返回false, 不存在返回true
     */
    public Boolean multiSetIfAbsent(Map<String, String> maps) {
        return redisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key key
     * @return 变化后的值
     */
    public Long incrBy(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key key
     * @return 变化后的值
     */
    public Double incrByFloat(String key, double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 追加到末尾
     *
     * @param key   key
     * @param value 值
     * @return 结果
     */
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    // ------------------ hash 相关操作 ------------------

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key   key
     * @param field 字段
     * @return 对象
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key key
     * @return 字段-值
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key    key
     * @param fields 字段集合
     * @return 值列表
     */
    public List<Object> hMultiGet(String key, Collection<Object> fields) {
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * 设置 key 的 field 的值
     *
     * @param key   key
     * @param field 字段
     * @param value 值
     */
    public void hPut(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 批量设置 key 的 field 的值
     *
     * @param key  key
     * @param maps 字段-值
     */
    public void hPutAll(String key, Map<String, String> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * 仅当 field 不存在时才设置
     *
     * @param key   key
     * @param field 字段
     * @param value 值
     * @return 是否成功
     */
    public Boolean hPutIfAbsent(String key, String field, String value) {
        return redisTemplate.opsForHash().putIfAbsent(key, field, value);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key    key
     * @param fields 字段数组
     * @return 删除个数
     */
    public Long hDelete(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key   key
     * @param field 字段
     * @return 是否存在
     */
    public Boolean hExists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key       key
     * @param field     字段
     * @param increment 增量
     * @return 变化后的值
     */
    public Long hIncrBy(String key, Object field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key       key
     * @param field     字段
     * @param increment 增量
     * @return 变化后的值
     */
    public Double hIncrByFloat(String key, Object field, double increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key key
     * @return 数量
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希表中所有字段
     *
     * @param key key
     * @return key集合
     */
    public Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key key
     * @return 值集合
     */
    public List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key     key
     * @param options 扫描条件
     * @return 游标数据
     */
    public Cursor<Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    // ------------------ list 相关操作 ------------------

    /**
     * 通过索引获取列表中的元素
     *
     * @param key   key
     * @param index 索引
     * @return 值
     */
    public String lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param key   key
     * @param start 开始位置, 0是开始位置
     * @param end   结束位置, -1返回所有
     * @return 值集合
     */
    public List<String> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 存储在 list 头部
     *
     * @param key   key
     * @param value 值
     * @return 操作后 list 长度
     */
    public Long lLeftPush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 批量存储在 list 头部
     *
     * @param key   key
     * @param value 值数组
     * @return 操作后 list 长度
     */
    public Long lLeftPushAll(String key, String... value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 批量存储在 list 头部
     *
     * @param key   key
     * @param value 值集合
     * @return 操作后 list 长度
     */
    public Long lLeftPushAll(String key, Collection<String> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当 list 存在的时候才加入头部
     *
     * @param key   key
     * @param value 值
     * @return 操作后 list 长度
     */
    public Long lLeftPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 如果 pivot 存在,在 pivot 前面添加
     *
     * @param key   key
     * @param pivot 节点
     * @param value 值
     * @return 操作后 list 长度
     */
    public Long lLeftPush(String key, String pivot, String value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 存储在 list 尾部
     *
     * @param key   key
     * @param value 值
     * @return 操作后 list 长度
     */
    public Long lRightPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 批量存储在 list 尾部
     *
     * @param key   key
     * @param value 值数组
     * @return 操作后 list 长度
     */
    public Long lRightPushAll(String key, String... value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 批量存储在 list 尾部
     *
     * @param key   key
     * @param value 值集合
     * @return 操作后 list 长度
     */
    public Long lRightPushAll(String key, Collection<String> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 当 list 存在的时候才加入尾部
     *
     * @param key   key
     * @param value 值
     * @return 操作后 list 长度
     */
    public Long lRightPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 如果 pivot 存在,在 pivot 后面添加
     *
     * @param key   key
     * @param pivot 节点
     * @param value 值
     * @return 操作后 list 长度
     */
    public Long lRightPush(String key, String pivot, String value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key   key
     * @param index 位置
     * @param value 值
     */
    public void lSet(String key, long index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param key key
     * @return 删除的元素
     */
    public String lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return 值
     */
    public String lBLeftPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key key
     * @return 删除的元素
     */
    public String lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return 值
     */
    public String lBRightPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param sourceKey      源 key
     * @param destinationKey 目标 key
     * @return 值
     */
    public String lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
                destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey      源 key
     * @param destinationKey 目标 key
     * @param timeout        等待时间
     * @param unit           时间单位
     * @return 值
     */
    public String lBRightPopAndLeftPush(String sourceKey, String destinationKey,
                                        long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
                destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param key   key
     * @param index index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *              index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value 值
     * @return 操作后 list 长度
     */
    public Long lRemove(String key, long index, String value) {
        return redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * 裁剪list
     *
     * @param key   key
     * @param start 开始位置
     * @param end   结束位置
     */
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表长度
     *
     * @param key key
     * @return 长度
     */
    public Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    // ------------------ set 相关操作 ------------------

    /**
     * set添加元素
     *
     * @param key    key
     * @param values 值（变长）
     * @return 剩余数量
     */
    public Long sAdd(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * set移除元素
     *
     * @param key    key
     * @param values 值（变长）
     * @return 剩余数量
     */
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key key
     * @return 值
     */
    public String sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 将元素value从一个集合移到另一个集合
     *
     * @param key     源 key
     * @param value   值
     * @param destKey 目标 key
     * @return 是否成功
     */
    public Boolean sMove(String key, String value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 获取集合的大小
     *
     * @param key key
     * @return 数量
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含value
     *
     * @param key   key
     * @param value 值
     * @return 是否包含
     */
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取两个集合的交集
     *
     * @param key      key
     * @param otherKey 另一个 key
     * @return 值集合
     */
    public Set<String> sIntersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的交集
     *
     * @param key       key
     * @param otherKeys 另外的key集合
     * @return 值集合
     */
    public Set<String> sIntersect(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的交集存储到destKey集合中
     *
     * @param key      key
     * @param otherKey 另一个 key
     * @param destKey  目标 key
     * @return 数量
     */
    public Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * key集合与多个集合的交集存储到destKey集合中
     *
     * @param key       key
     * @param otherKeys 另外的key集合
     * @param destKey   目标 key
     * @return 数量
     */
    public Long sIntersectAndStore(String key, Collection<String> otherKeys,
                                   String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 获取两个集合的并集
     *
     * @param key      key
     * @param otherKey 另一个 key
     * @return 值集合
     */
    public Set<String> sUnion(String key, String otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的并集
     *
     * @param key       key
     * @param otherKeys 另外的key集合
     * @return 值集合
     */
    public Set<String> sUnion(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的并集存储到destKey中
     *
     * @param key      key
     * @param otherKey 另一个 key
     * @param destKey  目标 key
     * @return 数量
     */
    public Long sUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的并集存储到destKey中
     *
     * @param key       key
     * @param otherKeys 另外的 key 集合
     * @param destKey   目标 key
     * @return 数量
     */
    public Long sUnionAndStore(String key, Collection<String> otherKeys,
                               String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的差集
     *
     * @param key      key
     * @param otherKey 另一个 key
     * @return 值集合
     */
    public Set<String> sDifference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的差集
     *
     * @param key       key
     * @param otherKeys 另外的 key 集合
     * @return 值集合
     */
    public Set<String> sDifference(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的差集存储到destKey中
     *
     * @param key      key
     * @param otherKey 另一个 key
     * @param destKey  目标 key
     * @return 数量
     */
    public Long sDifference(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey,
                destKey);
    }

    /**
     * key集合与多个集合的差集存储到destKey中
     *
     * @param key       key
     * @param otherKeys 另外的 key 集合
     * @param destKey   目标 key
     * @return 数量
     */
    public Long sDifference(String key, Collection<String> otherKeys,
                            String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 获取集合所有元素
     *
     * @param key key
     * @return 值集合
     */
    public Set<String> setMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key key
     * @return 值
     */
    public String sRandomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取集合中count个元素
     *
     * @param key   key
     * @param count 要获取的数量
     * @return 值集合
     */
    public List<String> sRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     *
     * @param key   key
     * @param count 要获取的数量
     * @return 值集合
     */
    public Set<String> sDistinctRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * 扫描集合表中的值
     *
     * @param key     key
     * @param options 遍历条件
     * @return 游标数据
     */
    public Cursor<String> sScan(String key, ScanOptions options) {
        return redisTemplate.opsForSet().scan(key, options);
    }

    // ------------------ zSet 相关操作 ------------------

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key   key
     * @param value 值
     * @param score 权值
     * @return 是否成功
     */
    public Boolean zAdd(String key, String value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * @param key    key
     * @param values 值集合
     * @return 数量
     */
    public Long zAdd(String key, Set<TypedTuple<String>> values) {
        return redisTemplate.opsForZSet().add(key, values);
    }

    /**
     * @param key    key
     * @param values 值数组
     * @return 数量
     */
    public Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key   key
     * @param value 值
     * @param delta 增加的权值
     * @return 操作的权值
     */
    public Double zIncrementScore(String key, String value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @param key   key
     * @param value 值
     * @return 0表示第一位
     */
    public Long zRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key   key
     * @param value 值
     * @return 0表示第一位
     */
    public Long zReverseRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param key   key
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return 值集合
     */
    public Set<String> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 获取集合元素, 并且把score值也获取, 从小到大排序
     *
     * @param key   key
     * @param start 开始位置
     * @param end   结束位置
     * @return 值集合
     */
    public Set<TypedTuple<String>> zRangeWithScores(String key, long start,
                                                    long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 根据 Score 值查询集合元素, 从小到大排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return 值集合
     */
    public Set<String> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 根据 Score 值查询集合元素, 从小到大排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return 值集合
     */
    public Set<TypedTuple<String>> zRangeByScoreWithScores(String key,
                                                           double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * 根据 Score 值查询集合元素, 从小到大排序
     *
     * @param key   key
     * @param min   最小值
     * @param max   最大值
     * @param start 开始位置
     * @param end   结束位置
     * @return 值集合
     */
    public Set<TypedTuple<String>> zRangeByScoreWithScores(String key,
                                                           double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max,
                start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key   key
     * @param start 开始位置
     * @param end   结束位置
     * @return 值集合
     */
    public Set<String> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key   key
     * @param start 开始位置
     * @param end   结束位置
     * @return 值集合
     */
    public Set<TypedTuple<String>> zReverseRangeWithScores(String key,
                                                           long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start,
                end);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return 值集合
     */
    public Set<String> zReverseRangeByScore(String key, double min,
                                            double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return 值集合
     */
    public Set<TypedTuple<String>> zReverseRangeByScoreWithScores(
            String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,
                min, max);
    }

    /**
     * 根据 Score 值查询集合元素, 从大到小排序
     *
     * @param key   key
     * @param min   最小值
     * @param max   最大值
     * @param start 开始位置
     * @param end   结束位置
     * @return 值集合
     */
    public Set<String> zReverseRangeByScore(String key, double min,
                                            double max, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max,
                start, end);
    }


    /**
     * 根据score值获取集合元素数量
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return 值集合
     */
    public Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key key
     * @return 长度
     */
    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取集合大小
     *
     * @param key key
     * @return 长度
     */
    public Long zZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key   key
     * @param value 值
     * @return 权重值
     */
    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key   key
     * @param start 开始位置
     * @param end   结束位置
     * @return 剩余数量
     */
    public Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return 剩余数量
     */
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 把 key 和 otherKey 的并集并存储在 destKey 中
     *
     * @param key      key
     * @param otherKey 另一个 key
     * @param destKey  目标 key
     * @return 数量
     */
    public Long zUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 把 key 和另外一些 otherKey 的并集并存储在 destKey 中
     *
     * @param key       key
     * @param otherKeys 另外的 key 集合
     * @param destKey   目标 key
     * @return 数量
     */
    public Long zUnionAndStore(String key, Collection<String> otherKeys,
                               String destKey) {
        return redisTemplate.opsForZSet()
                .unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 把 key 和 otherKey 的交集集并存储在 destKey 中
     *
     * @param key      key
     * @param otherKey 另一个 key
     * @param destKey  目标 key
     * @return 数量
     */
    public Long zIntersectAndStore(String key, String otherKey,
                                   String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * 把 key 和另外一些 otherKey 的交集并存储在 destKey 中
     *
     * @param key       key
     * @param otherKeys 另外的 key 集合
     * @param destKey   目标 key
     * @return 数量
     */
    public Long zIntersectAndStore(String key, Collection<String> otherKeys,
                                   String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 扫描有序集合表中的值
     *
     * @param key     key
     * @param options 遍历条件
     * @return 游标数据
     */
    public Cursor<TypedTuple<String>> zScan(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }
}
