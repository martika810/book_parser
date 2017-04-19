package com.madcoding.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfFileSplitter implements FileSplitter {

	private final static String EXTENSION = ".pdf";
	private final static String TEMP_LOCATION = "./tmp/";

	@Override
	public List<String> splitDocument(String fileLocation, int splitSize) {
		List<String> splittedDocumentNames = new LinkedList<>();
		List<PDDocument> splittedDocuments = new ArrayList<>();
		try {
			PDDocument document = PDDocument.load(new File(fileLocation));
			Splitter splitter = new Splitter();
			splitter.setSplitAtPage(splitSize);

			splittedDocuments = splitter.split(document);

			splittedDocuments.stream().forEach(doc -> {
				String name = saveSplittedPDFFile(doc);
				splittedDocumentNames.add(name);
			});
		} catch (Exception e) {
			e.printStackTrace();
			return splittedDocumentNames;
		} finally {
			try {
				for (PDDocument doc : splittedDocuments) {
					doc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return splittedDocumentNames;
	}

	

	@Override
	public void clean() {
		File temporalFolder = new File(TEMP_LOCATION);
		Arrays.stream(temporalFolder.listFiles()).forEach(file ->file.delete());
		
	}
	
	private String saveSplittedPDFFile(PDDocument document) {
		String randomName = UUID.randomUUID().toString();
		String fileName = TEMP_LOCATION + randomName + EXTENSION;
		try {
			document.save(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return fileName;

	}
}
