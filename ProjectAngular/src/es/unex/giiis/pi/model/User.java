package es.unex.giiis.pi.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private long id;
	private String username;
	private String email;
	private String password;
	private String avatar;

	public boolean validateName(Map<String, String> messages){
		if(this.username.trim().isEmpty()||this.username==null) {
			messages.put("error", "Empty name");
		} else if(!this.username.trim().matches("[A-Za-zÃ¡Ã©Ã­Ã³ÃºÃ±Ã�Ã‰Ã�Ã“Ãš]{2,}([\\s][A-Za-zÃ¡Ã©Ã­Ã³ÃºÃ±Ã�Ã‰Ã�Ã“Ãš]{2,})*")) {
			messages.put("error", "Invalid name: " + this.username.trim());
		}
		if(messages.isEmpty()) return true; 
		else return false;
	}
	
	public boolean validatePasswords(String password_confirmation) {
		if(this.password.equals(password_confirmation))
			return true;
		else
			return false;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


}
