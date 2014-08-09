-------------------------------------inicio script nuevo para produccion-------------------------------------------

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
'20140801' as fecha_grabacion,
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
'Asesor' as descripcion_rol,
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
'Informante' as descripcion_rol,
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
218 as id_rol,
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
218 as id_rol,
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
218 as id_rol,
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

--planificacion

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1838,41,0,'ROLE_RIGHT_MENU_PLANIFICACIONES','compartido/paginamaestra.xhtml','Menu planificaciones',0,NOW()
union
select 1839,41,0,'ROLE_RIGHT_CREAR_PLANIFICACIONES','planificacion/planificacion.xhtml','Crear planificaciones',1,NOW()
union
select 1840,41,0,'ROLE_RIGHT_VER_PLANIFICACIONES','planificacion/edicion_planificacion.xhtml','Ver planificaciones',1,NOW()

--ejecuciones

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1841,41,0,'ROLE_RIGHT_MENU_EJECUCUIONES','compartido/paginamaestra.xhtml','Menu ejecuciones',0,NOW()
union
select 1842,41,0,'ROLE_RIGHT_AGREGAR_BITACORAS','planificacion/bitacoras.xhtml','Agregar bitacoras',1,NOW()
union
select 1843,41,0,'ROLE_RIGHT_VER_BITACORAS','planificacion/ejecuciones.xhtml','Ver bitacoras',1,NOW()

--instrumentos y evaluaciones

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1844,41,0,'ROLE_RIGHT_MENU_INSTRUMENTOS','compartido/paginamaestra.xhtml','Menu instrumentos',0,NOW()
union
select 1845,41,0,'ROLE_RIGHT_CONFIGURAR_INSTRUMENTOS','planificaccion/instrumentos.xhtml','Configurar instrumentos',1,NOW()
union
select 1850,41,0,'ROLE_RIGHT_AGREGAR_EVALUACIONES','solicitudes/registro_evaluacion.xhtml','Agregar evaluaciones',1,NOW()
union
select 1852,41,0,'ROLE_RIGHT_VER_EVALUACIONES','solicitudes/registro_solicitud.xhtml','Ver evaluaciones',1,NOW()

--solicitudes

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1846,41,0,'ROLE_RIGHT_MENU_SOLICITUDES','compartido/paginamaestra.xhtml','Menu solicitudes',0,NOW()
union	--EL QUE CREA TAMBIEN ANULA Y ENVIA
select 1847,41,0,'ROLE_RIGHT_CREAR_SOLICITUDES','compartido/solicitudes.xhtml','Crear solicitudes',1,NOW()

--mantenedores

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1848,41,0,'ROLE_RIGHT_MENU_CONFIGURACION','compartido/paginamaestra.xhtml','Menu configuracion',0,NOW()
union
select 1849,41,0,'ROLE_RIGHT_CONFIGURAR_MANTENEDORES','usuario/configuracion.xhtml','Configurar mantenedores',1,NOW()

--reportes

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1853,41,0,'ROLE_RIGHT_MENU_REPORTES','planificacion/reportes.xhtml','Reportes',1,NOW()

--expediente/portafolio

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1854,41,0,'ROLE_RIGHT_MENU_PORTAFOLIO','solicitudes/candidatos.xhtml','Portafolio',1,NOW()
union
select 1855,41,0,'ROLE_RIGHT_EDITAR_PORTAFOLIO','solicitudes/expediente.xhtml','Edita portafolio',1,NOW()
union
select 1856,41,0,'ROLE_RIGHT_VER_PORTAFOLIO','solicitudes/expediente.xhtml','Ver portafolio',1,NOW()

--convocatorias

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1857,41,0,'ROLE_RIGHT_EDITAR_CONVOCATORIA','solicitudes/solicitud.xhtml','Editar convocatoria',1,NOW()
union
select 1858,41,0,'ROLE_RIGHT_VER_CONVOCATORIA','solicitudes/solicitud.xhtml','Ver convocatoria',1,NOW()

--autorizacion, rechazo y matricula

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1851,41,0,'ROLE_RIGHT_SELECCION','solicitudes/solicitud.xhtml','Seleccion',1,NOW()

--actualizando las opciones de cada rol

update admon.roles set opciones='1838,1840,1846,1853,1854,1856,1857,1858' where id_rol=213			--asesor
update admon.roles set opciones='1838,1840,1858,1854,1856,1853,1846,1841,1842,1843' where id_rol=214			--verificador
update admon.roles set opciones='1838,1839,1840,1841,1842,1843,1844,1845,1846,1847,1850,1851,1852,1853,1854,1855,1856,1857,1858' where id_rol=215		--tecnico docente
update admon.roles set opciones='1838,1839,1840,1850,1852,1858,1854,1855,1856,1853,1846,1847' where id_rol=216		--informante
update admon.roles set opciones='1858,1855,1854,1856,1846,1847' where id_rol=217				--visitante
update admon.roles set opciones='1838,1839,1840,1841,1842,1843,1844,1845,1846,1847,1848,1849,1850,1851,1852,1853,1854,1855,1856,1857,1858' where id_rol=218		--admin
update admon.roles set opciones='1838,1840,1844,1845,1846,1850,1852,1853,1854,1855,1856,1857,1858' where id_rol=219	--evaluador

---vistas auxiliares de la aplicacion

drop table sccl.vista_evaluaciones;			--eliminando tabla creada automaticamente por JPA, usamos la vista en su lugar

