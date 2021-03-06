package cis.nlp.io;

/**
 * @author Do Quang Dat k59 HUS_CIS
 * Create a BufferReader for read input from file *.csv
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {
	private BufferedReader br = null;

	public ReadFile() {

	}

	/**
	 * Open a Buffer for read input
	 * 
	 * @param pathfile
	 *            : path of input file
	 */
	public void open(String pathfile) {
		try {
			this.br = new BufferedReader(new FileReader(new File(pathfile)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error when read file");
		}
	}

	/**
	 * Close BufferReader
	 */
	public void close() {
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot close ReadFile");
		}
	}

	/**
	 * Read Candidates from input file
	 * 
	 * @return : List of Candidates from file input
	 */
	public ArrayList<String> read() {
		ArrayList<String> lines = new ArrayList<>();
		String aline = "";
		try {
			while ((aline = br.readLine()) != null) {
				aline = aline.trim();
				String quotation = String.valueOf('"');
				aline = aline.replaceAll(quotation, "");
				aline = aline.replaceAll("//s+", " ");
				aline = aline.toLowerCase();
				if (aline.length() != 0) {
					lines.add(aline);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}

	public ArrayList<String> readFirst() {
		ArrayList<String> lines = new ArrayList<>();
		String aline = "";
		try {
			while ((aline = br.readLine()) != null) {
				aline = removeNameInLine(aline);
				aline = aline.trim();
				String quotation = String.valueOf('"');
				aline = aline.replaceAll(quotation, "");
				aline = aline.replaceAll("//s+", " ");
				aline = aline.toLowerCase();
				if (aline.length() != 0) {
					lines.add(aline);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}

	public String removeNameInLine(String line) {
		String[] tokens = line.split(" ");
		for (int i = 0; i < tokens.length; i++) {
			if (isName(tokens[i])) {
				System.out.println(tokens[i]);
				tokens[i] = ".";
			}
		}
		line = "";
		for (String string : tokens) {
			line += string + " ";
		}
		return line;
	}

	public boolean isName(String string) {
		if (!string.contains("_"))
			return false;
		String[] tokens = string.split("_");
		for (String token : tokens) {
			if (token.compareTo("") == 0)
				return false;
			String firstCharacter = token.substring(0, 1);
			if (firstCharacter.compareTo(firstCharacter.toUpperCase()) != 0)
				return false;
		}
		return true;
	}

	public ArrayList<String> readConfig() {
		ArrayList<String> lines = new ArrayList<>();
		String aline = "";
		try {
			while ((aline = br.readLine()) != null) {
				lines.add(aline);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}
}
