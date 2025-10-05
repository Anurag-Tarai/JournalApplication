package com.anurag.journalapp.serviceTests;

import com.anurag.journalapp.dto.request.RegisterRequest;
import com.anurag.journalapp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(

                Arguments.of(RegisterRequest.builder().email("this@anu")
                        .password("1234")
                        .lastName("anu")
                        .lastName("ta").build()),

                Arguments.of(RegisterRequest.builder().email("a@gmail.com")
                        .password("1234")
                        .lastName("anu")
                        .lastName("ta").build())

        );
    }
}
