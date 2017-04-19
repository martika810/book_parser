package com.madcoding.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.junit.Test;

public class FileSplitterTest {

	@Test
	public void splitPdfFile() throws InvalidPasswordException,IOException {
			FileSplitter splitter = new PdfFileSplitter();
			
			List<String> splittedDocumentNames = splitter.splitDocument("./big_sample_346pages.pdf", 100);
			assertEquals(4,new File("./tmp/").listFiles().length);
			
			splitter.clean();
			assertEquals(0,new File("./tmp/").listFiles().length);

	}
	
	
}
