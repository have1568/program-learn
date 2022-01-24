package net.wang.jtatxmulti.domain.wx;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wx_token")
@Data
public class WXToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private Date createAt = new Date();

    public WXToken() {
    }

    public WXToken(String token) {
        this.token = token;

    }
}
