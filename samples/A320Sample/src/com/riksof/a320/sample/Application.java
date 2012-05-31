package com.riksof.a320.sample;

import com.google.gson.annotations.Expose;
import com.riksof.a320.remote.RemoteObject;


public class Application extends RemoteObject {

	@Expose private String applicationKey;
	@Expose private String applicationTitle;
	@Expose private String applicationBuild; 
	@Expose private String applicationNumber;
	@Expose private String applicationRegistrationDate; 
	@Expose private String userId;
	@Expose private String passwordValidationRegex;
	
	public String getApplicationTitle() {
		return applicationTitle;
	}
	public void setApplicationTitle(String applicationTitle) {
		this.applicationTitle = applicationTitle;
	}
	public String getApplicationBuild() {
		return applicationBuild;
	}
	public void setApplicationBuild(String applicationBuild) {
		this.applicationBuild = applicationBuild;
	}
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public String getApplicationRegistrationDate() {
		return applicationRegistrationDate;
	}
	public void setApplicationRegistrationDate(String applicationRegistrationDate) {
		this.applicationRegistrationDate = applicationRegistrationDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getApplicationKey() {
		return applicationKey;
	}
	public void setApplicationKey(String applicationKey) {
		this.applicationKey = applicationKey;
	}
	public String getPasswordValidationRegex() {
		return passwordValidationRegex;
	}
	public void setPasswordValidationRegex(String passwordValidationRegex) {
		this.passwordValidationRegex = passwordValidationRegex;
	}
}
