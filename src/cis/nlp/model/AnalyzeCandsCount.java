package cis.nlp.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import cis.nlp.calculate.Candidate;
import cis.nlp.count.CountBigram;
import cis.nlp.file.SuperData;

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
		
		Hashtable<String, Hashtable<String, SuperData>> bi = bigram.getBigram();
		Enumeration<String> first = bi.keys();
		int end = 0;
		while(first.hasMoreElements()){
			String firstWord = first.nextElement();
			Enumeration<String> second = bi.get(firstWord).keys();
			int numberOfFirstWord = 0;
			int begin = cands.size();
			while(second.hasMoreElements()){
				String secondWord = second.nextElement();
				int fAB = bi.get(firstWord).get(secondWord).getNumberOccurence();
				numberOfFirstWord += fAB;
				Candidate cand = new Candidate();
				cand.setFreAB(fAB);
				cand.setName(firstWord + " " + secondWord);
				cand.setOnFiles(bi.get(firstWord).get(secondWord).getResultSuperData());
				cands.add(cand);
				end ++;
			}
			
			for(int i = begin; i < end; i ++){
				Candidate cand = cands.get(i);
				cand.setFreA(numberOfFirstWord - cand.getFreAB());
			}
		}
		Hashtable<String, Hashtable<String, SuperData>> reverseBigram = load.getReverseBigram().getBigram();
		cands = getFreBofBigram(reverseBigram, cands);
		return cands;
	}
	public ArrayList<Candidate> getFreBofBigram(Hashtable<String, Hashtable<String, SuperData>> reverseBigram, 
			ArrayList<Candidate> cands){
		for (Candidate candidate : cands) {
			String[] tokens = candidate.getName().split(" ");
			Enumeration<String> second = reverseBigram.get(tokens[1]).keys();
			int numberOfFirstToken = 0;
			while (second.hasMoreElements()) {
				String secondWord = second.nextElement();
				numberOfFirstToken += reverseBigram.get(tokens[1]).get(secondWord).getNumberOccurence();
			}
			candidate.setFreB(numberOfFirstToken - reverseBigram.get(tokens[1]).get(tokens[0]).getNumberOccurence());
		}
		return cands;
	}
	public ArrayList<Candidate> getAnalyzeTrigramCount(){
		ArrayList<Candidate> cands = new ArrayList<>();
		Hashtable<String, Hashtable<String, Hashtable<String, SuperData>>> tri = load.loadTrigram().getTrigramCount();
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
					numberOfFirstWord += tri.get(firstWord).get(secondWord).get(thirdWord).getNumberOccurence();
					Candidate cand = new Candidate();
					cand.setFreAB(tri.get(firstWord).get(secondWord).get(thirdWord).getNumberOccurence());
					cand.setName(firstWord + " " + secondWord + " " + thirdWord);
					cand.setOnFiles(tri.get(firstWord).get(secondWord).get(thirdWord).getResultSuperData());
					cands.add(cand);
					end ++;
				}
				for (int i = begin; i < end; i++) {
					int freAB = cands.get(i).getFreAB();
					cands.get(i).setFreA(numberOfFirstWord - freAB);
				}
			}
		}
		Hashtable<String, Hashtable<String, Hashtable<String, SuperData>>> reverseTrigram = load.getReverseTrigram().getTrigramCount();
		cands = getFreBofTrigram(reverseTrigram, cands);
		
		return cands;
	}
	public ArrayList<Candidate> getFreBofTrigram(Hashtable<String, Hashtable<String, Hashtable<String, SuperData>>> reverseTrigram,
			ArrayList<Candidate> cands){
		for (Candidate candidate : cands) {
			String[] tokens = candidate.getName().split(" ");
			Enumeration<String> third = reverseTrigram.get(tokens[1]).get(tokens[2]).keys();
			int numberOfFirstWord = 0;
			while (third.hasMoreElements()) {
				String thirdWord = third.nextElement();
				numberOfFirstWord += reverseTrigram.get(tokens[1]).get(tokens[2]).get(thirdWord).getNumberOccurence();
			}
			candidate.setFreB(numberOfFirstWord - reverseTrigram.get(tokens[1]).get(tokens[2]).get(tokens[0]).getNumberOccurence());
		}
		return cands;
	}
	
	public ArrayList<Candidate> getAnalyzeFourgramCount(){
		ArrayList<Candidate> cands = new ArrayList<>();
		
		Hashtable<String, Hashtable<String, SuperData>> fourgram = load.load4gram().get4gram();
		Enumeration<String> firstKeys = fourgram.keys();
		while(firstKeys.hasMoreElements()){
			String first = firstKeys.nextElement();
			Enumeration<String> secondKeys = fourgram.get(first).keys();
			int end = 0, begin = cands.size();
			int numberOfFirstKey = 0;
			while(secondKeys.hasMoreElements()){
				String second = secondKeys.nextElement();
				SuperData sd = fourgram.get(first).get(second);
				
				Candidate cand = new Candidate();
				cand.setFreAB(sd.getNumberOccurence());
				cand.setOnFiles(sd.getResultSuperData());
				cand.setName(first + " " + second);
				
				cands.add(cand);
				numberOfFirstKey += sd.getNumberOccurence();
				end ++;
			}
			
			for(int i = begin; i < begin + end; i++){
				Candidate cand = cands.get(i);
				cand.setFreA(numberOfFirstKey - cand.getFreAB());
			}
		}
		Hashtable<String, Hashtable<String, SuperData>> reverseFourgram = load.getReverseFourgram().get4gram();
		cands = getFreBofFourgram(cands, reverseFourgram);
		return cands;
	}
	
	public ArrayList<Candidate> getFreBofFourgram(ArrayList<Candidate> cands, Hashtable<String, Hashtable<String, SuperData>> reverseFourgram){
		for (Candidate candidate : cands) {
			String[] elems = candidate.getName().split(" ");
			Enumeration<String> firstKeys = reverseFourgram.get(elems[3]).keys();
			int numberOfSecond = 0;
			while(firstKeys.hasMoreElements()){
				String first = firstKeys.nextElement();
				SuperData sd = reverseFourgram.get(elems[3]).get(first);
				numberOfSecond += sd.getNumberOccurence();
			}
			candidate.setFreB(numberOfSecond - candidate.getFreAB());
		}
		return cands;
	}
	public void removeBigramBelongTrigram(ArrayList<Candidate> bigram, ArrayList<Candidate> trigram){
		Hashtable<String, Integer> hashBiOnTrigram = getHashBigramContainTrigram(trigram);
		ArrayList<Candidate> cp = copyCandidates(bigram);
		for (Candidate candidate : cp) {
			int freOfBi = candidate.getFreAB();
			String name = candidate.getName();
			Integer freBiOnTri = hashBiOnTrigram.get(name);
			if(freBiOnTri != null){
				if(freOfBi - freBiOnTri < 5){
					bigram.remove(candidate);
				}
			}
		}
	}
	
	public Hashtable<String, Integer> getHashBigramContainTrigram(ArrayList<Candidate> trigram){
		Hashtable<String, Integer> hashBiOnTrigram = new Hashtable<>();
		for (Candidate candidate : trigram) {
			String[] syllables = candidate.getName().split(" ");
			String b1 = syllables[0] + " " + syllables[1];
			String b2 = syllables[1] + " " + syllables[2];
			hashBiOnTrigram.put(b1, candidate.getFreAB());
			hashBiOnTrigram.put(b2, candidate.getFreAB());
		}
		return hashBiOnTrigram;
	}
	
	public ArrayList<Candidate> copyCandidates(ArrayList<Candidate> cands){
		ArrayList<Candidate> cp = new ArrayList<>();
		for (Candidate candidate : cands) {
			cp.add(candidate);
		}
		return cp;
	}
}
