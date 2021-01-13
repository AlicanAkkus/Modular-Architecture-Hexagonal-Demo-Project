FROM gcr.io/distroless/java:11
LABEL maintainer=hexagonaldemo

COPY build/libs/payment-api.jar lib/payment-api.jar

CMD ["-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "lib/payment-api.jar"]