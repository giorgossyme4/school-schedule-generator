import java.util.ArrayList;

public class Teacher {

    private int id;
    private String name;
    private int maxDay;
    private int maxWeek;
    private ArrayList<Integer> lessonsId; 
    public ArrayList<Lesson> tLessons;

    public Teacher(int id, String name, int maxDay, int maxWeek) {
        this.id = id;
        this.name = name;
        this.maxDay = maxDay;
        this.maxWeek = maxWeek;
    }

    public void setTeacherId(int id){
        this.id = id;
    }

    public int getTeacherId(){
        return this.id;
    }

    public void setTeacherName(String name){
        this.name = name;
    }

    public String getTeacherName(){
        return this.name;
    }

    public void setMaxDay(int maxDay){
        this.maxDay = maxDay;
    }

    public int getMaxDay(){
        return this.maxDay;
    }

    public void setMaxWeek(int maxWeek){
        this.maxWeek = maxWeek;
    }

    public int getMaxWeek(){
        return this.maxWeek;
    }

    public void setTeacherLessonsId(ArrayList<Integer> lessonsId){
        this.lessonsId = lessonsId;
    }

    public ArrayList<Integer> getTeacherLessonsId(){
        return this.lessonsId;
    }

    public void setTeacherLessons(ArrayList<Lesson> tLessons){
        this.tLessons = tLessons;
    }

    public ArrayList<Lesson> getTeacherLessons(){
        return this.tLessons;
    }

    public int findIndex(ArrayList<Teacher> ts) {
        int index = -1;
        for (int i = 0; i < ts.size(); i++) {
            if (this == ts.get(i)) {
                index = i;
            }
        }
        return index;
    }
    
    public void printTeacher2() {
        System.out.print("Teacher's: Id "+ getTeacherId() +", Teacher's Name: "+ getTeacherName()+", ");
        for (int i = 0; i < tLessons.size(); i++) {
            System.out.print(tLessons.get(i).getLessonName()+" ");
            
        }
        System.out.print(" MAX DAYS: " +getMaxDay()+ ", MAX WEEKS: "+getMaxWeek());
        System.out.println("");
    }
}