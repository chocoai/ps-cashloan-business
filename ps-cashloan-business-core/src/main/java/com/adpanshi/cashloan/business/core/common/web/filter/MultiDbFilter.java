package com.adpanshi.cashloan.business.core.common.web.filter;

import com.adpanshi.cashloan.business.core.common.db.DatabaseContextHolder;

import javax.servlet.*;
import java.io.IOException;

/**
 * 
 * 清除数据库选择信息
 * 
 */
public class MultiDbFilter implements Filter {

	@Override
	public void init(FilterConfig fc) throws ServletException {
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain fc) throws IOException, ServletException {
		fc.doFilter(request, response);
		//清除线程变量信息
		DatabaseContextHolder.clearDbName();
	}

	@Override
	public void destroy() {
		
	}

}
