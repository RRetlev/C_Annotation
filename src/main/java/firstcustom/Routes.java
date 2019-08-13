package firstcustom;

public class Routes {
    @Webroute(path ="/test")
    public static String test1(){
        return"a";
    }

    @Webroute(path = "/nottest")
    public static String test2(){
        return"b";
    }
}
