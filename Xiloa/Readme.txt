PROYECTO XILOA
==============

REQUISITOS
==========

1. Glassfish 3.1 o superior
2. JDK 1.7
3. PostgreSQL
4. Eclipse Kepler + Glassfish tools

INSTALACION
===========

Una vez preparado el ambiente, proceder de la siguiente manera:

1. Crear una base de datos en blanco en PostgreSQL, llamarla por ejemplo Xiloa
2. Desde la consola de administracion de Glassfish, crear un pool de conexiones JDBC para la base de datos PostgreSQL, llamarlo por ejemplo postgres. 
   En la pestana de propiedades adicionales indicar:
   
   ServerName
   PortNumber
   DatabaseName
   User
   Password
 
3. Copiar el .jar jdbc de PostgreSQL a la carpeta \glassfish\domains\domain1\lib de Glassfish y reiniciar Glassfish
4. Acceder nuevamente a la consola de administracion de Glassfish y probar la conexion (ping) del pool de conexciones recien creado 
5. Desde Eclipse, ejecutar la aplicacion web en el servidor Glassfish
6. Una vez iniciada la aplicacion, ejecutar el scrip del archivo ScriptBD.sql en la base de datos PostgreSQL
7. Autenticarse en la aplicacion con usuario admin y contrasena admin