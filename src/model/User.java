package model;

import lombok.Data;

@Data
public class User {

	private String userId;

	private String userName;

	private String userPwd;

	private String userCp;

	private String userAddr;
	
	private String userDetailAddr;
	
	private int userGrade;
}
