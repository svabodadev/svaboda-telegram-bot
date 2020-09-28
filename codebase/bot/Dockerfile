FROM openjdk:14-oracle

WORKDIR /app
ADD "build/libs/bot-1.0.0-SNAPSHOT.jar" "/app"
ADD "run.sh" "/"
RUN chmod +x "/run.sh"
ARG _name=$_name
ARG _token=$_token
ENV _BOT_NAME=$_name
ENV _BOT_TOKEN=$_token
ENTRYPOINT ["/run.sh"]