package support;

import java.io.Serializable;

import model.Solicitud;

public class USolicitud implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Solicitud solicitud;
	private boolean esConvocatoria; 
	 
    
	public Solicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}
	
	public boolean isEsConvocatoria() {
		return esConvocatoria;
	}
	public void setEsConvocatoria(boolean esConvocatoria) {
		this.esConvocatoria = esConvocatoria;
	}
	
	public USolicitud(Solicitud solicitud, boolean esConvocatoria) {
		super();
		this.solicitud = solicitud;
		this.esConvocatoria = esConvocatoria;
	}
	    
}

