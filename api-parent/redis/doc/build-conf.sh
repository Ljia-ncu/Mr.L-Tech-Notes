for port in $(seq 1 6);
do
mkdir -p ./node${port}/conf
touch ./node${port}/conf/redis.conf
cat << EOF >./node${port}/conf/redis.conf
#访问端口
port 637${port}

#不绑定ip，没什么作用
bind 0.0.0.0

#开启集群模式
cluster-enabled yes

#集群配置文件(redis自己维护)，port只作区分作用
cluster-config-file nodes-637${port}.conf

cluster-node-timeout 5000

#开启aof
appendonly yes

#关闭保护模式
protected-mode no

#redis访问密码
requirepass 123456

#集群间访问密码
masterauth 123456
EOF
done