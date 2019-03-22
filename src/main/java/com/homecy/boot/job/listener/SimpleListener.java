/*
 * 项目名称：boot
 * 包名称：com.homecy.boot.job.listener
 * 创建者：Homecy
 * 创建时间：2019年1月11日 下午11:35:43
 * 版本：1.0
 */
package com.homecy.boot.job.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 说明：job监听器
 * 
 * @author Homecy
 * @date 2019年1月11日 下午11:35:43
 *
 * @操作列表 编号 | 操作时间 | 操作人员 | 操作说明（Create、Modify、Deprecated）
 * @操作列表 (1) | 2019年1月11日 | Homecy | Create
 */
@Slf4j
public class SimpleListener implements ElasticJobListener
{
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts)
    {
        log.info("beforeJobExecuted: " + shardingContexts);
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts)
    {
        log.info("afterJobExecuted: " + shardingContexts);
    }
}