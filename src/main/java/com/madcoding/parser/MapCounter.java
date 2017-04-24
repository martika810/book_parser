package com.madcoding.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

public class MapCounter {

	private Map<WordCount,Integer> countedWords;
	private Map<String,Integer> directAccessWords;

	
	private MapCounter(){

		this.countedWords = new TreeMap<>();
		this.directAccessWords = new HashMap<>();
	}
	private String cleanWord(String word){
		return word.trim().toLowerCase();
	}

	public void add(String word){
		String cleanWord = cleanWord(word);
		if(directAccessWords.get(cleanWord)!=null){
			synchronized (directAccessWords) {
				Integer currentCount = directAccessWords.get(cleanWord) + 1;
				countedWords.put(WordCount.of(cleanWord, currentCount), currentCount);
				directAccessWords.put(cleanWord,currentCount);
			}
		}else{
			synchronized(directAccessWords) {
				countedWords.put(WordCount.of(cleanWord, 1), 1);
				directAccessWords.put(cleanWord,1);
			}
		}
		
	}
	
	public void addSentence(String sentence){
		Stream<String> words = Arrays.stream(sentence.split(" "));
		words.forEach(this::add);
	}
	


	public void add(final MapCounter another){

		another.directAccessWords.keySet().stream().parallel().forEach(word ->{
			int anotherCount = another.getCount(word);
			if(directAccessWords.get(word)!=null){
				synchronized(countedWords) {
					int currentCount = getCount(word) + anotherCount;
					countedWords.put(WordCount.of(word, currentCount), currentCount);
					directAccessWords.put(word,currentCount);
				}
			}else{
				synchronized (countedWords) {
					countedWords.put(WordCount.of(word, anotherCount), anotherCount);
					directAccessWords.put(word,anotherCount);
				}
			}
		});
	}
	
	public Set<String> allWords(){

		return new TreeSet<>(countedWords.keySet());
	}
	
	public int getCount(String word){
		String cleanWord = cleanWord(word);
		if(null == directAccessWords.get(cleanWord)){
			return 0;
		}else{
			return directAccessWords.get(cleanWord);
		}
	}
	
	public static MapCounter of(){
		return new MapCounter();
	}

}
