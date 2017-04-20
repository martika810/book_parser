package com.madcoding.parser;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

public class PdfFileParser implements FileParser {
	
	private final String fileLocation;
	
	
	public PdfFileParser(String fileLocation){
		this.fileLocation = fileLocation;
	}

	@Override
	public List<String> findSentences(String words) {
		InputStream input = null;
		List<String> sentences = new ArrayList<>();
        try {
            input = new FileInputStream(fileLocation);
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            AutoDetectParser parser = new AutoDetectParser();
            ParseContext parserContext = new ParseContext();

            Map<String, Object> map = new HashMap<String, Object>();

            parser.parse(input,handler,metadata,parserContext);

            map.put("text",handler.toString().replaceAll("\n|\r|\t"," "));
            
            sentences =Arrays.stream(((String)map.get("text")).split("\\."))
            		.filter( line -> line.contains(words))
            		.collect(Collectors.toList());
           
            
        }catch(Exception e){
        	
        	e.printStackTrace();
        	return sentences;
        }
		return sentences;
	}
	

	@Override
	public String lastNotFullSentence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapCounter countWords() {
		InputStream input = null;
		Stream<String[]> words = null;
		MapCounter countedWords = MapCounter.of();
		try{
			
			input = new FileInputStream(fileLocation);
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            AutoDetectParser parser = new AutoDetectParser();
            ParseContext parserContext = new ParseContext();

            Map<String, Object> map = new HashMap<String, Object>();

            parser.parse(input,handler,metadata,parserContext);
            
            String extractedText = handler.toString().replaceAll("\n|\r|\t"," ");
            Arrays.stream(extractedText.split("\\.")).forEach(s -> countedWords.addSentence(s));
            return countedWords;
			
		}catch(Exception e){
			 e.printStackTrace();
			 return countedWords;
		}
	}
	
}
