package util;

public class Properties {
    public static final String URL_CODIFICATION = "UTF-8";

    // Category not selected
    public static final String NIL_CATEGORY = "nil";

    // provisional users (pre-userCRUD)
    public static final String USER_SELECTED = "session-user";
    public static final String USER_GUEST = "Guest";
    public static final String USER_USER = "afernandez@gmail.com";
    public static final String USER_SUPER = "mariag@uma.es";
    public static final String USER_EDITOR = "juanlopez@yahoo.es";

    // database roles notation
    public static final String ROLE_USER = "0";
    public static final String ROLE_SUPER = "1";
    public static final String ROLE_EDITOR = "2";

    // opcodes for servlet requests
    public static final int OP_CREATE = 0;
    public static final int OP_EDIT = 1;
    public static final int OP_DELETE = 2;
    public static final int OP_APPROVE = 3;
    public static final int OP_FILTER = 4;

    // URL params
    public static final String PARAM_OPCODE = "opcode";
    public static final String PARAM_ID = "id";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_DESCRIPTION = "description";
    public static final String PARAM_IMAGE = "image";
    public static final String PARAM_TAG = "tag";
    public static final String PARAM_DATE = "date";
    public static final String PARAM_ADDRESS = "address";
    public static final String PARAM_PRICE = "price";
    public static final String PARAM_SHOPURL = "shopurl";
    public static final String PARAM_CATEGORY = "category";
    public static final String PARAM_KEYWORDS = "keywords";
    public static final String PARAM_FREE = "free";
    public static final String PARAM_OWN = "own";
}
