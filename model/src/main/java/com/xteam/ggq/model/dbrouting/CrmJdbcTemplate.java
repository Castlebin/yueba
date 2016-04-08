package com.xteam.ggq.model.dbrouting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

/**
 * 指定使用主从读写分离的数据源。
 * Created by hanxu on 2015/8/28.
 */
public class CrmJdbcTemplate extends JdbcTemplate {

    @Autowired
    @Qualifier("routingDS")
    private CrmRoutingDataSource routingDataSource;

    @PostConstruct
    public void postConstruct(){
        super.setDataSource(routingDataSource);
    }
}
