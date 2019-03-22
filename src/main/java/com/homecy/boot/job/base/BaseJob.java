/*
 * 项目名称：boot
 * 包名称：com.homecy.boot.job.base
 * 创建者：Homecy
 * 创建时间：2017年6月1日 上午11:27:56
 * 版本：1.0
 */
package com.homecy.boot.job.base;

import java.io.Serializable;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 说明：定时任务基础类
 * 
 * @author Homecy
 * @date 2017年6月1日 上午11:27:56
 *
 * @操作列表 编号 | 操作时间 | 操作人员 | 操作说明（Create、Modify、Deprecated）
 * @操作列表 (1) | 2017年6月1日 | Homecy | Create
 */
public abstract class BaseJob<T extends Serializable> implements SimpleJob
{
    @Override
    public void execute(ShardingContext shardingContext)
    {
        if (check(shardingContext))
        {
            process(shardingContext);
        }
    }

    /**
     * 数据检查
     * 
     * @param shardingContext
     *            分片信息
     * @return boolean
     */
    protected abstract boolean check(ShardingContext shardingContext);

    /**
     * 执行任务
     * 
     * @param shardingContext
     *            分片信息
     * @return T
     */
    protected abstract T process(ShardingContext shardingContext);
}