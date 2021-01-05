FROM java:8
MAINTAINER mrl <1307640502@qq.com>
COPY *.jar /oss.jar
CMD ["--server.port=8080"]
EXPOSE 8080
ENTRYPOINT ["java","-jar","/oss.jar"]

#cmd 这里是为 entrypoint 提供可选参数
#比如直接build,相当于entrypoint + cmd
#如果build时指定了参数，则相当于覆盖了cmd参数

#expose 用来暴露容器提供服务的端口,只作声明
