package cis.nlp.count;

import java.util.ArrayList;
import java.util.Hashtable;

import cis.nlp.io.ReadFile;

public class StopWord {
	private Hashtable<String, Integer> stopWords = new Hashtable<>();
	private String file = "NLP_DATA/charAndSyllables/Stopwords.txt";
	ReadFile read;
	private void readStopWords(){
		read = new ReadFile();
		read.open(file);
		ArrayList<String> lines = read.read();
		read.close();
		for (String string : lines) {
			string = string.trim();
			string = string.replaceAll("//s+", " ");
			stopWords.put(string, 1);
		}
	}
	
	public boolean isStopWord(String s){
		s=s.trim();
		s.replaceAll(" ", "");
		return stopWords.containsKey(s);
	}

	public StopWord() {
		readStopWords();
	}
}
