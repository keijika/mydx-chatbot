# ベースイメージとしてOpenJDKを指定
FROM openjdk:17-jdk-slim

# 作業ディレクトリを設定
WORKDIR /app

# コンテナ内にJARファイルをコピー
COPY target/mydx-chatbot-0.0.1-SNAPSHOT.jar /app/mydx-chatbot.jar

# ポート10000を開放
EXPOSE 10000

# アプリケーションを実行
CMD ["java", "-jar", "/app/mydx-chatbot.jar"]
