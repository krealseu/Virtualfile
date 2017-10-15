package org.kreal.httpfileServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;

public class NativeHttpFile implements HttpFile {
	private File file;
//	private String Root = "G:\\downloads";
	private String HttpPath = "";

	public NativeHttpFile(String FilePath,String path) {
		this.HttpPath = path;
		this.file = new File(FilePath);
	};

	public NativeHttpFile(File file , String path) {
		this.HttpPath = path;
		this.file = file;
	};

	@Override
	public boolean isFile() {
		return this.file.isFile();
	}

	@Override
	public HttpFile[] listfile() {
		File[] files = this.file.listFiles();
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File f1, File f2) {
				if (f1.isDirectory() && f2.isFile())
					return -1;
				if (f1.isFile() && f2.isDirectory())
					return 1;
				return f1.getName().compareToIgnoreCase(f2.getName());
			}
		});
		HttpFile[] httpFiles = new HttpFile[files.length];
		for (int i = 0; i < files.length; ++i) {
			File fileObj = files[i];
			httpFiles[i] = new NativeHttpFile(fileObj,this.HttpPath+"/"+fileObj.getName());
		}
		return httpFiles;
	}

	@Override
	public InputStream getInputStream(long pos) throws IOException {
		final RandomAccessFile raf = new RandomAccessFile(this.file, "r");
		raf.seek(pos);
		return new FileInputStream(raf.getFD()) {
			public void close() throws IOException {
				super.close();
				raf.close();
			}
		};
	}

	@Override
	public String getEtag() {
		try {
			if (!this.file.exists())
				return null;
			else if (this.file.isFile()) {
				String info = new StringBuffer().append(this.file.length()).append(this.file.lastModified()).toString();
				return String.format("W/\"%s\"", calMD5(info));
			} else {
				String allname = this.file.getPath();
				for (String str : this.file.list())
					allname += str;
				return String.format("W/\"%s\"", calMD5(allname));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getmimetype() {
		return initResponseHeader(this.file.getName());
	}

	@Override
	public long length() {
		return this.file.length();
	}

	@Override
	public long lastModified() {
		return this.file.lastModified();
	}

	@Override
	public String getName() {
		return this.file.getName();
	}

	private String calMD5(String str) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("MD5");
		byte[] bytes = mDigest.digest(str.getBytes());
		StringBuffer result = new StringBuffer();
		for (byte byt : bytes) {
			int digital = byt & 0xff;
			if (digital < 16) {
				result.append("0");
			}
			result.append(Integer.toHexString(digital));
		}
		return result.toString();
	}

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

	private String initResponseHeader(String name) {
		name = name.toLowerCase();
		String lastname = name.substring(name.lastIndexOf(".") + 1, name.length());
		if (isPhoto(lastname))
			return String.format("image/%s", lastname);
		else if (isVideo(lastname))
			return String.format("video/%s", lastname);
		else if (isMusic(lastname))
			return String.format("audio/%s", lastname);
		else if (isTxt(lastname))
			return String.format("text/%s", lastname);
		else {
			return String.format("application/%s", lastname);
		}
	}

	@Override
	public boolean exist() {
		return this.file.exists();
	}

	@Override
	public boolean isDirectory() {
		return this.file.isDirectory();
	}

	@Override
	public String getpath() {
		return this.HttpPath;
	}
}
