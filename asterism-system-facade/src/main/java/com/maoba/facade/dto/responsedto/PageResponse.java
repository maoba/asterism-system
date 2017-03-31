package com.maoba.facade.dto.responsedto;
import java.util.Date;
import com.github.pagehelper.PageInfo;
/**
 * @author kitty daddy
 * 返回分页response
 */
public class PageResponse extends BaseResponse{
	/**
	 * 总页数
	 */
    private int pageSize;
	
    /**
     * 当前页码
     */
	private int currentPage;
	
	/**
	 * 总数量
	 */
	private long totalNum;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}
	
	public PageResponse returnSuccess(PageResponse response){
		if(response!=null){
			response.setStatus("200");
			response.setResultCode("success");
			response.setMsg("成功");
			response.setCacheDate(new Date());
		}
		return response;
	}
	
	public static PageResponse getSuccessPage(PageInfo<?> info){
		PageResponse page = new PageResponse();
		if(info == null){
			page.setCurrentPage(1);
			page.setPageSize(10);
			page.setTotalNum(0);
		}else{
			page.setCurrentPage(info.getPageNum());
			page.setPageSize(info.getPageSize());
			page.setData(info.getList());
			page.setTotalNum(info.getTotal());
		}
		page.setStatus("200");
		page.setResultCode("success");
		page.setMsg("成功");
		page.setCacheDate(new Date());
		return page;
	}
	
	public static PageResponse getSuccessPage(PageInfo<?> info, Object filter){
		PageResponse page = new PageResponse();
		if(info == null){
			page.setCurrentPage(1);
			page.setPageSize(10);
			page.setTotalNum(0);
		}else{
			page.setCurrentPage(info.getPageNum());
			page.setPageSize(info.getPageSize());
			page.setData(info.getList());
			page.setTotalNum(info.getTotal());
		}
		page.setStatus("200");
		page.setResultCode("success");
		page.setMsg("成功");
		page.setCacheDate(new Date());
		page.setFilter(filter);
		return page;
	}
	
	public static PageResponse getSuccessPage(PageInfo<?> info,int currentPage){
		PageResponse page = new PageResponse();
		if(info == null){
			page.setCurrentPage(1);
			page.setPageSize(10);
			page.setTotalNum(0);
		}else{
			page.setCurrentPage(currentPage);
			page.setPageSize(info.getPageSize());
			page.setData(info.getList());
			page.setTotalNum(info.getTotal());
		}
		page.setStatus("200");
		page.setResultCode("success");
		page.setMsg("成功");
		page.setCacheDate(new Date());
		return page;
	}
} 
