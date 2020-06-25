import java.util.ArrayList;
import java.util.Random;

public class Schedule {
	
	private Class[][] genes;
	private int fitness;
	
	private ArrayList<Teacher> teachers = MainApp.Teachers;
	private ArrayList<Class> classes = MainApp.allClasses;

	public Schedule() {

		this.genes = new Class[teachers.size()][35];  //rows = teachers.size() , columns = 35 = (7 hours * 5 days)
		Random r = new Random();

		for(int i = 0; i < classes.size(); i++) {
			Class c = classes.get(i);
			int[] temp = new int[c.getTeachers().size()];
			for (int j = 0; j < c.getTeachers().size(); j++) {
				temp[j] = (c.getTeachers().get(j)).findIndex(teachers);
			}
			
			int index = r.nextInt(temp.length);
			int row = temp[index];

			int column = r.nextInt(35);
			
			//Add to genes in a (x,y) that it has not another lesson
			while (true) {
				if (this.genes[row][column] == null) {
					this.genes[row][column] = c;
					break;
				} else {
					index = r.nextInt(temp.length);
					row = temp[index];
					column = r.nextInt(35);
				}
			}
		}
		this.fitness = this.calculateFitness(false);
	}

	public Schedule(Class[][] genes) {
		this.genes = genes;
		this.fitness = this.calculateFitness(false);
	}
	
	public boolean cExists(String cl, int column) {
		for (int j = 0; j < this.genes.length; j++) {
			if (this.genes[j][column] != null) {
				if (cl.equals(this.genes[j][column].getClassName())) {
					return true;
				}
			}
		}
		return false;
	}

	public void setTeachers(ArrayList<Teacher> teachers) {
		this.teachers = teachers;
	}

	public ArrayList<Teacher> getTeachers() {
		return this.teachers;
	}

	public void setClasses(ArrayList<Class> classes) {
		this.classes = classes;
	}

	public ArrayList<Class> getClasses() {
		return this.classes;
	}

	public void setGenes(Class[][] genes) {
		this.genes = genes;
	}

