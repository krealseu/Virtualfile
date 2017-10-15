package org.kreal.httpfileServer;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class fileindex
 */
@WebServlet( "/Virtual/*")
public class Virtualfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String indexpath = "/fileindexJSTL.jsp";
	private String downloadpath = "/download";
	private VirtualRoot roots = new VirtualRoot();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Virtualfile() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ss = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println(ss);
		String path = request.getPathInfo();
		if (path== null)
			path = "";
		//生成file文件对象  和 该Servlet的url地址
		HttpFile file = roots.getHttpFile(path);
		String HomeUrl = request.getContextPath()+request.getServletPath();
		request.setAttribute(download.NATIVEFILE, file);
		request.setAttribute("HomeUrl", HomeUrl);
		//针对file的类型分发 处理对象
		if(file.isFile())
			request.getServletContext().getRequestDispatcher(downloadpath).forward(request, response);
		else if(file.isDirectory()){
			request.getServletContext().getRequestDispatcher(indexpath).forward(request, response);
		}
		else response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
