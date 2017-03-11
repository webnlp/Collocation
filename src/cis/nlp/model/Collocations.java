package cis.nlp.model;

import java.util.ArrayList;

import cis.nlp.calculate.HandlingCollocation;
import cis.nlp.io.DirectorySavedResult;
import cis.nlp.io.ReadFile;

public class Collocations {
	private HandlingCollocation collocation;
	private String inputFolder = DirectorySavedResult.getDirectoryToSaveResult() + "cands-nontokenized/";
	private String outputFolder = DirectorySavedResult.getDirectoryToSaveResult() + "collocation-nontokenized/";
	public Collocations(int isTokenized) {
		if(isTokenized == 0){
			inputFolder =  DirectorySavedResult.getDirectoryToSaveResult() + "cands-tokenized/";
			outputFolder =  DirectorySavedResult.getDirectoryToSaveResult() + "collocation-tokenized/";
		}
	}
	
	public void calculate(String txtInput){
		collocation = new HandlingCollocation(inputFolder + txtInput +".txt");
		collocation.restultUnigram("mle", outputFolder + txtInput + "-mle.txt");
		collocation.restultUnigram("pmi", outputFolder + txtInput + "-pmi.txt");
		collocation.restultUnigram("tscore", outputFolder + txtInput +"-tscore.txt");
		collocation.restultUnigram("dice", outputFolder + txtInput +"-dice.txt");
		collocation.restultUnigram("ll", outputFolder + txtInput +"-ll.txt");
	}
	public ArrayList<String> showCollocation(String input){
		ArrayList<String> list = new ArrayList<>();
		ReadFile rf = new ReadFile();
		
		rf.open(outputFolder + input + "-mle.txt");
		ArrayList<String> candsMLE = rf.read();
		
		rf.open(outputFolder + input + "-pmi.txt");
		ArrayList<String> candsPMI = rf.read();
		
		rf.open(outputFolder + input + "-dice.txt");
		ArrayList<String> candsDICE = rf.read();
		
		rf.open(outputFolder + input + "-tscore.txt");
		ArrayList<String> candsTSORE = rf.read();
		
		rf.open(outputFolder + input + "-ll.txt");
		ArrayList<String> candsLL = rf.read();
		
		int size = candsDICE.size();
		for (int i = 0; i < size; i++) {
			String mle = nameCollocation(candsMLE.get(i));
			String pmi = nameCollocation(candsPMI.get(i));
			String dice = nameCollocation(candsDICE.get(i));
			String tsocre = nameCollocation(candsTSORE.get(i));
			String ll = nameCollocation(candsLL.get(i));
			
			list.add(mle + "," + pmi + "," + dice + "," + tsocre + "," + ll);
		}
		
		return list;
	}
	public String nameCollocation(String cand){
		String[] parts = cand.split(",");
		return parts[0];
	}
	public static void main(String[] args) {
		Collocations collocations = new Collocations(0);
		collocations.calculate("cands-bi");
		collocations.calculate("cands-tri");
		System.out.println("Complete!");
	}
}
