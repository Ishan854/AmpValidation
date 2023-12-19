package learnTestNgAnnotations;

import org.testng.annotations.*;

public class TC2 {
    @BeforeTest
    void beforeTest(){
        System.out.println("This is before test method");
    }
    @AfterTest
    void afterTest(){
        System.out.println("This is after test method");
    }

    @BeforeMethod
    void beforeMethod(){
        System.out.println("This is before  method");
    }
    @Test
    void test3(){
        System.out.println("This is Test3 Method");
    }
    @Test
    void test4(){
        System.out.println("This is Test4 Method");
    }

    @AfterMethod
    void afterMethod(){
        System.out.println("This is  after  method");
    }
}
