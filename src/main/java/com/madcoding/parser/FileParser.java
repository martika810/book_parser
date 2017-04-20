package com.madcoding.parser;

import java.util.List;
import java.util.Map;

public interface FileParser {
	List<String> findSentences(String words);
	String lastNotFullSentence();
	MapCounter countWords();
}
