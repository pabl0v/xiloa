$(document).ready(function() {
    //run code that MUST be after initialize
	openid.init('openid_identifier');
	$("#loginForm").appendTo("#aqui_formulario");
	$("#openid_form").appendTo("#opciones_openid");
});



$(function(){
	
	//Foco en el nombre de usuario
	$("#username").focus();
	
	var param1var = getQueryVariable("error");
	
	if(param1var == '1'){
		//alert("Error de autentificacion");
		$("#error").css("display","inline-block");
	}	
});
/**Funcion que permite obtener variables por URL GET**/
function getQueryVariable(variable) {
	  var query = window.location.search.substring(1);
	  var vars = query.split("&");
	  for (var i=0;i<vars.length;i++) {
	    var pair = vars[i].split("=");
	    if (pair[0] == variable) {
	      return pair[1];
	    }
	  } 
	  //alert('Query Variable ' + variable + ' not found');
}