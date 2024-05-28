package com.son.spring_rest_doc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {
    private int id;
    private String username;
    private String email;
}
