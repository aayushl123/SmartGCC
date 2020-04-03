package Backend;

public class UserClass {
    private final String[] users = {"Novice", "Typical", "Expert"};
    private static String userType;

    public UserClass(String userType){
        this.userType = userType;
    }

    public static String getUserType() {
        return userType;
    }

}
