FROM gcr.io/distroless/java:11
LABEL maintainer=hexagonaldemo

COPY build/libs/ticket-api.jar lib/ticket-api.jar

EXPOSE 8094

CMD ["-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "lib/ticket-api.jar"]
