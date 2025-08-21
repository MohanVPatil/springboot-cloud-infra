# Stage 1:
FROM ubuntu:latest as base

RUN apt-get update && \
    apt-get install -y curl p7zip-full && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Stage 2:
FROM base as jboss_builder

ENV JBOSS_HOME /opt/jboss-eap
ENV JBOSS_VERSION 7.1

RUN mkdir -p $JBOSS_HOME
COPY ./jboss-eap-7.1.7z $JBOSS_HOME/jboss-eap-7.1.7z
COPY ./STRUTS2_MENU-XML.7z $JBOSS_HOME/STRUTS2_MENU-XML.7z

WORKDIR $JBOSS_HOME
RUN 7z x jboss-eap-7.1.7z -o$JBOSS_HOME && \
    7z x STRUTS2_MENU-XML.7z -o$JBOSS_HOME && \
    rm jboss-eap-7.1.7z STRUTS2_MENU-XML.7z

# Stage 3:
FROM base as java_installer

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Stage 4:
FROM ubuntu:latest

ENV JBOSS_HOME /opt/jboss-eap/jboss-eap-7.1
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

COPY --from=jboss_builder /opt/jboss-eap /opt/jboss-eap
COPY --from=java_installer /usr/lib/jvm/java-8-openjdk-amd64 /usr/lib/jvm/java-8-openjdk-amd64

WORKDIR $JBOSS_HOME
RUN chmod -R 755 /opt/jboss-eap/jboss-eap-7.1

EXPOSE 8009 8080

CMD ["/bin/sh", "-c", "/opt/jboss-eap/jboss-eap-7.1/bin/runLOS.sh"]
