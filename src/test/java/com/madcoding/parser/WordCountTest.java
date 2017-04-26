package com.madcoding.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class WordCountTest {
	
	@Test
	public void testEquals(){
		WordCount w1 = WordCount.of("w1", 1);
		WordCount w2 = WordCount.of("w2", 1);
		
		WordCount wEquals = WordCount.of("w1", 1);
		
		assertFalse(w1.equals(w2));
		assertEquals(w1,wEquals);
	}

}
