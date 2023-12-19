package learnTestNgAnnotations;

import org.testng.annotations.*;

public class TC1 {
    @BeforeSuite
    void beforeSuite(){
        System.out.println("This is before Suite");
    }
    @AfterSuite
    void afterSuite(){
        System.out.println("This is after Suite");

    }
    @BeforeClass
    void beforeClass(){
        System.out.println("This is before class");
    }
    @AfterClass
    void afterClass(){
        System.out.println("This is after class");

    }
    @BeforeMethod
    void beforeMethod(){
        System.out.println("This is before test method");
    }
    @Test
    void test1(){
        System.out.println("This is Test1 Method");
    }
    @Test
    void test2(){
        System.out.println("This is Test2 Method");
    }

    @AfterMethod
    void afterMethod(){
        System.out.println("This is  after test method");
    }
}
