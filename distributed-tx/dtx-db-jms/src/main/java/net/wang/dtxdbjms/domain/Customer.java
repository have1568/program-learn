package net.wang.dtxdbjms.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "c_customer")
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @Column(name = "create_at")
    private Date createAt = new Date();


    public Customer(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
