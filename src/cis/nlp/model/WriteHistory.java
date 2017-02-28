package cis.nlp.model;

import java.io.FileWriter;
import java.io.IOException;

public class WriteHistory {
	private static final String FILENAME = "NLP_RESULT/History/History.txt";

	public void writeHistory(String history) throws IOException{
		FileWriter writer = new FileWriter(FILENAME, true);
		writer.write(history + "\n");
		writer.close();
	}
}
