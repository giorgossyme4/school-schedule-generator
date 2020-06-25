import java.util.ArrayList;

public class Class {

    private String name;
    private Lesson lesson;
    private int lesHours;
    public ArrayList<Class> full;
    private ArrayList<Teacher> teacher;
	
	public Class(){
        this.name = null;
        this.lesson = null;
        this.lesHours = 1;
    }
	
	public Class(String name, Lesson lesson){
        this.name = name;
        this.lesson = lesson;
        this.lesHours = 1;
    }

    public Class(String name, Lesson lesson, int lesHours){
        this.name = name;
        this.lesson = lesson;
        this.lesHours = lesHours;
    }

    public Class(String name, Lesson lesson, ArrayList<Teacher> teacher){
        this.name = name;
        this.lesson = lesson;
        this.teacher = teacher;
    }

    public void setClassName(String name){
        this.name = name;
    }

    public String getClassName(){
        return this.name;
    }

    public void setClassLesson(Lesson lesson){
        this.lesson = lesson;
    }

    public Lesson getClassLesson(){
        return this.lesson;
    }

    public void setClassHours(int lesHours){
        this.lesHours = lesHours;
    }

    public int getClassHours(){
        return this.lesHours;
    }

    public void setTeachers(ArrayList<Teacher> teacher) {
        this.teacher = teacher;
    }

    public ArrayList<Teacher> getTeachers() {
        return this.teacher;
    }
	
	public ArrayList<Class> fullClasses(ArrayList<Class> c) {
		ArrayList<Class> full = new ArrayList<Class>();
		for (int i = 0; i < c.size(); i++) {
			int mul = c.get(i).getClassHours();
			for (int j = 0; j < mul; j++) {
				full.add(new Class(c.get(i).getClassName(), c.get(i).getClassLesson()));
			}
		}
		return full;
    }
    
    public ArrayList<Class> getFullClasses() {
        return full;
    }

    public void printClass() {
        System.out.print("Class: "+ getClassName() +", Lesson: "+ getClassLesson().getLessonName()+ ", " /*+", Hours every week: "+ getClass_hours()*/);
        for(int i = 0; i < getTeachers().size(); i++) {
            if (i == 0) {
                System.out.print("Teacher: ");
            }
            System.out.print(getTeachers().get(i).getTeacherName()+" ");
        }
        System.out.println("");
    }
    
}