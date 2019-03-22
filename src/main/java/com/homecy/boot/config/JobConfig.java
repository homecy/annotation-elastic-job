/*
 * 项目名称：boot
 * 包名称：com.homecy.boot.config
 * 创建者：Homecy
 * 创建时间：2019年1月11日 下午4:34:47
 * 版本：1.0
 */
package com.homecy.boot.config;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.homecy.boot.annotations.BootJob;
import com.homecy.boot.job.listener.SimpleDistributeListener;
import com.homecy.boot.job.listener.SimpleListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 说明：elastic-job 任务注释自动注册
 * 
 * @author Homecy
 * @date 2019年1月11日 下午4:34:47
 *
 * @操作列表 编号 | 操作时间 | 操作人员 | 操作说明（Create、Modify、Deprecated）
 * @操作列表 (1) | 2019年1月11日 | Homecy | Create
 */
@Configuration
@Slf4j
public class JobConfig implements ApplicationContextAware
{
    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        log.info("任务自动注册开始");

        // 获取所有带有定时任务注释的bean
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(BootJob.class);

        for (Object beanJob : beanMap.values())
        {
            // 类类型
            Class<?> cls = beanJob.getClass();

            // 获取注释
            BootJob bootJob = cls.getAnnotation(BootJob.class);

            // 作业配置
            JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration
                    .newBuilder(bootJob.name(), bootJob.cron(), bootJob.shardingTotalCount())
                    .shardingItemParameters(bootJob.shardingItemParameters()).description(bootJob.description())
                    .failover(bootJob.failover()).jobParameter(bootJob.jobParameter()).misfire(bootJob.misfire())
                    .build();

            // 任务配置
            LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration
                    .newBuilder(new SimpleJobConfiguration(jobCoreConfiguration, cls.getCanonicalName()))
                    .overwrite(bootJob.overwrite()).build();

            // 构建SpringJobScheduler对象来初始化任务
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                    .genericBeanDefinition(SpringJobScheduler.class);
            beanDefinitionBuilder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            beanDefinitionBuilder.addConstructorArgValue(beanJob);
            beanDefinitionBuilder.addConstructorArgValue(zookeeperRegistryCenter);
            beanDefinitionBuilder.addConstructorArgValue(liteJobConfiguration);
            beanDefinitionBuilder.addConstructorArgValue(elasticJobListener());

            // SpringJobScheduler对象注册到Spring容器
            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext
                    .getAutowireCapableBeanFactory();
            defaultListableBeanFactory.registerBeanDefinition("SpringJobScheduler",
                    beanDefinitionBuilder.getBeanDefinition());

            // 获取容器中SpringJobScheduler对象
            SpringJobScheduler springJobScheduler = (SpringJobScheduler) applicationContext
                    .getBean("SpringJobScheduler");
            springJobScheduler.init();

            log.info(String.format("任务【%s】初始化成功！", bootJob.name()));
        }

        log.info("任务自动注册结束");
    }

    /**
     * 任务监听器
     * 
     * @return 监听器列表
     */
    protected ElasticJobListener[] elasticJobListener()
    {
        ElasticJobListener[] list = new ElasticJobListener[2];
        list[0] = new SimpleListener();
        list[1] = new SimpleDistributeListener(50000L, 100000L);
        return list;
    }
}