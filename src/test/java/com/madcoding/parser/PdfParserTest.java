package com.madcoding.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.metadata.Metadata;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.madcoding.docgenerator.HtmlGenerator;

import static org.junit.Assert.*;
import static thredds.inventory.CollectionUpdateType.test;

/**
 * Created by rsm095 on 10/04/2017.
 */
public class PdfParserTest {

	@Test
	public void findSentence() {
		FileSplitter splitter = new PdfFileSplitter();
		List<String> splittedDocumentNames = splitter.splitDocument("./big_sample_346pages.pdf", 10);
		List<String> result = new ArrayList<>();
		
		
		for(String fileLocation:splittedDocumentNames){
			FileParser parser = new PdfFileParser(fileLocation);
			result.addAll(parser.findSentences("DNA"));
		}
		
		assertTrue(result.size()>2);
		result.stream().forEach(line ->{System.out.println(line);System.out.println("");});
		// TO DO generate an html with <p> and <h6>
		// count words
	}
	
	@Test
	public void testCountWords(){
		MapCounter counter = MapCounter.of();
		FileSplitter splitter = new PdfFileSplitter();
		List<String> splittedDocumentNames = splitter.splitDocument("./codigo_civil.pdf", 20);
		
		for(String fileName:splittedDocumentNames){
			FileParser parser = new PdfFileParser(fileName);
			MapCounter acummulatorCounter = parser.countWords();
			counter.add(acummulatorCounter);
		}

		try {
			HtmlGenerator.saveOnFile(counter);
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		assertTrue(counter.getCount("Decreto")>0);
		
		splitter.clean();
		
	}
	
	
}
