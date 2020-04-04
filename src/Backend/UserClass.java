package Backend;

public class UserClass {
    private static String userType;

    public static void setUserType(String type) {
        userType = type;
    }

    public static String getUserType() {
        return userType;
    }

}
