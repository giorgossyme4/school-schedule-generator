import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter; 
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.io.*;
import java.util.*;

public class MainApp {

	public static ArrayList<Lesson> Lessons = new ArrayList<Lesson>();
	public static ArrayList<Teacher> Teachers = new ArrayList<Teacher>();
	public static ArrayList<Class> Classes = new ArrayList<Class>();
	public static ArrayList<Class> allClasses;

	private static DecimalFormat df2 = new DecimalFormat("#.##");

	public static ArrayList<Lesson> loadLessons(String data) {
		ArrayList<Lesson> lessons = new ArrayList<Lesson>();
		File f = null;
		BufferedReader reader = null;
		String line;
		
		try {
            f = new File(data);
        } catch (NullPointerException e) {
			System.err.println("File not found.");

		} try {
			reader = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file!");	

        } try {
			line = reader.readLine();  
			while(line != null){

				String[] splited = line.split("\\s+");
				int id = Integer.parseInt(splited[0]);
				String name = splited[1];
				int aHours = Integer.parseInt(splited[2]);
				int bHours = Integer.parseInt(splited[3]);
				int cHours = Integer.parseInt(splited[4]);
				Lesson less = new Lesson(id, name, aHours, bHours, cHours);

				for (int i = 1; i <= 3; i++) {
					if (less.getAHours() != 0 ) {
						Class c = new Class("A"+i, less, less.getAHours());
						Classes.add(c);
					}
				}

				for (int j = 1; j <= 3; j++) {
					if (less.getBHours() != 0 ) {
						Class c = new Class("B"+j, less, less.getBHours());
						Classes.add(c);
					}
				}

				for (int m = 1; m <= 3; m++) {
					if (less.getCHours() != 0 ) {
						Class c = new Class("C"+m, less, less.getCHours());
						Classes.add(c);
					}
				}

				lessons.add(less);
				line = reader.readLine();
			}
        } catch (IOException e) {
            System.out.println("Error!!!");
		}
		return lessons;
	}

	public static ArrayList<Teacher> loadTeachers(String data) {
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		File f = null;
		BufferedReader reader = null;
		String line;
		
		try {
            f = new File(data);
        } catch (NullPointerException e) {
			System.err.println("File not found.");

		} try {
			reader = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file!");	

        } try {
			line = reader.readLine();  
			while(line != null){

				String[] splited = line.split("\\s+");
				int id = Integer.parseInt(splited[0]);
				String name = splited[1];
				ArrayList<Integer> lessonsId = new ArrayList<Integer>();
				ArrayList<Lesson> teachrless = new ArrayList<Lesson>();
				for (int i = 2; i < splited.length - 2; i++) {
					lessonsId.add(Integer.parseInt(splited[i]));
					
				}
				int maxDay = Integer.parseInt(splited[splited.length - 2]);
				int maxWeek = Integer.parseInt(splited[splited.length - 1]);
				Teacher teachr = new Teacher(id, name, maxDay, maxWeek);
				teachr.setTeacherLessonsId(lessonsId);
				teachers.add(teachr);
				

				for (int k = 0; k < lessonsId.size(); k++) {
					for (int l = 0; l < Lessons.size(); l++) {
						if (lessonsId.get(k) == Lessons.get(l).getLessonId()) {
							teachrless.add(Lessons.get(l));
						}
					}
				}
				teachr.setTeacherLessons(teachrless);

				line = reader.readLine();
			}
        } catch (IOException e) {
            System.out.println("Error!!!");
		}
		return teachers;
	}

	public static void createFile(String data, Schedule sh) {

		File f = null;
		BufferedWriter writer = null;

		try	{
			f = new File(data);
		}
		catch (NullPointerException e) {
			System.out.println ("Can't create file");
		}

		try	{
			writer = new BufferedWriter(new FileWriter(f));

		} catch (IOException e){
			System.out.println("Can't write to file");

		} try {

			writer.write("---------------------------------------------------------------------------------------------------------------------------------------------"+
			"-----------------------------------------------------------------------------------------------------------------------------------------------------"+
			"---------------------------\n");
			writer.write(String.format("%1$-26s", "| Teachers"));
			writer.write(" ||");
			writer.write(String.format("%1$-54s", " Monday"));
			writer.write(" | |");
			writer.write(String.format("%1$-54s", " Tuesday"));
			writer.write(" | |");
			writer.write(String.format("%1$-54s", " Wednesday"));
			writer.write(" | |");
			writer.write(String.format("%1$-54s", " Thursday"));
			writer.write(" | |");
			writer.write(String.format("%1$-54s", " Friday"));
			writer.write(" |\n");
			writer.write("---------------------------------------------------------------------------------------------------------------------------------------------"+
			"-----------------------------------------------------------------------------------------------------------------------------------------------------"+
			"---------------------------\n");
			writer.write(String.format("%1$-27s", "| "+Teachers.get(0).getTeacherName()));
			writer.write("|");
			for (int i = 0; i < sh.getGenes().length; i++) {        
				for (int j = 0; j < sh.getGenes()[i].length; j++) {
					if ((j % 7) == 0) {
						writer.write("| ");
					}
					if (sh.getGenes()[i][j] == null ) {
						writer.write("-- -- | ");
					} else {
						if (sh.getGenes()[i][j].getClassLesson().getLessonId() < 10) {
							writer.write("0"+sh.getGenes()[i][j].getClassLesson().getLessonId() +" "+ sh.getGenes()[i][j].getClassName() +" | ");
						} else {
							writer.write(sh.getGenes()[i][j].getClassLesson().getLessonId() +" "+ sh.getGenes()[i][j].getClassName() +" | ");
						}
					}
				}
				writer.write("\n");
				if (i != sh.getGenes().length -1) {
					writer.write(String.format("%1$-27s", "| "+Teachers.get(i+1).getTeacherName()));
					writer.write("|");
				}
			}
			writer.write("---------------------------------------------------------------------------------------------------------------------------------------------"+
			"-----------------------------------------------------------------------------------------------------------------------------------------------------"+
			"---------------------------\n");
								
		}catch (IOException e){
				System.err.println("Write error!");

		} try {
			writer.write("\n");
			writer.close();
		}
		catch (IOException e) {
			System.err.println("Error closing file.");
		}
	}
	
	public static void main(String args[]) {

		long startTime = System.nanoTime();
		String l = "lessons.txt";
		String t = "teachers.txt";

		Lessons = loadLessons(l);
		Teachers = loadTeachers(t);

		Class c = new Class();
		allClasses = c.fullClasses(Classes);
		
		for(int i = 0; i < allClasses.size(); i++) {
			ArrayList<Teacher> ts = new ArrayList<Teacher>();
			for(int j = 0; j < Teachers.size(); j ++) {
				for (int m = 0; m < Teachers.get(j).getTeacherLessons().size() ; m++) {
					if (allClasses.get(i).getClassLesson() == ((Teachers.get(j)).getTeacherLessons()).get(m)) {
						ts.add(Teachers.get(j));
					}
				}
			}
			allClasses.get(i).setTeachers(ts);
		}

		Schedule s = new Schedule();
		Genetic g = new Genetic();
		s = g.geneticAlgorithm(11, 0.09, 26, 20000);

		System.out.println();
		s.calculateFitness(true);

		createFile("Schedule.txt", s);
		
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		double seconds = (double)totalTime / 1_000_000_000.0;
		System.out.println();
		System.out.println("Run time: " + df2.format(seconds) + "s"); 
	}
}