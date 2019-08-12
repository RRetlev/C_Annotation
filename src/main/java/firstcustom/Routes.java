package firstcustom;

public class Routes {
    @Webroute(path ="/test")
    public String test1(){
        return"a";
    }

    @Webroute(path = "/nottest")
    public String test2(){
        return"b";
    }
}
