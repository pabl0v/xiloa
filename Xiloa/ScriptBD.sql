--registrando usuario administrador

insert into perfiles(perfil_id,perfil_descripcion,perfil_habilitado,perfil_modulo,perfil_nombre,perfil_permiso,perfil_permitido,perfil_seccion)
values(1,'admin',true,'all','admin','all',true,'all');

insert into roles(rol_id,rol_descripcion,rol_nombre,rol_estatus)
values(1,'admin','admin',true);

insert into perfiles_roles(rol_id,perfil_id)
values(1,1);

insert into usuarios(usuario_id,usuario_alias,usuario_pwd,usuario_estatus,rol_id)
values(1,'admin','admin',true,1);

--otros usuarios y roles

insert into roles(rol_id,rol_descripcion,rol_nombre,id_rol_inatec,rol_estatus)
values(2,'Supervisor','supervisor',213,true);

insert into roles(rol_id,rol_descripcion,rol_nombre,id_rol_inatec,rol_estatus)
values(3,'Verificador','verificador',214,true);

insert into roles(rol_id,rol_descripcion,rol_nombre,id_rol_inatec,rol_estatus)
values(4,'Tecnico Docente','tecnico_docente',215,true);

insert into roles(rol_id,rol_descripcion,rol_nombre,id_rol_inatec,rol_estatus)
values(5,'Registro Academico','registro_academico',216,true);

insert into roles(rol_id,rol_descripcion,rol_nombre,rol_estatus)
values(6,'Visitante','visitante',true);

insert into perfiles_roles(rol_id,perfil_id)
values(2,1);

insert into perfiles_roles(rol_id,perfil_id)
values(3,1);

insert into perfiles_roles(rol_id,perfil_id)
values(4,1);

insert into perfiles_roles(rol_id,perfil_id)
values(5,1);

insert into perfiles_roles(rol_id,perfil_id)
values(6,1);

insert into usuarios(usuario_id,usuario_alias,usuario_pwd,usuario_estatus,rol_id)
values(2,'nlopez','nlopez',true,1);

insert into usuarios(usuario_id,usuario_alias,usuario_pwd,usuario_estatus,rol_id)
values(3,'aruiz','aruiz',true,1);

insert into usuarios(usuario_id,usuario_alias,usuario_pwd,usuario_estatus,rol_id)
values(4,'lperez','lperez',true,1);

insert into usuarios(usuario_id,usuario_alias,usuario_pwd,usuario_estatus,rol_id)
values(5,'larauz','larauz',true,1);

--registrando usuarios con roles varios para prueba

insert into contactos(
contacto_id,
correo1,
correo2,
direccion_actual,
entidad_id,
fecha_registro,
lugar_nacimiento,
nacionalidad_id,
numero_identificacion,
primer_apellido,
primer_nombre,
segundo_apellido,
segundo_nombre,
sexo_id,
telefono1,
telefono2,
tipo_contacto,
tipo_identificacion,
inatec,
rol_id,
usuario_id)
select
1,
'nleon@inactec.edu.ni',
null,
'Managua',
1,
now(),
'Managua',
1,
'00000000000000',
'Lopez',
'Norcecy',
null,
null,
2,
'12345678',
null,
1,
0,
false,
1,
2;

insert into contactos(
contacto_id,
correo1,
correo2,
direccion_actual,
entidad_id,
fecha_registro,
lugar_nacimiento,
nacionalidad_id,
numero_identificacion,
primer_apellido,
primer_nombre,
segundo_apellido,
segundo_nombre,
sexo_id,
telefono1,
telefono2,
tipo_contacto,
tipo_identificacion,
inatec,
rol_id,
usuario_id)
select
2,
'aruiz@inactec.edu.ni',
null,
'Managua',
1,
now(),
'Managua',
1,
'00000000000000',
'Ruiz',
'Ana',
null,
null,
2,
'12345678',
null,
1,
0,
false,
1,
3;

