package net.wang.jtatxmulti.domain.rsa;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rsa_key_pair")
@Data
public class RSAKeyPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String privateKey;
    private String publicKey;
    private Date createAt = new Date();

    public RSAKeyPair() {
    }

    public RSAKeyPair(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }
}
