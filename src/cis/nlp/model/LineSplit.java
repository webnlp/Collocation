package cis.nlp.model;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import cis.nlp.io.DirectoryContents;

public class LineSplit {
	private String result = "NLP_RESULT/sentencesplit";
	private int isTokenize;
	public void setTypeCorpus(int isTokenize){
		this.isTokenize = isTokenize;
	}
	public void setOuput(){
		if(isTokenize == 0){
			result = "NLP_RESULT/tokenized-split";
		} else {
			result = "NLP_RESULT/nontokenized-split";
		}
	}
	public void split(ArrayList<String> pathname){
		setOuput();
		ArrayList<String> currentContent = DirectoryContents.getFileTxt(result);
		int i = currentContent.size();
		JOptionPane.showMessageDialog(null, i);
		for (String path : pathname) {
			System.out.println(path);
			cis.nlp.count.LineSplit ls = new cis.nlp.count.LineSplit();
			ls.docFileVaTachCau(path, result + "/" + i + ".txt");
			i++;
		}
	}
	public void process(String fileInput) {
		
		ArrayList<String> listPath = DirectoryContents.getFileTxt(fileInput);
		split(listPath);
		System.out.println("Split ....... Ok");
	}
}
