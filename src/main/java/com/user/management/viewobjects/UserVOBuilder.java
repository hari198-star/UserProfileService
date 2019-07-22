package com.user.management.viewobjects;

/*
 * Builder class to create UserVO object
 */
public class UserVOBuilder {
	
	private long id;
    private String firstName;
    private String lastName;
    private String email;
    
    public UserVOBuilder(long id) {
    	this.id = id;
    }

	public long getId() {
		return id;
	}

	public UserVOBuilder setId(long id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public UserVOBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public UserVOBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserVOBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public UserVO build() {
		return new UserVO(this);
	}

}
