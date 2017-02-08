package jUnit;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Test;

import src.Document;

public class DocTests {

	@Test
	public void testAddLine(){
		Document doc = new Document();
		Scanner in = new Scanner("testFiles/addLineTest.txt");
		
		doc.addLine(in);
		
		String result = doc.toString();
		String expected = "";
		
		assertEquals(expected,result);
	
	}
	
	@Test
	public void testRemoveLine(){
		Document doc = new Document();
		doc.loadFile("testFiles/removeLineTest.txt");
		
		doc.removeLine("1");
		
		String result = doc.toString();
		String expected = "";
		
		assertEquals(expected,result);
	}
	
	@Test
	public void testLoadFile(){
		Document doc = new Document();
		
		doc.loadFile("testFiles/loadFileTest.txt");
		
		String result = doc.toString();
		String expected = "";
		
		assertEquals(expected,result);
	}
	
	@Test
	public void testSaveFile(){
		Document doc1 = new Document();
		Document doc2 = new Document();
		doc1.loadFile("testFiles/saveFileTestIn.txt");
	
		doc1.writeToFile("testFiles/saveFileTestOut.txt");
		
		doc2.loadFile("testFiles/saveFileTestOut.txt");
		
		String result = doc2.toString();
		String expected = doc1.toString();
		
		assertEquals(expected,result);
		
	}
}
