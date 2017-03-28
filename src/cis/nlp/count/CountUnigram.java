package cis.nlp.count;

import java.util.Enumeration;
import java.util.Hashtable;

import cis.nlp.file.Document;
import cis.nlp.file.SuperData;

public class CountUnigram {
	private static final int COUNTCUTOFF = 5;
	private Hashtable<String, SuperData> oneCount;
	private int n;
	public Hashtable<String, SuperData> getOneCount() {
		return oneCount;
	}
	
	public void setOneCount(Hashtable<String, SuperData> oneCount) {
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

	public void add(String word, Document doc){
		
			word = word.trim().replaceAll("//s+", " ").toLowerCase();
			if(oneCount.get(word) == null){
				oneCount.put(word, new SuperData(doc.getId(), 1));
				n++;
			} else {
				SuperData sd = oneCount.get(word);
				sd.setFileNames(doc.getId());
				sd.setNumberOccurence(sd.getNumberOccurence() + 1);
				oneCount.put(word, sd);
			}
		
	}
	private long sum;
	
	public void reload(Hashtable<String, SuperData> loadUnigram){
		oneCount = loadUnigram;
	}
	public void sumary(){
		Enumeration<String> e = oneCount.keys();
		sum = 0;
		while(e.hasMoreElements()){
			String temp = e.nextElement();
			SuperData sd = oneCount.get(temp);
			int count = sd.getNumberOccurence();
			if(count >= COUNTCUTOFF){
				sum += count;
			} else {
				oneCount.remove(temp);
				n--;
			}
		}
	}
	public long getSum(){
		return sum;
	}
	public void setN(int numberOfUnigram) {
		n = numberOfUnigram;
	}
}
