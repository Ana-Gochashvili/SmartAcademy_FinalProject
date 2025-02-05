package endpoints;

public class Endpoint {
    public static final String Base_URL = "https://petstore.swagger.io/v2/";
    public static final String Store_Post_Order = "store/order";
    public static final String Store_Get_Order = "store/order/{orderId}";
    public static final String Store_Delete_Order = "store/order/{orderId}";

    public static final String User_Post = "user";
    public static final String User_Get = "user/{username}";
    public static final String User_Put = "user/{username}";
    public static final String User_Get_Login = "user/login";
    public static final String User_Get_Logout = "user/logout";
    public static final String User_Delete = "user/{username}";
}
