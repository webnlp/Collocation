package cis.nlp.test;

import java.util.ArrayList;

import cis.nlp.calculate.Candidate;
import cis.nlp.model.AnalyzeCandsCount;

public class TestRemoveBigram {
	public static void main(String[] args) {
		AnalyzeCandsCount ac = new AnalyzeCandsCount(true);
		ArrayList<Candidate> bigram = ac.getAnalyzeBigramCount();
		ArrayList<Candidate> fourgram = ac.getAnalyzeFourgramCount();
		ArrayList<Candidate> trigram = ac.getAnalyzeTrigramCount();
		System.out.println(bigram.size());
		System.out.println(trigram.size());
		ac.removeNgramBelongNgram(bigram, trigram);
		ac.removeNgramBelongNgram(trigram, fourgram);
//		System.out.println(bigram.size());
		System.out.println(trigram.size());
//		for (Candidate candidate : bigram) {
//			System.out.println(candidate.toString());
//		}
	}
}
