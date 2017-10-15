package org.kreal.httpfileServer;

import java.util.ArrayList;
import java.util.List;

public class VirtualRoot {
private class RootInfo{
	private String VirtualRoot;
	private String RealRoot;
	public RootInfo(String virtualRoot, String realRoot) {
		super();
		VirtualRoot = virtualRoot;
		RealRoot = realRoot;
	}
}
private List<RootInfo> Roots = new ArrayList<RootInfo>();
private VirtualHome virtualHome= new VirtualHome();
public VirtualRoot(){
	Roots.add(new RootInfo("/G", "G:"));
	Roots.add(new RootInfo("/F", "F:"));
	Roots.add(new RootInfo("/downloads", "G:/Downloads"));
	for(RootInfo info:Roots){
		virtualHome.addRoot(info.RealRoot, info.VirtualRoot);
	}
}

public HttpFile getHttpFile(String path){
	//System.out.print("df");
	//System.out.print(path);
	if(path.matches("")){
		return virtualHome;
	}
	return new NativeHttpFile(getRealPath(path),path);
	
}

public String getRealPath(String path){
	String result = "";
	for(RootInfo rootinfo:Roots){
		if(path.startsWith(rootinfo.VirtualRoot)){
			result = path.replaceFirst(rootinfo.VirtualRoot, rootinfo.RealRoot);
			break;
		}
	}
	return result;
}

}
