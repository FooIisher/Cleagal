package com.ynkj.common.tags;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ynkj.common.util.Utils;


/**
 * 分页标签 
 * @author SCG
 */
public class PageSplitTag extends TagSupport {

	private static final long serialVersionUID = 4987824566028940939L;

	private String url = ""; // 页面指向地址

	private String pageNo = ""; // 当前页面，字符串型，由外面传入

	private String paramsStr = ""; // 组装后的参数字符串

	private int totalPages = 1; // 总页面数

	private int count = 0; // 总记录数

	private int intPageNo = 1; // 当前页面
	
	private String type ;
	
	private boolean isWebSite = false;
	
	private boolean isWebSiteClient = false;

	private int pageSize = 10; // 每一页面显示的最大记录数

	public PageSplitTag() {
	}

	public int doStartTag() throws JspException {
		if (url == null) {
			url = "";
		}
		url = url.trim();

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Enumeration en = request.getParameterNames();
		StringBuffer param = new StringBuffer();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			if ("pageId".equals(key) || key.toLowerCase().startsWith("submit")||"pageSize".equals(key))
				continue;
			String value = Utils.trim(request.getParameter(key));
			if (value.equals(""))
				continue;
			param.append("&amp;" + key + "=" + Utils.encodeStr(value));
		}
		paramsStr = param.toString();

		
		try {
			intPageNo = Utils.parseInt(pageNo, 1);
			if(intPageNo<1){
				intPageNo = 1;
			}
		} catch (Exception e) {
		}
		
