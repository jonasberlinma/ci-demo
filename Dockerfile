FROM openjdk
MAINTAINER Jonas Berlin <jonas@theberlins.org>
ADD target/ci-demo-0.0.1-SNAPSHOT.jar /tmp/ci-demo-0.0.1-SNAPSHOT.jar
ADD deeplearning.zip deeplearning.zip

EXPOSE 8080

ENTRYPOINT java -jar /tmp/ci-demo-0.0.1-SNAPSHOT.jar
