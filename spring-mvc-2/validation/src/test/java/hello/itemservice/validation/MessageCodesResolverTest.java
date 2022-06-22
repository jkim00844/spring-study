package hello.itemservice.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodesResolverTest {
    
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
    
    @Test
    void messageCodesResolverObject(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
//        for (String messageCode : messageCodes){
//            System.out.println("messageCode = " + messageCode);
//        }

        // 객체 오류의 경우 다음 순서로 2가지 생성
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName",  String.class);
//        for (String messageCode : messageCodes) {
//            System.out.println("messageCode = " + messageCode);
//        }

        // 필드 오류의 경우 다음 순서로4가지 메시지 코드 생성
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required");

    }
}
