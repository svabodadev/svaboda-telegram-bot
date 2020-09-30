FROM openjdk:14-oracle AS builder

ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY build.gradle $APP_HOME
COPY settings.gradle $APP_HOME
COPY gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build

FROM openjdk:14-oracle
ENV APP_HOME=/app
ENV ARTIFACT_NAME="bot-1.0.0-SNAPSHOT.jar"
WORKDIR $APP_HOME
COPY --from=builder $APP_HOME/bot/build/libs/$ARTIFACT_NAME .
ADD "/bot/run.sh" "/"
RUN chmod +x "/run.sh"
ARG _name=$_name
ARG _token=$_token
ENV BOT_NAME=$_name
ENV BOT_TOKEN=$_token
ENTRYPOINT ["/run.sh"]