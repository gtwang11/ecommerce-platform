# 1. 基础镜像：使用轻量级的 Java 17 环境
FROM openjdk:17-jdk-slim

# 2. 作者信息
LABEL maintainer="wgt"

# 3. 关键步骤：把 target 目录下的 jar 包复制进容器，并改名为 app.jar
# 请确保下面 target/ 后面的名字和你实际生成的 jar 包名字一致！
COPY target/ecommerce-platform-0.0.1-SNAPSHOT.jar app.jar

# 4. 暴露端口 (容器内部端口)
EXPOSE 8080

# 5. 启动命令
ENTRYPOINT ["java", "-jar", "/app.jar"]