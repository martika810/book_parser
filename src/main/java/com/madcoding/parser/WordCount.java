package com.madcoding.parser;

import java.util.Comparator;

public final class WordCount implements Comparable<WordCount> {
	
	private final String word;
	private final int count;
	
	private WordCount(final String word,final int count){
		this.word=word;
		this.count = count;
	}
	
	public static WordCount of(final String word,final int count){
		return new WordCount(word,count);
	}
	
	public String word(){
		return word;
	}
	
	public int count(){
		return count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordCount other = (WordCount) obj;
		if (count != other.count)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}


	@Override
	public int compareTo(WordCount another) {
		return this.count() - another.count();
	}
}
