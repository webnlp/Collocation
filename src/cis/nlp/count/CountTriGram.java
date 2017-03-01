package cis.nlp.count;

import java.util.Enumeration;
import java.util.Hashtable;

import cis.nlp.io.WriteFile;

public class CountTriGram {
	private static final int COUNTCUTOFF = 5;
	private Hashtable<String, Hashtable<String, Hashtable<String, Integer>>> trigram;
	private int n;
	private WriteFile write;
	
	public CountTriGram() {
		trigram = new Hashtable<>();
		n = 0;
	}
	public int getNumberTrigrams(){
		return n;
	}
	public void add(String w1, String w2, String w3){
		w1 = w1.toLowerCase();
		w2 = w2.toLowerCase();
		w3 = w3.toLowerCase();
		if(trigram.get(w1) == null){
			Hashtable<String, Hashtable<String, Integer>> value1 = new Hashtable<>();
			Hashtable<String, Integer> value2 = new Hashtable<>();
			value2.put(w3, 1);
			value1.put(w2, value2);
			trigram.put(w1, value1);
			n++;
		} else if(trigram.get(w1).get(w2) == null){
			Hashtable<String, Integer> value2 = new Hashtable<>();
			value2.put(w3, 1);
			trigram.get(w1).put(w2, value2);
			n++;
		} else if(trigram.get(w1).get(w2).get(w3) == null){
			trigram.get(w1).get(w2).put(w3, 1);
			n++;
		} else {
			int count = trigram.get(w1).get(w2).get(w3) + 1;
			trigram.get(w1).get(w2).put(w3, count);
		}
		System.out.println(n);
		
	}
	public void setHashtableTrigram(String[] list){
		int lengthList = list.length - 2;
		for(int i = 0; i < lengthList; i++){
			add(list[i], list[i+1], list[i+2]);
		}
	}
	
	public String getTrigramInString(){
		String res ="";
		Enumeration<String> firstKeys = trigram.keys();
		long sum = 0;
		while(firstKeys.hasMoreElements()){
			String firstWord = firstKeys.nextElement();
			Enumeration<String> secondKeys = trigram.get(firstWord).keys();
			while(secondKeys.hasMoreElements()){
				String secondWord = secondKeys.nextElement();
				Enumeration<String> thirdKeys = trigram.get(firstWord).get(secondWord).keys();
				while(thirdKeys.hasMoreElements()){
					String thirdWord = thirdKeys.nextElement();
					int count = trigram.get(firstWord).get(secondWord).get(thirdWord);
					if(count >= COUNTCUTOFF){
						res += firstWord + " " + secondWord + " " + thirdWord + " " + count + "\n";
						sum += count;
					} else {
						trigram.get(firstWord).get(secondWord).remove(thirdWord);
						n--;
					}
				}
				if(trigram.get(firstWord).get(secondWord).isEmpty()){
					trigram.get(firstWord).remove(secondWord);
				}
			}
			if(trigram.get(firstWord).isEmpty()){
				trigram.remove(firstWord);
			}
		}
		return n +"\n" + sum / n + "\n" + res;
	}
	
	public void writeTrigramToFile(String pathfile, CountBigram bigram){
		write = new WriteFile();
		write.open(pathfile);
		String res =n+ "\n";
		Enumeration<String> firstKeys = trigram.keys();
		while(firstKeys.hasMoreElements()){
			String firstWord = firstKeys.nextElement();
			Enumeration<String> secondKeys = trigram.get(firstWord).keys();
			while(secondKeys.hasMoreElements()){
				String secondWord = secondKeys.nextElement();
				Enumeration<String> thirdKeys = trigram.get(firstWord).get(secondWord).keys();
				while(thirdKeys.hasMoreElements()){
					String thirdWord = thirdKeys.nextElement();
					int fAB = bigram.getBigram().get(firstWord).get(secondWord);
					int fBC = bigram.getBigram().get(secondWord).get(thirdWord);
					
					res += firstWord + " " + secondWord + " " + thirdWord + " " + fAB + " " + fBC
							+ " " + trigram.get(firstWord).get(secondWord).get(thirdWord) + "\n";
				}
			}
		}
		write.write(res);
		write.close();
	}
	public void setN(int numberOfBigram) {
		n = numberOfBigram;
	}
	public void setTrigram(Hashtable<String, Hashtable<String, Hashtable<String, Integer>>> loadTrigram){
		trigram = loadTrigram;
	}
	public Hashtable<String, Hashtable<String, Hashtable<String, Integer>>> getTrigramCount(){
		return trigram;
	}
}
