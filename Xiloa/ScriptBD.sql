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
42 as id_sistema,
'Sistema de Certificacion de Competencias Laborales' as nombre_sistema,
'SCCL' as siglas,
1 as activo,
'admin' as usuario_grabacion,
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
226 as id_rol,
'Asesor' as descripcion_rol,
42 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
227 as id_rol,
'Verificador' as descripcion_rol,
42 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
228 as id_rol,
'Tecnico Docente' as descripcion_rol,
42 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
229 as id_rol,
'Informante' as descripcion_rol,
42 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
230 as id_rol,
'Visitante' as descripcion_rol,
42 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
231 as id_rol,
'Administrador' as descripcion_rol,
42 as id_sistema,
1 as activo,
'admon' as usuario_grabacion,
now() as fecha_grabacion
union
select
232 as id_rol,
'Evaluador' as descripcion_rol,
42 as id_sistema,
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
42 as id_sistema,
id_empleado as id_empleado,
usuario as usuario,
231 as id_rol,
1 as activo,
'admin' as usuario_grabacion,
now() as fecha_grabacion
from admon.usuario
where usuario='djanson'
union
select
42 as id_sistema,
id_empleado as id_empleado,
usuario as usuario,
231 as id_rol,
1 as activo,
'admin' as usuario_grabacion,
now() as fecha_grabacion
from admon.usuario
where usuario='cromero'
union
select
42 as id_sistema,
id_empleado as id_empleado,
usuario as usuario,
231 as id_rol,
1 as activo,
'admin' as usuario_grabacion,
now() as fecha_grabacion
from admon.usuario
where usuario='ccantarero'
union
select
42 as id_sistema,
id_empleado as id_empleado,
usuario as usuario,
231 as id_rol,
1 as activo,
'admin' as usuario_grabacion,
now() as fecha_grabacion
from admon.usuario
where usuario='admin';

--configurando los permisos de cada rol

--planificacion

delete from admon.menu where id between 1704 and 1724

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1704,42,0,'ROLE_RIGHT_MENU_PLANIFICACIONES','compartido/paginamaestra.xhtml','Menu planificaciones',0,NOW()
union
select 1705,42,0,'ROLE_RIGHT_CREAR_PLANIFICACIONES','planificacion/planificacion.xhtml','Crear planificaciones',1,NOW()
union
select 1706,42,0,'ROLE_RIGHT_VER_PLANIFICACIONES','planificacion/edicion_planificacion.xhtml','Ver planificaciones',1,NOW()

--ejecuciones

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1707,42,0,'ROLE_RIGHT_MENU_EJECUCUIONES','compartido/paginamaestra.xhtml','Menu ejecuciones',0,NOW()
union
select 1708,42,0,'ROLE_RIGHT_AGREGAR_BITACORAS','planificacion/bitacoras.xhtml','Agregar bitacoras',1,NOW()
union
select 1709,42,0,'ROLE_RIGHT_VER_BITACORAS','planificacion/ejecuciones.xhtml','Ver bitacoras',1,NOW()

--instrumentos y evaluaciones

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1710,42,0,'ROLE_RIGHT_MENU_INSTRUMENTOS','compartido/paginamaestra.xhtml','Menu instrumentos',0,NOW()
union
select 1711,42,0,'ROLE_RIGHT_CONFIGURAR_INSTRUMENTOS','planificaccion/instrumentos.xhtml','Configurar instrumentos',1,NOW()
union
select 1712,42,0,'ROLE_RIGHT_AGREGAR_EVALUACIONES','solicitudes/registro_evaluacion.xhtml','Agregar evaluaciones',1,NOW()
union
select 1713,42,0,'ROLE_RIGHT_VER_EVALUACIONES','solicitudes/registro_solicitud.xhtml','Ver evaluaciones',1,NOW()

--solicitudes

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1714,42,0,'ROLE_RIGHT_MENU_SOLICITUDES','compartido/paginamaestra.xhtml','Menu solicitudes',0,NOW()
union	--EL QUE CREA TAMBIEN ANULA Y ENVIA
select 1715,42,0,'ROLE_RIGHT_CREAR_SOLICITUDES','compartido/solicitudes.xhtml','Crear solicitudes',1,NOW()

--mantenedores

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1716,42,0,'ROLE_RIGHT_MENU_CONFIGURACION','compartido/paginamaestra.xhtml','Menu configuracion',0,NOW()
union
select 1717,42,0,'ROLE_RIGHT_CONFIGURAR_MANTENEDORES','usuario/configuracion.xhtml','Configurar mantenedores',1,NOW()

--reportes

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1718,42,0,'ROLE_RIGHT_MENU_REPORTES','planificacion/reportes.xhtml','Reportes',1,NOW()

