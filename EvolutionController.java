package machine.learning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EvolutionController {
	public static String TARGET = "Hello NCC Group!";
	public static int TARGET_LENGTH = TARGET.length();
	public static int POPULATION_SIZE = 100;
	public static List<Person> POPULATION = new ArrayList<>();
	public static int GENERATION = 0;
	public static int BEST_SCORE = 130;
	public static boolean STOP = false;

	public static void main(String[] args) {
		generate_population();
		
		//TODO: Need to use a while loop here
		while(GENERATION < 100) {
			calculate_fitness();
			order_population();
			cross_over();
			mutation();
		}
	}

	public static void generate_population() {
		for (int i = 0; i < POPULATION_SIZE; i++) {
			POPULATION.add(new Person(GENERATION));
		}
	}

	public static void calculate_fitness() {
		int right_length = 50;
		int correct_character = 5;
		for (int i = 0; i < POPULATION.size(); i++) {
			if (POPULATION.get(i).DNA.size() == TARGET_LENGTH) {
				POPULATION.get(i).add_score(right_length);
			}
			
			for (int j = 0; j < TARGET_LENGTH; j ++) {
				
				if (POPULATION.get(i).DNA.size() > j && TARGET.charAt(j) == POPULATION.get(i).DNA.get(j)) {
					POPULATION.get(i).add_score(correct_character);
				}
			}
			
			System.out.println("SCORE: " + POPULATION.get(i).get_score() + " DNA: " + POPULATION.get(i).printDna());
			
		/*if(POPULATION.get(i).get_score() == BEST_SCORE) {
				System.out.println("-------------------------------------");
				System.out.println("BEST REACHED: " );
				System.out.println(POPULATION.get(i).generation + " " + POPULATION.get(i).score + " " + POPULATION.get(i).printDna());
				System.out.println("-------------------------------------");
				STOP = true;
			}*/
		}
	}

	public static void order_population() {
		POPULATION.sort(Person :: compareTo);
/*		System.out.println("Best in generation: " + GENERATION);
		System.out.println("Gen: " + POPULATION.get(0).generation + " score:" + POPULATION.get(0).score + " DNA: " + POPULATION.get(0).printDna());*/
	}

	public static void cross_over() {
		//List to save the top 50 populatin
		List<Person> selectedPopulation = new ArrayList<>();
		//Where the entire next gen will live
		List<Person> nextGen = new ArrayList<>();
		
		Random x = new Random();
		
		//Keeping track of current generation
		GENERATION ++;
		
		//Taking the top People and adding them to this selected population list
		for (int i = 0; i < POPULATION_SIZE/2; i ++) {
			selectedPopulation.add(POPULATION.get(i));
		}
		
		for (int i = 0; i < selectedPopulation.size(); i++) {
			
			
			int firstPersonSelected = x.nextInt(selectedPopulation.size() - 1);
			int secondPersonSelected = x.nextInt(selectedPopulation.size() - 1); 
			
			Person firstPerson = selectedPopulation.get(firstPersonSelected);
			Person secondPerson = selectedPopulation.get(secondPersonSelected);
			
			//TODO: What if both random numbers are the same????
			
			nextGen.add(firstPerson);
			nextGen.add(secondPerson);
			
			List<Character> firstChildDNA = new ArrayList<>();
			List<Character> secondChildDNA = new ArrayList<>();
			
			for (int j = 0; j < firstPerson.DNA.size(); j++) {
				if(j%2 == 0) {
					
					if(secondPerson.DNA.size() <= j) {
						firstChildDNA.add(firstPerson.DNA.get(j));
						secondChildDNA.add(firstPerson.DNA.get(j));
					} else {
						secondChildDNA.add(secondPerson.DNA.get(j));
						firstChildDNA.add(firstPerson.DNA.get(j));
					}
					
				} else {
					
					if(secondPerson.DNA.size() <= j) {
						firstChildDNA.add(firstPerson.DNA.get(j));
						secondChildDNA.add(firstPerson.DNA.get(j));
					}
					else {
						firstChildDNA.add(secondPerson.DNA.get(j));
						secondChildDNA.add(firstPerson.DNA.get(j));
					}
				}
				
			}
			
			Person firstChild = new Person(GENERATION);
			Person secondChild = new Person(GENERATION);
			
			firstChild.DNA = firstChildDNA;
			secondChild.DNA = secondChildDNA;
			
			nextGen.add(firstChild);
			nextGen.add(secondChild);
		}
		
		POPULATION = nextGen;
	}

	public static void mutation() {
		// Go through each person in population
		// mutation is at a rate of 5%
		// randomly generate a number between 1 and 100
		// If it is 5 or less, select random part of DNA and randomly generate a new
		// character (between 32 and 126)
		Random x = new Random();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			//Shall we mutate?
			if(x.nextInt(100) < 5) {

				int positionToChange = x.nextInt(POPULATION.get(i).DNA.size() -1);
				int character_val = x.nextInt(95) + 32;
				
				POPULATION.get(i).DNA.add(positionToChange, (char) character_val); 
			}
		}
		
	}
}
