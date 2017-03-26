package cis.nlp.model;

import java.util.ArrayList;
import java.util.Hashtable;

import cis.nlp.count.CheckWord;
import cis.nlp.count.Count4gram;
import cis.nlp.count.CountBigram;
import cis.nlp.count.CountTriGram;
import cis.nlp.count.CountUnigram;
import cis.nlp.count.StopWord;
import cis.nlp.file.Document;
import cis.nlp.file.SuperData;
import cis.nlp.io.DirectoryContents;
import cis.nlp.io.DirectorySavedResult;
import cis.nlp.io.ReadFile;
import cis.nlp.io.WriteFile;

public class CountNgram {
	private String outputUnigram;
	private String outputBigram;
	private String outputTrigram;
	private String output4gram;
	private static CountUnigram one;
	private static CountBigram bigram;
	private static CountTriGram trigram;
	private static Count4gram fourgram;
	private static StopWord stopWords;
	private static CheckWord check;
	private boolean isTokenized;
	private long numberOfSyllables = 0;
	private ArrayList<String> listpath;
	private String target = DirectorySavedResult.getDirectoryToSaveResult();
	static boolean isLoadFile = false;
	private ReadFile rf;
	public CountNgram() {
		check = new CheckWord();
		stopWords = new StopWord();
	}
	
	public void setFileInput(String fileInput){
		if(!isLoadFile){
			listpath = DirectoryContents.getFileTxt(fileInput);
		}
		
	}
	
	public void resetLoadFile(){
		isLoadFile = false;
	}
	public void setType(boolean isTokenized){
		target = DirectorySavedResult.getDirectoryToSaveResult();
		this.isTokenized = isTokenized;
		if(isTokenized){
			target += "tokenized/";
		} else {
			target += "nontokenized/";
		}
		outputUnigram = target + "unigram.txt";
		outputBigram = target + "bigram.txt";
		outputTrigram = target + "trigram.txt";
		output4gram = target + "fourgram.txt";
	}
	public void countUnigram() {
		one = new CountUnigram();
		for (String apath : listpath) {
			rf = new ReadFile();
			rf.open(apath);
			ArrayList<String> allLines = rf.read();
			rf.close();
			Document doc = new Document(apath, allLines);
			for (String aline : allLines) {
				String[] tokens = aline.split(" ");
				for (String token : tokens) {
					numberOfSyllables ++;
					if (check.checkWord(token) && stopWords.isStopWord(token) == false) {
						one.add(token, doc);
					}
				}
			}
		}
		System.out.println("Number of Syllables " + numberOfSyllables);
	}

	public void countBiGram(CountUnigram loadUnigram) {
		bigram = new CountBigram();
		Hashtable<String, SuperData> unigram = loadUnigram.getOneCount();
		for (String apath : listpath) {
			rf = new ReadFile();
			rf.open(apath);
			ArrayList<String> allLines = rf.read();
			rf.close();
			Document doc = new Document(apath, allLines);
			for (String aline : allLines) {
				String[] tokens = aline.split(" ");
				int length = tokens.length;
				int i = 0;
				while (i + 1 < length) {
					if (unigram.get(tokens[i]) != null && unigram.get(tokens[i + 1]) != null) {
						bigram.add(tokens[i], tokens[i + 1], doc);
					}
					i++;
				}
			}
		}
	}

	public void countTrigram(CountUnigram loadUnigram) {
		trigram = new CountTriGram();
		Hashtable<String, SuperData> unigram = loadUnigram.getOneCount();
		for (String apath : listpath) {
			rf = new ReadFile();
			rf.open(apath);
			ArrayList<String> allLines = rf.read();
			rf.close();
			Document doc = new Document(apath, allLines);
			for (String aline : allLines) {
				String[] tokens = aline.split(" ");
				int length = tokens.length;
				int i = 0;
				while (i + 2 < length) {
					if (unigram.get(tokens[i]) != null && unigram.get(tokens[i + 1]) != null
							&& unigram.get(tokens[i + 2]) != null) {
						trigram.add(tokens[i], tokens[i + 1], tokens[i + 2], doc);
					}
					i++;
				}
			}
		}
	}
	
