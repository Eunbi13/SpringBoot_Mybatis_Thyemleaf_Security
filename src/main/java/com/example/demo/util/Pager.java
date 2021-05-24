package com.example.demo.util;

public class Pager {
	private Long curPage;
	private Long perPage;
	
	private Long startRow;
	//makeNum page
	private long startNum;
	private long lastNum;
	//버튼 활성화
	private boolean pre;
	private boolean next;
	
	
	//search
	private String kind;
	private String search;
	
	
	
	public void makeNum(Long totalCount) {
		int perBlock =5;
		
		//1.totalCount
		
		//2.totalPage 수 perPage기준으로 몇페이지가 나오냐
		Long totalPage = totalCount/this.getPerPage();
		if(totalCount%this.getPerPage() !=0) {
			totalPage++;
		}
		//3.totalBlock 수 총페이지를 묶는다면 몇블록이 나오냐
		Long totalBlock = totalPage/perBlock;
		if(totalPage%perBlock!=0) {
			totalBlock++;
		}
		//4.curPage를 이용해서 curBlock 구하기 현재 페이지가 뭐고 몇번째 블럭에 있냐
		Long curBlock = curPage/perBlock;
		if(curPage%perBlock!=0) {
			curBlock++;
		}
		//5.curBlock으로 starNum, lastNum구하기 
		startNum = (curBlock-1)*perBlock+1;
		lastNum = curBlock*perBlock;
		
		//6-1 pre &next 셋팅
		this.next=true;
		this.pre=true;
		//6. curBlock이 마지막과 같다면
		if(curBlock==totalBlock) {
			lastNum=totalPage;
			this.next=false;
		}
		if(curBlock==1) {
			this.pre=false;
		}
			
		//7
	}
	
	public void makeRow() {
		//curPage가 1이면 startRow=0 2면 10
		this.startRow = (this.getCurPage()-1)*this.getPerPage();
	}

	public Long getCurPage() {
		if(curPage == null||curPage==0){
			this.curPage=1L;
		}
		return curPage;
	}

	public void setCurPage(Long curPage) {
		if(curPage == null||curPage==0){
			this.curPage=1L;
		}else {
			this.curPage=curPage;
		}
	}

	public Long getPerPage() {
		if(this.perPage==null||this.perPage==0) {
			this.perPage=10L;
		}
		return perPage;
	}

	public void setPerPage(Long perPage) {
		if(this.perPage==null||this.perPage==0) {
			this.perPage=10L;
		}else {
			this.perPage = perPage;
		}
	}

	public Long getStartNow() {
		return startRow;
	}

	public void setStartNow(Long startNow) {
		this.startRow = startNow;
	}

	public long getStartNum() {
		return startNum;
	}

	public void setStartNum(long startNum) {
		this.startNum = startNum;
	}

	public long getLastNum() {
		return lastNum;
	}

	public void setLastNum(long lastNum) {
		this.lastNum = lastNum;
	}

	public boolean isPre() {
		return pre;
	}

	public void setPre(boolean pre) {
		this.pre = pre;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public Long getStartRow() {
		return startRow;
	}

	public void setStartRow(Long startRow) {
		this.startRow = startRow;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getSearch() {
		if(this.search==null) {
			search="";
		}
		return search;
	}

	public void setSearch(String search) {
		
			this.search = search;
		
	}
	
	
}
