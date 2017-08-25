FROM maven:3.3.9-jdk-8

MAINTAINER Ricardo Baltazar Chaves <ricardobchaves6@gmail.com>

RUN mkdir -p /opt/app
WORKDIR /opt/app

# install dependencies
COPY pom.xml /opt/app/
RUN mvn install

COPY src /opt/app/src
RUN mvn package

EXPOSE 8080

CMD ["mvn", "exec:java"]