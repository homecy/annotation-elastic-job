/*
 * 项目名称：boot
 * 包名称：com.homecy.boot
 * 创建者：Homecy
 * 创建时间：2017年6月2日 下午6:14:19
 * 版本：1.0
 */
package com.homecy.boot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 说明： 启动类
 * 
 * @author Homecy
 * @date 2017年6月2日 下午6:14:19
 *
 * @操作列表 编号 | 操作时间 | 操作人员 | 操作说明（Create、Modify、Deprecated）
 * @操作列表 (1) | 2017年6月2日 | Homecy | Create
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
    {
        return configureApplication(builder);
    }

    public static void main(String[] args)
    {
        configureApplication(new SpringApplicationBuilder()).run(args);
    }

    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder)
    {
        return builder.sources(Application.class);
    }
}