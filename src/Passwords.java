public class Passwords {
    private static final String AdminUserName = "admin";
    private static final String AdminPassword = "0000";
    private static final String ClientUsername = "client";
    private static final String ClientPass = "0000";


    public static boolean CheckAdmin(String AdminUN, String Adminpassword) {
        return (AdminUserName.equals(AdminUN)) && (AdminPassword.equals(Adminpassword));
    }   
    
    public static boolean CheckClient(String clientUsr, String clientpass) {
        return (ClientUsername.equals(clientUsr)) && (ClientPass.equals(clientpass));
    }   
}
