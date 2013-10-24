package view;

import java.util.Date;
import java.util.List;

import model.Mantenedor;
import model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;

@Component
@Scope("request")
public class ActividadManagedBean {

	@Autowired
	private IService service;

	private String nombreActividad;
	private String descripcionActividad;
	private Mantenedor selectedTipoActividad;
	private List<Mantenedor> tipoActividades;
	private Date fechaInicial;
	private Date fechaFinal;
	private String destino;
	private Usuario creador;
	private Usuario ejecutor;

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public String getDescripcionActividad() {
		return descripcionActividad;
	}

	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
	}

	public Mantenedor getSelectedTipoActividad() {
		return selectedTipoActividad;
	}

	public void setSelectedTipoActividad(Mantenedor selectedTipoActividad) {
		this.selectedTipoActividad = selectedTipoActividad;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Usuario getCreador() {
		return creador;
	}

	public Usuario getEjecutor() {
		return ejecutor;
	}

	public void setEjecutor(Usuario ejecutor) {
		this.ejecutor = ejecutor;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	public List<Mantenedor> getMantenedorActividades(){
		tipoActividades = service.getMantenedorActividades(); 
		return tipoActividades;
	}
	
	public void guardar(){
		
	}
}