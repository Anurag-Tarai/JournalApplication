//package com.anurag.journalapp.serviceTests;
//
//import com.anurag.journalapp.dto.request.RegisterRequest;
//import com.anurag.journalapp.dto.response.UserResponse;
//import com.anurag.journalapp.repository.UserRepository;
//import com.anurag.journalapp.service.AuthService;
//
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ArgumentsSource;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest // to start spring application context
//public class UserServiceTests {
//
//    @Autowired
//     private UserRepository userRepository;
//
//    @Autowired
//    private AuthService userService;
//
//    @ParameterizedTest
//    @CsvSource({
//            "1,2,3",
//            "8,2,6"
//    })
//    public void test(int a, int b, int expected){
//        assertEquals(expected, a+b);
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "emma.wilson@gmail.com",
//            "anu@gmail.com",
//            "satya@gmail.com",
//            "a",
//            "michael.brown@gmail.com"
//    })
//    public void test2(String name){
//        assertTrue(userRepository.findByEmail(name).isPresent());
//    }
//
//    @ParameterizedTest
//    @ArgumentsSource(UserArgumentProvider.class)
//    public void test3(RegisterRequest request){
//        // Act
//        UserResponse response = userService.register(request);
//
//        // Assert
//        assertNotNull(response.getId(), "User ID should not be null");
//        assertEquals(request.getEmail().toLowerCase(), response.getEmail(), "Emails should match");
//        assertEquals(request.getFirstName(), response.getFirstName());
//        assertEquals(request.getLastName(), response.getLastName());
//    }
//
//}
