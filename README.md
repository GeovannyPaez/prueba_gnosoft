# Proyecto de Facturación

Este proyecto consta de un backend desarrollado con Spring Boot y un frontend con Vue.js y Vite. Sigue estas instrucciones para configurar y ejecutar el proyecto de facturas.

## Requisitos previos

- Docker y Docker Compose
- Java JDK 11 o superior
- Node.js y npm
- Git (opcional, para clonar el repositorio)

## Pasos para ejecutar el proyecto

### 1. Configurar y ejecutar la base de datos

Primero, necesitamos crear y ejecutar el contenedor de la base de datos PostgreSQL usando Docker.

1. Navega a la carpeta `demo` donde se encuentra el backend:

   ```
   cd ./demo
   ```

2. Asegúrate de que tienes un archivo `docker-compose.yml` en esta carpeta. Si no lo tienes, créalo con el siguiente contenido:

   ```yaml
   version: "3.8"

   services:
     postgres:
       image: postgres:latest
       container_name: factura-db
       environment:
         POSTGRES_DB: factura
         POSTGRES_USER: postgres
         POSTGRES_PASSWORD: adminsan
       ports:
         - "5432:5432"
       volumes:
         - postgres-data:/var/lib/postgresql/data

   volumes:
     postgres-data:
   ```

3. Ejecuta el siguiente comando para iniciar el contenedor de la base de datos:
   ```
   docker-compose up -d
   ```

Esto creará y ejecutará un contenedor de PostgreSQL con las credenciales especificadas.

### 2. Ejecutar el backend de Spring Boot

1. Permanece en la carpeta `demo`.

2. Ejecuta la aplicación Spring Boot. Dependiendo de cómo esté configurado tu proyecto, puedes usar uno de estos comandos:

   ```
   ./mvnw spring-boot:run
   ```

   o

   ```
   java -jar nombre-del-archivo.jar
   ```

   Asegúrate de reemplazar `nombre-del-archivo.jar` con el nombre real de tu archivo JAR si estás usando el segundo método.

3. Espera hasta que veas mensajes indicando que la aplicación ha iniciado correctamente.

### 3. Ejecutar el frontend de Vue.js

1. Abre una nueva terminal.

2. Navega a la carpeta del frontend:

   ```
   cd ./frontend_facturas
   ```

3. Instala las dependencias del proyecto (si aún no lo has hecho):

   ```
   npm install
   ```

4. Inicia el servidor de desarrollo:

   ```
   npm run dev
   ```

5. Espera hasta que el servidor de desarrollo de Vite esté listo. Verás un mensaje con la URL donde se está ejecutando la aplicación, generalmente `http://localhost:3000`.

## Acceder a la aplicación

Abre tu navegador y visita la URL proporcionada por el servidor de Vite (normalmente `http://localhost:3000`) para acceder a la aplicación de facturación.

## Notas adicionales

- Asegúrate de que todos los puertos necesarios (5432 para PostgreSQL, 8080 para Spring Boot, y 3000 para Vite) estén disponibles y no bloqueados por un firewall.
- Si encuentras algún problema de conexión, verifica que las credenciales de la base de datos en la configuración de Spring Boot coincidan con las especificadas en el `docker-compose.yml`.

¡Listo! Ahora deberías tener tu aplicación de facturación funcionando con el backend de Spring Boot, la base de datos PostgreSQL en Docker, y el frontend de Vue.js con Vite.
