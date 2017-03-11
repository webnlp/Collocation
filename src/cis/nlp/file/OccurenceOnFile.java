package cis.nlp.file;

public class OccurenceOnFile {
	private String name = "";
	private int count = 0;
	public OccurenceOnFile(String name, int count) {
		this.name = name;
		this.count = count;
	}
	public OccurenceOnFile(String occurOnFileInString){
		String[] elems = occurOnFileInString.split(":");
		name = elems[0];
		count = Integer.parseInt(elems[1]);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void increaseCount(){
		count ++;
	}
	@Override
	public String toString() {
		return name + ":" + count;
	}
	
}
