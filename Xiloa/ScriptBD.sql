--registrando usuario administrador

insert into perfiles(perfil_id,perfil_descripcion,perfil_habilitado,perfil_modulo,perfil_nombre,perfil_permiso,perfil_permitido,perfil_seccion)
values(1,'admin',true,'all','admin','all',true,'all');

insert into roles(rol_id,rol_descripcion,rol_nombre,rol_estatus)
values(1,'admin','admin',true);

insert into perfiles_roles(rol_id,perfil_id)
values(1,1);

insert into usuarios(usuario_id,usuario_alias,usuario_pwd,usuario_estatus,rol_id)
values(1,'admin','d033e22ae348aeb5660fc2140aec35850c4da997',true,1);

--otros usuarios y roles

insert into roles(rol_id,rol_descripcion,rol_nombre,rol_estatus)
values(2,'supervisor','supervisor',true);

insert into roles(rol_id,rol_descripcion,rol_nombre,rol_estatus)
values(3,'verificador','verificador',true);

insert into roles(rol_id,rol_descripcion,rol_nombre,rol_estatus)
values(4,'tecnico docente','tecnico_docente',true);

insert into roles(rol_id,rol_descripcion,rol_nombre,rol_estatus)
values(5,'registro academico','registro_academico',true);

insert into perfiles_roles(rol_id,perfil_id)
values(2,1);

insert into perfiles_roles(rol_id,perfil_id)
values(3,1);

insert into perfiles_roles(rol_id,perfil_id)
values(4,1);

insert into perfiles_roles(rol_id,perfil_id)
values(5,1);

insert into usuarios(usuario_id,usuario_alias,usuario_pwd,usuario_estatus,rol_id)
values(2,'nlopez','6fb201c9a82fff28137a7458a6a1ab30e62f0638',true,1);

insert into usuarios(usuario_id,usuario_alias,usuario_pwd,usuario_estatus,rol_id)
values(3,'aruiz','d2f019744f8979e21d34ffd0559f4b5f32e49672',true,1);

insert into usuarios(usuario_id,usuario_alias,usuario_pwd,usuario_estatus,rol_id)
values(4,'lperez','063a4afbf02e71da217002cac400f7a352fd1f92',true,1);

insert into usuarios(usuario_id,usuario_alias,usuario_pwd,usuario_estatus,rol_id)
values(5,'larauz','da5e0771d5309508c615aa312cd116c64e07e303',true,1);

--registrando usuarios con roles varios para prueba

insert into contactos(
contacto_id,
correo1,
correo2,
direccion_actual,
entidad_id,
fecha_nacimiento,
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
usuario_id)
select
1,
'nleon@inactec.edu.ni',
null,
'Managua',
1,
'20130101',
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
2;

insert into contactos(
contacto_id,
correo1,
correo2,
direccion_actual,
entidad_id,
fecha_nacimiento,
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
usuario_id)
select
2,
'aruiz@inactec.edu.ni',
null,
'Managua',
1,
'20130101',
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
3;

insert into contactos(
contacto_id,
correo1,
correo2,
direccion_actual,
entidad_id,
fecha_nacimiento,
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
usuario_id)
select
3,
'lperez@inactec.edu.ni',
null,
'Managua',
1,
'20130101',
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
4;

insert into contactos(
contacto_id,
correo1,
correo2,
direccion_actual,
entidad_id,
fecha_nacimiento,
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
usuario_id)
select
5,
'aruiz@inactec.edu.ni',
null,
'Managua',
1,
'20130101',
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
5;