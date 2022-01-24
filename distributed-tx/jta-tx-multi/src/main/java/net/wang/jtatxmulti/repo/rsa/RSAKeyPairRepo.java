package net.wang.jtatxmulti.repo.rsa;

import net.wang.jtatxmulti.domain.rsa.RSAKeyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RSAKeyPairRepo extends JpaRepository<RSAKeyPair, Integer> {

}
