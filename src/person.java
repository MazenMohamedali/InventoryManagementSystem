
<<<<<<< HEAD
public class person {

=======

public abstract class Person {
>>>>>>> 175b6c0936298d442bd454f0ab73a24c0f33bb2b
    // datafields
    private int id;
    private String phone_no;
    private String password;
    private String email;
    private String name;
    private String address;

    Person() {
    }

    Person(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone_no = phone;
    }

    public Person(String name, String email, String phone_no, String password, String address) {
        this.phone_no = phone_no;
        this.password = password;
        this.email = email;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
