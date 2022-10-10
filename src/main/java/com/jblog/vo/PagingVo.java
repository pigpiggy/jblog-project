package com.jblog.vo;

public class PagingVo {
	private final int PAGE_PER_NUM=3;    //한 페이지에 보여질 행 갯수
	private final int PAGE_PER_BLOCK=3;  //한 페이지에 보여질 페이지 갯수
	
	private int totalRowNum;   //전체 행 갯수
	private int totalPageNum;  //전체 페이지 갯수
	private int startRowNum;   //시작 행 번호
	private int endRowNum;     //끝 행 번호
	public int getEndRowNum() {
    return endRowNum;
  }
  public void setEndRowNum(int endRowNum) {
    this.endRowNum = endRowNum;
  }

  private int nowPageNum;    //현재 페이지 번호
	
	public PagingVo(int totalRowNum,int nowPageNum) {
		this.totalRowNum = totalRowNum;
		this.nowPageNum = nowPageNum;
		startpaging();
	}
	public int getPAGE_PER_BLOCK() {
		return PAGE_PER_BLOCK;
	}
	private void startpaging() {
		//전체 페이지 갯수
		if(totalRowNum < PAGE_PER_NUM) {
			this.totalPageNum = 1;
		}else if(totalRowNum%PAGE_PER_NUM == 0) {
			this.totalPageNum = totalRowNum/PAGE_PER_NUM;
		}else {
			this.totalPageNum = totalRowNum/PAGE_PER_NUM+1;
		}
		//시작할 행번호
		startRowNum = (nowPageNum-1)*PAGE_PER_NUM;
		endRowNum = ((startRowNum + PAGE_PER_NUM ) <= totalPageNum) ?  (startRowNum + PAGE_PER_NUM): totalPageNum+1;
	}

	public int getTotalRowNum() {
		return totalRowNum;
	}
	public void setTotalRowNum(int totalRowNum) {
		this.totalRowNum = totalRowNum;
	}
	public int getTotalPageNum() {
		return totalPageNum;
	}
	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}
	public int getstartRowNum() {
		return startRowNum;
	}
	public void setstartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}
	public int getnowPageNum() {
		return nowPageNum;
	}
	public void setnowPageNum(int nowPageNum) {
		this.nowPageNum = nowPageNum;
	}
	public int getPAGE_PER_NUM() {
		return PAGE_PER_NUM;
	}
  @Override
  public String toString() {
    return "PagingVo [PAGE_PER_NUM=" + PAGE_PER_NUM + ", PAGE_PER_BLOCK=" + PAGE_PER_BLOCK + ", totalRowNum="
        + totalRowNum + ", totalPageNum=" + totalPageNum + ", startRowNum=" + startRowNum + ", endRowNum=" + endRowNum
        + ", nowPageNum=" + nowPageNum + "]";
  }
	


}