		System.out.println("pageSize:"+pageSize);
		System.out.println("totalPages:"+totalPages);
		System.out.println("count:"+count);
		System.out.println("paramsStr:"+paramsStr);
		if (count % pageSize > 0) {
			totalPages = count / pageSize + 1;
		} else {
			totalPages = count / pageSize;
		}
		System.out.println("pageSize1:"+pageSize);
		System.out.println("totalPages1:"+totalPages);
		System.out.println("count1:"+count);
		if (intPageNo > totalPages) {
			intPageNo = totalPages;
		}
		return (SKIP_BODY);
	}

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext
		.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		StringBuffer reStr = new StringBuffer();

		reStr.append("<!------------------------------ start pageTag ------------------------------>\r\n");
		reStr.append("<script type=\"text/javascript\" language=\"javaScript\">\r\n");
		reStr.append("function trim(str){\r\n");
		reStr.append("return str.replace(/(^\\s*)|(\\s*$)/g, \"\");\r\n");
		reStr.append("}\r\n");
		reStr.append("function goTo(){\r\n");
		reStr.append("var totalPage=parseInt(document.getElementById(\"totalpage\").value);\r\n");
		reStr.append("var inputPage = parseInt(document.getElementById(\"inputPage\").value);\r\n");
		reStr.append("if(trim(document.getElementById(\"inputPage\").value) == \"\"){alert(\"请输入页码\");return;}\r\n");
		reStr.append("if(" + intPageNo + " == inputPage){alert(\"不需跳转\");return;}\r\n");
		reStr.append("if(inputPage<1){alert(\"请正确输入页码\");return;}\r\n");
		reStr.append("if(inputPage > totalPage){ alert(\"输入错误,超出最大页码!\"); return;}\r\n ");
		reStr.append("window.location=\"" + url + "?pageId=\"+ inputPage" + "+ \"&pageSize="+pageSize+ paramsStr+  "\";\r\n");
		reStr.append("}\r\n");
		reStr.append("</script>\r\n");
		
		reStr.append("<input type=\"hidden\" id=\"totalpage\" value=\"" + totalPages + "\">");
		if(isWebSiteClient) {
			reStr.append("<table width=\"20%\" align=\"center\"><tr>");
			reStr.append("<td align=\"center\" colspan=\"4\">");
			reStr.append("<span style=\"padding-top:-10px;font-size:14px;\">共<b>&nbsp;<span color=\"red\">" + count + "</span>&nbsp;</b>条记录&nbsp;当前<b>" + intPageNo + "/"
					+ totalPages + "</b>页&nbsp;&nbsp;</span></tr><tr width=\"20\"><td align=\"center\">");
			if (totalPages < 2) {
				//reStr.append("<img src=\""+basePath+"/websiteClient/images/1.png\" width=\"51\" height=\"51\" title=\"首页\">&nbsp;&nbsp;");
				//reStr.append("<img src=\""+basePath+"/websiteClient/images/3.png\" width=\"51\" height=\"51\" title=\"上一页\">&nbsp;&nbsp;");
				//reStr.append("<img src=\""+basePath+"/websiteClient/images/4.png\" width=\"51\" height=\"51\" title=\"下一页\">&nbsp;&nbsp;");
				//reStr.append("<img src=\""+basePath+"/websiteClient/images/2.png\" width=\"51\" height=\"51\" title=\"末页\">&nbsp;&nbsp;");
			} else {
				if (intPageNo < 2) {
					//reStr.append("<img src=\""+basePath+"/websiteClient/images/1.png\" width=\"51\" height=\"51\" title=\"首页\">&nbsp;&nbsp;");
					//reStr.append("<img src=\""+basePath+"/websiteClient/images/3.png\" width=\"51\" height=\"51\" title=\"上一页\">&nbsp;&nbsp;");
					//reStr.append(getUrl(intPageNo + 1, "<img src=\""+basePath+"/websiteClient/images/4.png\" width=\"51\" height=\"51\" border=\"0\" title=\"下一页\">&nbsp;&nbsp;"));
					//reStr.append(getUrl(totalPages, "<img src=\""+basePath+"/websiteClient/images/2.png\" width=\"51\" height=\"51\" border=\"0\" title=\"末页\">&nbsp;&nbsp;"));
					
					reStr.append(getUrl(intPageNo + 1, "<div style=\"cursor:hand;width:31px;height:27px;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='"+basePath+"/websiteClient/images/2.png'\" ></div>"));
					reStr.append("</td>");
					reStr.append("<td align=\"center\">");
					reStr.append(getUrl(totalPages, "<div style=\"cursor:hand;width:31px;height:27px;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='"+basePath+"/websiteClient/images/4.png'\" ></div>"));
				} else if (intPageNo == totalPages) {
					reStr.append(getUrl(1, "<div style=\"cursor:hand;width:31px;height:27px;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='"+basePath+"/websiteClient/images/1.png'\" ></div>"));
					reStr.append("</td>");
					reStr.append("<td align=\"center\">");
					reStr.append(getUrl(intPageNo - 1, "<div style=\"cursor:hand;width:31px;height:27px;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='"+basePath+"/websiteClient/images/3.png'\" ></div>"));
					//reStr.append(getUrl(1, "<img src=\""+basePath+"/websiteClient/images/1.png\" width=\"51\" height=\"51\" border=\"0\" title=\"首页\">"));
					//reStr.append(getUrl(intPageNo - 1, "<img src=\""+basePath+"/websiteClient/images/3.png\" width=\"51\" height=\"51\" title=\"上一页\" border=\"0\">"));
					//reStr.append("<img src=\""+basePath+"/websiteClient/images/4.png\" width=\"51\" height=\"51\" title=\"下一页\">");
					//reStr.append("<img src=\""+basePath+"/websiteClient/images/2.png\" width=\"51\" height=\"51\" title=\"末页\">");
				} else {
					reStr.append(getUrl(1, "<div style=\"cursor:hand;width:31px;height:27px;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='"+basePath+"/websiteClient/images/1.png'\" ></div>"));
					reStr.append("</td>");
					reStr.append("<td align=\"center\">");
					reStr.append(getUrl(intPageNo - 1, "<div style=\"cursor:hand;width:31px;height:27px;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='"+basePath+"/websiteClient/images/3.png'\" ></div>"));
					reStr.append("</td>");
					reStr.append("<td align=\"center\">");
					reStr.append(getUrl(intPageNo + 1, "<div style=\"cursor:hand;width:31px;height:27px;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='"+basePath+"/websiteClient/images/2.png'\" ></div>"));
					reStr.append("</td>");
					reStr.append("<td align=\"center\">");
					reStr.append(getUrl(totalPages, "<div style=\"cursor:hand;width:31px;height:27px;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='"+basePath+"/websiteClient/images/4.png'\" ></div>"));
					//reStr.append(getUrl(1, "<img src=\""+basePath+"/websiteClient/images/1.png\" width=\"51\" height=\"51\" border=\"0\" title=\"首页\">&nbsp;&nbsp;"));
					//reStr.append(getUrl(intPageNo - 1, "<img src=\""+basePath+"/websiteClient/images/3.png\" width=\"51\" height=\"51\" border=\"0\" title=\"上一页\">&nbsp;&nbsp;"));
					//reStr.append(getUrl(intPageNo + 1, "<img src=\""+basePath+"/websiteClient/images/4.png\" width=\"51\" height=\"51\" border=\"0\" title=\"下一页\">&nbsp;&nbsp;"));
					//reStr.append(getUrl(totalPages, "<img src=\""+basePath+"/websiteClient/images/2.png\" width=\"51\" height=\"51\" border=\"0\" title=\"末页\">&nbsp;&nbsp;"));
				}
			}
			reStr.append("</td>");
			reStr.append("</tr></table>");			
		} else {
			if(isWebSite){
				reStr.append("<table width=\"100%\"><tr>");
				reStr.append("<td align=\"right\"><span style=\"padding-top:-10px;\">共<b>&nbsp;<span color=\"red\">" + count + "</span>&nbsp;</b>条记录&nbsp;当前<b>" + intPageNo + "/"
						+ totalPages + "</b>页&nbsp;&nbsp;</span>");
				if (totalPages < 2) {
					reStr.append("<img src=\""+basePath+"/website/images/b1.gif\" width=\"17\" height=\"17\" title=\"首页\">&nbsp;&nbsp;");
					reStr.append("<img src=\""+basePath+"/website/images/b2.gif\" width=\"17\" height=\"17\" title=\"上一页\">&nbsp;&nbsp;");
					reStr.append("<img src=\""+basePath+"/website/images/b3.gif\" width=\"17\" height=\"17\" title=\"下一页\">&nbsp;&nbsp;");
					reStr.append("<img src=\""+basePath+"/website/images/b4.gif\" width=\"17\" height=\"17\" title=\"末页\">&nbsp;&nbsp;");
					reStr.append("<input style=\"width:20px;height:18px\">GO&nbsp;&nbsp;");
				} else {
					if (intPageNo < 2) {
						reStr.append("<img src=\""+basePath+"/website/images/b1.gif\" width=\"17\" height=\"17\" title=\"首页\">&nbsp;&nbsp;");
						reStr.append("<img src=\""+basePath+"/website/images/b2.gif\" width=\"17\" height=\"17\" title=\"上一页\">&nbsp;&nbsp;");
						reStr.append(getUrl(intPageNo + 1, "<img src=\""+basePath+"/website/images/b3.gif\" width=\"17\" height=\"17\" border=\"0\" title=\"下一页\">&nbsp;&nbsp;"));
						reStr.append(getUrl(totalPages, "<img src=\""+basePath+"/website/images/b4.gif\" width=\"17\" height=\"17\" border=\"0\" title=\"末页\">&nbsp;&nbsp;"));
					} else if (intPageNo == totalPages) {
						reStr.append(getUrl(1, "<img src=\""+basePath+"/website/images/b1.gif\" width=\"17\" height=\"17\" border=\"0\" title=\"首页\">&nbsp;&nbsp;"));
						reStr.append(getUrl(intPageNo - 1, "<img src=\""+basePath+"/website/images/b2.gif\" width=\"17\" height=\"17\" title=\"上一页\" border=\"0\">&nbsp;&nbsp;"));
						reStr.append("<img src=\""+basePath+"/website/images/b3.gif\" width=\"17\" height=\"17\" title=\"下一页\">&nbsp;&nbsp;");
						reStr.append("<img src=\""+basePath+"/website/images/b4.gif\" width=\"17\" height=\"17\" title=\"末页\">&nbsp;&nbsp;");
					} else {
						reStr.append(getUrl(1, "<img src=\""+basePath+"/website/images/b1.gif\" width=\"17\" height=\"17\" border=\"0\" title=\"首页\">&nbsp;&nbsp;"));
						reStr.append(getUrl(intPageNo - 1, "<img src=\""+basePath+"/website/images/b2.gif\" width=\"17\" height=\"17\" border=\"0\" title=\"上一页\">&nbsp;&nbsp;"));
						reStr.append(getUrl(intPageNo + 1, "<img src=\""+basePath+"/website/images/b3.gif\" width=\"17\" height=\"17\" border=\"0\" title=\"下一页\">&nbsp;&nbsp;"));
						reStr.append(getUrl(totalPages, "<img src=\""+basePath+"/website/images/b4.gif\" width=\"17\" height=\"17\" border=\"0\" title=\"末页\">&nbsp;&nbsp;"));
					}
					reStr.append("<input style=\"width:20px;height:18px\" name=\"inputPage\" id=\"inputPage\"><a href=\"javascript:void(0)\" onclick=\"goTo()\">GO</a>");
				}
				reStr.append("</td>");
				reStr.append("</tr></table>");		
			}else{
//				if (totalPages < 2) {
//					reStr.append("<a href=\"#\">上一页</a>");
//					reStr.append("<a href=\"#\">1</a>");
//					reStr.append("<a href=\"#\">下一页</a>");
//					reStr.append("<input type=\"text\" style=\"width:15px;\" class=\"sp_inp_jump\" name=\"inputPage\" id=\"inputPage\" />");
//					reStr.append("<a class=\"sp_jump\">确定</a>");
//				} else {
//					if (intPageNo < 2) {
//						reStr.append("<a href=\"#\">上一页</a>");
//						reStr.append(getUrl(intPageNo + 1,"下一页"));
//						reStr.append("<input type=\"text\" style=\"width:15px;\" class=\"sp_inp_jump\" name=\"inputPage\" id=\"inputPage\" />");
//						reStr.append("<a class=\"sp_jump\" href=\"javascript:void(0)\" onclick=\"goTo()\">确定</a>");
//						reStr.append(getUrl(totalPages,"<div class=\"sp_right\"></div>"));
//					} else if (intPageNo == totalPages) {
//						reStr.append(getUrl(1,"<div class=\"sp_left\"></div>"));
//						reStr.append(getUrl(intPageNo - 1,"上一页"));
//						reStr.append("下一页");
//						reStr.append("<input type=\"text\" style=\"width:15px;\" class=\"sp_inp_jump\" name=\"inputPage\" id=\"inputPage\" />");
//						reStr.append("<a class=\"sp_jump\" href=\"javascript:void(0)\" onclick=\"goTo()\">确定</a>");
//						reStr.append("<div class=\"sp_right\"></div>");
//					} else {
//						
//					}
//				} 
				if(count>0){
					if(totalPages<5){
						//reStr.append(getUrl(1,"<div class=\"sp_left\"></div>"));
						if (intPageNo < 2) 
							reStr.append("<a href=\"javascript:;\">上一页</a>");
						else	
							reStr.append(getUrl(intPageNo - 1,"上一页"));
						for(int i=1;i<=totalPages;i++){
							reStr.append(getUrl(i,i+""));
						}
						if(intPageNo == totalPages)
							reStr.append("<a href=\"javascript:;\">下一页</a>");
						else
							reStr.append(getUrl(intPageNo + 1,"下一页"));
					}else{
						if(intPageNo<5){
							reStr.append(getUrl(1,"<div class=\"sp_left\"></div>"));
							if (intPageNo < 2) 
								reStr.append("<a href=\"javascript:;\">上一页</a>");
							else	
								reStr.append(getUrl(intPageNo - 1,"上一页"));
							for(int i=1;i<=5;i++){
								reStr.append(getUrl(i,i+""));
							}
							reStr.append("...");
							if(intPageNo == totalPages)
								reStr.append("<a href=\"javascript:;\">下一页</a>");
							else
								reStr.append(getUrl(intPageNo + 1,"下一页"));
						}else{
							reStr.append(getUrl(1,"<div class=\"sp_left\"></div>"));
							if (intPageNo < 2) 
								reStr.append("<a href=\"javascript:;\">上一页</a>");
							else	
								reStr.append(getUrl(intPageNo - 1,"上一页"));
							reStr.append(getUrl(1,1+""));
							reStr.append("...");
							int  jj = totalPages;
							if(intPageNo+2<=totalPages){
								jj = intPageNo+2;
							}
							for(int i=intPageNo-1;i<=jj;i++){
								reStr.append(getUrl(i,i+""));
							}
							if(intPageNo == totalPages)
								reStr.append("<a href=\"javascript:;\">下一页</a>");
							else
								reStr.append(getUrl(intPageNo + 1,"下一页"));
							//reStr.append(getUrl(totalPages,"<div class=\"sp_right\"></div>"));
						}
					}
					reStr.append("<span class=\"page\">当前" + intPageNo + "/"+ totalPages + "页 到<input type=\"text\" size=\"3\" class=\"sp_inp_jump\" name=\"inputPage\" id=\"inputPage\" />页<input class=\"sp_jump\" href=\"javascript:void(0)\" onclick=\"goTo()\" value=\"确定\" type=\"button\"></input></span>");
					//<span class="page">共100页 到第<input name="" type="text" size="3">页<input onclick="location.href='#'"  name="Submit" value="确定"  type="button"></span>
				}
			}
		}
		
		
		JspWriter writer = pageContext.getOut();
		try {
			
			writer.println(reStr.toString());
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		return (EVAL_PAGE);
	}

	//private String getPagePage()
	
	private String getUrl(int pageNo, String name) {
		if(pageNo == intPageNo)
			return "<a class=\"selected\" href=\"" + dealUrl(url, pageNo)+ "\" class=\"splitPage\">" + name + "</a>";
		else
			return "<a  href=\"" + dealUrl(url, pageNo)+ "\" class=\"splitPage\">" + name + "</a>";
	}

	private String dealUrl(String url, int pageNo) {
		 
		return url + "?pageId=" + pageNo +"&amp;pageSize="+pageSize+paramsStr;
	}

	private String addParams(String params) {
		if (params == null || params.equals("")) {
			return "";
		}
		return "?" + params.substring(1);
	}

	public void release() {
		super.release();
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isWebSite() {
		return isWebSite;
	}

	public void setIsWebSite(boolean isWebSite) {
		this.isWebSite = isWebSite;
	}
	
	public boolean isWebSiteClient() {
		return isWebSiteClient;
	}
	
	public void setIsWebSiteClient(boolean isWebSiteClient) {
		this.isWebSiteClient = isWebSiteClient;
	}

}
