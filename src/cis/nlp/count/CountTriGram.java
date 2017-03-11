package cis.nlp.count;

import java.util.Enumeration;
import java.util.Hashtable;

import cis.nlp.file.Document;
import cis.nlp.file.SuperData;
import cis.nlp.io.WriteFile;

public class CountTriGram {
	private static final int COUNTCUTOFF = 5;
	private Hashtable<String, Hashtable<String, Hashtable<String, SuperData>>> trigram;
	private int n;
	private WriteFile write;
	
	public CountTriGram() {
		trigram = new Hashtable<>();
		n = 0;
	}
	public int getNumberTrigrams(){
		return n;
	}
	public void add(String w1, String w2, String w3, Document doc){
		w1 = w1.toLowerCase();
		w2 = w2.toLowerCase();
		w3 = w3.toLowerCase();
		if(trigram.get(w1) == null){
			Hashtable<String, Hashtable<String, SuperData>> value1 = new Hashtable<>();
			Hashtable<String, SuperData> value2 = new Hashtable<>();
			value2.put(w3, new SuperData(doc.getId(), 1));
			value1.put(w2, value2);
			trigram.put(w1, value1);
			n++;
		} else if(trigram.get(w1).get(w2) == null){
			Hashtable<String, SuperData> value2 = new Hashtable<>();
			value2.put(w3, new SuperData(doc.getId(), 1));
			trigram.get(w1).put(w2, value2);
			n++;
		} else if(trigram.get(w1).get(w2).get(w3) == null){
			trigram.get(w1).get(w2).put(w3, new SuperData(doc.getId(), 1));
			n++;
		} else {
			SuperData sd = trigram.get(w1).get(w2).get(w3);
			sd.setFileNames(doc.getId());
			sd.setNumberOccurence(sd.getNumberOccurence() + 1);
			trigram.get(w1).get(w2).put(w3, sd);
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
					SuperData sd = trigram.get(firstWord).get(secondWord).get(thirdWord);
					int count = sd.getNumberOccurence();
					String onFiles = sd.getFileNames();
					if(count >= COUNTCUTOFF){
						res += firstWord + " " + secondWord + " " + thirdWord + " " + count + "," + onFiles + "\n";
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
					SuperData sd = trigram.get(firstWord).get(secondWord).get(thirdWord);
					SuperData sd1 = bigram.getBigram().get(firstWord).get(secondWord);
					SuperData sd2 = bigram.getBigram().get(secondWord).get(thirdWord);
					int fAB = sd1.getNumberOccurence();
					int fBC = sd2.getNumberOccurence();
					
					res += firstWord + " " + secondWord + " " + thirdWord + " " + fAB + " " + fBC
							+ " " + sd.getNumberOccurence() + "," + sd.getFileNames() + "\n";
				}
			}
		}
		write.write(res);
		write.close();
	}
	public void setN(int numberOfBigram) {
		n = numberOfBigram;
	}
	public void setTrigram(Hashtable<String, Hashtable<String, Hashtable<String, SuperData>>> loadTrigram){
		trigram = loadTrigram;
	}
	public Hashtable<String, Hashtable<String, Hashtable<String, SuperData>>> getTrigramCount(){
		return trigram;
	}
}
