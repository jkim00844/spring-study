package hello.login.web;

public class SessionConst {
    public static final String LOGIN_MEMBER = "loginMember";
}


/*
   이건 상수로만 사용하니까
   추상클래스 or interface 로 선언하는 게 좋음.
   public abstract class SessionConst {
       public static final String LOGIN_MEMBER = "loginMember";
   }

    public interface SessionConst {
        String LOGIN_MEMBER = "loginMember";
    }
*/
