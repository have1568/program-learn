## 文档与书籍

* 《大数据高并发Redis一本通笔记》

* [Redis文档中心 -- Redis中国用户组（CRUG）](http://www.redis.cn/documentation.html)

## 1. Redis数据结构

### 1.1 String 类型选择规则

| DataType | 数值特性                                                             | 数值选择机制               |
| -------- | ------------------------------------------------------------------------ | -------------------------------- |
| int      | 节省内存，最高效                                                 | 数值long类型以内，长度不超过20位 |
| embstr   | RedisObject和SDS（简单动态字符串）连续，分配内存空间一次,提升CPU缓存效率 | 小字符串,长度小于44字节 |
| raw      | RedisObject和SDS（简单动态字符串）不连续，2次内存分配和2块空间 | 长度大于44字节的字符串 |

### 1.2 List类型选择规则

| 数据类型 | 特性                                                                                | 选择机制     |
| --------- | ------------------------------------------------------------------------------------- | ---------------- |
| QuickList | 多个Ziplist通过LinkList（双向）连接                                         | 元素长度大使用 |
| ZipList   | 双向压缩链表，每个节点的占用空间不相等，会压缩多余的空间,元素内存复制问题、查找效率低 | 元素长度小时使用 |

### 1.3 Hash类型选择规则

| DataType  | 特性                                                      |    选择机制   |
| --------- | -------------------------------------------------------- | ------------- |
| ZipList   | 同上                                                   | 内容64字节一下 |
| HashTable | 哈希表，有Hash碰撞问题，通过链表发解决                      | 内容超过64字节 |

### 1.4 Set类型选择规则

| DataType  | 特性                                 | 选择机制                         |
| --------- | -------------------------------------- | ------------------------------------ |
| intSet    | 元素是long类型以内的数值类型 | 数值类型long以内               |
| HashTable | 哈希表，有Hash碰撞问题，通过链表发解决 | 不是数值类型，或者比long大的数值类型 |

### 1.5 Zset类型选择规则

| DataType | 特性                     | 选择机制                                                  |
| -------- | -------------------------- | ------------------------------------------------------------- |
| ZipList  | 同上                     | 长度小于128，内容字节数64以内                     |
| SkipList | 层级结构，有效提升查询效率 | 长度大于128或者内容64字节以上，转成跳跃表之后，不会转会压缩表 |

### 数据操作

#### Strings

* 一个字符串类型的值最多能存储512M字节的内容。
* 利用`INCR`命令簇（`INCR`, `DECR`, `INCRBY`）来把字符串当作原子计数器使用。
* 使用APPEND命令在字符串后添加内容。
* 将字符串作为`GETRANGE` 和 `SETRANGE` 的随机访问向量。
* 在小空间里编码大量数据，或者使用 `GETBIT` 和 `SETBIT` 创建一个Redis支持的`Bloom过滤器`。

#### Lists

* 列表的数据结构是双向链表。
* 一个列表最多可以包含232-1个元素（4294967295，每个表超过40亿个元素）。
* 从时间复杂度的角度来看，Redis列表主要的特性就是支持时间常数的 插入和靠近头尾部元素的删除，即使是需要插入上百万的条目。 访问列表两端的元素是非常快的，但如果你试着访问一个非常大
  的列表的中间元素仍然是十分慢的，因为那是一个时间复杂度为 O(N) 的操作。
* 在社交网络中建立一个时间线模型，使用LPUSH去添加新的元素到用户时间线中，使用`LRANGE`去检索一些最近插入的条目。
* 你可以同时使用`LPUSH`和`LTRIM`去创建一个永远不会超过指定元素数目的列表并同时记住最后的N个元素。
* 列表可以用来当作消息传递的基元（primitive），例如，众所周知的用来创建后台任务的Resque Ruby库。

#### Sets

* Redis集合是一个无序的字符串合集。你可以以`O(1)` 的时间复杂度（无论集合中有多少元素时间复杂度都为常量）完成 添加，删除以及测试元素是否存在的操作。
* Redis集合有着不允许相同成员存在的优秀特性。向集合中多次添加同一元素，在集合中最终只会存在一个此元素。实际上这就意味着，在添加元素前，你并不需要事先进行检验此元素是否已经存在的操作。
* 一个Redis列表十分有趣的事是，它们支持一些服务端的命令从现有的集合出发去进行集合运算。 所以你可以在很短的时间内完成合并（union）,求交(intersection), 找出不同元素的操作。
* 一个集合最多可以包含232-1个元素（4294967295，每个集合超过40亿个元素）。

#### Hashes

* Redis Hashes是字符串字段和字符串值之间的映射，所以它们是完美的表示对象（eg:一个有名，姓，年龄等属性的用户）的数据类型。
* 一个拥有少量（100个左右）字段的hash需要 很少的空间来存储，所有你可以在一个小型的 Redis实例中存储上百万的对象。
* 尽管Hashes主要用来表示对象，但它们也能够存储许多元素，所以你也可以用Hashes来完成许多其他的任务。
* 一个hash最多可以包含232-1 个key-value键值对（超过40亿）。

#### Sorted sets

* Redis有序集合和Redis集合类似，是不包含 相同字符串的合集。它们的差别是，每个有序集合 的成员都关联着一个评分，这个评分用于把有序集 合中的成员按最低分到最高分排列。
* 使用有序集合，你可以非常快地（`O(log(N))`）完成添加，删除和更新元素的操作。 因为元素是在插入时就排好序的，所以很快地通过评分(score)或者 位次(position)获得一个范围的元素。
  访问有序集合的中间元素同样也是非常快的，因此你可以使用有序集合作为一个没用重复成员的智能列表。 在这个列表中， 你可以轻易地访问任何你需要的东西: 有序的元素，快速的存在性测试，快速访问集合中间元素！
* 简而言之，使用有序集合你可以很好地完成 很多在其他数据库中难以实现的任务。

#### Bitmap

#### HyperLogLogs

#### Geospatial

#### Spring Data Redis里操作各种数据类型的几口`RedisTemplate`

```java
public class RedisTemplate<K, V> extends RedisAccessor implements RedisOperations<K, V>, BeanClassLoaderAware {

    /**
     * 操作各种数据类型的接口
     */
    private final ValueOperations<K, V> valueOps = new DefaultValueOperations<>(this);
    private final ListOperations<K, V> listOps = new DefaultListOperations<>(this);
    private final SetOperations<K, V> setOps = new DefaultSetOperations<>(this);
    private final StreamOperations<K, ?, ?> streamOps = new DefaultStreamOperations<>(this, new ObjectHashMapper());
    private final ZSetOperations<K, V> zSetOps = new DefaultZSetOperations<>(this);
    private final GeoOperations<K, V> geoOps = new DefaultGeoOperations<>(this);
    private final HyperLogLogOperations<K, V> hllOps = new DefaultHyperLogLogOperations<>(this);
    private final ClusterOperations<K, V> clusterOps = new DefaultClusterOperations<>(this);

}
```

## 2. 配置文件

## 3. 发布与订阅 Stream

## 4. 事务和锁

#### 4.1 事务测试

```java

/**
 * Redis 事务测试
 */
public class RedisTransactionTest extends RedisExampleApplicationTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void before() {
        //开启事务支持
        redisTemplate.setEnableTransactionSupport(true);
        //每个测试清空数据库
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushDb();
    }

    @Test
    void test_transaction_with_exec() {

        //开启
        redisTemplate.multi();
        redisTemplate.opsForList().leftPushAll("T1", "V1", "V2", "V3");
        redisTemplate.opsForList().leftPushAll("T2", "V1", "V2");
        //未提交前为空
        Assertions.assertEquals(redisTemplate.opsForList().range("T1", 0, -1).size(), 0);
        Assertions.assertEquals(redisTemplate.opsForList().range("T2", 0, -1).size(), 0);
        //提交
        redisTemplate.exec();
        //提交后有数据
        Assertions.assertEquals(redisTemplate.opsForList().range("T1", 0, -1).size(), 3L);
        Assertions.assertEquals(redisTemplate.opsForList().range("T2", 0, -1).size(), 2L);


    }


    @Test
    void test_transaction_with_discard() {
        //开启
        redisTemplate.multi();
        redisTemplate.opsForList().leftPushAll("T1", "V1", "V2", "V3");
        redisTemplate.opsForList().leftPushAll("T2", "V1", "V2");
        //回滚
        redisTemplate.discard();
        //数据并没有存储
        Assertions.assertEquals(redisTemplate.opsForList().range("T1", 0, -1).size(), 0);
        Assertions.assertEquals(redisTemplate.opsForList().range("T2", 0, -1).size(), 0);
    }
}


```

#### 4.2 锁

* 解决多线程操作共享资源
* [B站参考视频](https://www.bilibili.com/video/BV14y4y127Fo?from=search&seid=154095181175473204&spm_id_from=333.337.0.0)
* 数据库: 乐观锁(`@Version`)和悲观锁(SQL `for update`)
* Redis: 见 4.2.1
* zookeeper:

##### 4.2.1 锁的问题

* [参考文档](https://cloud.tencent.com/developer/article/1440392)
* [多种分布式锁的实现](https://zhuanlan.zhihu.com/p/141023090)
* Redisson
* 死锁
* 超时机制+续命机制
* 重入锁(时间片)
* 锁误删
* 羊群效应 导致网络阻塞
* 代码演示 见 `RedisLockTest`

## 5. 持久化

### 持久化方式

##### RDB（Redis DataBase）

* 全量备份，耗时久，不实时 RDB持久化后的文件是二进制文件，更适用于备份、全量复制及灾难恢复，而且RDB恢复数据的性能要优于AOF。
* 主进程fork出子进程，子进程否则持久化
* 3种默认触发策略：60-1000，900-1，300-10，
* 2种IO方式阻塞`save`,异步`bgsave`
* 恢复速度快，
* 如果刷盘策略时间久，丢失数据风险大

##### AOF（Append Only File）

* 独立日志的方式记录每次的写命令，可以很好地解决了数据持久化的实时性 Redis重启优先加载AOF文件进行恢复
* AOF缓冲区
* 3种触发机制`always`、`everysec`(默认)、`no`

##### AOF+RDB 混合模式

* 4.0以上版本支持

## 6. 主从配置

### 问题项问题原因优化方案

##### 复制超时

* 如果RDB文件过大，主节点在复刻子进程和保存RDB文件时耗时过多，就可能导致从节点长时间收不到数据而触发超时，此时从节点会重连主节点，然后再次全量复制，再次超时，这样会进入一个无限循环。
* 如果主节点或从节点执行了一些慢查询，如keys*等大数据查询命令而导致服务器阻塞，阻塞期间无法响应复制连接中对方节点的请求，就可能导致复制超时，所以要慎重使用这些慢查询命令

1. Redis单机数据量不要过大
2. 适当增大`repl-timeout`（根据`bgsave`执行的耗时来）

#### 数据应用

* 数据延迟与不一致问题：主从复制是异步的设置从节点的`slave-serve-stale-data`参数
* 数据过期问题：惰性删除，定时删除
* 故障切换问题：需要手动切换

#### 复制中断

* 复制超时导致复制中断配置复制缓冲区的参数
* 复制缓冲区溢出，导致连接中断，进而导致全量复制，循环中断

#### 优化技巧 参见7.5.4　主从复制应用中的优化技巧

## 7. 哨兵模式

* 投票选举 `Raft`算法
* 使用Redis实例的发布与订阅功能来发现部署中用于监控相同主节点和从节点的其他Sentinel
* 监控（Monitoring）：哨兵节点不断地检查用户的主从实例是否按照预期在工作。
* 通知（Notification）：如果被监控的Redis实例有问题，哨兵节点可以通过一个API来通知系统管理员或者其他应用程序。
* 自动故障转移（Automatic
  Failover）：如果一个主节点没有按照预期工作，哨兵节点会启动故障转移过程，把一个从节点提升为主节点，重新配置其他的从节点并使用新的主节点，使用Redis服务的应用程序在连接的时候也会被通知使用新的主节点地址。
* 配置提供者（Configuration Provider）：哨兵节点为客户端提供服务来源，对于指定的服务，客户端连接到Sentinel来寻找当前主节点的地址。当发生故障转移时，哨兵节点将报告新的主节点地址。
* 哨兵模式决解了主从复制的故障转移问题

## 8. 集群

* 3主3从高可用集群
* 数据分区(扩容)
* 负载均衡 hash算法路由到不同的主节点上
* 故障转移 主节点故障，对应的从节点接替
* 单机默认16个数据库（可以修改），集群只有一个
  *` cluster-require-full-coverage`配置
* 一主一从演示版搭建脚本  TODO
```bash 
    
    

```
## 9. 常见问题

#### 9.1 缓存穿透

* 概念：缓存穿透是缓冲和数据库都没有数据时，大量请求下沉到数据库现象
* 在业务层对恶意请求进行拦截
* 运用布隆过滤器，布隆过滤器可以在Redis前，也可以在Redis和数据库之间

#### 9.2 缓存击穿

* 概念: 缓存有数据和数据库都有数据，热点数据在缓存种被淘汰出去，请求下沉到数据库层
* 设置热点数据过期时间为永久
* 互斥锁，保证统一时间只有小部分数据请求到数据库，避免大量请求下沉到数据库
* 热点数据限流（限流可以在缓存前，也可以在缓存和数据库中间）

#### 9.3 缓存雪崩

* 概念：同一时间大量缓存数据过期，导致大量请求下沉到数据库
* 缓存过期时间值设置相对分散一些
* 热点数据限流（限流可以在缓存前，也可以在缓存和数据库中间）

#### 9.4 分布式锁

#### 9.5 内存淘汰策略

当内存使用量超过了maxmemory配置的限制时，Redis可以使用以下策略来淘汰数据：
* noeviction：默认策略，当达到内存使用限制且客户端尝试执行可能会使用更多内存的命令时返回错误。
* volatile-lru：删除设置了过期时间且最近最少使用的键（LRU淘汰算法）。
* allkeys-lru：删除所有最近最少使用的键（LRU淘汰算法）。
* volatile-lfu：删除设置了过期时间且最不经常使用的键（LFU淘汰算法）。
* allkeys-lfu：删除所有最不经常使用的键（LFU淘汰算法）。
* volatile-random：随机淘汰设置了过期时间的键。
* allkeys-random：随机淘汰所有键。
* volatile-ttl：根据过期时间淘汰设置了过期时间的键，越早过期越早淘汰。