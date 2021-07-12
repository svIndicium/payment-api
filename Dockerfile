FROM adoptopenjdk/openjdk11-openj9:alpine-slim
ADD target/payment-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=,suspend=n"
EXPOSE 8080 8787
CMD java $JAVA_OPTS $JAVA_TOOL_OPTIONS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=main -Xshareclasses -Xquickstart -jar -Dserver.port=$PORT /app.jar