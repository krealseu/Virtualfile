<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%! private boolean isPhoto(String name) {
		if (name.matches(".+(bmp|jpg|jpeg|png|gif|BMP|JPG|JPEG|PNG|GIF)$"))
			return true;
		else
			return false;
	};

	private boolean isVideo(String name) {
		if (name.matches(".+(rmvb|avi|mp4|mkv|flv)$"))
			return true;
		else
			return false;
	};

	private boolean isMusic(String name) {
		if (name.matches(".+(mp3|ape|ogg|MP3|OGG|WAV|APE|CDA|AU|MIDI|MAC|AAC)$"))
			return true;
		else
			return false;
	};

	private boolean isTxt(String name) {
		if (name.matches(".+(txt|xml|html|ini)$"))
			return true;
		else
			return false;
	}
	
	private String getfileico(org.kreal.httpfileServer.HttpFile file) {
		if(file.isDirectory())
			return "/Virtualfile/image/appico/folder.png";
		String icopath = "/Virtualfile/image/appico";
 		String name = file.getName().toLowerCase();
		String lastname = name.substring(name.lastIndexOf(".") + 1, name.length());
		if(lastname.matches("(mp4|mkv|xml|txt|ini|zip|rar|apk|exe|7z|iso|bin|torrent|ppt|pptx|doc|docx|ass|pdf)$")){
			return String.format("%s/%s.png",icopath,lastname);
		}
		if (isPhoto(file.getName()))
			return "/Virtualfile/image/appico/photos.png";
		else if(isVideo(file.getName()))
			return "/Virtualfile/image/appico/videos.png";
		else if(isMusic(file.getName()))
			return "/Virtualfile/image/appico/music.png";
		else return "/Virtualfile/image/appico/Unknow.png" ;
	};
%>