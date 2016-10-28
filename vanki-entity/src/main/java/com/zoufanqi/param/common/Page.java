package com.zoufanqi.param.common;

import java.util.ArrayList;
import java.util.List;

public class Page<Entity>{
	
	//数据库总行数
	private long rowCount;

	//一页多少行
	private int pageSize = 10;

	//第几页
	private int num;

	//查询起始行
	private int startRow;

	//下一页
	private int next;

	//上一页
	private int prev;

	//一共多少页
	private int pageCount;

	//显示导航起始
	private int begin;

	//显示的导航结束
	private int end;

	//首页
	private int first = 1;

	//尾页
	private int last;

	//一共显示多少导航
	private int navNum = 10;

	//分页查询之后的数据
	private List<Entity> data = new ArrayList<Entity>(0);



	public Page() {
		super();
	}
	
	public Page(int pageNo, int pageSize, long rowCount){
		this(pageNo,pageSize,rowCount,10);
	}

	public Page(int pageNo, int pageSize, long rowCount, int navNum) {
		this.navNum = navNum;
		this.rowCount = rowCount;
		this.pageSize = pageSize;
		this.pageCount = (int) Math.ceil(this.rowCount * 1.0 / this.pageSize);
		this.last = this.pageCount;
		this.num = Math.max(this.first, pageNo);
		this.num = Math.min(this.last, this.num);

        //this.startRow = Math.max(0, (this.num - 1) * this.pageSize);
        this.startRow = Math.max(0, (pageNo - 1) * this.pageSize);

		this.next = Math.min(this.last, this.num + 1);
		this.prev = Math.max(this.first, this.num - 1);

		this.begin = Math.max(this.first, this.num - (this.navNum / 2));
		this.end = Math.min(this.last, this.begin + this.navNum - 1);

		if ((this.end - this.begin) < (this.navNum - 1)) {
			this.begin = Math.max(this.first, this.last - this.navNum + 1);
		}
	}	

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public <T> void copyData(Page<T> page) {
        if (page != null) {
            this.rowCount = page.getRowCount();
            this.pageSize = page.getPageSize();
            this.num = page.getNum();
            this.startRow = page.getStartRow();
            this.next = page.getNext();
            this.prev = page.getPrev();
            this.pageCount = page.getPageCount();
            this.begin = page.getBegin();
            this.end = page.getEnd();
            this.first = page.getFirst();
            this.last = page.getLast();
            this.navNum = page.getNavNum();
        }
    }

    public int getNavNum() {
        return navNum;
    }

	public void setNavNum(int navNum) {
		this.navNum = navNum;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
	}

	public int getNavCount() {
		return pageCount;
	}

	public void setNavCount(int navCount) {
		this.pageCount = navCount;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public List<Entity> getData() {
		return data;
	}

	public void setData(List<Entity> data) {
		this.data = data;
	}


	public int getPageCount() {
		return pageCount;
	}


	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	@Override
	public String toString() {
		return "Page [rowCount=" + rowCount + ", pageSize=" + pageSize + ", num=" + num + ", startRow=" + startRow
				+ ", next=" + next + ", prev=" + prev + ", pageCount=" + pageCount + ", begin=" + begin + ", end=" + end
				+ ", first=" + first + ", last=" + last + ", navNum=" + navNum + ", data=" + data + "]";
	}
}
