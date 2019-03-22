/*
 * 项目名称：boot
 * 包名称：com.homecy.boot.job
 * 创建者：Homecy
 * 创建时间：2019年2月27日 下午1:48:31
 * 版本：1.0
 */
package com.homecy.boot.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.homecy.boot.annotations.BootJob;
import com.homecy.boot.job.base.BaseJob;

import lombok.extern.slf4j.Slf4j;

/**
 * 说明：
 * 
 * @author Homecy
 * @date 2019年2月27日 下午1:48:31
 *
 * @操作列表 编号 | 操作时间 | 操作人员 | 操作说明（Create、Modify、Deprecated）
 * @操作列表 (1) | 2019年2月27日 | Homecy | Create
 */
@Slf4j
@BootJob(name = "清理任务", cron = "0/6 * * * * ?", shardingTotalCount = 1, shardingItemParameters = "0=A", failover = true, misfire = true, overwrite = true)
public class ClearingJob extends BaseJob<String>
{

    @Override
    protected boolean check(ShardingContext shardingContext)
    {
        return true;
    }

    @Override
    protected String process(ShardingContext shardingContext)
    {
        log.info("清理任务开始执行");

        log.info("**********************清理任务分片信息**********************");
        log.info("JobName: " + shardingContext.getJobName());
        log.info("ShardingItem: " + shardingContext.getShardingItem());
        log.info("ShardingTotalCount: " + shardingContext.getShardingTotalCount());

        log.info("清理任务执行结束");
        return null;
    }
}