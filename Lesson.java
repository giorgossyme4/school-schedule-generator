import java.util.ArrayList;

public class Lesson {

    private int id;
    private String name;
    private int aHour;
    private int bHour;
    private int cHour;
    

    public Lesson(int id, String name, int aHour, int bHour, int cHour) {
        this.id = id;
        this.name = name;
        this.aHour = aHour;
        this.bHour = bHour;
        this.cHour = cHour;
    }

    public void setLessonId(int id){
        this.id = id;
    }

    public int getLessonId(){
        return this.id;
    }

    public void setLessonName(String name){
        this.name = name;
    }

    public String getLessonName(){
        return this.name;
    }

    public void setAHours(int aHour){
        this.aHour = aHour;
    }

    public int getAHours(){
        return this.aHour;
    }

    public void setBHours(int bHour){
        this.bHour = bHour;
    }

    public int getBHours(){
        return this.bHour;
    }

    public void setCHours(int cHour){
        this.cHour = cHour;
    }

    public int getCHours(){
        return this.cHour;
    }

    public void printLesson() {
        System.out.print(getLessonName());
    }

    public void printLesson2(){
        System.out.println("Id: "+ getLessonId() +", Name: "+ getLessonName() + ", A: "+ getAHours() + ", B: " + getBHours() + ", C: " +getCHours());
    }
}