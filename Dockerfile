# ベースイメージを指定
FROM openjdk:17-jdk-slim

# 作業ディレクトリを指定
WORKDIR /app

# JARファイルをコンテナ内にコピー
COPY target/mydx-chatbot-0.0.1-SNAPSHOT.jar /app/mydx-chatbot.jar

# コンテナのポート8080を開放
EXPOSE 8080

# Javaコマンドでアプリケーションを実行
CMD ["java", "-jar", "mydx-chatbot.jar"]
