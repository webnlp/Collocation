package cis.nlp.model;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import cis.nlp.io.DirectoryContents;

public class LineSplit {
	private String target = "/home/zic/Desktop/NLP_RESULT/";
	private int isTokenize;
	public void setTypeCorpus(int isTokenize){
		this.isTokenize = isTokenize;
	}
	public void setOuput(){
		if(isTokenize == 0){
			target += "tokenized-split";
		} else {
			target += "nontokenized-split";
		}
	}
	public void split(ArrayList<String> pathname){
		setOuput();
		ArrayList<String> currentContent = DirectoryContents.getFileTxt(target);
		int i = currentContent.size();
		JOptionPane.showMessageDialog(null, i);
		for (String path : pathname) {
			System.out.println(path);
			cis.nlp.count.LineSplit ls = new cis.nlp.count.LineSplit();
			ls.docFileVaTachCau(path, target + "/" + i + ".txt");
			i++;
		}
	}
	public void process(String fileInput) {
		
		ArrayList<String> listPath = DirectoryContents.getFileTxt(fileInput);
		split(listPath);
		System.out.println("Split ....... Ok");
	}
}
