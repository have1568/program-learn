package net.wang.axon.account.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountEntityRepo extends JpaRepository<AccountEntity,String> {
}
