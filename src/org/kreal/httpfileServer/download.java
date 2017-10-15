package org.kreal.httpfileServer;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class download
 */
@WebServlet("/download")
public class download extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String NATIVEFILE = "NativeFile" ;
	private int LENMAX = 1024 * 1024;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public download() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		String childpath = request.getPathInfo();
		
		HttpFile file = (HttpFile)request.getAttribute(NATIVEFILE);
		if (!file.isFile()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// deal with ETag
		String IfNoneMatch = request.getHeader("If-None-Match");
		String fileETage = file.getEtag();
		response.setHeader("ETag", fileETage);
		if (IfNoneMatch != null) {
			if (IfNoneMatch.matches(fileETage)){
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
//				response.flushBuffer();
				return;	
			}
		}
		// detail range
		String range = request.getHeader("Range");
		long fSize = file.length();
		long[] pos = new long[2];
		pos[0] = 0;
		pos[1] = fSize-1L;
		if (range != null) {
			boolean isstandard = true;
			//初始化Range的个参数，并判断是否正规
			if(range.matches("bytes=\\d*-\\d*$")){
				String tmp = range.replaceAll("bytes=", "");
				String[] posStr = tmp.split("-");
				if(posStr.length == 1)
					if(tmp.startsWith("-")){
						pos[0] = fSize - Long.parseLong(posStr[0]);
						pos[1] = fSize - 1L;
					}
					else {
						pos[0] = Long.parseLong(posStr[0]);
						pos[1] = fSize -1L;
					}
				else if(posStr.length == 2L){
					for (int i = 0; i < posStr.length; i++)
						pos[i] = Long.parseLong(posStr[i]);
				}else pos[0]=-1L;
				if(pos[0]<0||pos[0]>pos[1]||pos[1]>fSize)
					isstandard = false;
			}else{
				isstandard = false;
			}
			//对是否正规进行判断
			if(!isstandard){
				response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
				return;
			}
			//deal with If Match 
			String ifMatch = request.getHeader("If-Match");
			if(ifMatch != null){
				if(!ifMatch.matches(fileETage)){
					response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
//					response.flushBuffer();
					return;	
				}
			}
			//detail with if Range
			String ifRange = request.getHeader("If-Range");
			if(ifRange!=null){
				if(!ifRange.matches(fileETage)){
					pos[0]=0;
					pos[1]=fSize-1L;
				}
			}
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
		}
		else{
			response.setStatus(HttpServletResponse.SC_OK);
		}
		
		String contentRange = String.format("bytes %d-%d/%d", pos[0],pos[1],fSize);
		response.setHeader("Content-Range", contentRange);
		response.setContentLengthLong(pos[1]-pos[0]+1L);	
		
		//deal heard
		response.setHeader("Accept-Ranges", "bytes");
		response.setContentType(file.getmimetype());
		response.setDateHeader("Last-Modified",file.lastModified());
		
		//处理响应体
		
		InputStream in = file.getInputStream(pos[0]);

		int len = 0;
		byte[] buffer = new byte[LENMAX];
//		PrintWriter out = response.getWriter();
		ServletOutputStream out = response.getOutputStream();
		while ((len = in.read(buffer, 0, LENMAX)) != -1) {
			out.write(buffer, 0, len);
		}
		//系统在server结束后会 检查 并close
//		out.flush();
//		out.close();
		in.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	protected void doHead(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		System.out.print("dohead\n");
//		String childpath = request.getPathInfo();
		
		HttpFile file = (HttpFile)request.getAttribute(NATIVEFILE);
		if (!file.isFile()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// deal with ETag
		String IfNoneMatch = request.getHeader("If-None-Match");
		String fileETage = file.getEtag();
		response.setHeader("ETag", fileETage);
		if (IfNoneMatch != null) {
			if (IfNoneMatch.matches(fileETage)){
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
//				response.flushBuffer();
				return;	
			}
		}
		// detail range
		String range = request.getHeader("Range");
		long fSize = file.length();
		long[] pos = new long[2];
		pos[0] = 0;
		pos[1] = fSize-1L;
		if (range != null) {
			boolean isstandard = true;
			//初始化Range的个参数，并判断是否正规
			if(range.matches("bytes=\\d*-\\d*$")){
				String tmp = range.replaceAll("bytes=", "");
				String[] posStr = tmp.split("-");
				if(posStr.length == 1)
					if(tmp.startsWith("-")){
						pos[0] = fSize - Long.parseLong(posStr[0]);
						pos[1] = fSize - 1L;
					}
					else {
						pos[0] = Long.parseLong(posStr[0]);
						pos[1] = fSize -1L;
					}
				else if(posStr.length == 2L){
					for (int i = 0; i < posStr.length; i++)
						pos[i] = Long.parseLong(posStr[i]);
				}else pos[0]=-1L;
				if(pos[0]<0||pos[0]>pos[1]||pos[1]>fSize)
					isstandard = false;
			}else{
				isstandard = false;
			}
			//对是否正规进行判断
			if(!isstandard){
				response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
				return;
			}
			//deal with If Match 
			String ifMatch = request.getHeader("If-Match");
			if(ifMatch != null){
				if(!ifMatch.matches(fileETage)){
					response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
//					response.flushBuffer();
					return;	
				}
			}
			//detail with if Range
			String ifRange = request.getHeader("If-Range");
			if(ifRange!=null){
				if(!ifRange.matches(fileETage)){
					pos[0]=0;
					pos[1]=fSize-1L;
				}
			}
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
		}
		else{
			response.setStatus(HttpServletResponse.SC_OK);
		}
		
		String contentRange = String.format("bytes %d-%d/%d", pos[0],pos[1],fSize);
		response.setHeader("Content-Range", contentRange);
		response.setContentLengthLong(pos[1]-pos[0]+1L);	
		
		//deal heard
		response.setHeader("Accept-Ranges", "bytes");
		response.setContentType(file.getmimetype());
		response.setDateHeader("Last-Modified",file.lastModified());
		
		//deal with buffer
		
	}

}
