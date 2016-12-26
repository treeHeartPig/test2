package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 269871 on 2016/12/13.
 */
public class CustomerService {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerService.class);

    /**
     *   获取客户列表
     */
    public List<Customer> getCustomerList(){
        String sql="SELECT * FROM customer";
        List<Customer> customerList=DatabaseHelper.queryEntityList(Customer.class,sql);
        return  customerList;
    }

    /**
     * 获取客户
     */
    public Customer getCustomer(int id){
        String sql="SELECT * FROM customer WHERE id=?";
        Customer customer=DatabaseHelper.queryEntity(Customer.class,sql,id);
        return  customer;
    }

    /**
     * 创建客户
     */
    public boolean createCustomer(Map<String,Object> map){
        return DatabaseHelper.insertEntity(Customer.class,map);
    }

    /**
     * 删除客户
     */
    public  boolean deleteCustomer(long id){

        return DatabaseHelper.deleteEntity(Customer.class,id);
    }

}
