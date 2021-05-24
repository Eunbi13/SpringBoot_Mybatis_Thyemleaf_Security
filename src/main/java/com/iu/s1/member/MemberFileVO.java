package com.iu.s1.member;

import lombok.Data;

@Data
public class MemberFileVO {
	private Long fileNum;
	private String userName;
	private String fileName;
	private String oriName;
	public Long getFileNum() {
		return fileNum;
	}
	public void setFileNum(Long fileNum) {
		this.fileNum = fileNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOriName() {
		return oriName;
	}
	public void setOriName(String oriName) {
		this.oriName = oriName;
	}
}
