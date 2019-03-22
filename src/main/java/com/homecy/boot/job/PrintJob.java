/*
 * 项目名称：boot
 * 包名称：com.homecy.boot.job
 * 创建者：Homecy
 * 创建时间：2017年6月1日 上午11:40:47
 * 版本：1.0
 */
package com.homecy.boot.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.homecy.boot.annotations.BootJob;
import com.homecy.boot.job.base.BaseJob;

import lombok.extern.slf4j.Slf4j;

/**
 * 说明：打印任务
 * 
 * @author Homecy
 * @date 2017年6月1日 上午11:40:47
 *
 * @操作列表 编号 | 操作时间 | 操作人员 | 操作说明（Create、Modify、Deprecated）
 * @操作列表 (1) | 2017年6月1日 | Homecy | Create
 */
@Slf4j
@BootJob(name = "打印任务", cron = "0/5 * * * * ?", shardingTotalCount = 1, shardingItemParameters = "0=A", failover = true, misfire = true, overwrite = true)
public class PrintJob extends BaseJob<String>
{
    @Override
    protected boolean check(ShardingContext shardingContext)
    {
        return true;
    }

    @Override
    protected String process(ShardingContext shardingContext)
    {
        log.info("打印任务开始执行");

        log.info("**********************分片信息**********************");
        log.info("JobName: " + shardingContext.getJobName());
        log.info("ShardingItem: " + shardingContext.getShardingItem());
        log.info("ShardingTotalCount: " + shardingContext.getShardingTotalCount());

        log.info("打印任务执行结束");
        return null;
    }
}