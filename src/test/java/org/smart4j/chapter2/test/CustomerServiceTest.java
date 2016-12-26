package org.smart4j.chapter2.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.FileHandler;

/**
 * Created by 269871 on 2016/12/13.
 */
public class CustomerServiceTest {
    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerServiceTest.class);
    private final CustomerService customerService;
    public  CustomerServiceTest(){
        customerService=new CustomerService();
    }

    @Before
    public  void  init() throws IOException {

        String filePath="jsp/customer_init.sql";
       DatabaseHelper.executeSqlFile(filePath);
    }

    @Test
    public void getCustomerListTest(){
        List<Customer> customerList;
        customerList = customerService.getCustomerList();
        LOGGER.info("----查询出"+customerList.size()+"条数据--");
        Assert.assertEquals(2,customerList.size());
    }
    @Test
    public void getCustomerTest(){
        Customer customer=customerService.getCustomer(1);
        LOGGER.info("--查询出的结果："+customer.getName());
    }

    @Test
    public void deleteCustomerTest(){
        boolean flag=customerService.deleteCustomer(2);
        LOGGER.info("--删除是否成功:"+flag);
    }
}
