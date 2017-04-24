package com.madcoding.docgenerator;

import static j2html.TagCreator.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import j2html.tags.ContainerTag;
import j2html.tags.Tag;

import com.madcoding.parser.MapCounter;

import java.nio.file.Files;;

public class HtmlGenerator {
	
	private static final String HTML_EXTENSION = ".html";
	
	
	public static void saveOnFile(MapCounter wordCount) throws IOException{
		
		String fileName = new StringBuilder("./html/html-").append(UUID.randomUUID().toString()).append(HTML_EXTENSION).toString();
		String htmlContent = generateDocument(wordCount);
		Files.write(Paths.get(fileName), htmlContent.getBytes(),StandardOpenOption.CREATE);
		
	}
	
	private static String generateDocument(final MapCounter wordCount) {
		
		List<Tag> tags =wordCount.allWords().stream().parallel()
					.map(word ->{return withWordCount(word,wordCount.getCount(word));})
					.collect(Collectors.toList());
		
		Tag body = body().with(h1("Document")).with(tags);
		
		ContainerTag doc = html()
				.with(body);
		
		return doc.render();
		
	}
	
	private static Tag withWordCount(String word, int count){
		return p().withText(new StringBuilder(word).append(" - ").append(count).toString());
	}
	
	

}
