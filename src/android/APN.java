package com.eruipan.cordova.plugin.apnmanager;

public class APN {
	 private String id;  
	  
     private String apn;  

     private String type;  
     

    public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getApn() {
		return apn;
	}



	public void setApn(String apn) {
		this.apn = apn;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String toString() {  
         return "id=" + id + ",apn=" + apn + ";type=" + type;  
     }  
}
