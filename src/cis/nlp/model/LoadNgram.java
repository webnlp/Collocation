package cis.nlp.model;

import java.util.ArrayList;
import java.util.Hashtable;

import cis.nlp.count.CountBigram;
import cis.nlp.count.CountTriGram;
import cis.nlp.count.CountUnigram;
import cis.nlp.io.ReadFile;

public class LoadNgram {
	private static final int BEGIN = 2;
	private String outputUnigram;
	private String outputBigram;
	private String outputTrigram;
	private CountUnigram unigram;
	private CountBigram bigram;
	private CountTriGram trigram;
	private CountBigram reverseBigram;
	private CountTriGram reverseTrigram;
	private ReadFile rf;
	private long totalFrequencyBigram = 0;
	private long totalFrequencyTrigram = 0;
	
	public LoadNgram() {
		unigram = new CountUnigram();
		bigram = new CountBigram();
		trigram = new CountTriGram();
		reverseBigram = new CountBigram();
		reverseTrigram = new CountTriGram();
	}
	public void setType(boolean isTokenized){
		String output = "NLP_RESULT/";
		if(isTokenized){
			output += "tokenized/";
		} else {
			output += "nontokenized/";
		}
		outputUnigram = output + "unigram.txt";
		outputBigram = output + "bigram.txt";
		outputTrigram = output + "trigram.txt";
	}
	public CountUnigram loadUnigram(){
		rf = new ReadFile();
		rf.open(outputUnigram);
		ArrayList<String> list = rf.read();
		rf.close();
		Hashtable<String, Integer> uniHashTable = new Hashtable<>();
		int i = BEGIN;
		int sizeOfList = list.size();
		int average = Integer.parseInt(list.get(1));
		int numberOfUnigram = 0;
		while(i < sizeOfList){
			String[] uni = list.get(i).split(" ");
			if(Integer.parseInt(uni[1]) < 4 * average){
				uniHashTable.put(uni[0], Integer.parseInt(uni[1]));
				numberOfUnigram ++;
			}
			
			i++;
		}
		unigram.setN(numberOfUnigram);
		unigram.reload(uniHashTable);
		return unigram;
	}
	
	public CountBigram loadBigram(){
		rf = new ReadFile();
		rf.open(outputBigram);
		ArrayList<String> list = rf.read();
		rf.close();
		
		Hashtable<String, Hashtable<String, Integer>> biHashTable = new Hashtable<>();
		Hashtable<String, Hashtable<String, Integer>> reverseBiHashTable = new Hashtable<>();
		int i = BEGIN;
		int sizOfList = list.size();
		int numberOfBigram = 0;
		while(i < sizOfList){
			String[] bi = list.get(i).split(" ");
			
			if(biHashTable.get(bi[0]) == null){
				Hashtable<String, Integer> second = new Hashtable<>();
				second.put(bi[1], Integer.parseInt(bi[2]));
				biHashTable.put(bi[0], second);
			} else {
				biHashTable.get(bi[0]).put(bi[1], Integer.parseInt(bi[2]));
			}
			numberOfBigram ++;
			totalFrequencyBigram += Integer.parseInt(bi[2]);
			if(reverseBiHashTable.get(bi[1]) == null){
				Hashtable<String, Integer> second = new Hashtable<>();
				second.put(bi[0], Integer.parseInt(bi[2]));
				reverseBiHashTable.put(bi[1], second);
			} else {
				reverseBiHashTable.get(bi[1]).put(bi[0], Integer.parseInt(bi[2]));
			}
			
			i++;
		}
		
		reverseBigram.setN(numberOfBigram);
		reverseBigram.setBigram(reverseBiHashTable);
		
		bigram.setN(numberOfBigram);
		bigram.setBigram(biHashTable);
		
		return bigram;
	}
	
	public CountTriGram loadTrigram(){
		rf = new ReadFile();
		rf.open(outputTrigram);
		ArrayList<String> list = rf.read();
		rf.close();
		
		Hashtable<String, Hashtable<String, Hashtable<String, Integer>>> triHashTable = new Hashtable<>();
		Hashtable<String, Hashtable<String, Hashtable<String, Integer>>> reverseTriHashTable = new Hashtable<>();
		int i = BEGIN;
		int sizOfList = list.size();
		int numberOfBigram = 0;
		while(i < sizOfList){
			String[] tri = list.get(i).split(" ");
			
			if(triHashTable.get(tri[0]) == null){
				Hashtable<String, Integer> third = new Hashtable<>();
				third.put(tri[2], Integer.parseInt(tri[3]));
				Hashtable<String, Hashtable<String, Integer>> second = new Hashtable<>();
				second.put(tri[1], third);
				triHashTable.put(tri[0], second);
			} else if(triHashTable.get(tri[0]).get(tri[1]) == null){
				Hashtable<String, Integer> third = new Hashtable<>();
				third.put(tri[2], Integer.parseInt(tri[3]));
				triHashTable.get(tri[0]).put(tri[1], third);
			} else {
				triHashTable.get(tri[0]).get(tri[1]).put(tri[2], Integer.parseInt(tri[3]));
			}
			numberOfBigram ++;
			totalFrequencyTrigram += Integer.parseInt(tri[3]);
			if(reverseTriHashTable.get(tri[1]) == null){
				Hashtable<String, Integer> third = new Hashtable<>();
				third.put(tri[0], Integer.parseInt(tri[3]));
				Hashtable<String, Hashtable<String, Integer>> second = new Hashtable<>();
				second.put(tri[2], third);
				reverseTriHashTable.put(tri[1], second);
			} else if(reverseTriHashTable.get(tri[1]).get(tri[2]) == null){
				Hashtable<String, Integer> third = new Hashtable<>();
				third.put(tri[0], Integer.parseInt(tri[3]));
				reverseTriHashTable.get(tri[1]).put(tri[2], third);
			} else {
				reverseTriHashTable.get(tri[1]).get(tri[2]).put(tri[0], Integer.parseInt(tri[3]));
			}
			
			
			i++;
		}
		
		reverseTrigram.setN(numberOfBigram);
		reverseTrigram.setTrigram(reverseTriHashTable);
		
		trigram.setN(numberOfBigram);
		trigram.setTrigram(triHashTable);
		return trigram;
	}
	public CountBigram getReverseBigram(){
		return reverseBigram;
	}
	public CountTriGram getReverseTrigram(){
		return reverseTrigram;
	}
	
	public long getTotalFrequencyBigram(){
		return totalFrequencyBigram;
	}
	public long getTotalFrequencyTrigram(){
		return totalFrequencyTrigram;
	}
	public static void main(String[] args) {
		LoadNgram ngram = new LoadNgram();
		ngram.setType(true);
		ngram.loadTrigram();
		System.out.println(ngram.getReverseTrigram().getTrigramInString());
	}
}