	public Class[][] getGenes() {
		return this.genes;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
	
	public int getFitness() {
		return this.fitness;
	}

	public void printSchedule() {
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%1$-26s", "| Teachers");
		System.out.print(" ||");
		System.out.format("%1$-33s", " Monday");
		System.out.print(" | |");
		System.out.format("%1$-33s", " Tuesday");
		System.out.print(" | |");
		System.out.format("%1$-33s", " Wednesday");
		System.out.print(" | |");
		System.out.format("%1$-33s", " Thursday");
		System.out.print(" | |");
		System.out.format("%1$-33s", " Friday");
		System.out.print(" |");
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%1$-27s", "| "+teachers.get(0).getTeacherName());
		System.out.print("|");
		for (int i = 0; i < this.genes.length; i++) {        
			for (int j = 0; j < this.genes[i].length; j++) {
				if ((j % 7) == 0) {
					System.out.print("| ");
				}
				if (this.genes[i][j] == null ) {
					System.out.print("-- | ");
				} else {
					if (this.genes[i][j].getClassLesson().getLessonId() < 10) {
						System.out.print("0"+this.genes[i][j].getClassLesson().getLessonId() + " | ");
					} else {
						System.out.print(this.genes[i][j].getClassLesson().getLessonId() + " | ");
					}
				}
			}
			System.out.println();
			if (i != this.genes.length -1) {
				System.out.format("%1$-27s", "| "+teachers.get(i+1).getTeacherName());
				System.out.print("|");
			}
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%1$-26s", "| Teachers");
		System.out.print(" ||");
		System.out.format("%1$-33s", " Monday");
		System.out.print(" | |");
		System.out.format("%1$-33s", " Tuesday");
		System.out.print(" | |");
		System.out.format("%1$-33s", " Wednesday");
		System.out.print(" | |");
		System.out.format("%1$-33s", " Thursday");
		System.out.print(" | |");
		System.out.format("%1$-33s", " Friday");
		System.out.print(" |");
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%1$-27s", "| "+teachers.get(0).getTeacherName());
		System.out.print("|");
		for (int i = 0; i < this.genes.length; i++) {        
			for (int j = 0; j < this.genes[i].length; j++) {
				if ((j % 7) == 0) {
					System.out.print("| ");
				}
				if (this.genes[i][j] == null ) {
					System.out.print("-- | ");
				} else {
					System.out.print(this.genes[i][j].getClassName() + " | ");
				}
			}
			System.out.println();
			if (i != this.genes.length -1) {
				System.out.format("%1$-27s", "| "+teachers.get(i+1).getTeacherName());
				System.out.print("|");
			}
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}

	
	public int calculateFitness(boolean flag) {
		int conflicts = 0;
		int sumc = 0;
		int sumc2 = 0;
		int sumc3 = 0;
		int sumc4 = 0;
		int sumc5 = 0;
		int sumc6 = 0;
		int sumc7 = 0;
		int sumc8 = 0;
		int total = 0;

		//c
		for (int i = 0; i < this.genes[0].length; i++) { 
			int c = calculateFitColumn(i);
			sumc = sumc + c;
			conflicts = conflicts + c;
		}
		if (flag == true) {
			//System.out.println("Conflicts of c: " +sumc+ " ---> "+ (sumc / 10000) +" (1 conflict = 10000)");
			total += (sumc / 10000);
		}
		
		//c2
		for (int i = 0; i < this.genes[0].length; i = i + 7) {		
			int c2 = twoHoursMax(i);
			sumc2 = sumc2 + c2;
			conflicts = conflicts + c2;
		}
		if (flag == true) {
			//System.out.println("Conflicts of c2: " +sumc2+ " ---> "+ (sumc2 / 1000) +" (1 conflict = 1000)");
			total += (sumc2 / 1000);
		}
		
		//c3
		int empty = (9 * this.genes[0].length) - (this.classes.size());
		empty *= -4000;
		conflicts += empty;
		sumc3 += empty;

		for (int i = 0; i < this.genes[0].length; i++) {		
			int c3 = noEmptyHours(i);
			sumc3 = sumc3 + c3;
			conflicts = conflicts + c3;
		}
		if (flag == true) {
			//System.out.println("Conflicts of c3: " +sumc3+ " ---> "+ (sumc3 / 4000) +" (1 conflict = 4000)");
			total += (sumc3 / 4000);
		}
		
		//c4
		int[] monday = even(0);
		int[] tuesday = even(7);
		int[] wednesday = even(14);
		int[] thursday = even(21);
		int[] friday = even(28);

		for (int i = 0; i < 9; i++) {
			int c4 = evenConflicts(monday, tuesday, wednesday, thursday, friday, i);
			sumc4 = sumc4 + c4;
			conflicts = conflicts + c4;
		}
		if (flag == true) {
			//System.out.println("Conflicts of c4: " +sumc4+ " ---> "+(sumc4 / 100)+ " (1 conflict = 4000)");
			total += (sumc4 / 100);
		}
		
		//c5
		for (int i = 0; i < this.genes[0].length; i = i + 7) {
			int c5 = evenHoursOfSubject(i);
			sumc5 = sumc5 + c5;
			conflicts = conflicts + c5;
		}
		if (flag == true) {
			//System.out.println("Conflicts of c5: " +sumc5+ " ---> "+(sumc5 / 100)+ " (1 conflict = 100)");
			total += (sumc5 / 100);
		}
		
		//c6
		for (int i = 0; i < this.genes.length; i++) {
			int c6 = evenTeachingHours(i);
			sumc6 = sumc6 + c6;
			conflicts = conflicts + c6;
		}
		if (flag == true) {
			//System.out.println("Conflicts of c6: " +sumc6+ " ---> "+(sumc6 / 4000)+ " (1 conflict = 100)");
			total += (sumc6 / 4000);
		}

		//c7
		for (int i = 0; i < this.genes.length; i++) {
			int c7 = maxDayHours(i);
			sumc7 = sumc7 + c7;
			conflicts = conflicts + c7;
		}
		if (flag == true) {
			//System.out.println("Conflicts of c7: " +sumc7+" ---> "+(sumc7 / 100)+ " (1 conflict = 100)");
			total += (sumc7 / 100);
		}

		//c8
		for (int i = 0; i < this.genes.length; i++) {
			int c8 = maxWeekHours(i);
			sumc8 = sumc8 + c8;
			conflicts = conflicts + c8;
		}
		if (flag == true) {
			//System.out.println("Conflicts of c8: " +sumc8+" ---> "+(sumc8 / 100)+ " (1 conflict = 100)");
			total += (sumc8 / 100);
			System.out.println("Total Conflicts: " +total);
		}
		
		return conflicts;

	}

	//5th limitation
	public int evenTeachingHours(int row) {
		int conflicts = 0;
		int hours = 0;
		
		int[] teacherWeekHours = new int[this.genes.length];
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		ArrayList<Teacher> samet = new ArrayList<Teacher>();		//samet = same teachers

		for (int i = 0; i < this.genes.length; i++) {
			int counter = 0;
			for (int j = 0; j < this.genes[0].length; j++) {
				if (this.genes[i][j] != null) {
					counter++;
				}
			}
			teacherWeekHours[i] = counter;
		}

		int size = 0;
		for (int i = 0; i < teachers.size(); i++) {
			if (teachers.get(row).tLessons.size() < teachers.get(i).tLessons.size()) {
				size = teachers.get(row).tLessons.size();
			} else {
				size = teachers.get(i).tLessons.size();
			}
			for (int j = 0; j < size; j++) {
				if ((teachers.get(row).tLessons).get(j) == (teachers.get(i).tLessons).get(j)) {
					hours = hours + teacherWeekHours[i];
					samet.add(teachers.get(i));
					indexes.add(i);
					break;
				}
			}
		}
		int even = hours / samet.size();
		int ts = teacherWeekHours[row];
		if ((ts < (even - 1)) || (ts > (even + 1))) {
			conflicts += 100;
		}
		
		return conflicts;
	}

	//4th limitation
	public int evenHoursOfSubject(int day) {
		int conflicts = 0;

		for (int k = 0; k < this.genes.length; k++) {
			for (int i = 0; i < 7; i++) {
				if (this.genes[k][day +i] != null) {
					for (int j = i + 1; j < 7; j++) {
						if (this.genes[k][day +j] != null) {

							if (this.genes[k][day + i].getClassLesson().getLessonId() == this.genes[k][day +j].getClassLesson().getLessonId() && this.genes[k][day + i].getClassName().equals(genes[k][day + j].getClassName())) {
								conflicts += 100;
							}
						}
					}
				}
			}
		}

		return conflicts;
	}

	//3rd limitation
	public int evenConflicts(int[] mon, int[] tue, int[] wed, int[] thur, int[] fri, int i) {
		int conflicts = 0;

		int average = (mon[i] + tue[i] + wed[i] + thur[i] + fri[i]) / 5;
		
		if ((mon[i] > (average + 1)) || (mon[i] < (average))) {
			conflicts += 4000;
		}
		if ((tue[i] > (average + 1)) || (tue[i] < (average))) {
			conflicts += 4000;
		}
		if ((wed[i] > (average + 1)) || (wed[i] < (average))) {
			conflicts += 4000;
		}
		if ((thur[i] > (average + 1)) || (thur[i] < (average))) {
			conflicts += 4000;
		}
		if ((fri[i] > (average + 1)) || (fri[i] < (average))) {
			conflicts += 4000;
		}
		return conflicts;
	}

	//(3rd limitation)
	public int[] even(int column) {
		int[] array = new int[9];
		for (int i = column; i < column + 7; i++) {
			for (int t = 1; t <= 3; t++) {
				for (int j = 0; j < this.genes.length; j++) {
					if (this.genes[j][i] != null) {
						if (("A"+t).equals(this.genes[j][i].getClassName())) {
							array[t - 1] = array[t - 1] + 1;
						}
						if (("B"+t).equals(this.genes[j][i].getClassName())) {
							array[(t - 1)+ 3] = array[(t - 1)+ 3] + 1;
						}
						if (("C"+t).equals(this.genes[j][i].getClassName())) {
							array[(t - 1)+ 6] = array[(t - 1)+ 6] + 1;
						}
					}
				}
			}
		}
		return array;
	}

	//1st limitation
	public int noEmptyHours(int column) {
		int conflicts = 36000;	//worst case score on every column if there is no class on it

		for (int t = 1; t <= 3; t++) {
			int counterA = 0;
			int counterB = 0;
			int counterC = 0;
			for (int j = 0; j < this.genes.length; j++) {
				if (this.genes[j][column] != null) {
					if (("A"+t).equals(this.genes[j][column].getClassName())) {
						if (counterA == 0) {
							conflicts -= 4000;
							counterA++;
						}
					}
					if (("B"+t).equals(this.genes[j][column].getClassName())) {
						if (counterB == 0) {
							conflicts -= 4000;
							counterB++;
						}
					}
					if (("C"+t).equals(this.genes[j][column].getClassName())) {
						if (counterC == 0) {
							conflicts -= 4000;
							counterC++;
						}
					}
				}
			}
		}
		return conflicts;
	}

	//2nd limitation
	public int twoHoursMax(int column) {
		int conflicts = 0;
		int counter = 0;

		for (int i = 0; i < this.genes.length; i++) {        
			for (int j = column; j < column + 7; j++) {
				if (this.genes[i][j] != null) {
					counter++;
					if (counter > 2) {
						conflicts += 1000;
					}
				} else {
					counter = 0;
				}
			}
			counter = 0;
		}
		return conflicts;
	}

	/*our limitation
	counts how much times every class is included on every column
	more than one teachers can't teach at the same class at the same time*/
	public int calculateFitColumn(int column) {
		int conflicts = 0;

		for (int t = 1; t <= 3; t++) {
			int counterA = 0;
			int counterB = 0;
			int counterC = 0;
			for (int j = 0; j < this.genes.length; j++) {
				if (this.genes[j][column] != null) {
					if (("A"+t).equals(this.genes[j][column].getClassName())) {
						counterA++;
						if (counterA > 1) {
							conflicts += 10000;
						}
					}
					if (("B"+t).equals(this.genes[j][column].getClassName())) {
						counterB++;
						if (counterB > 1) {
							conflicts += 10000;
						}
					}
					if (("C"+t).equals(this.genes[j][column].getClassName())) {
						counterC++;
						if (counterC > 1) {
							conflicts += 10000;
						}
					}
				}
			}
		}
		return conflicts;
	}

	/*our limitation
	controls the max hours in every day that every teacher can teach*/
	public int maxDayHours(int row) {
		int conflicts = 0;
		int hours = 0;

		for (int i = 0; i < this.genes[0].length; i += 7) {
			for (int j = i; j < i + 7; j++) {
				if (genes[row][j] != null) {
					hours++;
				}
			}
			if (hours > teachers.get(row).getMaxDay()) {
				conflicts += 100;
			}
			hours = 0;
		}
		return conflicts;
	}

	/*our limitation
	controls the max hours in every week that every teacher can teach*/
	public int maxWeekHours(int row) {
		int conflicts = 0;
		int hours = 0;

		for (int i = 0; i < this.genes[0].length; i++) {
			if (genes[row][i] != null) {
				hours++;
			}
			if (hours > teachers.get(row).getMaxWeek()) {
				conflicts += 100;
			}
		}
		return conflicts;
	}

	public void mutate() {

		Random r = new Random();
		int row = r.nextInt(this.genes.length);
		int column = r.nextInt(this.genes[0].length);
		int column2 = r.nextInt(this.genes[0].length);

		Class temp = genes[row][column];
		Class temp2 = genes[row][column2];
		this.genes[row][column] = temp2;
	 	this.genes[row][column2] = temp;
		
		this.fitness = this.calculateFitness(false);
	}

}