package com.iu.s1.board;

import lombok.Data;

@Data
public class BoardFileVO {
	private Long num;
	private Long fileNum;
	private String fileName;
	private String oriName;
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public Long getFileNum() {
		return fileNum;
	}
	public void setFileNum(Long fileNum) {
		this.fileNum = fileNum;
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
