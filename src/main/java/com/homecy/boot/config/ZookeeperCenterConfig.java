/*
 * 项目名称：boot
 * 包名称：com.homecy.boot.config
 * 创建者：Homecy
 * 创建时间：2019年1月11日 下午11:06:44
 * 版本：1.0
 */
package com.homecy.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * 说明：elastic-job zookeeper注册中心
 * 
 * @author Homecy
 * @date 2019年1月11日 下午11:06:44
 *
 * @操作列表 编号 | 操作时间 | 操作人员 | 操作说明（Create、Modify、Deprecated）
 * @操作列表 (1) | 2019年1月11日 | Homecy | Create
 */
@Configuration
@PropertySource("classpath:job.properties")
@ConditionalOnExpression("'${zk.serverlist}'.length() > 0")
public class ZookeeperCenterConfig
{
    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "zk")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(@Value("${zk.serverlist}") String serverLists,
            @Value("${zk.namespace}") String nameSpace)
    {
        // zookeeper配置信息
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(serverLists, nameSpace);

        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }
}