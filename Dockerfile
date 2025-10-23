FROM tomcat:9.0

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8090
CMD ["catalina.sh", "run"]