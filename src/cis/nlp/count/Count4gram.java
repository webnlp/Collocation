package cis.nlp.count;

import java.util.Enumeration;
import java.util.Hashtable;

import cis.nlp.file.Document;
import cis.nlp.file.SuperData;
import cis.nlp.io.WriteFile;

public class Count4gram {
	private static final Integer COUNTCUTOFF = 5;
	private Hashtable<String, Hashtable<String, SuperData>> fourgram;
	public Hashtable<String, Hashtable<String, SuperData>> get4gram() {
		return fourgram;
	}
	public void set4gram(Hashtable<String, Hashtable<String, SuperData>> fourgram) {
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

	public void add(String word1, String word2, Document doc){
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		if(fourgram.get(word1) == null){
			Hashtable<String, SuperData> value = new Hashtable<>();
			value.put(word2, new SuperData(doc.getId(), 1));
			fourgram.put(word1, value);
			n++;
		} else {
			if (fourgram.get(word1).get(word2) == null) {
				fourgram.get(word1).put(word2, new SuperData(doc.getId(), 1));
				n++;
			} else {
				Hashtable<String, SuperData> value = new Hashtable<>();
				value = fourgram.get(word1);
				SuperData sd = value.get(word2);
				sd.setFileNames(doc.getId());
				sd.setNumberOccurence(sd.getNumberOccurence() + 1);
				value.put(word2, sd);
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
				SuperData sd = fourgram.get(temp).get(tempValue);
				Integer val = sd.getNumberOccurence();
				if(val >= COUNTCUTOFF){
					resValue +=temp + " " + tempValue + " " + sd.toString() + "\n";
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
				SuperData sd = fourgram.get(temp).get(tempValue);
				Integer val = sd.getNumberOccurence();
				String onFiles = sd.getFileNames();
				Integer fA = triAsUni.getOneCount().get(temp).getNumberOccurence();
				Integer fB = triAsUni.getOneCount().get(tempValue).getNumberOccurence();
				resValue += temp +" " + tempValue + "," + val +","+ fA + ","+ fB + "," + onFiles + "\n";
			}
			res += resValue;
		}
		write.write(res);
		write.close();
	}
	
	public void loadBigram(Hashtable<String, Hashtable<String, SuperData>> fourgram){
		this.fourgram = fourgram;
	}
}
