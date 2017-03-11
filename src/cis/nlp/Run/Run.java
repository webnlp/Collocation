package cis.nlp.Run;

import cis.nlp.controler.Controler;
import cis.nlp.io.DirectorySavedResult;

public class Run {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Controler control = new Controler();
		DirectorySavedResult.pathToSavedResult();
	}
}
