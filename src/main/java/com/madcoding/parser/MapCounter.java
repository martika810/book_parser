package com.madcoding.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

public class MapCounter {
	private Map<WordCount,Integer> countedWords;
	
	private MapCounter(){
		this.countedWords = new TreeMap<>();
	}
	private String cleanWord(String word){
		return word.trim().toLowerCase();
	}
	public void add(String word){
		String cleanWord = cleanWord(word);
		if(countedWords.get(cleanWord)!=null){
			Integer currentCount = countedWords.get(cleanWord) + 1;
			countedWords.put(WordCount.of(cleanWord, currentCount), currentCount);
		}else{
			countedWords.put(WordCount.of(cleanWord, 1), 1);
		}
		
	}
	
	public void addSentence(String sentence){
		Stream<String> words = Arrays.stream(sentence.split(" "));
		words.forEach(this::add);
	}
	
	public void addSentence(Object sentence){
		Stream<String> words = Arrays.stream(((String)sentence).split(" "));
		words.forEach(this::add);
	}
	
	public void add(final MapCounter another){
		another.allWords().stream().parallel().forEach(word ->{
			int anotherCount = another.getCount(word);
			if(countedWords.get(word)!=null){
				int currentCount = getCount(word)+anotherCount;
				countedWords.put(WordCount.of(word, currentCount), currentCount);
			}else{
				countedWords.put(WordCount.of(word,anotherCount),anotherCount);
			}
		});
	}
	
	public Set<WordCount> allWords(){
		return new TreeSet<WordCount>(countedWords.keySet());
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
