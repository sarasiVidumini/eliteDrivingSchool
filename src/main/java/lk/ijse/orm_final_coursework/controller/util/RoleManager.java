package lk.ijse.orm_final_coursework.controller.util;

public class RoleManager {
    private static String currentUserRole;

    public static void setUserRole(String role) {
        currentUserRole = role;
    }

    public static String getUserRole() {
        return currentUserRole;
    }

    public static void clear() {
        currentUserRole = null;
    }
}
