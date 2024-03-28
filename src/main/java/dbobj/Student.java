package dbobj;

import annotation.Column;
import annotation.Id;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    public Student() {

    }
    public Student(int id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    @Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Column
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Column
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
