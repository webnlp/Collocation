package cis.nlp.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import cis.nlp.calculate.Candidate;
import cis.nlp.count.CountBigram;

public class AnalyzeCandsCount {
	private LoadNgram load;
	public AnalyzeCandsCount(boolean isTokenized) {
		load = new LoadNgram();
		load.setType(isTokenized);
	}
	public LoadNgram getLoadNgram(){
		return load;
	}
	public ArrayList<Candidate> getAnalyzeBigramCount(){
		ArrayList<Candidate> cands = new ArrayList<>();
		CountBigram bigram = load.loadBigram();
//		wf = new WriteFile();
//		wf.open("NLP_RESULT/AnalyzeCandsCount/BigramCount");
		Hashtable<String, Hashtable<String, Integer>> bi = bigram.getBigram();
		Enumeration<String> first = bi.keys();
		int end = 0;
		while(first.hasMoreElements()){
			String firstWord = first.nextElement();
			Enumeration<String> second = bi.get(firstWord).keys();
			int numberOfFirstWord = 0;
			int begin = cands.size();
			while(second.hasMoreElements()){
				String secondWord = second.nextElement();
				numberOfFirstWord += bi.get(firstWord).get(secondWord);
				int fAB = bi.get(firstWord).get(secondWord);
				Candidate cand = new Candidate();
				cand.setFreAB(fAB);
				cand.setName(firstWord + " " + secondWord);
				cands.add(cand);
				end ++;
			}
			
			for(int i = begin; i < end; i ++){
				Candidate cand = cands.get(i);
				cand.setFreA(numberOfFirstWord - cand.getFreAB());
			}
		}
		Hashtable<String, Hashtable<String, Integer>> reverseBigram = load.getReverseBigram().getBigram();
		cands = getFreBofBigram(reverseBigram, cands);
		return cands;
	}
	public ArrayList<Candidate> getFreBofBigram(Hashtable<String, Hashtable<String, Integer>> reverseBigram, 
			ArrayList<Candidate> cands){
		for (Candidate candidate : cands) {
			String[] tokens = candidate.getName().split(" ");
			Enumeration<String> second = reverseBigram.get(tokens[1]).keys();
			int numberOfFirstToken = 0;
			while (second.hasMoreElements()) {
				String secondWord = second.nextElement();
				numberOfFirstToken += reverseBigram.get(tokens[1]).get(secondWord);
			}
			candidate.setFreB(numberOfFirstToken - reverseBigram.get(tokens[1]).get(tokens[0]));
		}
		return cands;
	}
	public ArrayList<Candidate> getAnalyzeTrigramCount(){
		ArrayList<Candidate> cands = new ArrayList<>();
		Hashtable<String, Hashtable<String, Hashtable<String, Integer>>> tri = load.loadTrigram().getTrigramCount();
		int end = 0;
		int numberOfFirstWord = 0;
		Enumeration<String> first = tri.keys();
		while (first.hasMoreElements()) {
			String firstWord = first.nextElement();
			Enumeration<String> second = tri.get(firstWord).keys();
			while (second.hasMoreElements()) {
				String secondWord = second.nextElement();
				Enumeration<String> third = tri.get(firstWord).get(secondWord).keys();
				int begin = cands.size();
				numberOfFirstWord = 0;
				while(third.hasMoreElements()){
					String thirdWord = third.nextElement();
					numberOfFirstWord += tri.get(firstWord).get(secondWord).get(thirdWord);
					Candidate cand = new Candidate();
					cand.setFreAB(tri.get(firstWord).get(secondWord).get(thirdWord));
					cand.setName(firstWord + " " + secondWord + " " + thirdWord);
					cands.add(cand);
					end ++;
				}
				for (int i = begin; i < end; i++) {
					int freAB = cands.get(i).getFreAB();
					cands.get(i).setFreA(numberOfFirstWord - freAB);
				}
			}
		}
		Hashtable<String, Hashtable<String, Hashtable<String, Integer>>> reverseTrigram = load.getReverseTrigram().getTrigramCount();
		cands = getFreBofTrigram(reverseTrigram, cands);
		
		return cands;
	}
	public ArrayList<Candidate> getFreBofTrigram(Hashtable<String, Hashtable<String, Hashtable<String, Integer>>> reverseTrigram,
			ArrayList<Candidate> cands){
		for (Candidate candidate : cands) {
			String[] tokens = candidate.getName().split(" ");
			Enumeration<String> third = reverseTrigram.get(tokens[1]).get(tokens[2]).keys();
			int numberOfFirstWord = 0;
			while (third.hasMoreElements()) {
				String thirdWord = third.nextElement();
				numberOfFirstWord += reverseTrigram.get(tokens[1]).get(tokens[2]).get(thirdWord);
//				System.out.println(tokens[1] + " " + tokens[2] + " " + tokens[0] + 
//						reverseTrigram.get(tokens[1]).get(tokens[2]).get(thirdWord));
//				System.out.println(firstWord);
			}
			candidate.setFreB(numberOfFirstWord - reverseTrigram.get(tokens[1]).get(tokens[2]).get(tokens[0]));
		}
		return cands;
	}
	
//	public static void main(String[] args) {
//		AnalyzeCandsCount analyzeCandsCount = new AnalyzeCandsCount(true);
//		ArrayList<Candidate> cands = analyzeCandsCount.getAnalyzeBigramCount();
//		System.out.println(analyzeCandsCount.getLoadNgram().getTotalFrequencyBigram());
//		ArrayList<Candidate> cands = analyzeCandsCount.getAnalyzeTrigramCount();
//		System.out.println(cands.get(0).toString());
//	}
}
