package learnTestNgAnnotations;

import org.testng.annotations.Test;

public class TestPriority {
    @Test
    public void defaultTest(){
        System.out.println("This Default Test Case");
    }
    @Test(priority = 1)
    void priorityTest1(){
        System.out.println("This is 1st Priority Test");
    }
    @Test(priority = -1)
    void negativeTest(){
        System.out.println("This is Negative Test");
    }

}
