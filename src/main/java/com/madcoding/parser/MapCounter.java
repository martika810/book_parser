package com.madcoding.parser;

import java.util.HashMap;
import java.util.Map;

public class MapCounter {
	private Map<String,Integer> countedWords;
	
	private MapCounter(){
		this.countedWords = new HashMap<>();
	}
	private String cleanWord(String word){
		return word.trim().toLowerCase();
	}
	public void add(String word){
		String cleanWord = cleanWord(word);
		if(countedWords.get(cleanWord)!=null){
			Integer currentCount = countedWords.get(cleanWord);
			countedWords.put(cleanWord, ++currentCount);
		}else{
			countedWords.put(cleanWord, 1);
		}
		
	}
	
	public int getCount(String word){
		String cleanWord = cleanWord(word);
		if(null == countedWords.get(cleanWord)){
			return 0;
		}else{
			return countedWords.get(cleanWord);
		}
	}
	
	public static MapCounter of(){
		return new MapCounter();
	}

}
