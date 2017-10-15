package org.kreal.httpfileServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class VirtualHome implements HttpFile {
	private List<HttpFile> homefiles = new ArrayList<HttpFile>();
	
	public VirtualHome(){
	}

	public void addRoot(String Realpath, String HttpPath) {
		homefiles.add(new NativeHttpFile(Realpath, HttpPath));
	}
	@Override
	public boolean isFile() {
		return false;
	}

	@Override
	public boolean exist() {
		return true;
	}

	@Override
	public HttpFile[] listfile() {
		// TODO Auto-generated method stub
		HttpFile[] homefile = new HttpFile[homefiles.size()];
		int i=0;
		for(HttpFile file:homefiles){
			homefile[i] =file;
			i++;
		}
		return homefile;
	}

	@Override
	public InputStream getInputStream(long pos) throws IOException {
		return null;
	}

	@Override
	public String getEtag() {
		return "W/Accelerator";
	}

	@Override
	public String getpath() {
		return "";
	}

	@Override
	public String getmimetype() {
		return null;
	}

	@Override
	public long length() {
		return 0;
	}

	@Override
	public long lastModified() {
		return 0;
	}

	@Override
	public String getName() {
		return "Roots";
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

}
