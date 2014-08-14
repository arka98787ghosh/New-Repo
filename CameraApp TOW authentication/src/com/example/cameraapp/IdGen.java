package com.example.cameraapp;

import java.util.UUID;


public class IdGen {
	public static String getId(){
		String id = UUID.randomUUID().toString();  
		return id;
		//System.out.println(id); 
	}
}