package net.wang.axon.saga.order.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OrderId {
    private final String identifier;
    private final int hashCode;

    public OrderId(String identifier) {
        this.identifier = identifier;
        this.hashCode = identifier.hashCode();
    }

}
