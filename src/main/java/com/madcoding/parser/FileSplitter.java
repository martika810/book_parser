package com.madcoding.parser;

import java.util.List;

public interface FileSplitter {
	List<String> splitDocument(String fileLocation,int splitSize);
	
	void clean();

}