--expediente/portafolio

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1719,42,0,'ROLE_RIGHT_MENU_PORTAFOLIO','solicitudes/candidatos.xhtml','Portafolio',1,NOW()
union
select 1720,42,0,'ROLE_RIGHT_EDITAR_PORTAFOLIO','solicitudes/expediente.xhtml','Edita portafolio',1,NOW()
union
select 1721,42,0,'ROLE_RIGHT_VER_PORTAFOLIO','solicitudes/expediente.xhtml','Ver portafolio',1,NOW()

--convocatorias

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1722,42,0,'ROLE_RIGHT_EDITAR_CONVOCATORIA','solicitudes/solicitud.xhtml','Editar convocatoria',1,NOW()
union
select 1723,42,0,'ROLE_RIGHT_VER_CONVOCATORIA','solicitudes/solicitud.xhtml','Ver convocatoria',1,NOW()

--autorizacion, rechazo y matricula

insert into admon.menu(id,id_sistema,parent_id,texto,href,title,posicion,fecha_grabacion)
select 1724,42,0,'ROLE_RIGHT_SELECCION','solicitudes/solicitud.xhtml','Seleccion',1,NOW()

--actualizando las opciones de cada rol

update admon.roles set opciones='1704,1706,1714,1718,1719,1721,1722,1723' where id_rol=226			--asesor
update admon.roles set opciones='1704,1706,1723,1719,1721,1718,1714,1707,1708,1709' where id_rol=227			--verificador
update admon.roles set opciones='1704,1705,1706,1707,1708,1709,1710,1711,1714,1715,1712,1724,1713,1718,1719,1720,1721,1722,1723' where id_rol=228		--tecnico docente
update admon.roles set opciones='1704,1705,1706,1712,1713,1723,1719,1720,1721,1718,1714,1715' where id_rol=229		--informante
update admon.roles set opciones='1723,1720,1719,1721,1714,1715' where id_rol=230				--visitante
update admon.roles set opciones='1704,1705,1706,1707,1708,1709,1710,1711,1714,1715,1716,1717,1712,1724,1713,1718,1719,1720,1721,1722,1723' where id_rol=231		--admin
update admon.roles set opciones='1704,1706,1710,1711,1714,1712,1713,1718,1719,1720,1721,1722,1723' where id_rol=232	--evaluador

---vistas auxiliares de la aplicacion

CREATE OR REPLACE VIEW sccl.vista_evaluaciones AS 
 SELECT e.evaluacion_id, 
        CASE
            WHEN sum(COALESCE(eg.puntaje, 0)) >= e.puntaje_minimo THEN true
            ELSE false
        END AS aprobado, 
    sum(COALESCE(eg.puntaje, 0)) AS puntaje_obtenido
   FROM sccl.evaluaciones e
   LEFT JOIN sccl.evaluacion_guia eg ON e.evaluacion_id = eg.evaluacion_id
  GROUP BY e.evaluacion_id,e.puntaje_minimo;
  
--vistas para reportes

--reporte de rpt_prematricula

create or replace view sccl.rpt_prematricula as
select	p.certificacion_ifp_id as centro_id,
	p.certificacion_ifp_nombre as nombre_centro,
	s.solicitud_id as solicitud_id,
	s.fecha_registro as fecha_solicitud,
	c.primer_nombre as primer_nombre,
	c.segundo_nombre as segundo_nombre,
	c.primer_apellido as primer_apellido,
	c.segundo_apellido as segundo_apellido,
	case when c.tipo_identificacion=1 then c.numero_identificacion else null end as cedula,
	c.fecha_nacimiento as fecha_nacimiento,
	c.lugar_nacimiento as lugar_nacimiento,
	c.telefono1 as telefono,
	c.telefono2 as celular,
	m.municipioid as municipio_id,
	m.nombre as municipio,
	d.departamentoid as departamento_id,
	d.nombre as departamento,
	c.direccion_actual as direccion,
	case when c.sexo_id=1 then 'F' else 'M' end as sexo,
	p.certificacion_id as certificacion_id,
	p.certificacion_nombre as certificacion,
	p.certificacion_curso_id as curso_id,
	p.certificacion_estructura_id as estructura_id,
	n.descripcion_nivel as nivel,
	s.experiencia as experiencia,
	s.situacion_laboral as labora
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
	on n.id_nivel_academico=c.nivel_academico

--buscando al asesor grupal, asesor individual, evaluador y verificador de una solicitud

create or replace view sccl.vista_involucrados as
select	s.solicitud_id as solicitud_id,
	a.actividad_tipo_id as actividad_tipo_id,
	x.contacto_id as contacto_id,
	x.nombre_completo as nombre
