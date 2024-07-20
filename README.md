# Devsu Microservicios

La aplicación consta de 2 microservicios:
* Clientes: gestiona los datos de usuarios y los reporte de movimientos
* Cuentas: gestiona la creación de cuentas y movimientos relacionados.

Los servicios se comunican de forma asíncrona mediante RabbitMQ para registrar las estadísticas
relativas a los movimientos de cuentas, que luego serán incluidas en los reportes.

Cada microservicio utiliza una base de datos MariaDB individual para la persistencia
de las entidades.

Para la validación y prueba de la API se incluyó en el repositorio el archivo
[devsu.postman_collection.json](devsu.postman_collection.json)
con varias llamadas preconfiguradas.

Se incluyó además el archivo [BaseDatos.sql](BaseDatos.sql)
que contiene el script de creación de bases de datos y tablas,
aunque no es necesario ejecutarlo ya que está configurado junto
con docker-compose.

## Instalación y ejecución

## Requisitos:
* Java 17

## Generar los archivos .jar de cada microservicio:
```
./devsu-clientes/mvnw -f ./devsu-clientes/pom.xml clean package
./devsu-cuentas/mvnw -f ./devsu-cuentas/pom.xml clean package
```

## Crear imágenes Docker y ejecutar los contenedores:
```
docker-compose up
```