insert into contactos(
contacto_id,
correo1,
correo2,
direccion_actual,
entidad_id,
fecha_registro,
lugar_nacimiento,
nacionalidad_id,
numero_identificacion,
primer_apellido,
primer_nombre,
segundo_apellido,
segundo_nombre,
sexo_id,
telefono1,
telefono2,
tipo_contacto,
tipo_identificacion,
inatec,
rol_id,
usuario_id)
select
3,
'lperez@inactec.edu.ni',
null,
'Managua',
1,
now(),
'Managua',
1,
'00000000000000',
'Perez',
'Luis',
null,
null,
2,
'12345678',
null,
1,
0,
false,
1,
4;

insert into contactos(
contacto_id,
correo1,
correo2,
direccion_actual,
entidad_id,
fecha_registro,
lugar_nacimiento,
nacionalidad_id,
numero_identificacion,
primer_apellido,
primer_nombre,
segundo_apellido,
segundo_nombre,
sexo_id,
telefono1,
telefono2,
tipo_contacto,
tipo_identificacion,
inatec,
rol_id,
usuario_id)
select
5,
'lruiz@inactec.edu.ni',
null,
'Managua',
1,
now(),
'Managua',
1,
'00000000000000',
'Arauz',
'Luz',
null,
null,
2,
'12345678',
null,
1,
0,
false,
1,
5;

alter sequence seq_usuarios start with 6;

--script para BD Inatec

--registrando el sistema

insert into admon.sistemas(
id_sistema,
nombre_sistema,
siglas,
activo,
usuario_grabacion,
fecha_grabacion,
usuario_actualizacion,
alias)
select
40 id_sistema,
'Sistema de Certificacion de Competencias Laborales' as nombre_sistema,
'SCCL' as siglas,
1 as activo,
'admin' usuario_grabacion,
'20131022' as fecha_grabacion,
null as usuario_actualizacion,
null as alias;

--creando una cuenta administrador para el sistema
/*
insert into admon.usuario(
usuario,
id_empleado,
id_centro,
clave,
clave_anterior,
nombre_completo,
cargo_usuario,
tipo_usuario,
correo,
contador_intentos,
activo,
fecha_cambio_clave,
usuario_grabacion,
fecha_grabacion,
estado)
select
'admin',
null as id_empleado,
1000 as id_centro,
'admin' as clave,
'admin' as clave_anterior,
'Administrador SCCL' as nombre_completo,
'Administrador SCCL' as cargo_usuario,
1 as tipo_usuario,
null as correo,
0 as contador_intentos,
1 as activo,
now() as fecha_cambio_clave,
'admin' as usuario_grabacion,
now() as fecha_grabacion,
1 as estado
*/
--creando los roles

insert into admon.roles(
id_rol,
descripcion_rol,
id_sistema,
activo,
usuario_grabacion,
fecha_grabacion)
select
213 as id_rol,
'Supervisor' as descripcion_rol,
40 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
214 as id_rol,
'Verificador' as descripcion_rol,
40 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
215 as id_rol,
'Tecnico Docente' as descripcion_rol,
40 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
216 as id_rol,
'Registro Academico' as descripcion_rol,
40 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion;

--asignando roles a los usuarios inatec

--asignando usuarios a roles

insert into admon.usuarios_sistemas(
id_sistema,
id_empleado,
usuario,
id_rol,
activo,
usuario_grabacion,
fecha_grabacion)
select
40 as id_sistema,
id_empleado as id_empleado,
usuario as usuario,
213 as id_rol,
1 as activo,
'admin' as usuario_grabacion,
now() as fecha_grabacion
from admon.usuario
where usuario='djanson'
union
select
40 as id_sistema,
id_empleado as id_empleado,
usuario as usuario,
214 as id_rol,
1 as activo,
'admin' as usuario_grabacion,
now() as fecha_grabacion
from admon.usuario
where usuario='cromero'
union
select
40 as id_sistema,
id_empleado as id_empleado,
usuario as usuario,
215 as id_rol,
1 as activo,
'admin' as usuario_grabacion,
now() as fecha_grabacion
from admon.usuario
where usuario='ccantarero';