from	sccl.solicitudes s
	inner join
	sccl.certificaciones c
	on s.certificacion_id=c.certificacion_id
	inner join
	sccl.actividades a
	on a.actividad_certificacion_id=c.certificacion_id
	and a.actividad_tipo_id in (6,7,8,12)		--asesor grupal, individual, evaluador y verificador
	and s.solicitud_estatus not in (35,44,45)
	inner join
	sccl.involucrados i
	on a.actividad_id=i.actividad_id
	and i.activo=true
	inner join
	sccl.contactos x
	on i.contacto_id=x.contacto_id

--requisitos de una solicitud

create or replace view sccl.vista_requisitos_certificacion as
select	r.certificacion_id as certificacion_id,
	r.requisito_codigo as codigo,
	r.requisito_codigo_acreditacion as codigo_acreditacion,
	r.requisito_descripcion as descripcion
from	sccl.requisitos r

--convocatoria de una solicitud

create or replace view sccl.rpt_convocatorias as
select	cv.solicitud_id as solicitud_id,
	cv.convocatoria_id as convocatoria_id,
	x.actividad_tipo_id as tipo_id,
	c.contacto_id as contacto_id,
	c.primer_nombre as primer_nombre,
	c.segundo_nombre as segundo_nombre,
	c.primer_apellido as primer_apellido,
	c.segundo_apellido as segundo_apellido,
	case when c.tipo_identificacion=1 then c.numero_identificacion else null end as cedula,
	a.nombre_completo as nombre_asesor,
	a.telefono2 as celular,
	a.correo1 as email,
	cv.fecha_programacion as fecha_hora,
	cv.centro as centro,
	cv.lugar as lugar
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
	and x.actividad_tipo_id in (6,7,8)	--asesoramiento grupal, individual y evaluacion
	and x.actividad_certificacion_id=s.certificacion_id
	and x.actividad_estado_id!=14		--actidad activa
	inner join
	sccl.contactos c
	on s.contacto_id=c.contacto_id

--unidades de una solicitud

create or replace view sccl.vista_unidades_solicitud as
select	s.solicitud_id as solicitud_id,
	u.unidad_id as unidad_id,
	uc.descripcion as descripcion,
	uc.cod_cualificacion as codigo
from	sccl.solicitudes s
	inner join
	sccl.unidades u
	on s.certificacion_id=u.certificacion_id
	inner join
	registro_cobranza.cu_cat_uc uc
	on u.unidad_id=uc.id
order by
	2

--evaluaciones x unidad

create or replace view sccl.vista_evaluacion_unidades as
select	e.solicitud_id as solicitud_id,
	e.evaluacion_id as evaluacion_id,
	e.instrumento_id as instrumento_id,
	i.instrumento_codigo as instrumento_codigo,
	i.instrumento_nombre as instrumento_nombre,
	u.codigo as unidad_codigo,
	u.descripcion as unidad_descripcion,
	e.requiere_evidencia as evidencia,
	ve.puntaje_obtenido as puntaje,
	ve.aprobado as aprobado,
	e.observaciones as observaciones
from	sccl.evaluaciones e
	inner join
	sccl.vista_evaluaciones ve
	on e.evaluacion_id=ve.evaluacion_id
	inner join
	sccl.instrumentos i
	on e.instrumento_id=i.instrumento_id
	left join
	sccl.vista_unidades_solicitud u
	on u.unidad_id=i.instrumento_unidad_id
order by
	i.instrumento_id

--datos laborales y academicos

create or replace view sccl.vista_laborales as
select	l.contacto_id as contacto_id,
	l.laboral_institucion as institucion,
	l.laboral_institucion_direccion as direccion,
	l.laboral_institucion_telefono as telefono,
	null as tipo_empresa,
	l.laboral_institucion_cargo as cargo,
	l.laboral_descripcion as descripcion_cargo,
	l.laboral_fecha_inicia as inicio,
	l.laboral_fecha_finaliza as fin,
	l.laboral_nombre as diploma,
	l.laboral_tipo as tipo
from	sccl.laborales l
	inner join
	sccl.solicitudes s
	on s.contacto_id=l.contacto_id
	and l.laboral_tipo in (23,24,25,26)

--vista instrumentos previstos

create or replace view sccl.instrumentos_previstos as
select	e.solicitud_id as solicitud_id,
	m.mantenedor_valor as tipo
from	sccl.evaluaciones e
	inner join
	sccl.instrumentos i
	on e.instrumento_id=i.instrumento_id
	and e.activo=true
	inner join
	sccl.mantenedores m
	on i.instrumento_tipo=m.mantenedor_id
group by
	e.solicitud_id,
	m.mantenedor_valor

--ajustando los mantenedores eliminamos los estados que no seran utilizados o que seran reconvertidos
	
