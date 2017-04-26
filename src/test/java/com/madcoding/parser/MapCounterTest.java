package com.madcoding.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class MapCounterTest {
	
	@Test
	public void testAdd(){
		MapCounter counter = MapCounter.of();
		counter.add("Word1");
		assertEquals(1,counter.getCount("word1"));
		assertEquals(1,counter.getCount("word1   "));
		assertEquals(1,counter.getCount("wORd1"));
		
		counter.add("Word1");
		assertEquals(2,counter.getCount("word1"));
		
		counter.add("Word2");
		assertEquals(1,counter.getCount("word2"));
		
		assertEquals(0,counter.getCount("word3"));
		
	}
	
	@Test
	public void testAddAnother(){
		MapCounter counter = MapCounter.of();
		counter.add("w1");
		assertEquals(counter.sizeCounted(),counter.sizeDirect());
		counter.add("w2");
		assertEquals(counter.sizeCounted(),counter.sizeDirect());
		
		MapCounter counter2 = MapCounter.of();
		counter2.add("w1");
		counter2.add("w1");
		counter2.add("w2");
		
		counter.add(counter2);
		
		assertEquals(3,counter.getCount("w1"));
	}
	
	@Test
	public void testAllWords(){
		MapCounter counter = MapCounter.of();
		counter.add("w1","w2","w2","w2","w2","w3","w3","w3","w3","w3","w3","w3");
		
		List<String> allWords = counter.allWords();
		System.out.println(allWords);
		assertEquals(counter.sizeCounted(),counter.sizeDirect());
		assertEquals(allWords.get(0),"w3");
	}

}
