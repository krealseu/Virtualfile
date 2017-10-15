package org.kreal.httpfileServer;

public class IcoBean {
	private String IcoRoot = "/Virtualfile/image/appico";
	private String Filename;
	private boolean isPhoto(String name) {
		if (name.matches("(bmp|jpg|jpeg|png|gif)$"))
			return true;
		else
			return false;
	};

	private boolean isVideo(String name) {
		if (name.matches("(rmvb|avi|mp4|mkv|flv)$"))
			return true;
		else
			return false;
	};

	private boolean isMusic(String name) {
		if (name.matches("(mp3|ape|ogg|wav|ape|cda|au|midi|acc)$"))
			return true;
		else
			return false;
	};

	private boolean isTxt(String name) {
		if (name.matches("(txt|xml|html|ini)$"))
			return true;
		else
			return false;
	}
	
	public String getfileico() {
		String lastname = this.Filename;
		if (isPhoto(lastname))
			return IcoRoot+"/photos.png";
		else if(isVideo(lastname))
			return IcoRoot+"/videos.png";
		else if(isMusic(lastname))
			return IcoRoot+"/music.png";
		else return IcoRoot+"/Unknow.png" ;
	}

	public String getIcoRoot() {
		return IcoRoot;
	}

	public void setIcoRoot(String icoRoot) {
		IcoRoot = icoRoot;
	}

	public String getFilename() {
		return Filename;
	}

	public void setFilename(String filename) {
		String name = filename.toLowerCase();
		Filename = name.substring(name.lastIndexOf(".") + 1, name.length());;
	};
	
}
