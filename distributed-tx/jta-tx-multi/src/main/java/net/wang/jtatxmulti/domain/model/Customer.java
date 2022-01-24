package net.wang.jtatxmulti.domain.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "u_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    private Date createAt = new Date();

    public Customer() {
    }

    public Customer(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
