# ---------- ESTÁGIO 1: build ----------
# imagem completa, com Maven e JDK, usada só para compilar o projeto
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

# copia primeiro só o pom.xml -- aproveita cache de dependências
# (se só o código mudar, o Docker não baixa as dependências de novo)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# agora copia o restante do código-fonte
COPY src ./src

# compila e gera o .jar, pulando os testes (ambiente de container não deveria rodar testes de novo)
RUN mvn clean package -DskipTests


# ---------- ESTÁGIO 2: runtime ----------
# imagem enxuta, só com JRE (não precisa do JDK completo pra RODAR)
FROM eclipse-temurin:25-jre

WORKDIR /app

# copia só o artefato final do estágio anterior -- nada de Maven, nada de código-fonte
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]