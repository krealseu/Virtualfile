package org.kreal.httpfileServer;

import java.io.IOException;
import java.io.InputStream;

public interface HttpFile {
	public boolean isFile();
	public boolean exist();
	public HttpFile[] listfile();
	public InputStream getInputStream(long pos) throws IOException;
	public String getEtag();
	public String getpath();
	public String getmimetype();
	public long length();
	public long lastModified();
	public String getName();
	public boolean isDirectory();
}
