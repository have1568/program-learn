package net.wang.jtatxmulti.repo.wx;

import net.wang.jtatxmulti.domain.wx.WXToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WXTokenRepo extends JpaRepository<WXToken,Integer> {
}
