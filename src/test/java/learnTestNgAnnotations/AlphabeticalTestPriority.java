package learnTestNgAnnotations;

import org.testng.annotations.Test;

public class AlphabeticalTestPriority {
    @Test
    void login(){
        System.out.println("Login");
    }
    @Test
    void cLoginTest(){
        System.out.println("Login Successful");
    }

    @Test
    void bRegister(){
        System.out.println("Registered Successful");
    }
}
