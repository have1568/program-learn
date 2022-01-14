Logstash-6.4.3 笔记
=========================

 一、概述
 -----------------------
 
 1、架构图
 
 ![架构图](./logstash.png)
 
 2、项目主要目录结构
 
 ```text
    ├── bin
    │   ├── benchmark.sh
    │   ├── cpdump
    │   ├── dependencies-report
    │   ├── ingest-convert.sh
    │   ├── logstash
    │   ├── logstash.bat
    │   ├── logstash-keystore
    │   ├── logstash-keystore.bat
    │   ├── logstash.lib.sh
    │   ├── logstash-plugin
    │   ├── logstash-plugin.bat
    │   ├── pqcheck
    │   ├── pqrepair
    │   ├── ruby
    │   ├── setup.bat
    │   └── system-install
    ├── config
    │   ├── jvm.options
    │   ├── log4j2.properties
    │   ├── logstash-sample.conf
    │   ├── logstash.yml
    │   ├── pipelines.yml
    │   └── startup.options
    ├── CONTRIBUTORS
    ├── data
    ├── Gemfile
    ├── Gemfile.lock
    ├── lib
    │   ├── bootstrap
    │   ├── pluginmanager
    │   ├── secretstore
    │   └── systeminstall
    ├── LICENSE.txt

```



