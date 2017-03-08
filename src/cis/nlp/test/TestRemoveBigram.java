package cis.nlp.test;

import java.util.ArrayList;

import cis.nlp.calculate.Candidate;
import cis.nlp.model.AnalyzeCandsCount;

public class TestRemoveBigram {
	public static void main(String[] args) {
		AnalyzeCandsCount ac = new AnalyzeCandsCount(true);
		ArrayList<Candidate> bigram = ac.getAnalyzeBigramCount();
		System.out.println(bigram.size());
		ArrayList<Candidate> trigram = ac.getAnalyzeTrigramCount();
		ac.removeBigramBelongTrigram(bigram, trigram);
		System.out.println(bigram.size());
		for (Candidate candidate : bigram) {
			System.out.println(candidate.toString());
		}
	}
}
