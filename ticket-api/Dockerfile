FROM gcr.io/distroless/java:11
LABEL maintainer=hexagonaldemo

COPY build/libs/order-api.jar lib/order-api.jar

CMD ["-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "lib/order-api.jar"]
