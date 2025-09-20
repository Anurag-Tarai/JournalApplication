package com.anurag.journalapp.serviceTests;

import com.anurag.journalapp.repository.UserRepositoryImp;
import com.anurag.journalapp.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImpTest {
    @Autowired
    UserRepositoryImp userRepositoryImp;

    @Test
    public void testFindUserSA(){
       Assertions.assertNotNull( userRepositoryImp.getUsersForSA());
    }
}
