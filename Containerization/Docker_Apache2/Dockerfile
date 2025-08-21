# Stage 1: Build stage
FROM ubuntu:latest AS builder

RUN apt-get update && apt-get install -y --no-install-recommends apache2

# Stage 2: Final stage
FROM ubuntu:latest

RUN apt-get update && apt-get install -y --no-install-recommends \
    apache2 \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

COPY --from=builder /usr/sbin/apache2 /usr/sbin/apache2
COPY --from=builder /usr/sbin/apache2ctl /usr/sbin/apache2ctl
COPY --from=builder /usr/lib/apache2 /usr/lib/apache2
COPY --from=builder /etc/apache2 /etc/apache2

ADD ./apache2.conf /etc/apache2/apache2.conf
ADD ./000-default.conf /etc/apache2/sites-enabled/000-default.conf

VOLUME ["/var/log/apache2"]

EXPOSE 80 

CMD ["apache2ctl", "-D", "FOREGROUND"]
