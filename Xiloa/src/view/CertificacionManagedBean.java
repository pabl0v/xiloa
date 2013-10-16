package view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;
import support.Involucrado;

@Component
@Scope("request")
public class CertificacionManagedBean {
	
	@Autowired
	private IService service;
	private String descripcionCertificacion;
	private List<Involucrado> contactos = new ArrayList<Involucrado>();
	private Involucrado[] selectedContactos;
	private List<Involucrado> involucrados = new ArrayList<Involucrado>();
	private Date fechaIniciaDivulgacion;
	private Date fechaFinalizaInscripcion;
	private Date fechaIniciaConvocatoria;
	private Date fechaIniciaEvaluacion;
	private String nombreCentroEvaluador;
	private String direccionCentroEvaluador;
	private String estatus;
	
	
}