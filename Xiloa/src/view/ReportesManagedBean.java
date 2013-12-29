package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import model.Certificacion;
import model.Mantenedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import controller.LoginController;

import service.IService;
import support.Ifp;
import util.Global;

//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Bean asociado al facet reportes.xhtml
@Component
@Scope(value="view")
public class ReportesManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IService service;
	
	@Autowired
	private LoginController login; 
				
	//Implementacion SelectItems	
	private List<SelectItem> listCentros;				
	
	private List<SelectItem> listCertByCentro;
	
	private Integer selectedIdIfp;
	
	private Long selectedIdCertificacion;
	private Long selectedIdCertByCentro;	
				
	private String rptNombre;
	private Integer rptId;
	private Integer estadoId;
	private Map<Integer, String> catalogoTiposReportes;
	private List<SelectItem> listaTiposReportes;
	private List<SelectItem> listaEstados;
			
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Constructor de la clase.
	public ReportesManagedBean() {
		
		super();	
				
		listCentros = new ArrayList<SelectItem>();
		listCertByCentro = new ArrayList<SelectItem>();
			
		selectedIdIfp = null;		
		selectedIdCertByCentro = null;	
			
		catalogoTiposReportes = new HashMap<Integer, String> ();
		listaTiposReportes = new ArrayList<SelectItem>();
		listaEstados = new ArrayList<SelectItem> ();
							
	}	

	public Integer getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Integer estadoId) {
		this.estadoId = estadoId;
	}

	public Integer getRptId() {
		return rptId;
	}

	public void setRptId(Integer rptId) {
		this.rptId = rptId;
	}

	public List<SelectItem> getListaTiposReportes() {
		return listaTiposReportes;
	}

	public void setListaTiposReportes(List<SelectItem> listaTiposReportes) {
		this.listaTiposReportes = listaTiposReportes;
	}

	public List<SelectItem> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<SelectItem> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public Map<Integer, String> getCatalogoTiposReportes() {
		return catalogoTiposReportes;
	}

	public void setCatalogoTiposReportes(Map<Integer, String> catalogoTiposReportes) {
		this.catalogoTiposReportes = catalogoTiposReportes;
	}

	public String getRptNombre() {
		return rptNombre;
	}


	public void setRptNombre(String rptNombre) {
		this.rptNombre = rptNombre;
	}

	public Integer getSelectedIdIfp() {
		return selectedIdIfp;
	}

	public void setSelectedIdIfp(Integer idIfp) {
		this.selectedIdIfp = idIfp;
	}
	
	public Long getSelectedIdCertificacion() {
		return selectedIdCertificacion;
	}

	public void setSelectedIdCertificacion(Long idCertificacion) {
		this.selectedIdCertificacion = idCertificacion;
	}
	
	public Long getSelectedIdCertByCentro() {
		return selectedIdCertByCentro;
	}

	public void setSelectedIdCertByCentro(Long selectedIdCertByCentro) {
		this.selectedIdCertByCentro = selectedIdCertByCentro;
	}	
	
	public List<SelectItem> getListCentros() {
		return listCentros;
	}

	public void setListCentros(List<SelectItem> listCentros) {
		this.listCentros = listCentros;
	}

	public List<SelectItem> getListCertByCentro() {
		return listCertByCentro;
	}

	public void setListCertByCentro(List<SelectItem> listCertByCentro) {
		this.listCertByCentro = listCertByCentro;
	}	
			
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Metodo que se ejecuta posterior al constructor.
	@PostConstruct
	private void initBeanDBSolicitudes(){
		List<Ifp> lista = service.getIfpByInatec(login.getEntidadUsuario());
		
		if (lista.size() > 1 )
			this.listCentros.add(new SelectItem(null, "Todos"));
		
		for (Ifp dato : lista) {	
			this.listCentros.add(new SelectItem(dato.getIfpId(),dato.getIfpNombre()));
		}		
		
		catalogoTiposReportes = new HashMap<Integer, String> ();
		catalogoTiposReportes.put(new Integer(1), new String("listadosolicitudes"));
		catalogoTiposReportes.put(new Integer(2), new String("listadoplanificaciones"));
		listaTiposReportes = new ArrayList<SelectItem>();
		listaTiposReportes.add(new SelectItem(new Integer(1), new String("Solicitudes")));
		listaTiposReportes.add(new SelectItem(new Integer(2), new String("Planificaciones")));
				
		llenarListaEstados (new Integer(1)); //Por defecto indica Reporte Solicitudes
			
		handleCertByCentro();
				
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Asigna valores en el selectItem de los estatus.
	public void llenarListaEstados (Integer tipo){
		listaEstados = new ArrayList<SelectItem> ();
		listaEstados.add(new SelectItem(null, "Todos los estados"));
		switch(tipo) {
		   //Boton Convocar
			case 1:	{
				for (Mantenedor dato : service.getCatalogoEstadoSolicitud().values()){
					listaEstados.add(new SelectItem(dato.getId(), dato.getValor()));
				}
				break;
			}
			case 2:	{
				for (Mantenedor dato : service.getCatalogoEstatusCertificacion().values()){
					listaEstados.add(new SelectItem(dato.getId(), dato.getValor()));
				}
				break;
			}
			default:
				break;
		}
		
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Consulta los estados segun el reporte seleccionado.
	public void handleEstadosByRpt(){
		if (rptId != null)
			llenarListaEstados (rptId);
	}
	
	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Consulta las certificaciones por centro de formacion profesional seleccionado.
	public void handleCertByCentro() {
		List<Certificacion> certificacionList = service.getCertificacionesByIdIfp(this.getSelectedIdIfp());
		listCertByCentro = new ArrayList<SelectItem>();
		this.listCertByCentro.add(new SelectItem(null,"Todas las Unidades"));
		for (Certificacion dato : certificacionList) {
			this.listCertByCentro.add(new SelectItem(dato.getId(),dato.getNombre()));
		}
		
	}

	//Ing. Miriam Martínez Cano || Proyecto SCCL INATEC - CENICSA || Ejecuta el reporte generico.
    public void runReporte() throws Exception {
    	Map<String,Object> params = new HashMap<String,Object>();
    	rptNombre = this.catalogoTiposReportes.get(rptId);
    	    	       	   	
		params.put("idCentro",selectedIdIfp);
		params.put("idEstado", estadoId);
		params.put("certificacionid", selectedIdCertByCentro);
		
    	service.imprimirReporte(rptNombre, params, Global.EXPORT_PDF, true);

	}
	
}
