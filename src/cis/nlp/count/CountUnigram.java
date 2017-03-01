package cis.nlp.count;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import cis.nlp.io.WriteFile;

public class CountUnigram {
	private static final int COUNTCUTOFF = 5;
	private Hashtable<String, Integer> oneCount;
	private int n;
	public Hashtable<String, Integer> getOneCount() {
		return oneCount;
	}
	
	public void setOneCount(Hashtable<String, Integer> oneCount) {
		this.oneCount = oneCount;
		n = 0;
	}
	public int getN(){
		return n;
	}
	public CountUnigram() {
		// TODO Auto-generated constructor stub
		oneCount = new Hashtable<>();
	}

	public void setHashtableOneWord(ArrayList<String> list){
		for (int i = 0; i < list.size(); i++) {
			add(list.get(i));
		}
	}
	

	public void add(String word){
		
			word = word.trim().replaceAll("//s+", " ").toLowerCase();
			if(oneCount.get(word) == null){
				oneCount.put(word, 1);
				n++;
			} else {
				oneCount.put(word, oneCount.get(word) + 1);
			}
		
	}
	
	public String getUnigramInString(){
		String res = "";
		Enumeration<String> e = oneCount.keys();
		long sum = 0;
		while(e.hasMoreElements()){
			String temp = e.nextElement();
			int count = oneCount.get(temp);
			if(count >= COUNTCUTOFF){
				res += temp + " " + count +"\n";
				sum += count;
				if(temp.compareTo("sâm") == 0) System.out.println(count);
			} else {
				oneCount.remove(temp);
				n--;
			}
		}
		return n + "\n"  + (sum / n) + "\n" +res;
	}
	
	public void writeUnigramToFile(String fileOut){
		WriteFile write = new WriteFile();
		write.open(fileOut);
		write.write(getUnigramInString());
		write.close();
	}
	
	public void reload(Hashtable<String, Integer> loadUnigram){
		oneCount = loadUnigram;
	}

	public void setN(int numberOfUnigram) {
		n = numberOfUnigram;
	}
}
