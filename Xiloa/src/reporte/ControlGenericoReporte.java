package reporte;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.Map;

import javax.faces.context.ExternalContext;

import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import support.FacesUtil;
import util.Global;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;  
import net.sf.jasperreports.engine.JasperPrint;  
import net.sf.jasperreports.engine.JasperReport;  
import net.sf.jasperreports.engine.design.JasperDesign;  
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;  

@Component
@Scope(value="request")
public class ControlGenericoReporte implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private static ControlGenericoReporte instancia = new ControlGenericoReporte();
	private String exportMode  = Global.EXPORT_HTML;
	private ByteArrayOutputStream result = new ByteArrayOutputStream();
	private String formatoAplicacion;	
	private ExternalContext extContext;
	private String reportFile = null;
	private File file = null;

	private Connection conn;
	
	public ControlGenericoReporte (){
	}
	
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getReportFile() {
		return reportFile;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}
	
	public void runReport(String nombreReporte,String path,String formato,Map<String,Object> params,String nombreArchivo,File pathFile) {		
		Connection jdbcConnection = null;
		Map<String,Object> parametro = params;
	try {	    
		parametro.put("logoinatec", new String("../../resources/imagenes/logo INATEC.png")); //Parametro que contiene la ubicacion del logo de INATEC
		reportFile = path + "\\" + nombreReporte;		
		formatoAplicacion = getContentType(formato);		
		exportMode = formato;
		result.reset();						
		JasperDesign jasperDesign = JRXmlLoader.load(reportFile + ".jrxml");		   
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        jdbcConnection = this.conn;
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, jdbcConnection);
        JRExporter exporter = getReportExporter(parametro);	 
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);        
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, result);
        exporter.exportReport();
        if (nombreArchivo == null) {
        	 file =  new File( reportFile + "." +  formato.toLowerCase());
        	// file =  new File(nombreReporte + "." +  formato.toLowerCase()); // detallado
        }
        else {
        	file = new File(pathFile,nombreArchivo + "." + formato.toLowerCase()); //Lote
        }
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(result.toByteArray()); 
        outputStream.flush();
        outputStream.close();
        
     } catch (Exception e) {  
             e.printStackTrace(); 
     } /*
     finally{
    	 try {
    		 if (jdbcConnection != null)
    			 service.getSqlConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
     */
    }
	
	public void runReporteFisico(String nombreReporte, Map<String,Object> params,String formato, Connection conn, boolean visualiza){
		String pathReporte = FacesUtil.getRealPath(Global.REPORTE_DIR);
		this.conn = conn;
		runReport(nombreReporte,pathReporte,formato,params,null,null);
		FacesUtil.setParamBySession("file",getFile());
		//JavascriptContext.addJavascriptCall(FacesUtils.getCurrentInstance(), "window.open('" +  FacesUtils.getContentPath() + "/viewFile?file="+ reporte + "&formato=" + this.getFormatoAplicacion() + "','myWindow');");
		String reporte = nombreReporte + "." + formato.toLowerCase();
		if (visualiza) {
			RequestContext context = RequestContext.getCurrentInstance();
			//context.execute("window.open('" +  FacesUtil.getContentPath() + "/reporte?file="+ reporte + "&formato=" + formato + "','myWindow');");		
		    context.execute("window.open('" +  FacesUtil.getContentPath() + "/reportes/"+ reporte + "','myWindow');");
		}
		
	}	
	
	
	public static ControlGenericoReporte getInstancia(){
		 if (instancia == null){
			 instancia = new ControlGenericoReporte();
		 }
		 return instancia;
	}

    protected JRExporter getReportExporter(Map<String,Object> parameters){
	   if (exportMode != null){
	      if (Global.EXPORT_HTML.equals(exportMode))
	         return getHtmlReportExporter();
	      if (Global.EXPORT_CSV.equals(exportMode))
	    	  return getCSVReportExporter();
	      if(Global.EXPORT_XLS.equals(exportMode))
	         return  getXlsReportExporter();
	      if (Global.EXPORT_PDF.equals(exportMode))
	        return getPdfReportExporter();
	   }
	   return getHtmlReportExporter(); //default
	}
    
    protected JRHtmlExporter getHtmlReportExporter() {
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, " ");
        exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, " ");
        exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "iso-8859-1");
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        return exporter;
    }
    
    protected JRXlsExporter getXlsReportExporter() {        
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        return exporter;
    }
    
    protected JRPdfExporter getPdfReportExporter() {
        JRPdfExporter exporter = new JRPdfExporter();
        return exporter;
    }
    
    protected JRCsvExporter getCSVReportExporter(){
    	JRCsvExporter exporter = new JRCsvExporter();
    	exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER,Global.CSV_DELIMETER);
    	exporter.setParameter(JRCsvExporterParameter.CHARACTER_ENCODING,"iso-8859-1");
    	exporter.setParameter(JRCsvExporterParameter.CHARACTER_ENCODING,"iso-8859-1");
    	return exporter;
    }


	public ByteArrayOutputStream getResult() {
		return result;
	}


	public void setResult(ByteArrayOutputStream result) {
		this.result = result;
	}


	public String getFormatoAplicacion() {
		return formatoAplicacion;
	}


	public void setFormatoAplicacion(String formatoAplicacion) {
		this.formatoAplicacion = formatoAplicacion;
	}
	
	public byte[] toByArray(){
		return result.toByteArray(); 
	} 
	

	protected String getContentType(String formato){
		if (Global.EXPORT_HTML.equals(formato.toUpperCase()))
		{
			return "text/"  + formato.toLowerCase();
		}
		else {
			if (formato.equals(Global.EXPORT_XLS)) {
				return "application/vnd.ms-excel";
			}			
			return "application/"  + formato.toLowerCase();
		}
	}


	public ExternalContext getExtContext() {
		return extContext;
	}


	public void setExtContext(ExternalContext extContext) {
		this.extContext = extContext;
	}


	public static void setInstancia(ControlGenericoReporte instancia) {
		ControlGenericoReporte.instancia = instancia;
	}
	
	
}