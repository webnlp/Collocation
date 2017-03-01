package cis.nlp.count;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import cis.nlp.io.WriteFile;


public class CountBigram{

	private static final Integer COUNTCUTOFF = 5;
	private Hashtable<String, Hashtable<String, Integer>> bigram;
	public Hashtable<String, Hashtable<String, Integer>> getBigram() {
		return bigram;
	}
	public void setBigram(Hashtable<String, Hashtable<String, Integer>> bigram) {
		this.bigram = bigram;
	}

	private int n;
	private WriteFile write;
	public CountBigram() {
		bigram = new Hashtable<>();
		n = 0;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public void setHashTableBigram(ArrayList<String> list){
		for (int i = 0; i < list.size() - 1; i++) {
			add(list.get(i), list.get(i+1));
		}
	}
	public void add(String word1, String word2){
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		if(bigram.get(word1) == null){
			Hashtable<String, Integer> value = new Hashtable<>();
			value.put(word2, 1);
			bigram.put(word1, value);
			n++;
		} else {
			if (bigram.get(word1).get(word2) == null) {
				bigram.get(word1).put(word2, 1);
				n++;
			} else {
				Hashtable<String, Integer> value = new Hashtable<>();
				value = bigram.get(word1);
				value.put(word2, value.get(word2) + 1);
				bigram.put(word1, value);
			}
		}
	}
	
	public String getBigramInString(){
		
		String res = "";
		Enumeration<String> e = bigram.keys();
		long sum = 0;
		while(e.hasMoreElements()){
			String temp = e.nextElement();
			Enumeration<String> e2 = bigram.get(temp).keys();
			String resValue = "";
			while(e2.hasMoreElements()){
				String tempValue = e2.nextElement();
				Integer val = bigram.get(temp).get(tempValue);
				if(val >= COUNTCUTOFF){
					resValue +=temp +" " + tempValue + " " + val + "\n";
					sum += val;
				} else {
					bigram.get(temp).remove(tempValue);
					n--;
				}
				
			}
			if(bigram.get(temp).isEmpty()){
				bigram.remove(temp);
			}
			res += resValue;
		}
		
		return n + "\n" + (sum / n) + "\n" + res;
	}
	
	public void writeBigramToFile(CountUnigram one,String fileOutbigram){
		write = new WriteFile();
		write.open(fileOutbigram);
		String res = n+"\n";
		Enumeration<String> e = bigram.keys();
		while(e.hasMoreElements()){
			String temp = e.nextElement();
			Enumeration<String> e2 = bigram.get(temp).keys();
			String resValue = "";
			while(e2.hasMoreElements()){
				String tempValue = e2.nextElement();
				Integer val = bigram.get(temp).get(tempValue);
				Integer fA = one.getOneCount().get(temp);
				Integer fB = one.getOneCount().get(tempValue);
				if(fA == null){
					fA = 0;
				} 
				if(fB == null){
					fB = 0;
				}
				resValue += temp +" " + tempValue + "," + val +","+ fA + ","+ fB +"\n";
			}
			res += resValue;
		}
		write.write(res);
		write.close();
	}
	
	public void loadBigram(Hashtable<String, Hashtable<String, Integer>> loadBigram){
		bigram = loadBigram;
	}
	
}
