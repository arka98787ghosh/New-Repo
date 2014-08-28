package models;


import java.util.UUID;


public class IdGen {
	public String getId(){
		String id = UUID.randomUUID().toString();  
		return id;
		//System.out.println(id); 
	}
}

