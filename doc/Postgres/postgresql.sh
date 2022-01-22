#!/bin/bash
# 关闭防火墙
systemctl stop firewalld
systemctl disable firewalld

# 关闭selinux
sed -i 's/enforcing/disabled/' /etc/selinux/config  # 永久
setenforce 0  # 临时

# 关闭swap
swapoff -a  # 临时
sed -ri 's/.*swap.*/#&/' /etc/fstab    # 永久

# 时间同步
yum install ntpdate
ntpdate time.windows.com

#安装源
sudo yum install https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm
yum search postgresql
sudo yum install  postgresql12-server

#数据库插件安装
yum search contrib
yum install postgresql12-contrib.x86_64

#配置初始环境变量
echo "[Service]" >> "/etc/systemd/system/postgresql-12.service.d/override.conf"
echo "Environment=PGDATA=/opt/data/postgres" >> "/etc/systemd/system/postgresql-12.service.d/override.conf"

mkdir -p "/opt/data/postgres"

sudo chown -R postgres:postgres /opt/data/postgres && chmod -R 750 /opt/data/postgres

sudo /usr/pgsql-12/bin/postgresql-12-setup initdb
sudo systemctl enable postgresql-12
sudo systemctl start postgresql-12