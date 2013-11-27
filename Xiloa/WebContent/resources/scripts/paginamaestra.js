$(function(){
    $("#barra_navegacion").click(function(){
        if($("#barra_navegacion").hasClass("mini")){         
            $("#barra_navegacion").animate({height: "75px"},100,function()
	            {
            		$(".botones_navegacion").css("display","inline-block");
	            	$("#barra_navegacion").removeClass("mini");
	            }
            );        
        }
        else {
          
        }      
        
    });
    
    $("#barra_navegacion").mouseleave(function(){
    	$(".botones_navegacion").fadeOut(100,function(){
            $("#barra_navegacion").animate({height: "25px"},100);
            $("#barra_navegacion").addClass("mini");
        });	
    });
    
});