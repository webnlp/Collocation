package cis.nlp.model;

import java.util.ArrayList;
import java.util.Hashtable;

import cis.nlp.count.CheckWord;
import cis.nlp.count.Count4gram;
import cis.nlp.count.CountBigram;
import cis.nlp.count.CountTriGram;
import cis.nlp.count.CountUnigram;
import cis.nlp.count.StopWord;
import cis.nlp.io.DirectoryContents;
import cis.nlp.io.ReadFile;
import cis.nlp.io.WriteFile;

public class CountNgram {
	private String fileInput = null;
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
	private String target = "/home/zic/Desktop/NLP_RESULT/";
	public CountNgram() {
		check = new CheckWord();
		stopWords = new StopWord();
	}
	
	public void setFileInput(String fileInput){
		this.fileInput = fileInput;
	}
	
	public void setType(boolean isTokenized){
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
	public void countUnigram(ArrayList<String> listpath) {
		one = new CountUnigram();
		for (String apath : listpath) {
			ReadFile rf = new ReadFile();
			rf.open(apath);
			ArrayList<String> lines = rf.read();
			rf.close();
			for (String aline : lines) {
				String[] tokens = aline.split(" ");
				for (String token : tokens) {
					
					if (check.checkWord(token) && stopWords.isStopWord(token) == false) {
						one.add(token);
					}
				}
			}
		}
		
	}

	public void countBiGram(ArrayList<String> listpath, CountUnigram loadUnigram) {
		bigram = new CountBigram();
		Hashtable<String, Integer> unigram = loadUnigram.getOneCount();
		for (String apath : listpath) {
			ReadFile rf = new ReadFile();
			rf.open(apath);
			ArrayList<String> lines = rf.read();
			rf.close();
			for (String aline : lines) {
				String[] tokens = aline.split(" ");
				int length = tokens.length;
				int i = 0;
				while (i + 1 < length) {
					if (unigram.get(tokens[i]) != null && unigram.get(tokens[i + 1]) != null) {
						bigram.add(tokens[i], tokens[i + 1]);
					}
					i++;
				}
			}
		}
	}

	public void countTrigram(ArrayList<String> listpath, CountUnigram loadUnigram) {
		trigram = new CountTriGram();
		Hashtable<String, Integer> unigram = loadUnigram.getOneCount();
		for (String apath : listpath) {
			ReadFile rf = new ReadFile();
			rf.open(apath);
			ArrayList<String> lines = rf.read();
			rf.close();
			for (String aline : lines) {
				String[] tokens = aline.split(" ");
				int length = tokens.length;
				int i = 0;
				while (i + 2 < length) {
					if (unigram.get(tokens[i]) != null && unigram.get(tokens[i + 1]) != null
							&& unigram.get(tokens[i + 2]) != null) {
						trigram.add(tokens[i], tokens[i + 1], tokens[i + 2]);
					}
					i++;
				}
			}
		}
	}
	
	public void count4gram(ArrayList<String> listpath, CountUnigram loadTriAsUnigram, CountUnigram loadUnigram){
		fourgram = new Count4gram();
		Hashtable<String, Integer> hasTriAsUni = loadTriAsUnigram.getOneCount();
		Hashtable<String, Integer> hasUni = loadUnigram.getOneCount();
		for (String apath : listpath) {
			ReadFile rf = new ReadFile();
			rf.open(apath);
			ArrayList<String> lines = rf.read();
			for (String aline : lines) {
				String[] tokens = aline.split(" ");
				int i = 0;
				while(i + 3 < tokens.length){
					String tri = tokens[i + 0] + " " + tokens[i + 1] + " " + tokens[i + 2];
					String uni = tokens[i + 3];
					i ++;
					if(hasTriAsUni.get(tri) != null && hasUni.get(uni) != null){
						fourgram.add(tri, uni);
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
		ArrayList<String> listpath = DirectoryContents.getFileTxt(fileInput);
		countUnigram(listpath);
		WriteFile wf = new WriteFile();
		
		wf.open(outputUnigram);
		wf.write(one.getUnigramInString());
		wf.close();
		System.out.println("Unigram: complete!");
	
		LoadNgram load = new LoadNgram();
		load.setType(isTokenized);
		CountUnigram loadUnigram = load.loadUnigram();
		countBiGram(listpath, loadUnigram);
		
		wf.open(outputBigram);
		wf.write(bigram.getBigramInString());
		wf.close();
		System.out.println("Bigram: complete!");
	}
	
	public void processTrigram(CountUnigram loadUnigram){
		ArrayList<String> listpath = DirectoryContents.getFileTxt(fileInput);
		WriteFile wf = new WriteFile();
		countTrigram(listpath, loadUnigram);
		wf.open(outputTrigram);
		wf.write(trigram.getTrigramInString());
		wf.close();
		System.out.println("Trigram: complete!");
	}
	
	public void process4gram(CountUnigram loadTriAsUnigram, CountUnigram loadUnigram){
		ArrayList<String> listpath = DirectoryContents.getFileTxt(fileInput);
		WriteFile wf = new WriteFile();
		count4gram(listpath, loadTriAsUnigram, loadUnigram);
		wf.open(output4gram);
		wf.write(fourgram.get4gramInString());
		wf.close();
		System.out.println("Fourgram: complete!");
	}
}
