package com.zoufanqi.servlet;

import com.alibaba.fastjson.JSON;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ExceptionServlet extends HttpServlet {

    private static final long serialVersionUID = 348919563866453093L;

    private static final String EXCEPTION_INDEX = "index";
    private static final String EXCEPTION_404 = "404";
    private static final String EXCEPTION_405 = "405";
    private static final String EXCEPTION_500 = "500";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultJson result = ResultBuilder.buildError500();
        
        Object exObject = req.getAttribute("ex");
        if (exObject instanceof ZouFanqiException) {
            ZouFanqiException ex = (ZouFanqiException) exObject;
        	result = new ResultJson();
        	result.setCode(ex.getCode());
        	result.setMsg(ex.getMessage());
        	result.setData(null);
            LOG.error("{}", ExceptionUtil.getExceptionAllMsg(ex));
        } else {
        	String uri = req.getRequestURI();
	        if (uri.endsWith(EXCEPTION_INDEX)) {
	            result = ResultBuilder.buildError500();
	
	            Exception exObj = (Exception)req.getAttribute("ex");
	            if (exObj != null) {
	                LOG.error(ExceptionUtil.getExceptionAllMsg(exObj));
	            }
	
	        } else if (uri.endsWith(EXCEPTION_404)) {
	            result = ResultBuilder.buildError404();
	        } else if (uri.endsWith(EXCEPTION_500)) {
	            result = ResultBuilder.buildError500();
	        }
        }

        resp.setContentType(CONTENT_TYPE);
        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSONString(result));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
