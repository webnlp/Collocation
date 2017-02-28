package cis.nlp.count;

import java.util.ArrayList;
import java.util.Hashtable;

import cis.nlp.io.ReadFile;
import cis.nlp.io.WriteFile;

public class LineSplit {
	private String fileDau = "NLP_DATA/charAndSyllables/Character.txt";
	private Hashtable<String, Integer> dau = new Hashtable<>();
	private ReadFile rf;
	private WriteFile wf;
	private String[] markOld = {"òa", "óa", "ỏa", "õa", "ọa", 
								"òe", "óe", "ỏe", "õe", "ọe",
								"ùy", "úy", "ủy", "ũy", "ụy"};
	private String[] markNew = {"oà", "oá", "oả", "oã", "oạ",
								"oè", "oé", "oẻ", "oẽ", "oẹ",
								"uỳ", "uý", "uỷ", "uỹ", "uỵ"};
	public Hashtable<String, Integer> getDau() {
		return dau;
	}

	public void readFile() { // doc file chua cac dau Tieng Viet,

		rf = new ReadFile();
		rf.open(fileDau);
		ArrayList<String> lines = rf.read();
		for (String string : lines) {
			dau.put(string, 1);
		}

	}
	public String remark(String line){
		for (int i = 0; i < markNew.length; i++) {
			line = line.replaceAll(markOld[i], markNew[i]);
		}
		return line;
	}
	public LineSplit() {
		readFile();
		//System.out.println(dau.size());
	}

	// tra ve 1 ArrayList cac cau trong 1 dong
	public ArrayList<String> tachTrenMotDong(String line) { // line dau vao can
															// duoc fix voi
															// truong hop line
															// == null;
		line = remark(line);
		ArrayList<String> listString = new ArrayList<>();
		line = line.trim();
		line = line.replaceAll("//s+", " "); // dinh dang lai 1 dong
		String element = "";
		String s[] = line.split(" "); // tach
		for (String string : s) {
			if (dau.containsKey(string) == false) { // neu ko phai la dau thi
													// them vao element
				element += string + " ";
			} else { // gap 1 stopWord
				if (element.length() != 0) {
					listString.add(element); // them vao array
				}
				element = "";
			}
		}
		
		if (element.length() != 0) 
			listString.add(element);
		if (listString.size() == 0) { // them khi dong chi chua 1 thanh phan
			listString.add(element);
		}
		return listString;
	}

	public void print(ArrayList<String> list) {
		System.out.println("Co " + list.size() + " thanh phan: ");
		for (String string : list) {
			System.out.println(string);
		}
	}

	public void docFileVaTachCau(String fileNameInput, String fileNameOutPut) { 
		rf = new ReadFile();
		rf.open(fileNameInput);
		ArrayList<String> lines  = rf.read();
		rf.close();
		
		wf = new WriteFile();
		wf.open(fileNameOutPut);
		for (String string : lines) {
			string = string.trim();
			if(string.equals(" "))
				string="";
			if(string.trim().length()!=0&&string.trim().length()!=1){
				ArrayList<String> lineChild = tachTrenMotDong(string.trim());
				
				wf.writeLines(lineChild);
			}
		}
		wf.close();
	}
}
