package com.newcore.laboratory.model.bo;

import lombok.Data;

import java.util.Date;

/**
 * 任务BO模型
 * @author zhouchaowei
 */
@Data
public class TaskBO {

    /** 任务ID */
    private Long id;
    /** 任务名 */
    private String jobName;
    /** 任务描述 */
    private String description;
    /** CRON表达式 */
    private String cronExpression;
    /** 任务执行时调用哪个类的方法 包名 + 类名 */
    private String beanClass;
    /** 任务状态 */
    private String jobStatus;
    /** 任务分组 */
    private String jobGroup;
    /** 创建人 */
    private String createUser;
    /** 创建时间 */
    private Date createTime;
    /** 更新人 */
    private String updateUser;
    /** 更新时间 */
    private Date updateTime;
}
