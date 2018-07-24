package com.adpanshi.cashloan.business.core.common.web.filter;

import com.adpanshi.cashloan.business.core.common.model.TimeProfiler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 性能监听器:将访问时间以100毫秒为单位，
 * 超过100毫秒的，将记录下访问信息，以日志形式输出

 */
public class PerformanceMonitorFilter implements Filter {

	/**
	 * 缺省监测值为100毫秒，超过这个值的request请求将被记录
	 */
	private int threshold = 100;

	@Override
	public void init(FilterConfig config) throws ServletException {
		String s = config.getInitParameter("threshold");

		if (s == null) {
			return;
		}
		threshold = Integer.valueOf(s);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (TimeProfiler.isProfileEnable()) {
			doFilterWithProfile(request, response, chain);
		} else {
			chain.doFilter(request, response);
		}
	}

	private void doFilterWithProfile(ServletRequest request,
			ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest hs = (HttpServletRequest) request;
		hs.setCharacterEncoding("UTF-8");
		String profilerId = getProfilerId(hs);
		TimeProfiler profiler = TimeProfiler.start(profilerId, threshold);
		try {
			chain.doFilter(request, response);
		} finally {
			profiler.release(getProfilerName(hs));
		}
	}

	private String getProfilerId(HttpServletRequest hs) {
		StringBuffer id = hs.getRequestURL();
		return id.toString();
	}

	private String getProfilerName(HttpServletRequest hs) {
		StringBuffer id = hs.getRequestURL();
		id.append(' ');
		Enumeration enumer = hs.getParameterNames();
		while (enumer.hasMoreElements()) {
			String name = enumer.nextElement().toString();
			String value = hs.getParameter(name);
			id.append('[').append(name).append('=').append(value).append("] ");
		}
		return id.toString();
	}
}
