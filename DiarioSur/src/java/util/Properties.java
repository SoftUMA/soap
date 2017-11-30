package util;

public class Properties {
    // provisional users (pre-userCRUD)
    public static final String USER_SELECTED = "role";
    public static final String    USER_GUEST = "guest";
    public static final String     USER_USER = "afernandez@gmail.com";
    public static final String    USER_SUPER = "mariag@uma.es";
    public static final String   USER_EDITOR = "juanlopez@yahoo.es";

    // database roles notation
    public static final int   ROLE_USER = 0;
    public static final int  ROLE_SUPER = 1;
    public static final int ROLE_EDITOR = 2;

    // opcodes for servlet requests
    public static final int  OP_CREATE = 0;
    public static final int    OP_EDIT = 1;
    public static final int  OP_DELETE = 2;
    public static final int OP_APPROVE = 3;
    public static final int  OP_FILTER = 4;
}
