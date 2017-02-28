package cis.nlp.count;


public class CheckWord {
	private Syllables syllables;
	public CheckWord() {
		syllables = new Syllables();
		syllables.readSyllable();
	}
	public boolean isUpperCase(String word){
		return word.toUpperCase().equals(word);
	}
	public boolean hasNumber(String word){
		for (int i = 0; i < word.length(); i++) {
			Character c = word.charAt(i);
			int assiicode = c;
			if(assiicode <= 57 && assiicode >48) return true;
		}
		return false;
	}
	public boolean isVietNameseSyllable(String word){
		word = word.toLowerCase();
		if(word.length() != 0){
			String tokens[] = word.split("_");
			for (String token : tokens) {
				if(!syllables.isSyllables(token)) return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean checkWord(String word){
		if(hasNumber(word) || isUpperCase(word)|| !isVietNameseSyllable(word) ){
			return false;
		}
		return true;
	}
	
}
