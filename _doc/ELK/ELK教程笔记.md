## ELK 笔记

#### 1、 ELK安装
```
#JDK 1.8环境配置 ...

#系统配置
vim /etc/selinux/config

SELINUX=disabled

#创建 /opt/elk 文件夹
mkdir /opt/elk
#添加elk用户 并将elk 文件夹的所有者改为 elk

useradd elk

passwd elk

#输入密码

chown -R elk.elk /opt/elk

#切换用户
su - elk

#在/opt/elk目录下下载组件

#下载组件
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.2.0-linux-x86_64.tar.gz
wget https://artifacts.elastic.co/downloads/logstash/logstash-7.2.0.tar.gz
wget https://artifacts.elastic.co/downloads/kibana/kibana-7.2.0-linux-x86_64.tar.gz

#中文分词器（日志里有中文需要用到）
wget https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.1.1/elasticsearch-analysis-ik-7.2.0.zip

#解压文件
tar -xzvf /opt/elk/elasticsearch-7.2.0-linux-x86_64.tar.gz -C /opt/elk
tar -xzvf /opt/elk/logstash-7.2.0.tar.gz -C /opt/elk
tar -xzvf /opt/elk/kibana-7.2.0-linux-x86_64.tar.gz -C /opt/elk
unzip elasticsearch-analysis-ik-7.2.0.zip -d elasticsearch-7.2.0/plugins/ik

#以elk用户启动 elasticsearch (-d是后台启动的意思)
/opt/elk/elasticsearch-7.2.0/bin/elasticsearch -d

#检查是否启动成功
curl http://localhost:9200


#配置logstash

/opt/elk/logstash-7.2.0/vendor/bundle/jruby/2.5.0/gems/logstash-patterns-core-4.1.2/patterns/grok-patterns

#文件末尾加上
#Nginx
WZ ([^ ]*)
NGINXACCESS %{IP:remote_ip} \- \- \[%{HTTPDATE:timestamp}\] "%{WORD:method} %{WZ:request} HTTP/%{NUMBER:httpversion}" %{NUMBER:status} %{NUMBER:bytes} %{QS:referer} %{QS:agent}
%{QS:xforward}

```

#### 2、配置es
 * elasticsearch.yml 配置
```yaml 
cluster.name: es-server
node.name: es-master
#node.attr.rack: r1
#path.data: /path/to/data
#path.logs: /path/to/logs
#bootstrap.memory_lock: true
network.host: 192.168.241.144
#http.port: 9200
discovery.seed_hosts: ["192.168.241.144", "192.168.241.146","192.168.241.147"]
cluster.initial_master_nodes: ["es-master", "es-slave1", "es-slave2"]
#gateway.recover_after_nodes: 3
#action.destructive_requires_name: true

```

* 系统配置
``` 
#关闭防火墙

#关闭 selinux

#系统限制修改
echo "* soft nofile 65536" >> /etc/security/limits.conf
echo "* hard nofile 131072" >> /etc/security/limits.conf
echo "* soft nproc 2048" >> /etc/security/limits.conf
echo "* hard nproc 4096" >> /etc/security/limits.conf
echo "vm.max_map_count=655360" >> /etc/sysctl.conf
sysctl -p

```
* 查看节点和集权的状态

http://192.168.241.144:9200/_cat/

