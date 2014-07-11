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
now() as fecha_grabacion
union
select
219 as id_rol,
'Evaluador' as descripcion_rol,
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
select 1838,41,0,'ROLE_RIGHT_MENU_PLANIFICACIONES','compartido/paginamaestra.xhtml','Menu planificaciones',0,NOW()
union
select 1839,41,0,'ROLE_RIGHT_CREAR_PLANIFICACIONES','planificacion/planificacion.xhtml','Crear planificaciones',1,NOW()
union
select 1840,41,0,'ROLE_RIGHT_EDITAR_PLANIFICACIONES','planificacion/edicion_planificacion.xhtml','Editar planificaciones',1,NOW()
union
select 1841,41,0,'ROLE_RIGHT_MENU_EJECUCUIONES','compartido/paginamaestra.xhtml','Menu ejecuciones',0,NOW()
union
select 1842,41,0,'ROLE_RIGHT_AGREGAR_BITACORAS','planificacion/bitacoras.xhtml','Agregar bitacoras',1,NOW()
union
select 1843,41,0,'ROLE_RIGHT_VER_BITACORAS','planificacion/ejecuciones.xhtml','Ver bitacoras',1,NOW()
union
select 1844,41,0,'ROLE_RIGHT_MENU_INSTRUMENTOS','compartido/paginamaestra.xhtml','Menu instrumentos',0,NOW()
union
select 1845,41,0,'ROLE_RIGHT_CONFIGURAR_INSTRUMENTOS','planificaccion/instrumentos.xhtml','Configurar instrumentos',1,NOW()
union
select 1846,41,0,'ROLE_RIGHT_MENU_SOLICITUDES','compartido/paginamaestra.xhtml','Menu solicitudes',0,NOW()
union
select 1847,41,0,'ROLE_RIGHT_CREAR_SOLICITUDES','compartido/solicitudes.xhtml','Crear solicitudes',1,NOW()
union
select 1848,41,0,'ROLE_RIGHT_MENU_CONFIGURACION','compartido/paginamaestra.xhtml','Menu configuracion',0,NOW()
union
select 1849,41,0,'ROLE_RIGHT_CONFIGURAR_MANTENEDORES','usuario/configuracion.xhtml','Configurar mantenedores',1,NOW()
union
select 1850,41,0,'ROLE_RIGHT_AGREGAR_EVALUACIONES','solicitudes/registro_evaluacion.xhtml','Agregar evaluaciones',1,NOW()
union
select 1851,41,0,'ROLE_RIGHT_EDITAR_PORTAFOLIO','solicitudes/registro_solicitud.xhtml','Editar portafolio',1,NOW()
union
select 1852,41,0,'ROLE_RIGHT_VER_PORTAFOLIO','solicitudes/registro_solicitud.xhtml','Ver portafolio',1,NOW()
union
select 1853,41,0,'ROLE_RIGHT_MENU_REPORTES','planificacion/reportes.xhtml','Reportes',1,NOW()
union
select 1854,41,0,'ROLE_RIGHT_MENU_PARTICIPANTE','solicitudes/expediente.xhtml','Participante',1,NOW()
union
select 1855,41,0,'ROLE_RIGHT_EDITA_EXPEDIENTE','solicitudes/expediente_evaluacion.xhtml','Edita Expediente',1,NOW()
union
select 1856,41,0,'ROLE_RIGHT_EDITA_EXPEDIENTE_CONTACTO','solicitudes/expediente.xhtml','Expediente',1,NOW()
union
select 1857,41,0,'ROLE_RIGHT_EDITA_SEGUIMIENTO','solicitudes/convocatoria.xhtml','Seguimiento',1,NOW()
union
select 1858,41,0,'ROLE_RIGHT_MENU_CANDIDATOS','solicitudes/candidatos.xhtml','Candidatos',1,NOW()

update admon.roles set opciones='1838,1839,1840,1841,1842,1843,1844,1845,1846,1847,1848,1849,1850,1851,1852,1853,1855,1856,1857,1858' where id_rol=215
update admon.roles set opciones='1838,1839,1840,1841,1842,1843,1844,1845,1846,1847,1848,1849,1850,1851,1852,1853,1855,1856,1857,1858' where id_rol=218
--update admon.roles set opciones='1840,1842,1843,1846,1847,1849,1850,1852,1854,1856' where id_rol=217
update admon.roles set opciones=null where id_rol=217

------------------nueva seccion

--creando y setenado los roles al visitante

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 2000,41,0,'ROLE_RIGHT_MIS_SOLICITUDES','solicitudes/candidatos.xhtml','Candidatos',1,NOW()
union
select 2001,41,0,'ROLE_RIGHT_MI_PORTAFOLIO','solicitudes/expediente.xhtml','Candidatos',1,NOW()

update admon.roles set opciones='2000,2001' where id_rol=217

------------------fin de nueva seccion

--actualizando el instrumento en evaluaciones existentes

update	sccl.evaluaciones
set		evaluacion_instrumento_id=(		select	distinct g.instrumento_id 
										from	sccl.guias g
										where	guia_id in (	select	eg.guia_id 
																from	sccl.evaluacion_guia eg
																where	eg.evaluacion_id=sccl.evaluaciones.evaluacion_id
															)
									)
									
--para borrar la duplicidad de los mantenedores, roles y perfiles que existe en la bd inatec 
									
delete from sccl.perfiles_roles where perfil_id=2
delete from sccl.roles where rol_id>7
delete from sccl.perfiles where perfil_id=2
delete from sccl.mantenedores where mantenedor_id>39

--actualiza los estados del portafolio, cambio realizado por dchavez 22/02/2014

update sccl.mantenedores
set mantenedor_anterior=null,
mantenedor_proximo=null,
mantenedor_valor='Verificado Aprobado'
where mantenedor_id=26;

update sccl.mantenedores
set mantenedor_anterior=null,
mantenedor_proximo=null,
mantenedor_valor='Verificado no Aprobado'
where mantenedor_id=27

--columnas que ahora son null

alter table sccl.solicitudes alter column experiencia drop not null
alter table sccl.solicitudes alter column ocupacion drop not null
alter table sccl.solicitudes alter column oficio drop not null
alter table sccl.laborales alter column laboral_fecha_finaliza drop not null
alter table sccl.evaluaciones alter column evaluacion_observaciones drop not null

update sccl.certificaciones set certificacion_estatus=9 where certificacion_estatus is null
update sccl.actividades set actividad_estado_id=12 where actividad_estado_id is null

--creando vistas

CREATE OR REPLACE VIEW sccl.evaluaciones_view AS 
 SELECT e.evaluacion_id, 
        CASE
            WHEN sum(COALESCE(eg.puntaje, 0::real)) >= 50::double precision THEN true
            ELSE false
        END AS aprobado, 
    sum(COALESCE(eg.puntaje, 0::real)) AS puntaje
   FROM sccl.evaluaciones e
   LEFT JOIN sccl.evaluacion_guia eg ON e.evaluacion_id = eg.evaluacion_id
  GROUP BY e.evaluacion_id;