delete from sccl.mantenedores where mantenedor_id in (14)		--estatus de actividad anulada
delete from sccl.mantenedores where mantenedor_id in (33)		--tipo de instrumento no definido
delete from sccl.mantenedores where mantenedor_id in (59,60)	--catalogo de generos
	
-------------------------------------fin script nuevo para produccion----------------------------------------------

-------------------------------------script para pruebas con el ENAH

/*
6. Preparando usuario y roles para el centro 4020 ENAH
*/

insert into admon.usuarios_sistemas(id_sistema,id_empleado,usuario,id_rol,activo,usuario_grabacion,fecha_grabacion)
select 42,id_empleado,usuario,228,1,'ccantarero',now() from admon.usuario where usuario='mespinozaf'	--tecnico docente
union
select 42,id_empleado,usuario,232,1,'ccantarero',now() from admon.usuario where usuario='mmoran'		--evaluador
union
select 42,id_empleado,usuario,226,1,'ccantarero',now() from admon.usuario where usuario='mportugal'	--asesor
union
select 42,id_empleado,usuario,229,1,'ccantarero',now() from admon.usuario where usuario='gsalvatierra'	--informante
union
select 42,id_empleado,usuario,227,1,'ccantarero',now() from admon.usuario where usuario='fredisacs'	--verificador

update admon.usuario set clave='0c3c46276360da4dab11bf247c4d93ce' where usuario in ('mespinozaf','mmoran','mportugal','gsalvatierra','fredisacs','cromero')

--operaciones adicionales

--eliminar campo ticket y nombre en entidad solicitud
--buscar expresion regular para validar bien la cedula
--optimizar la busqueda de municipios de un departament, actualmente se hace con query
--optimizar dashboarsolicitudesmanagedbean
--cambiar etiqueta a mantenedores para estado de solicitud apto y no apto

update sccl.mantenedores set mantenedor_valor='Superado' where mantenedor_id=42
update sccl.mantenedores set mantenedor_valor='No Superado' where mantenedor_id=43

--eliminar la tabla paises
--cambiar el tipo de dato de la tabla sccl.laboral laboral_pais_id a varchar 
update sccl.laborales set laboral_pais_id='NIC'
--campo descripcion el sccl.laborales puede ser nulo
--cambiar el tipo de dato a la columna nacionalidad de contactos
ALTER TABLE sccl.contactos ALTER COLUMN nacionalidad_id TYPE varchar(255)

--seteando la nacionalidad nicaraguense por defecto
alter table sccl.contactos alter column nacionalidad_id set default 'NIC'
update sccl.contactos set nacionalidad_id='NIC'

--relaciones

--relaciones de la tabla unidades

alter table sccl.unidades add foreign key(certificacion_id) references sccl.certificaciones(certificacion_id)
alter table sccl.unidades add foreign key(unidad_id) references registro_cobranza.cu_cat_uc(id)

--relaciones de la tabla evaluaciones

alter table sccl.evaluaciones add foreign key(solicitud_id) references sccl.solicitudes(solicitud_id)
alter table sccl.evaluaciones add foreign key(instrumento_id) references sccl.instrumentos(instrumento_id)

--relaciones de la tabla convocatorias

alter table sccl.convocatorias add foreign key(actividad_id) references sccl.actividades(actividad_id)
alter table sccl.convocatorias add foreign key(solicitud_id) references sccl.solicitudes(solicitud_id)
alter table sccl.convocatorias add foreign key(contacto_id) references sccl.contactos(contacto_id)

--relaciones de la tabla instrumentos

alter table sccl.instrumentos add foreign key(instrumento_certificacion_id) references sccl.certificaciones(certificacion_id)
alter table sccl.instrumentos add foreign key(instrumento_unidad_id) references registro_cobranza.cu_cat_uc(id)

--relaciones de la tabla guias

alter table sccl.guias add foreign key(guia_unidad_id) references registro_cobranza.cu_cat_uc(id)

--relaciones de la tabla roles

alter table sccl.roles add foreign key(id_rol_inatec) references admon.roles(id_rol)

--relaciones de la tabla contactos

alter table sccl.contactos add foreign key(municipio_id,departamento_id) references public.municipio(municipioid,departamentoid)
alter table sccl.contactos add foreign key(nivel_academico) references registro_cobranza.catalogo_nivel_academico(id_nivel_academico)
alter table sccl.contactos add foreign key(usuario_inatec) references admon.usuario(usuario)
alter table sccl.contactos add foreign key(nacionalidad_id) references public.paises(pais_id)

--relaciones de la tabla solicitud_unidades

alter table sccl.solicitud_unidades add foreign key(unidad_id) references registro_cobranza.cu_cat_uc(id)

--relaciones not null

--contacto usuario de bitacora
--actividad de bitacora
--mantenedor estado de archivo
--rol de usuario 