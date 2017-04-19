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

}
