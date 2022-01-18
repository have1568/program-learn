###linux 基本配置命令

https://centos.pkgs.org/7/centos-x86_64/

####修改hostname
```
hostnamectl set-hostname Docker-Server

```

####修改静态IP
```
vim /etc/sysconfig/network-scripts/ifcfg-ens33

内容如下：
TYPE="Ethernet"
PROXY_METHOD="none"
BROWSER_ONLY="no"
#BOOTPROTO="dhcp"
DEFROUTE="yes"
IPV4_FAILURE_FATAL="no"
IPV6INIT="yes"
IPV6_AUTOCONF="yes"
IPV6_DEFROUTE="yes"
IPV6_FAILURE_FATAL="no"
IPV6_ADDR_GEN_MODE="stable-privacy"
NAME="ens33"
UUID="517a7d46-b20c-41d9-af19-b710aac312d1"
DEVICE="ens33"
ONBOOT="yes"
IPV6_PRIVACY="no"
BOOTPROTO="static"
IPADDR="192.168.241.141"
NETMASK="255.255.255.0"
GATEWAY="192.168.241.2"
DNS1="192.168.241.2"

```

#### Centos 7.6 安装docker
* 1、安装依赖
```
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
```
* 2、添加镜像源

```
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

```
* 3、安装Docker
```$xslt
查看可安装的版本
yum list docker-ce --showduplicates | sort -r

安装最新版本
sudo yum install docker-ce

安装指定版本
sudo yum install docker-ce-18.03.0.ce   //以18.03为例

```

* 4、卸载docker

```
卸载命令
sudo yum remove docker-ce

sudo rm -rf /var/lib/docker   //因为属主机上的Images, containers, volumes, or customized configuration files不会自动删除，需手动删除

```

* 5、其他命令

```$xslt
sudo systemctl start docker
sudo systemctl enable docker

```

#### 常用镜像命令

```$xslt
docker run -d -u root --name nginx -p 80:80 -v /data/nginx/html:/etc/nginx/html -v /data/docker-conf/nginx.conf:/etc/nginx/nginx.conf  nginx
```

* 1、安装  docker-compose
```$xslt
curl -L https://github.com/docker/compose/releases/download/1.24.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
```
* 2、安装Nginx
```$xslt
yum install gcc-c++ pcre pcre-devel zlib zlib-devel openssl openssl-devel

wget -c https://nginx.org/download/nginx-1.17.0.tar.gz

tar zxf nginx-1.17.0.tar.gz


./configure --prefix=/usr/local/nginx --with-http_stub_status_module --with-http_ssl_module

所有配置
. /configure 
--user=nginx
--group=nginx
--prefix=/usr/local/nginx
--conf-path=/usr/local/nginx/conf/nginx.conf 
--pid-path=/usr/local/nginx/conf/nginx.pid
--lock-path=/var/lock/nginx.lock 
--error-log-path=/var/log/nginx/error.log
--http-log-path=/var/log/nginx/access.log
--with-http_gzip_static_module
--http-client-body-temp-path=/var/temp/nginx/client 
--http-proxy-temp-path=/var/temp/nginx/proxy
--http-fastcgi-temp-path=/var/temp/nginx/fastcgi 
--http-uwsgi-temp-path=/var/temp/nginx/uwsgi
--http-scgi-temp-path=/var/temp/nginx/scgi
--with-http_ssl_module

make && make install

vi /etc/rc.local

/usr/local/nginx/sbin/nginx

chmod 755 /etc/rc.local

```

#### 安装常用软件

``` 
yum install -y lrzsz wget vim net-tools

wget http://mirror.centos.org/centos/7/os/x86_64/Packages/tree-1.6.0-10.el7.x86_64.rpm

rpm -ivh tree-1.6.0-10.el7.x86_64.rpm

```

 * 1、开放范围端口

``` 
systemctl stop firewalld.service  #关闭防火墙

systemctl disable firewalld.service  #禁止firewall开机启动

firewall-cmd --permanent --zone=public --add-port=5904-5905/tcp
```
 
* 2 、JDK MAVEN

```
    #JAVA_HOME
    export JAVA_HOME=/opt/tools/jdk1.8.0_211
    export JRE_HOME=$JAVA_HOME/jre
    export CLASSPATH=$JAVA_HOME/lib:$JRE_HOME/lib:$CLASSPATH
    export PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$PATH
    #MAVEN_HOME
    MAVEN_HOME=/opt/tools/apache-maven-3.6.1
    export PATH=${MAVEN_HOME}/bin:${PATH}
```

### SSH 免密

``` 
ssh-keygen -t rsa

ssh-copy-id -i ~/.ssh/id_rsa.pub root@test.example.com
    
```

### 添加用户

``` 
useradd elk -d /opt/elk
passwd elk

#修改sudo权限

vim /etc/sudoers

elk     ALL=(ALL)       ALL

```


