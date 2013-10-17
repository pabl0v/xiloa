$(function(){
	$("#boton_planificacion").addClass("selected");
	
	//Breadcrumbs para esta pagina:
	$("#breadcrumbs").empty();
	$("#breadcrumbs").append("<li>Inicio</li>");
	$("#breadcrumbs").append("<li>Planificacion</li>");
	$("#breadcrumbs").append("<li>Editando planificacion</li>");
	
	/*$("#contenedor_tabla_unidadcomp").find("table").find("tbody").children("tr").click(function(){
		
		var idCentro = $(this).children("td").eq(0).text();
		var idUCompetencia = $(this).children("td").eq(1).text();
		
		alert(idCentro + "," + idUCompetencia);
		window.location = "/modulos/planificacion/edicion_planificacion.xhtml";
	});*/
});