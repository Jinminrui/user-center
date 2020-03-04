# 指定基础镜像(必需且为第一条指令,scratch是空白镜像)
FROM java:8

# MAINTAINER已经过期，具体参考：https://docs.docker.com/engine/reference/builder/#label
LABEL author-name="jinminrui"

# 为了防止运行时用户忘记将动态文件所保存目录挂载为卷，在 Dockerfile 中，我们可以事先指定某些目录挂载为匿名卷，
# 这样在运行时如果用户不指定挂载，其应用也可以正常运行，不会向容器存储层写入大量数据。
VOLUME /opt/tmp

# 在 COPY 和 ADD 指令中选择的时候，可以遵循这样的原则,
# 所有文件复制使用 COPY 指令，仅在需要自动解压缩的场合使用 ADD 指令
COPY tw-user-center.jar tw-user-center.jar

# JVM参数可选
CMD ["java","-jar","-Xms64m","-Xmx64m","tw-user-center.jar"]

# EXPOSE 指令仅仅是声明的是运行时容器提供服务的端口
# 用于帮助镜像使用者理解这个镜像服务的守护端口，以方便配置映射；
EXPOSE 8081