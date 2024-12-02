
public class Person {

    //datafields
    String phone_no;
    String password;
    String email;
    String name;
    String address;


    public Person(String name, String email, String phone_no, String password, String address) {
        this.phone_no = phone_no;
        this.password = password;
        this.email = email;
        this.name = name;
        this.address = address;
    }

    //method to create an account by taking data and store it in a table int the database
    //this method generate a random id for each user and store it to
    public static void createaccount(String name, String email, int password, int phone_no, String address) {
        //generating id , example ---> id = name123
        int random = (int) (Math.random() * 1000);
        String id = name + random;

    }

    //this method checks if the entered email and password are registered or not
    public static void authenticate(String email, int password) {

    }

}
