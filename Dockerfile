# �x�[�X�C���[�W���w��
FROM openjdk:17-jdk-slim

# ��ƃf�B���N�g�����w��
WORKDIR /app

# JAR�t�@�C�����R���e�i���ɃR�s�[
COPY target/mydx-chatbot-0.0.1-SNAPSHOT.jar /app/mydx-chatbot.jar

# �R���e�i�̃|�[�g8080���J��
EXPOSE 8080

# Java�R�}���h�ŃA�v���P�[�V���������s
CMD ["java", "-jar", "mydx-chatbot.jar"]
