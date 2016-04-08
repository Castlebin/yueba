package com.xteam.ggq.model.dbrouting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

/**
 * 依照配置文件创建主从数据源。
 * Created by hanxu on 2015/8/27.
 */
@Component
@Qualifier("routingDS")
@Primary
public class CrmRoutingDataSource extends AbstractRoutingDataSource {

    @Autowired
    @Qualifier("masterDS")
    private DataSource masterDS;

    @Autowired
    @Qualifier("slaveDB")
    private DataSource slaveDB;

    @Override
    protected Object determineCurrentLookupKey() {
        String dbType = ContextHolder.getDataSourceType();
        if(ContextHolder.ConnectionType.READ.equals(dbType)){
            return ContextHolder.ConnectionType.READ;
        }

        return ContextHolder.ConnectionType.READ_WRITE;
    }

    @PostConstruct
    public void postConstruct(){
        Object defaultTargetDataSource = masterDS;
        super.setDefaultTargetDataSource(defaultTargetDataSource);

        Map<Object, Object> targetDataSource = new HashMap();
        targetDataSource.put(ContextHolder.ConnectionType.READ, slaveDB);
        super.setTargetDataSources(targetDataSource);
    }

    @Bean(name = "masterDS") @Qualifier("masterDS")
    @ConfigurationProperties(prefix="spring.datasource")
    public javax.sql.DataSource getMasterDataSource() throws PropertyVetoException {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slaveDB") @Qualifier("slaveDB")
    @ConfigurationProperties(prefix="spring.datasource-slave")
    public javax.sql.DataSource getSlaveDataSource() throws PropertyVetoException {
        return DataSourceBuilder.create().build();
    }
}
