package com.da.learn.flowable.mytest;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;


/**
 * 自定义的监听器
 */
public class MyTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        //例如：可以进行任务分配m
    }
}
