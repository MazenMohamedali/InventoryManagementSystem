
public class person {

    // datafields
    private int id;
    private String phone_no;
    private String password;
    private String email;
    private String name;
    private String address;

    person() {
    }

    person(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone_no = phone;
    }

    public person(String name, String email, String phone_no, String password, String address) {
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

    // method to create an account by taking data and store it in a table int the
    // database
    // this method generate a random id for each user and store it to
    public static void createaccount(String name, String email, int password, int phone_no, String address) {
        // generating id , example ---> id = name123
        int random = (int) (Math.random() * 1000);
        String id = name + random;

    }

    // this method checks if the entered email and password are registered or not
    public static void authenticate(String email, int password) {

    }

}
