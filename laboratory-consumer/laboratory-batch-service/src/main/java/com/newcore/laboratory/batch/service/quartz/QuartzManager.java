package com.newcore.laboratory.batch.service.quartz;

import com.newcore.laboratory.model.bo.TaskBO;
import org.quartz.*;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 计划任务管理
 * @author 小卖铺的老爷爷
 * @date 2018年6月17日
 * @website www.laoyeye.net
 */
@Service
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;

    /**
     * 添加定时任务
     * @param taskBO 任务BO模型
     */    
    @SuppressWarnings("unchecked")
    public void addJob(TaskBO taskBO) {
        try {
            /** 1.创建jobDetail实例,绑定Job实现类 */
            Class<? extends Job> jobClass = (Class<? extends Job>) (Class.forName(taskBO.getBeanClass()).newInstance().getClass());
            /** 2.指明job的名称,所在组的名称,以及绑定job类 */
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(taskBO.getJobName(), taskBO.getJobGroup()).build();
            /** 3.定义调度触发规则,使用cornTrigger */
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(taskBO.getJobName(), taskBO.getJobGroup())
                    .startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(taskBO.getCronExpression()))
                    .startNow()
                    .build();
            /** 4.把作业和触发器注册到任务调度中 */
            scheduler.scheduleJob(jobDetail, trigger);
            /** 5.启动定时任务 */
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有计划中的任务列表
     * @return
     * @throws SchedulerException
     */
    public List<TaskBO> obtainAllJob() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
        List<TaskBO> taskBOList = new ArrayList<>();
        for (JobKey jobKey : jobKeySet) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                taskBOList.add(setTaskBO(jobKey,trigger));
            }
        }
        return taskBOList;
    }

    /**
     * 所有正在运行的job
     * 
     * @return
     * @throws SchedulerException
     */
    public List<TaskBO> obtainAllRunningJob() throws SchedulerException {
        List<JobExecutionContext> runningJobList = scheduler.getCurrentlyExecutingJobs();
        List<TaskBO> taskBOList = new ArrayList<>(runningJobList.size());
        for (JobExecutionContext runningJob : runningJobList) {
            taskBOList.add(setTaskBO(runningJob.getJobDetail().getKey(),runningJob.getTrigger()));
        }
        return taskBOList;
    }

    /**
     * 暂停一个job
     * @param task 任务BO模型
     * @throws SchedulerException
     */
    public void pauseJob(TaskBO task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     * @param task 任务BO模型
     * @throws SchedulerException
     */
    public void recoverJob(TaskBO task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     * @param task 任务BO模型
     * @throws SchedulerException
     */
    public void deleteJob(TaskBO task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即执行job
     * @param task 任务BO模型
     * @throws SchedulerException
     */
    public void runJobNow(TaskBO task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     * @param task
     * @throws SchedulerException
     */
    public void updateJobCron(TaskBO task) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(task.getJobName(), task.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 定时任务
     * @param jobKey
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    private TaskBO setTaskBO(JobKey jobKey,Trigger trigger) throws SchedulerException {
        TaskBO taskBO = new TaskBO();
        taskBO.setJobName(jobKey.getName());
        taskBO.setJobGroup(jobKey.getGroup());
        taskBO.setDescription("触发器:" + trigger.getKey());
        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
        taskBO.setJobStatus(triggerState.name());
        if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            String cronExpression = cronTrigger.getCronExpression();
            taskBO.setCronExpression(cronExpression);
        }
        return taskBO;
    }
}