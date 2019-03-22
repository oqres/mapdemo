package com.example.demo.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Paging {

	private int curPage;//현재 페이지
	private int firstPage;//각 block의 첫번째 페이지
	private int lastPage; //각 block의 마지막 페이지
	private int startRow;//각 페이지의 첫번째 record값 DB와 연동
	private int endRow; //각 페이지의 마지막 record값 db와 연동
	private int prevPage; //이전 block의 첫번째 페이지로 옴겨감
	private int nextPage; //다음 block의 첫번째 페이지로 옴겨감
	private int firstBlockOfPage; //첫번째 block의 첫번째 페이지로 옴겨감
	private int lastBlockOfPage; //마지막 block의 마지막 페이지로 옴겨감
	private int curBlock; //현재 block의 위치
	private int totalPage; //전체 페이지의 값
	private int totalBlock; //전체 block의 값
	private int totalRecord; //전체 row 개수


	//속성
	private int pageListSize; // 페이지당 출력 개수
	private int blockSize; // 블락당 출력 개수

	//map으로 받기
	public Map<String,Integer> getByMap(){
		Map<String, Integer> map = new HashMap<>();
		for (Field field : this.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				map.put(field.getName(), (Integer) field.get(this));
			} catch (IllegalAccessException e) {} //내부 클래스 int 값만 처리 하는 클래스이므로 이러한 접근이 불가함, 익셉션 처리 하지 않음
		}
		return map;
	};

	public Paging(int curPage, int totalRecord) {
		final int PAGE_LIST_SIZE = 10;//default 10 사용하겠음, 추후 변경시에는 이 값만 변경하면 됨
		final int BLOCK_SIZE = 10;
		init(curPage, totalRecord, PAGE_LIST_SIZE,BLOCK_SIZE);
	}

	public Paging(int curPage, int totalRecord, int pageListSize, int blockSize) {
		init(curPage, totalRecord, pageListSize, blockSize);
	}

	//curPage로 변수 넣으면 curPage가 local 변수로 바라 보기 시작하므로 이대로 넣어야됨
	private void init(int inputCurPage, int totalRecord, int pageListSize, int blockSize) {
		this.totalRecord = totalRecord;	this.pageListSize = pageListSize; this.blockSize = blockSize;//참조 변수들 변경 안되는 값들

		//전체 페이지 및 전체 block 갯수
		totalPage = ((totalRecord - 1) / pageListSize) + 1;
		totalBlock = ((totalPage-1) / blockSize) + 1;

		//this.curPage 값 setting 하는거 초기화, 최소 1 이상 최대 totalPage 이하 여야지만 curPage로 값이 등록될 수 있음
		curPage = inputCurPage < 1 ? 1 :inputCurPage > totalPage ? totalPage : inputCurPage;

		//화면에 출력되는 row 개수
		startRow = ((curPage - 1) * pageListSize + 1);
		endRow = curPage * pageListSize;

		//각 블락당 페이지 개수 정함
		firstPage = (((curPage - 1) / blockSize)*blockSize) + 1;
		lastPage = firstPage +(blockSize-1);
		//전체 페이지보다 마지막 페이지가 많을 수 없다
		if(lastPage>totalPage) lastPage = totalPage;

		//현재 block의 위치,
		curBlock = (curPage-1)/10+1;

		//< or > 처리
		prevPage = curPage - 1 < 1 ? 1 : curPage - 1;
		nextPage = curPage + 1 > totalPage ? totalPage : curPage +1;
		// << or >> 처리
		firstBlockOfPage = 1;
		lastBlockOfPage = totalPage;
	}


	//<  or > 경우에서 블락 단위의 첫번째로 이동 해야 되는 경우 임
	//이전 block의 첫번째 page로 옴겨감  if (curBlock > 1) {prevPage = ((curBlock - 1)*blockSize)-(blockSize-1);}
	//다음 block의 첫번째 page로 옴겨감  if (curBlock < totalBlock) {nextPage = lastPage + 1;}
	//		이 경우에는 가려야 되는 경우이나, 화면에서 처리 하게 하는게 맞는듯, << or >>의 경우 처리 비지니스 로직 보고 판단 해야됨
//		if (firstPage > 1) {firstBlock = 1 ;}


	public int getStartRow() {return startRow;}
	public int getEndRow() {return endRow;}
	public int getCurPage() {return curPage;}
	public int getFirstPage() {return firstPage;}
	public int getLastPage() {return lastPage;}
	public int getPrevPage() {return prevPage;}
	public int getNextPage() {return nextPage;}
	public int getFirstBlockOfPage() {return firstBlockOfPage;}
	public int getLastBlockOfPage() {return lastBlockOfPage;}
	public int getCurBlock() {return curBlock;}
	public int getTotalPage() {return totalPage;}
	public int getTotalBlock() {return totalBlock;}
	public int getTotalRecord() {return totalRecord;}
	public int getPageListSize() {return pageListSize;}
	public int getBlockSize() {return blockSize;}

	@Override
	public String toString() {
		return "Paging{curPage=" + curPage + ", firstPage=" + firstPage + ", lastPage=" + lastPage + ", startRow=" + startRow + ", endRow=" + endRow + ", prevPage=" + prevPage + ", nextPage=" + nextPage + ", firstBlockOfPage=" + firstBlockOfPage + ", lastBlockOfPage=" + lastBlockOfPage + ", curBlock=" + curBlock + ", totalPage=" + totalPage + ", totalBlock=" + totalBlock + ", totalRecord=" + totalRecord + ", pageListSize=" + pageListSize + ", blockSize=" + blockSize + '}';
	}


//	public Integer[] getBlock() {
//
//		int size=lastPage - firstPage +1;
//
////Integer 배열 선언한 이유, int 의 경우 아래와 같이 배열의 갯수 선언하면 null이 발생할 경우 다 0으로 처리해서 10개의 배열을 다 채운다
////		즉, 0을 남은 배열의 크기만큼 다 집어 넣어준다, 따라서 null을 처리해줄 수 있도록 Integer로 boxing한 것이다
//		Integer[] pageBlock = new Integer[size];
//
//		for (int i = 0; i < size; i++) {
//			pageBlock[i] = firstPage++;
//		}
//
//		return pageBlock;
//
//	}

//	public ArrayList<Object> getRowData(ArrayList<Object> objList){
//		ArrayList<Object> obj = new ArrayList<Object>();
//
//		int forEnd = 0;
//		if(endRow>totalRecord){
//			forEnd = totalRecord;
//		}else{
//			forEnd = endRow;
//		}
//
//		for(int i =startRow-1; i<=forEnd-1; i++){
//			obj.add(objList.get(i));
//		}
//		return obj;
//	}



}
