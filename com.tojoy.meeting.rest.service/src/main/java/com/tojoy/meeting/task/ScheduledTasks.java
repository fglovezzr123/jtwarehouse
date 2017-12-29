package com.tojoy.meeting.task;

import com.tojoy.meeting.common.ConstantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ScheduledTasks {

    @Autowired
    private ConstantService constantService;

    private Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private int cronCount = 1;

    @Scheduled(cron = "0/5 * * * * ?")  //cron接受cron表达式，根据cron表达式确定定时规则
    public void testCron() {
        logger.info("===initialDelay: 第{}次执行方法", cronCount++);

        System.out.println("auto:" + constantService.MEETING_SIGNUP_SUCCESS);

    }

}