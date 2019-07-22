package com.user.management.converters;

import java.util.ArrayList;
import java.util.List;

import com.user.management.model.User;
import com.user.management.viewobjects.UserVO;
import com.user.management.viewobjects.UserVOBuilder;

//Can use lombok later
public class UserConverter {

	public static UserVO convert(User user) {
		return
		   new UserVOBuilder(user.getId()).
				setEmail(user.getEmail()).
				setFirstName(user.getFirstName()).
				setLastName(user.getLastName()).build();				
	}
	
	public static List<UserVO> convert(List<User> users) {
		
		List<UserVO> userVOList = new ArrayList<UserVO>(users.size());
		
		for(int i = 0; i < users.size(); i++) {
			userVOList.add(convert(users.get(i)));
		}
		
		return userVOList;
		
	}
}
