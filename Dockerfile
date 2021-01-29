FROM openjdk:8-jdk-alpine
ENV  TIME_ZONE  Asia/Shanghai
RUN apk add --no-cache tzdata && \
echo "${TIME_ZONE}" > /etc/timezone && ln -sf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime

VOLUME /data

ADD target/application-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]