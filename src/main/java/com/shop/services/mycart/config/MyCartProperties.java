package com.shop.services.mycart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class MyCartProperties
{

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.port}")
    private Integer serverPort;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hbm2ddl;

    @Value("${spring.jpa.properties.hibernate.show_sql}")
    private Boolean showSQL;

    @Value("${spring.jpa.properties.hibernate.format_sql}")
    private Boolean formatSQL;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dialect;

    @Value("${spring.jpa.properties.hibernate.enable_lazy_load_no_trans}")
    private Boolean lazyLoad;

    @Value("${spring.jpa.properties.hibernate.globally_quoted_identifiers}")
    private Boolean quotedIdentifiers;

    @Value("${spring.jpa.properties.hibernate.use_sql_comments}")
    private Boolean sqlComments;


    public String getContextPath()
    {
        return contextPath;
    }

    public Integer getServerPort()
    {
        return serverPort;
    }

    public String getHbm2ddl()
    {
        return hbm2ddl;
    }

    public Boolean getShowSQL()
    {
        return showSQL;
    }

    public Boolean getFormatSQL()
    {
        return formatSQL;
    }

    public String getDialect()
    {
        return dialect;
    }

    public Boolean getLazyLoad()
    {
        return lazyLoad;
    }

    public Boolean getQuotedIdentifiers()
    {
        return quotedIdentifiers;
    }

    public Boolean getSqlComments()
    {
        return sqlComments;
    }
}
