package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name = "involucrados")
@Table(name = "involucrados", schema = "sccl")
@NamedQueries({
	@NamedQuery(name="Involucrado.findItemsByActividadId", query="select new support.Item(i.contacto.id, i.contacto.nombreCompleto) from actividades a, involucrados i where a.id=i.actividad.id and a.id=?1 and i.activo=true order by i.id desc"),
	@NamedQuery(name="Involucrado.findByActividadId", query="select i from involucrados i where i.actividad.id=?1 and i.activo=true order by i.id desc"),
	@NamedQuery(name="Involucrado.findNotInActividadId",
	query=	
	"select new model.Involucrado(a,c) "+
	"from	contactos c, actividades a, certificaciones x "+
	"where	a.id=?1 "+
			"and a.certificacion.id=x.id "+
			"and c.inatec=true "+
			"and c.rol.id=	case "+
						"when a.tipo.id in (8,9,10,11) then 8	"+	//evaluador evalua
						"when a.tipo.id in (6,7) then 7 "+			//evaluador evalua
						"when a.tipo.id in (5) then 3	"+			//tecnico docente matricula
						"when a.tipo.id in (1,2,3,4) then 4 "+		//informante prematricula y selecciona
						"else 0 end "+
			"and c.entidadId=x.ifpId "+
			"and not exists (select 1 from involucrados i where i.activo=true and i.contacto.id=c.id and i.actividad.id=a.id) "+
			"order by c.id desc"
	),
	@NamedQuery(name="Involucrado.findByActividadIdAndContactoId", query="select i from involucrados i where i.actividad.id=?1 and i.contacto.id=?2 and i.activo=true order by i.id desc")
})
public class Involucrado implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "involucrado_id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name="actividad_id", nullable = false)	
	private Actividad actividad;
	
	@ManyToOne
	@JoinColumn(name="contacto_id", nullable = false)	
	private Contacto contacto;
	
	@Column(name = "activo", nullable = false)	
	private boolean activo;
	
	public Involucrado(){
		super();
	}
	
	public Involucrado(Actividad actividad, Contacto contacto){
		super();
		this.actividad = actividad;
		this.contacto = contacto;
		this.activo = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}