package cis.nlp.test;

import java.io.File;

import cis.nlp.count.CheckWord;

public class TestChangeMark {
	public static void main(String[] args) {
		check();
	}
	public static long sizeInMb(String folder){
		File file = new File(folder);
		long size = 0;
		for (File child : file.listFiles()) {
			size += child.length();
		}
		return size / (1024 * 1024);
	}
	public static void check(){
		CheckWord cw = new CheckWord();
		System.out.println(cw.checkWord("sâm"));
	}
}