CREATE OR REPLACE VIEW sccl.vista_evaluaciones AS 
 SELECT e.evaluacion_id, 
        CASE
            WHEN sum(COALESCE(eg.puntaje, 0)) >= e.puntaje_minimo THEN true
            ELSE false
        END AS aprobado, 
    sum(COALESCE(eg.puntaje, 0)) AS puntaje_obtenido
   FROM sccl.evaluaciones e
   LEFT JOIN sccl.evaluacion_guia eg ON e.evaluacion_id = eg.evaluacion_id
  GROUP BY e.evaluacion_id;
  
--vistas para reportes

--reporte de rpt_prematricula

create or replace view sccl.rpt_prematricula as
select	p.certificacion_ifp_id centro_id,
	p.certificacion_ifp_nombre nombre_centro,
	s.solicitud_id solicitud_id,
	s.fecha_registro fecha_solicitud,
	c.primer_nombre primer_nombre,
	c.segundo_nombre segundo_nombre,
	c.primer_apellido primer_apellido,
	c.segundo_apellido segundo_apellido,
	case when c.tipo_identificacion=1 then c.numero_identificacion else null end cedula,
	c.fecha_nacimiento fecha_nacimiento,
	c.lugar_nacimiento lugar_nacimiento,
	c.telefono1 telefono,
	c.telefono2 celular,
	m.municipioid municipio_id,
	m.nombre municipio,
	d.departamentoid departamento_id,
	d.nombre departamento,
	c.direccion_actual direccion,
	case when c.sexo_id=1 then 'F' else 'M' end sexo,
	p.certificacion_nombre certificacion,
	n.descripcion_nivel nivel,
	s.experiencia experiencia,
	s.situacion_laboral labora
from	sccl.solicitudes s
	inner join
	sccl.contactos c
	on s.contacto_id=c.contacto_id
	and s.solicitud_estatus not in (35,44,45)	-- no registrada, no anulada, no rechazada
	inner join
	public.municipio m
	on c.municipio_id=m.municipioid
	and c.departamento_id=m.departamentoid
	inner join
	public.departamento d
	on m.departamentoid=d.departamentoid
	inner join
	sccl.certificaciones p
	on s.certificacion_id=p.certificacion_id
	and p.certificacion_estatus!=18
	inner join
	registro_cobranza.catalogo_nivel_academico n
	on n.id_nivel_academico=s.escolaridad_id

--convocatoria de una solicitud

create or replace view sccl.rpt_convocatorias as
select	cv.solicitud_id solicitud_id,
	cv.convocatoria_id convocatoria_id,
	x.actividad_tipo_id tipo_id,
	c.contacto_id contacto_id,
	c.primer_nombre primer_nombre,
	c.segundo_nombre segundo_nombre,
	c.primer_apellido primer_apellido,
	c.segundo_apellido segundo_apellido,
	case when c.tipo_identificacion=1 then c.numero_identificacion else null end cedula,
	a.nombre_completo nombre_asesor,
	a.telefono2 celular,
	a.correo1 email,
	cv.fecha_programacion fecha_hora,
	cv.centro centro,
	cv.lugar lugar
from	sccl.contactos a
	inner join
	sccl.convocatorias cv
	on a.contacto_id=cv.contacto_id
	inner join
	sccl.solicitudes s
	on cv.solicitud_id=s.solicitud_id
	inner join
	sccl.actividades x
	on cv.actividad_id=x.actividad_id
	and x.actividad_tipo_id in (6,7)	--asesoramiento grupal e individual
	and x.actividad_certificacion_id=s.certificacion_id
	and x.actividad_estado_id!=14		--actidad activa
	inner join
	sccl.contactos c
	on s.contacto_id=c.contacto_id

--unidades de una solicitud

create or replace view sccl.vista_unidades_solicitud as
select	s.solicitud_id solicitud_id,
	u.unidad_id unidad_id,
	uc.descripcion descripcion,
	uc.cod_cualificacion codigo
from	sccl.solicitudes s
	inner join
	sccl.unidades u
	on s.certificacion_id=u.certificacion_id
	inner join
	registro_cobranza.cu_cat_uc uc
	on u.unidad_id=uc.id
order by
	2

--ajustando los mantenedores eliminamos los estados que no seran utilizados

delete from sccl.mantenedores where mantenedor_id in (12,14)		--estatus de actividad pendiente y anulado
delete from sccl.mantenedores where mantenedor_id in (33)			--tipo de instrumento no definido
delete from sccl.mantenedores where mantenedor_id in (59,60)		--catalogo de generos
	
-------------------------------------fin script nuevo para produccion----------------------------------------------

-------------------------------------script para pruebas con el ENAH

/*
6. Preparando usuario y roles para el centro 4020 ENAH
*/

insert into admon.usuarios_sistemas(id_sistema,id_empleado,usuario,id_rol,activo,usuario_grabacion,fecha_grabacion)
select 41,id_empleado,usuario,215,1,'ccantarero',now() from admon.usuario where usuario='mespinozaf'	--tecnico docente
union
select 41,id_empleado,usuario,219,1,'ccantarero',now() from admon.usuario where usuario='mmoran'		--evaluador
union
select 41,id_empleado,usuario,213,1,'ccantarero',now() from admon.usuario where usuario='mportugal'	--asesor
union
select 41,id_empleado,usuario,216,1,'ccantarero',now() from admon.usuario where usuario='gsalvatierra'	--informante
union
select 41,id_empleado,usuario,214,1,'ccantarero',now() from admon.usuario where usuario='fredisacs'	--verificador

update admon.usuario set clave='0c3c46276360da4dab11bf247c4d93ce' where usuario in ('mespinozaf','mmoran','mportugal','gsalvatierra','fredisacs','cromero')