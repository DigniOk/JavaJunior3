import dbobj.Student;
import model.DBTransit;
import model.JDBC;

public class Main {
    public static void main(String[] args) {

        DBTransit<Student> dbTransit = new DBTransit<>(new JDBC());
        Student student = new Student(1, "Vladislav", "Zabagorov", 23);
        Student student1 = new Student(2, "Nick", "Krapivin", 22);
        Student student2 = new Student(3, "Genadiy", "Bulkin", 21);
        System.out.println(dbTransit.saveOrUpdate(student));
        System.out.println(dbTransit.saveOrUpdate(student1));
        System.out.println(dbTransit.saveOrUpdate(student2));
        System.out.println(dbTransit.simpleSelect());

    }
}
