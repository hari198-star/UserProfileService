package com.user.management.viewobjects;

public class UserVO {
	
	private long id;
    private String firstName;
    private String lastName;
    private String email;
    
    public UserVO(UserVOBuilder builder) {
    	this.id = builder.getId();
    	this.firstName = builder.getFirstName();
    	this.lastName = builder.getLastName();
    	this.email = builder.getEmail();
    }    
    
	public UserVO(long id, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
