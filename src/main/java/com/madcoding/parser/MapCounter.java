package com.madcoding.parser;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapCounter {
	private Map<WordCount,Integer> countedWords;
	private Map<String,Integer> directAccessWords;
	private Set<WordCount> sortedset;

	
	private MapCounter(){
		this.countedWords = new TreeMap<>((o1,o2) ->{return o1.count() - o2.count();});
		this.directAccessWords = new HashMap<>();
		this.sortedset = new TreeSet<>((o1,o2) -> {return o1.count()-(o2.count());});
	           
	}
	private String cleanWord(String word){
		return word.trim().toLowerCase();
	}
	public void add(String word){
		String cleanWord = cleanWord(word);
		if(directAccessWords.get(cleanWord)!=null){
			//synchronized (directAccessWords) {
				Integer currentCount = directAccessWords.get(cleanWord);
				Integer nextCount = currentCount + 1;
				
				countedWords.put(WordCount.of(cleanWord, currentCount), nextCount);
				directAccessWords.put(cleanWord,nextCount);
				sortedset.add(WordCount.of(cleanWord, currentCount));
			//}
		}else{
			//synchronized(directAccessWords) {
				countedWords.put(WordCount.of(cleanWord, 1), 1);
				directAccessWords.put(cleanWord,1);
			//}
		}
		
	}
	
	public void add(String...words){
		Arrays.stream(words).forEach(this::add);
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
	
	public List<String> allWords(){
		List<String> sortedWords = countedWords.keySet().stream().map(w -> w.word()).collect(Collectors.toList());
		return sortedWords;
	}
	
	public int sizeCounted(){
		
		return countedWords.keySet().size();
	}
	
	public int sizeDirect(){
		return directAccessWords.keySet().size();
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
