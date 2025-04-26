FROM openjdk:17
COPY "./target/com.example.grado-1.jar" "gestorproyectos.jar"
EXPOSE 8130
ENTRYPOINT ["java", "-jar", "gestorproyectos.jar"]