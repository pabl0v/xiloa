PROYECTO XILOA
==============

COMPONENTES
===========

El proyecto utiliza los siguientes componentes:

Base: Spring Framework 3.2
Seguridad: Spring Security 3.2
Capa de acceso a datos: Hibernate 4.1.9 + JPA 2.0 
Capa de presentacion: JSF 2.1 + Primefaces 4.0

REQUISITOS
==========

1. Glassfish 3.1 o superior
2. JDK 1.7
3. PostgreSQL 9.2
4. Eclipse Kepler + Glassfish tools

PREPARACION DEL AMBIENTE
========================

1. En Eclipse, registrar el servidor Glassfish. De ser necesario descargar el adaptador (Glassfish tools)
2. Registrar la JDK 1.7 en Eclipse y seleccionar su uso por defecto

INSTALACION
===========

Una vez preparado el ambiente, proceder de la siguiente manera:

1. Crear una base de datos en blanco en PostgreSQL, llamarla por ejemplo Xiloa
2. Copiar el .jar del jdbc de PostgreSQL a la carpeta \glassfish\domains\domain1\lib de la instalacion de Glassfish y reiniciar Glassfish
3. Desde la consola de administracion de Glassfish, crear un pool de conexiones JDBC para la base de datos Xiloa PostgreSQL, llamarlo por ejemplo postgres. 
   En la pestana de propiedades adicionales indicar:
   
   ServerName
   PortNumber
   DatabaseName
   User
   Password
   
4. Desde la consola de administracion de Glassfish, crear otro pool de conexiones JDBC para la base de datos inatec PostgreSQL, llamarlo por ejemplo inatec. 
   En la pestana de propiedades adicionales indicar la configuración para acceder a la base de datos inatec:
   
   ServerName
   PortNumber
   DatabaseName
   User
   Password
 
5. Acceder nuevamente a la consola de administracion de Glassfish y probar la conexion (ping) del pool de conexciones recien creado
6. Desde la consola de administración de Glassfish crear dos recursos JDBC para la base de datos Xiloa e Inatec y llamarlos jdbc/postgres y jdbc/inatec respectivamente
7. Importar el proyecto de la aplicacion web a Eclipse 
8. Desde Eclipse, ejecutar la aplicacion web en el servidor Glassfish
9. Una vez iniciada la aplicacion, ejecutar el test unitario completo, el cual creará usuarios y roles de prueba
10. Autenticarse en la aplicacion con cualquiera de los siguientes usuarios/contrasena: 
		admin/admin
		aruiz/aruiz
		nlopez/nlopez
		lperez/lperez
		larauz/larauz