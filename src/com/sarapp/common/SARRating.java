package com.sarapp.common;

public class SARRating {
	
	String modelName;
	String usRating;
	String euRating;
	
	public SARRating(String modelName, String usRating, String euRating) {
		super();
		this.modelName = modelName;
		this.usRating = usRating;
		this.euRating = euRating;
	}
	
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getUsRating() {
		return usRating;
	}
	public void setUsRating(String usRating) {
		this.usRating = usRating;
	}
	public String getEuRating() {
		return euRating;
	}
	public void setEuRating(String euRating) {
		this.euRating = euRating;
	}
	
	

}
