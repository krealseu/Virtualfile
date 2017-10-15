<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Miku</title>
<script src="/Virtualfile/js/showphoto.js"></script>
<link href='/Virtualfile/css/showstyle.css' rel='stylesheet' type='text/css'/>
<link href='/Virtualfile/css/fileindex.css' rel='stylesheet' type='text/css'/>
<script>
function resize(){
	var htmlWidth = document.body.clientWidth; 
	var n = Math.floor(htmlWidth/140);
	document.getElementById("filecontainer").style.width = 140*n+"px";
}
window.onresize = resize;
</script>
</head>
<%@ include file="jsp/filetest.jsp"%>
<%! String HOMEURL = "HomeUrl";
	String NATIVEFILE = "NativeFile" ;%>
<%
	org.kreal.httpfileServer.HttpFile hfile = (org.kreal.httpfileServer.HttpFile)request.getAttribute(NATIVEFILE);
	String HomeUrl = (String)request.getAttribute(HOMEURL);
	if(hfile==null||HomeUrl==null){
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		return;
	}
	String FileUrl = hfile.getpath();
	org.kreal.httpfileServer.HttpFile[] lists = hfile.listfile();
	boolean ishome = (FileUrl == "")?true:false;
	String LastUrl = HomeUrl+ ((FileUrl == "") ? "" : FileUrl.substring(0, FileUrl.lastIndexOf("/")));
%>
<body>

<div class="box" id="filecontainer">
<script> resize();</script>
  <div <%= ishome?"hidden=\"\"":"" %> class="filelayout" > <a href="<%= HomeUrl %>"> <img class="fileimg" src="/Virtualfile/image/appico/home.png"/> </a>
    <p class="filename">Home</p>
  </div>
  <div <%= ishome?"hidden=\"\"":"" %> class="filelayout" > <a href="<%= LastUrl %>"> <img class="fileimg" src="/Virtualfile/image/appico/back.png"/> </a>
    <p class="filename">..</p>
  </div>
  <% int imgid = 0 ;%>
  <%for (org.kreal.httpfileServer.HttpFile list:lists) {%>
  <div class="filelayout" >
    <% if (list.isFile()) {%>
    <% if(list.getmimetype().startsWith("image/")){%>
    <img id="<%= "img"+(imgid++)%>" class="fileimg" onClick="showPhotoElement(this)" src="<%= HomeUrl +list.getpath() %> "/> </a>
    <% }else {%>
    <a href="<%= HomeUrl +list.getpath() %> "> <img class="fileimg" src="<%= getfileico(list)%>" /> </a>
    <% } %>
    <%} else {%>
    <a href="<%= HomeUrl +list.getpath() %> "> <img class="fileimg" src="<%= getfileico(list)%>"/></a>
    <% } %>
    <p class="filename"><%= list.getName()%></p>
  </div>
  <%}	%>
</div>
</body>
</html>