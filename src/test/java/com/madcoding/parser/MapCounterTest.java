package com.madcoding.parser;

import static org.junit.Assert.assertEquals;

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
		counter.add("w2");
		
		MapCounter counter2 = MapCounter.of();
		counter2.add("w1");
		counter2.add("w1");
		counter2.add("w2");
		
		counter.add(counter2);
		
		assertEquals(3,counter.getCount("w1"));
	}

}
