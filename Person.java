package machine.learning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Person implements Comparable<Person>{
	int score;
	int generation;
	List<Character> DNA = new ArrayList<>();;

	public Person(int generation) {
		this.generation = generation;
		this.score = 0;
		Random x = new Random();
		int DNA_length = x.nextInt(100) + 1;
		for (int i = 0; i < DNA_length; i++) {
			int character_val = x.nextInt(95) + 32;
			DNA.add((char) character_val);
		}
	}

	public int get_score() {
		return score;
	}

	public void add_score(int score) {
		this.score = this.score + score;
	}

	public String printDna() {
		String val = "";
		for (int i = 0; i < DNA.size(); i++) {
			val = val + DNA.get(i);
		}
		return val;
	}
	
	@Override   
	public int compareTo(Person person) {
	        if (this.score == person.score)
	            return 0;
	        else
	            return this.score > person.score ? -1 : 1;
	    }
	 
}
