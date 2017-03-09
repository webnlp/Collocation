package cis.nlp.count;

import java.util.Enumeration;
import java.util.Hashtable;

import cis.nlp.io.WriteFile;

public class Count4gram {
	private static final Integer COUNTCUTOFF = 5;
	private Hashtable<String, Hashtable<String, Integer>> fourgram;
	public Hashtable<String, Hashtable<String, Integer>> get4gram() {
		return fourgram;
	}
	public void set4gram(Hashtable<String, Hashtable<String, Integer>> fourgram) {
		this.fourgram = fourgram;
	}

	private int n;
	private WriteFile write;
	public Count4gram() {
		fourgram = new Hashtable<>();
		n = 0;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}

	public void add(String word1, String word2){
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		if(fourgram.get(word1) == null){
			Hashtable<String, Integer> value = new Hashtable<>();
			value.put(word2, 1);
			fourgram.put(word1, value);
			n++;
		} else {
			if (fourgram.get(word1).get(word2) == null) {
				fourgram.get(word1).put(word2, 1);
				n++;
			} else {
				Hashtable<String, Integer> value = new Hashtable<>();
				value = fourgram.get(word1);
				value.put(word2, value.get(word2) + 1);
				fourgram.put(word1, value);
			}
		}
	}
	
	public String get4gramInString(){
		
		String res = "";
		Enumeration<String> e = fourgram.keys();
		long sum = 0;
		while(e.hasMoreElements()){
			String temp = e.nextElement();
			Enumeration<String> e2 = fourgram.get(temp).keys();
			String resValue = "";
			while(e2.hasMoreElements()){
				String tempValue = e2.nextElement();
				Integer val = fourgram.get(temp).get(tempValue);
				if(val >= COUNTCUTOFF){
					resValue +=temp +" " + tempValue + " " + val + "\n";
					sum += val;
				} else {
					fourgram.get(temp).remove(tempValue);
					n--;
				}
				
			}
			if(fourgram.get(temp).isEmpty()){
				fourgram.remove(temp);
			}
			res += resValue;
		}
		
		return n + "\n" + (sum / n) + "\n" + res;
	}
	
	public void write4gramToFile(CountUnigram triAsUni,String fileOut4gram){
		write = new WriteFile();
		write.open(fileOut4gram);
		String res = n+"\n";
		Enumeration<String> e = fourgram.keys();
		while(e.hasMoreElements()){
			String temp = e.nextElement();
			Enumeration<String> e2 = fourgram.get(temp).keys();
			String resValue = "";
			while(e2.hasMoreElements()){
				String tempValue = e2.nextElement();
				Integer val = fourgram.get(temp).get(tempValue);
				Integer fA = triAsUni.getOneCount().get(temp);
				Integer fB = triAsUni.getOneCount().get(tempValue);
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
	
	public void loadBigram(Hashtable<String, Hashtable<String, Integer>> fourgram){
		this.fourgram = fourgram;
	}
}
