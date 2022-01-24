package net.wang.dtxdbdb.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Customer {


    private Long id;

    private String name;

    private String password;

    private Date createAt = new Date();


    public Customer(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
