package view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import model.Certificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;

@Component
@Scope(value="request")
public class InicioManagedBean {

	@Autowired
	private IService service;
	private List<Certificacion> certificaciones;
	private Certificacion selectedCertificacion;

	public InicioManagedBean(){
		super();
		certificaciones = new ArrayList<Certificacion>();
	}
	
	@PostConstruct
	private void init(){
		certificaciones = service.getCertificacionesActivas();
	}

	public List<Certificacion> getCertificaciones(){
		return certificaciones;
	}
	
	public Certificacion getSelectedCertificacion() {
		return selectedCertificacion;
	}

	public void setSelectedCertificacion(Certificacion selectedCertificacion) {
		this.selectedCertificacion = selectedCertificacion;
	}
}