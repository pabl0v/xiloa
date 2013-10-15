$(function(){
    $("#barra_navegacion").click(function(){
        if($("#barra_navegacion").hasClass("mini")){
            $(".botones_navegacion").fadeIn(100);
            $("#barra_navegacion").animate({height: "75px"},100);
            $("#barra_navegacion").removeClass("mini");
        }
        else {
          
        }      
    });
    $("#barra_navegacion").click();
    $("#barra_navegacion").mouseleave(function(){
    	$(".botones_navegacion").fadeOut(100,function(){
            $("#barra_navegacion").animate({height: "25px"},100);
            $("#barra_navegacion").addClass("mini");
        });	
    });
    
})