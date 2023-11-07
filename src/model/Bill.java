package model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Bill {
	private String billNo;

	private String rcvrName;

	private String rcvrAddr;
	
	private String rcvrDetailAddr;

	private String rcvrCp;

	private String companyCd;

	private String companyName;
	
	private String userId;

	private String nonCp;
	
	private int totalFee;
	
	private Timestamp regDate;
	
	private String msg = "메시지를 입력하지 않았습니다.";

}
