FROM java:8
MAINTAINER mrl <1307640502@qq.com>
COPY *.jar /exec.jar
CMD ["--server.port=8080"]
EXPOSE 8080
ENTRYPOINT ["java","-jar","exec.jar"]