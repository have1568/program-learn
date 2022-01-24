package net.wang.jtatxmulti.service;

import net.wang.jtatxmulti.domain.model.Customer;
import net.wang.jtatxmulti.domain.rsa.RSAKeyPair;
import net.wang.jtatxmulti.domain.wx.WXToken;
import net.wang.jtatxmulti.repo.customer.CustomerRepo;
import net.wang.jtatxmulti.repo.rsa.RSAKeyPairRepo;
import net.wang.jtatxmulti.repo.wx.WXTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TestJTATX {

    @Resource
    private CustomerRepo customerRepo;
    @Autowired
    private WXTokenRepo wxTokenRepo;
    @Autowired
    private RSAKeyPairRepo rsaKeyPairRepo;

    @Transactional
    public void test() {
        customerRepo.save(new Customer("1111", "2222"));
        wxTokenRepo.save(new WXToken("test_token"));
        rsaKeyPairRepo.save(new RSAKeyPair("pr", "pu"));
        //int a = 1 / 0;
    }
}
