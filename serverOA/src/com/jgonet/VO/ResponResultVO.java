package com.jgonet.VO;

import java.io.Serializable;
import java.util.List;

/**
 * 列表集合信息
 */
public class ResponResultVO implements Serializable {

    private static final long serialVersionUID=1L;
    /**状态码*/
    private int code;
    /**消息描述*/
    private String msg;
    /**
     * 页数
     */
    private int pageSize;

    /**
     * 页码
     */
    private int pageNo;

    /**
     * 总页数
     */
	private int pageCnt;

	/**
     * 总记录数
     */
    private int recordCnt;

    /**
     * 
     */
    private List<?> list;
    
    /**
     * 跳转连接
     */
    private String url;
    
    private int contentStart;
    
    private int contentEnd;
    
    private int pageStart;
    
    private int pageEnd;

    public ResponResultVO() {
        super();
    }
    
    public ResponResultVO(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ResponResultVO(int code,String msg,List<?> list, int recordCnt, int pageSize, int pageNo) {
        super();
        this.code = code;
        this.msg = msg;
        this.pageSize=pageSize;
        this.pageNo=pageNo;
        if(pageSize >= 0) {
        	pageCnt=(int)Math.ceil((double)recordCnt / (double)pageSize);
        }
        this.recordCnt=recordCnt;
        this.list=list;
        contentStart = (pageNo-1)*pageSize+1;
        contentEnd=contentStart+list.size()-1;
        pageStart = pageNo-2>0?pageNo-2:pageNo-1>0?pageNo-1:1;
        pageEnd = pageStart+2<pageNo?pageStart+2:pageStart+1<pageNo?pageStart+1:pageStart;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list=list;
    }

    public int size() {
        return list == null ? 0 : list.size();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize=pageSize;
    }

    public int getPageNo() {
    	return pageNo;
    }
    
    public void setPageNo(int pageNo) {
    	this.pageNo = pageNo;
    }
    
    public int getPageCnt() {
        return pageCnt;
    }

    public void setPageCnt(int pageCnt) {
        this.pageCnt=pageCnt;
    }

    public int getRecordCnt() {
        return recordCnt;
    }

    public void setRecordCnt(int recordCnt) {
        this.recordCnt=recordCnt;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}

	public int getContentStart() {
		return contentStart;
	}

	public void setContentStart(int contentStart) {
		this.contentStart = contentStart;
	}

	public int getContentEnd() {
		return contentEnd;
	}

	public void setContentEnd(int contentEnd) {
		this.contentEnd = contentEnd;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    
}
