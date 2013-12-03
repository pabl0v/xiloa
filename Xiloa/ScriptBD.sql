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
41 id_sistema,
'Sistema de Certificacion de Competencias Laborales' as nombre_sistema,
'SCCL' as siglas,
1 as activo,
'admin' usuario_grabacion,
'20131022' as fecha_grabacion,
null as usuario_actualizacion,
null as alias;

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
41 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
214 as id_rol,
'Verificador' as descripcion_rol,
41 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
215 as id_rol,
'Tecnico Docente' as descripcion_rol,
41 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
216 as id_rol,
'Registro Academico' as descripcion_rol,
41 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
217 as id_rol,
'Visitante' as descripcion_rol,
41 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
218 as id_rol,
'Administrador' as descripcion_rol,
41 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion;

--registrando usuario administrador

--creando una cuenta administrador para el sistema

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
'21232f297a57a5a743894a0e4a801fc3' as clave,
'admin' as clave_anterior,
'Administrador SCCL' as nombre_completo,
'Administrador SCCL' as cargo_usuario,
1 as tipo_usuario,
'admin@test.com' as correo,
0 as contador_intentos,
1 as activo,
now() as fecha_cambio_clave,
'admin' as usuario_grabacion,
now() as fecha_grabacion,
1 as estado

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
41 as id_sistema,
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
41 as id_sistema,
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
41 as id_sistema,
id_empleado as id_empleado,
usuario as usuario,
215 as id_rol,
1 as activo,
'admin' as usuario_grabacion,
now() as fecha_grabacion
from admon.usuario
where usuario='ccantarero';
union
select
41 as id_sistema,
id_empleado as id_empleado,
usuario as usuario,
218 as id_rol,
1 as activo,
'admin' as usuario_grabacion,
now() as fecha_grabacion
from admon.usuario
where usuario='admin';

--configurando los permisos de cada rol

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1610,41,0,'ROLE_RIGHT_MENU_PLANIFICACIONES','compartido/paginamaestra.xhtml','Menu planificaciones',0,NOW()
union
select 1611,41,0,'ROLE_RIGHT_CREAR_PLANIFICACIONES','planificacion/planificacion.xhtml','Crear planificaciones',1,NOW()
union
select 1612,41,0,'ROLE_RIGHT_EDITAR_PLANIFICACIONES','planificacion/edicion_planificacion.xhtml','Editar planificaciones',1,NOW()
union
select 1613,41,0,'ROLE_RIGHT_MENU_EJECUCUIONES','compartido/paginamaestra.xhtml','Menu ejecuciones',0,NOW()
union
select 1614,41,0,'ROLE_RIGHT_AGREGAR_BITACORAS','planificacion/bitacoras.xhtml','Agregar bitacoras',1,NOW()
union
select 1615,41,0,'ROLE_RIGHT_VER_BITACORAS','planificacion/ejecuciones.xhtml','Ver bitacoras',1,NOW()
union
select 1616,41,0,'ROLE_RIGHT_MENU_INSTRUMENTOS','compartido/paginamaestra.xhtml','Menu instrumentos',0,NOW()
union
select 1617,41,0,'ROLE_RIGHT_CONFIGURAR_INSTRUMENTOS','planificaccion/instrumentos.xhtml','Configurar instrumentos',1,NOW()
union
select 1618,41,0,'ROLE_RIGHT_MENU_SOLICITUDES','compartido/paginamaestra.xhtml','Menu solicitudes',0,NOW()
union
select 1619,41,0,'ROLE_RIGHT_CREAR_SOLICITUDES','compartido/solicitudes.xhtml','Crear solicitudes',1,NOW()
union
select 1620,41,0,'ROLE_RIGHT_MENU_CONFIGURACION','compartido/paginamaestra.xhtml','Menu configuracion',0,NOW()
union
select 1621,41,0,'ROLE_RIGHT_CONFIGURAR_MANTENEDORES','usuario/configuracion.xhtml','Configurar mantenedores',1,NOW()
union
select 1622,41,0,'ROLE_RIGHT_AGREGAR_EVALUACIONES','solicitudes/registro_evaluacion.xhtml','Agregar evaluaciones',1,NOW()
union
select 1623,41,0,'ROLE_RIGHT_EDITAR_PORTAFOLIO','solicitudes/registro_solicitud.xhtml','Editar portafolio',1,NOW()
union
select 1624,41,0,'ROLE_RIGHT_VER_PORTAFOLIO','solicitudes/registro_solicitud.xhtml','Ver portafolio',1,NOW()
union
select 1625,41,0,'ROLE_RIGHT_MENU_REPORTES','planificacion/reportes.xhtml','Reportes',1,NOW()
union
select 1626,41,0,'ROLE_RIGHT_MENU_PARTICIPANTE','solicitudes/expediente.xhtml','Participante',1,NOW()
union
select 1627,41,0,'ROLE_RIGHT_EDITA_EXPEDIENTE','solicitudes/expediente_evaluacion.xhtml','Edita Expediente',1,NOW()
union
select 1628,41,0,'ROLE_RIGHT_EDITA_EXPEDIENTE_CONTACTO','solicitudes/expediente.xhtml','Expediente',1,NOW()
union
select 1629,41,0,'ROLE_RIGHT_EDITA_SEGUIMIENTO','solicitudes/convocatoria.xhtml','Seguimiento',1,NOW()

update admon.roles set opciones='1610,1611,1612,1613,1614,1615,1616,1617,1618,1619,1620,1621,1622,1623,1624,1625,1627,1628,1629' where id_rol=215
update admon.roles set opciones='1610,1611,1612,1613,1614,1615,1616,1617,1618,1619,1620,1621,1622,1623,1624,1625,1627,1628,1629' where id_rol=218
update admon.roles set opciones='1610,1611,1612,1613,1614,1615,1616,1617,1618,1619,1620,1621,1622,1623,1624,1625,1625' where id_rol=217