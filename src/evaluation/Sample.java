package evaluation;

import java.util.ArrayList;
import java.util.Hashtable;

import cis.nlp.io.ReadFile;

public class Sample {
	private Hashtable<String, Integer> positive = new Hashtable<>(); //type = 1
	private Hashtable<String, Integer> negative = new Hashtable<>(); //type = 2
	private int sizePositive;
	private ReadFile rf;
	public Sample(String filePositive, String fileNegative) {
		load(filePositive, 1);
		load(fileNegative, 2);
	}
	
	private void load(String file, int type){
		rf = new ReadFile();
		ArrayList<String> lines = rf.read();
		sizePositive = lines.size();
		rf.close();
		
		for (String line : lines) {
			if(type == 1){
				positive.put(line, 1);
			} else {
				negative.put(line, 1);
			}
		}
	}
	
	public int getSizePositive(){
		return sizePositive;
	}
	
	public Hashtable<String, Integer> getSample(int type){
		return (type == 1) ? positive : negative;
	}
	
	public boolean isExist(Collocation collocation){
		return positive.contains(collocation.getName()) || negative.contains(collocation.getName());
	}
	
	public boolean isExistOnPositive(Collocation collocation){
		return positive.contains(collocation.getName());
	}
	
	public boolean isExistOnNegative(Collocation collocation){
		return negative.contains(collocation.getName());
	}
}
