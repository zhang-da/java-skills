package com.da.learn.flowable;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootTest
public class FlowableApplicationTest {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Test
    public void testInit() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:3306/flowable_test?serverTimezone=UTC&nullCatalogMeansCurrent=true")
                .setJdbcUsername("root")
                .setJdbcPassword("zhangda")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();
    }

    /**
     * 部署流程涉及到3张表
     *      流程部署表: ACT_RE_DEPLOYMENT    一次部署会生成一条记录
     *      流程定义表: ACT_RE_PROCDEF       一次部署会生成多条记录，几个流程定义文件就会产生几条记录
     *      流程定义资源文件表: ACT_GE_BYTEARRAY   有多少资源就会生成多少记录
     */
    @Test
    public void testDeploy() {
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .name("holiday-request")
                .deploy();
        System.out.println("deploy id = " + deploy.getId());
        System.out.println("deploy name = " + deploy.getName());
    }

    @Test
    public void queryProcess() {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId("0ca4515d-c530-11ec-bed0-9e022f1462ec")
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());
    }

    /**
     * 流程的挂起和激活
     */
    @Test
    public void testSuspend() {
//        RepositoryService repositoryService = processEngine.getRepositoryService();
        //获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId("holidayRequest:1:0cb149af-c530-11ec-bed0-9e022f1462ec")    //ACT_RE_PROCDEF中的id
                .singleResult();
        //获取当前流程定义的状态信息
        boolean suspended = processDefinition.isSuspended();
        System.out.println("suspended = " + suspended);
        if (suspended) {
            //当前流程被挂起
            repositoryService.activateProcessDefinitionById("holidayRequest:1:0cb149af-c530-11ec-bed0-9e022f1462ec");
        } else {
            //当前流程是激活状态
            repositoryService.suspendProcessDefinitionById("holidayRequest:1:0cb149af-c530-11ec-bed0-9e022f1462ec");
        }
    }

    /**
     * 启动流程
     *      ACT_RU_EXECUTION: 运行时流程执行实例
     *      ACT_RU_IDENTITYLINK: 运行时用户关系信息
     *      ACT_RU_TASK: 运行时任务表
     *      ACT_RU_VARIABLE: 运行时变量表
     */
    @Test
    public void startProcess() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", "employee");
        variables.put("nrOfHolidays", "nrOfHolidays");
        variables.put("description", "description");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("holidayRequest:1:0cb149af-c530-11ec-bed0-9e022f1462ec", variables);
    }

    @Test
    public void myTestCompleteTask() {
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("")   //根据流程实例编号来查找
//                .processDefinitionId("holidayRequest:1:0cb149af-c530-11ec-bed0-9e022f1462ec")
                .taskAssignee("user2")
                .singleResult();

        //获取当前流程实例绑定的流程变量
        Map<String, Object> processVariables = task.getProcessVariables();
        Map<String, Object> taskLocalVariables = task.getTaskLocalVariables();

//        Map<String, Object> variables = new HashMap<>();
        processVariables.put("approved", true);

        taskService.complete(task.getId(), processVariables );
    }


}