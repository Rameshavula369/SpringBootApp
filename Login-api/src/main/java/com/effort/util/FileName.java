package com.effort.util;


public class FileName {
	
	private String fullPath;
	private char pathSeparator, extensionSeparator;

	public FileName(String str, char sep, char ext) {
	    fullPath = str;
	    pathSeparator = sep;
	    extensionSeparator = ext;
	}

	public String extension() {
	    int dot = fullPath.lastIndexOf(extensionSeparator);
	    if(dot>=0){
	    	return fullPath.substring(dot + 1);
	    }
	    else{
	    	return "";
	    }
	}

	public String filename() { // gets filename without extension
	    int dot = fullPath.lastIndexOf(extensionSeparator);
	    int sep = fullPath.lastIndexOf(pathSeparator);
	    if(pathSeparator>=0){
		    if(dot>=0){
		    	return fullPath.substring(sep + 1, dot);
		    }
		    else{
		    	return fullPath.substring(sep + 1);
		    }
	    }
	    else{
	    	if(dot>=0){
		    	return fullPath.substring(0, dot);
		    }
		    else{
		    	return fullPath;
		    }
	    }
	}

	public String path() {
	    int sep = fullPath.lastIndexOf(pathSeparator);
	    return fullPath.substring(0, sep);
	}
}