	public void count4gramV3_1(CountUnigram loadTriAsUnigram, CountUnigram loadUnigram){
		fourgram = new Count4gram();
		Hashtable<String, SuperData> hasTriAsUni = loadTriAsUnigram.getOneCount();
		Hashtable<String, SuperData> hasUni = loadUnigram.getOneCount();
		for (String apath : listpath) {
			rf = new ReadFile();
			rf.open(apath);
			ArrayList<String> allLines = rf.read();
			rf.close();
			Document doc = new Document(apath, allLines);
			for (String aline : allLines) {
				String[] tokens = aline.split(" ");
				int i = 0;
				while(i + 3 < tokens.length){
					String tri = tokens[i + 0] + " " + tokens[i + 1] + " " + tokens[i + 2];
					String uni = tokens[i + 3];
					i ++;
					if(hasTriAsUni.get(tri) != null && hasUni.get(uni) != null){
						fourgram.add(tri, uni, doc);
					}
				}
			}
		}
	}
	public void count4gramV2_2(CountUnigram loadBiAsUnigram){
		fourgram = new Count4gram();
		Hashtable<String, SuperData> hashBiAsUni = loadBiAsUnigram.getOneCount();
		for (String apath : listpath) {
			rf = new ReadFile();
			rf.open(apath);
			ArrayList<String> allLines = rf.read();
			rf.close();
			Document doc = new Document(apath, allLines);
			for (String aline : allLines) {
				String[] tokens = aline.split(" ");
				int i = 0;
				while(i + 3 < tokens.length){
					String bi1 = tokens[i + 0] + " " + tokens[i + 1];
					String bi2 = tokens[i + 2] + " " + tokens[i + 3];
					i ++;
					if(hashBiAsUni.get(bi1) != null && hashBiAsUni.get(bi2) != null){
						fourgram.add(bi1, bi2, doc);
					}
				}
			}
		}
	}
	public int[] getCount() {
		int[] counts = new int[3];
		counts[0] = one.getN();
		counts[1] = bigram.getN();
		return counts;
	}

	public void processUniBigram() {
		countUnigram();
		writeNgram(outputUnigram, one.getUnigramInString());
		System.out.println("Unigram: complete!");
	
		LoadNgram load = new LoadNgram();
		load.setType(isTokenized);
		CountUnigram loadUnigram = load.loadUnigram();
		countBiGram(loadUnigram);
		
		writeNgram(outputBigram, bigram.getBigramInString());
		System.out.println("Bigram: complete!");
	}
	
	public void processTrigram(CountUnigram loadUnigram){
		countTrigram(loadUnigram);
		writeNgram(outputTrigram, trigram.getTrigramInString());
		System.out.println("Trigram: complete!");
	}
	
	public void process4gramV3_1(CountUnigram loadTriAsUnigram, CountUnigram loadUnigram){
		count4gramV3_1(loadTriAsUnigram, loadUnigram);
		writeNgram(output4gram, fourgram.get4gramInString());
		System.out.println("Fourgram: complete!");
	}
	
	public void process4gramV2_2(CountUnigram loadBiAsUnigram){
		count4gramV2_2(loadBiAsUnigram);
		writeNgram(output4gram, fourgram.get4gramInString());
		System.out.println("Fourgram: complete!");
	}
	public void writeNgram(String fileOutput, String res){
		WriteFile wf = new WriteFile();
		wf.open(fileOutput);
		wf.write(res);
		wf.close();
	}
	public ArrayList<ArrayList<String>> getAllLine(ArrayList<String> listPath){
		ArrayList<ArrayList<String>> contentFile = new ArrayList<>();
		ReadFile rf = new ReadFile();
		for (String apath : listPath) {
			rf.open(apath);
			ArrayList<String> allLinesInFile = rf.read();
			contentFile.add(allLinesInFile);
		}
		return contentFile;
	}
}
