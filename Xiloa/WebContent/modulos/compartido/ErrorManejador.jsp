<%@page import="javax.mail.Transport"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.Address"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.Session"%>
<%@page import="java.util.Properties"%>
<%@page import="java.net.UnknownHostException"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" isErrorPage="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SCCL - Advertencia</title>
<link href="../../resources/css/error.css" rel="stylesheet" type="text/css" />
</head>
<body>
<br>
<center>
<table width="500" border="0" cellpadding="0" cellspacing="0" class="borde">
<tr>
<td height="30" class="fondoCeldaTitulo" align="center" style="font-size:16px"><b>Disculpe las molestias</b></td>
</tr>
<tr>
<td><table width="0" border="0" cellspacing="20">
<tr>
<td valign="top"><img src="../../resources/imagenes/warning.gif" width="32" height="32"></td>
<td><p>Por razones t&eacute;cnicas su petici&oacute;n no puede ser atendida en estos momentos...</p>
<p>Comuniquese con el Administrador del Sistema.....</p>
<p>&nbsp;</p></td>
</tr>
<tr>
<td colspan="2" align="center" valign="top"><form name="form1" method="post" action="">
<input type="button" name="button" id="button" value=" Regresar" class="atras" onClick="history.back()">
</form></td>
</tr>
</table></td>
</tr>
</table>
</center>
<%
String msg_enviar;
String pc="";
String ip="";
String direccion="";
String miDir ="";

SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
Calendar cal = Calendar.getInstance();

try {
pc=java.net.Inet4Address.getLocalHost().getHostName();
ip=java.net.Inet4Address.getLocalHost().getHostAddress();
} catch (UnknownHostException e1) {

// TODO Auto-generated catch block
e1.printStackTrace();
}

msg_enviar="";
msg_enviar="<h2>Error en: Testserver</h2>";
msg_enviar +="<b>Fallo en Pagina</b><br><br>"; 
msg_enviar += "<b>Fecha del error</b>: " +fecha.format(cal.getTime())+ "<br>";
msg_enviar += "<b>Archivo</b>:" +request.getAttribute("javax.servlet.error.request_uri")+"<br>";
msg_enviar += "<b>IP</b>:" +ip+"<br>";
msg_enviar +="<b>Nombre de la PC del usuario</b>: "+pc+"<br>";
msg_enviar +="<b>Tipo Error</b>:"+exception.getClass().getName()+"<br><b>Codigo Error:</b> ";
msg_enviar +=request.getAttribute("javax.servlet.error.status_code")+"<br>";
msg_enviar +="<b>Descripcion Error:</b>: <br>"+exception.getMessage()+"";

try {
// Se debe crear un archivo de propiedades que
// contenga el nombre del servidor SMTP
Properties props = new Properties();
props.put("mail.host", "mail.inatec.edu.ni");
// se crea un objeto de sesion
Session conexionCorreo = Session.getInstance(props, null);
// este objeto representa el mensaje a enviar
Message mensaje = new MimeMessage(conexionCorreo);
// los objetos Adress contienen informacion de recipientes de correo
Address desde = new InternetAddress("ctijerino@inatec.edu.ni",
"INATEC - DESARROLLO");
// asignamos el contenido. En este caso usamos 'text/plain' pero
// es posible usar 'text/html' para enviar correos con contenido
// HTML
mensaje.setContent(msg_enviar,"text/html");
// asignar el origen del correo
mensaje.setFrom(desde);
// asignar el asunto del correo
mensaje.setSubject("SCCL, Java Mail!");
// asignar un destinatario
Address dir [] = new Address[1];
dir[0] = new InternetAddress("competencial@inatec.edu.ni");
mensaje.setRecipients(Message.RecipientType.TO,dir);

/*Address para = new InternetAddress("","","");
mensaje.setRecipient(Message.RecipientType.TO, para);*/
Transport.send(mensaje);
} catch (Exception ex) {
ex.printStackTrace();
}

%>
</body>
</html>