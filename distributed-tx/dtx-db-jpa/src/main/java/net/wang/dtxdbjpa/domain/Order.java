package net.wang.dtxdbjpa.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
public class Order {

    private Long id;

    private String storeName;

    private int amount;

    private Date createAt = new Date();


    public Order(String storeName, int amount) {
        this.storeName = storeName;
        this.amount = amount;
    }
}
