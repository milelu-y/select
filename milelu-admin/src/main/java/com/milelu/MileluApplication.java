package com.milelu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * 启动程序
 *
 * @author milelu
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class MileluApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MileluApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  糜了鹿启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "         |      |      \n" +
                "/=\\=\\ = |  /=\\ |  | | \n" +
                "| | | | \\= \\=  \\= \\=/ \n");
    }
}
