package com.newcore.laboratory.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.newcore.laboratory.utils.RestServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Zuul初始化过滤器
 * @author zhouchaowei
 * @date 2019年10月28日 星期一 21:08
 */
@Component
public class RouteAuthenticationFilter extends ZuulFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(RouteAuthenticationFilter.class);

    /** 服务名称 */
    @Value("${spring.application.name}")
    private String serviceName;
    
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 进行路由的前置校验方法
     * @return 响应体
     */
    @Override
    public RestServerResponse<String> run() {
        logger.info("开始执行Zuul初始化过滤器...");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        logger.info("该次请求的客户端IP为:{},token值为:{}",request.getRemoteHost(),request.getHeader("Token"));

        /** TODO 待添加 校验token逻辑 */
        return RestServerResponse.createBySuccessMessage("请求成功",serviceName);
    }
}
