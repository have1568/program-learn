


##### Docker 快速安装

`docker run -d --name postgres-server -v "$PWD/my-postgres.conf":/etc/postgresql/postgresql.conf -e POSTGRES_PASSWORD=123456 postgres:12 -c 'config_file=/etc/postgresql/postgresql.conf'`


##### Centos 安装

* 官方安装教程

- https://www.postgresql.org/download/linux/redhat/ 
```
# Install the repository RPM:
sudo yum install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm

# Install PostgreSQL:
sudo yum install -y postgresql12-server

# Optionally initialize the database and enable automatic start:
sudo /usr/pgsql-12/bin/postgresql-12-setup initdb
sudo systemctl enable postgresql-12
sudo systemctl start postgresql-12

```

- 实际中需要修改数据存储位置

```
# Install the repository RPM:
sudo yum install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm

#搜索可安装版本
yum search postgresql

# Install PostgreSQL:
sudo yum install  postgresql12-server

#修改数据存储位置
vim /usr/lib/systemd/system/postgresql-12.service
Environment=PGDATA=/opt/data/postgres

#修改文件夹及子目录权限（没有迁移数据不需要修改）
sudo chown -R postgres:postgres /opt/data/postgres/* && chmod -R 750 /opt/data/postgres/*

#初始化
sudo /usr/pgsql-12/bin/postgresql-12-setup initdb
sudo systemctl enable postgresql-12
sudo systemctl start postgresql-12

#设置密码
su - postgres

psql

\password

exit

#配置局域网链接
修改文件
pg_hba.conf
host    all             all             0.0.0.0/0               md5

postgresql.conf
listen_addresses = '*'



#数据库插件安装
yum search contrib
yum -y install postgresql12-contrib.x86_64



```

- 备库搭建

> 优化性能（选择版本和计算机配置会有参考配置）
> 
> https://pgtune.leopard.in.ua/


