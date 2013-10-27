package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import model.Mantenedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IService;

@Component
@Scope("request")
public class MantenedorConverter implements Converter {
	
	@Autowired
	private IService service;
	private Map<String, Mantenedor> map;
		
	public MantenedorConverter(){
		super();
		map = new HashMap<String, Mantenedor>();
	}
	
	private void loadData(){
		if(map.isEmpty()){
			List<Mantenedor> lista = new ArrayList<Mantenedor>();
			lista = service.getMantenedores();
			map.clear();
			for(int i=0; i<lista.size(); i++){
				System.out.println("map: "+lista.get(i).getValor()+"-"+lista.get(i).toString());
				map.put(lista.get(i).getValor(), lista.get(i));
			}
		}
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		loadData();
		System.out.println("map value: "+value);
		return map.get(value);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		loadData();
		return ((Mantenedor)value).getValor();
	}
}