FROM ubuntu:latest
FROM java


RUN apt update -qq && apt install git -qqy

RUN git clone https://github.com/gonzalocasal/figaro.git

ENV JAVA_HOME=$JAVA_HOME

RUN cd /figaro && git fetch && git checkout docker && ./gradlew build


EXPOSE 8080

ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","/figaro/build/libs/figaro-1.0.0.jar"]



