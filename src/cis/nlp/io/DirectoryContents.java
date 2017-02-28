package cis.nlp.io;

import java.io.File;
import java.util.ArrayList;

public class DirectoryContents {
	/**
	 *get the whole file from the directory
	 *return: list name of file
	 * */
	public static ArrayList<String> getFileTxt(String currentDirectory) {
		// TODO Auto-generated method stub
		ArrayList<String> txtPath = new ArrayList<String>();
		File directory = new File(currentDirectory);
		File[] allFilesAndDirs = directory.listFiles();
		for (int i = 0; i < allFilesAndDirs.length; i++) {
			String p = allFilesAndDirs[i].getAbsolutePath();
			txtPath.add(p);
		}
		
		return txtPath;
	}
}
