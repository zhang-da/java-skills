package com.da.learn.activiti.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class HolidayService {
    private static final Logger logger = LoggerFactory.getLogger(HolidayService.class);

    @Resource
    private ProcessEngine processEngine;
    @Resource
    private RepositoryService repositoryService ;
    @Resource
    private RuntimeService runtimeService ;
    @Resource
    private TaskService taskService ;

    /**
     * <p>
     * 流程定义的部署 activiti表有哪些？
     * act_re_deployment 流程定义部署表，记录流程部署信息
     * act_re_procdef 流程定义表，记录流程定义信息
     * act_ge_bytearray 资源表（bpmn文件及png文件）
     * </p>
     * <p>时间：2021年1月22日-下午3:33:00</p>
     * @author xg
     * @return void
     */
    public void createDeploy() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/holiday.bpmn")
                .addClasspathResource("processes/holiday.png")
                .name("请假申请单流程")
                .key("holiday")
                .category("InnerP")
                .deploy();
        logger.info("流程部署id: {}", deployment.getId());
        logger.info("流程部署名称: {}", deployment.getName());
    }
    // 注意这里这个方法是当我们没有开启自动部署流程定义时，就需要手动部署。
    /**
     * <p>
     * 	流程定义查询
     * </p>
     * <p>时间：2021年1月22日-下午3:45:02</p>
     * @author xg
     * @param processDefinition
     * @return void
     */
    public List<ProcessDefinition> queryProcessDefinitionByKey(String processDefinition) {
        // 查询流程定义
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey(processDefinition).list();
        list.forEach(pd -> {
            logger.info("------------------------------------------------");
            logger.info("流程部署id：{}", pd.getDeploymentId());
            logger.info("流程定义id：{}", pd.getId());
            logger.info("流程定义名称：{}", pd.getName());
            logger.info("流程定义key：{}", pd.getKey());
            logger.info("流程定义版本：{}", pd.getVersion());
            logger.info("------------------------------------------------");
        });
        return list ;
    }

    /**
     * <p>
     * 	删除流程
     * </p>
     * <p>时间：2021年1月22日-下午4:21:40</p>
     * @author xg
     * @return void
     */
    public void deleteDeployment(String deploymentId) {
        // 设置true 级联删除流程定义，即使该流程有流程实例启动也可以删除，设置为false非级别删除方式，如果流程
        repositoryService.deleteDeployment(deploymentId, true);
    }

    /**
     * <p>
     * 	启动流程实例（比如用户根据定义好的流程发起一个流程的实例（这里的请假流程申请））
     * <p>时间：2021年1月22日-下午4:54:56</p>
     * @author xg
     * @return void
     */
    public void startProcessInstanceById(String processDefinitionId) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId) ;
        logger.info("流程定义ID: {}", processInstance.getProcessDefinitionId());
        logger.info("流程实例ID: {}", processInstance.getId());
    }

    /**
     * <p>
     * 	启动流程实例,指定业务Key（方便关联业务数据）（比如用户根据定义好的流程发起一个流程的实例（这里的请假流程申请））
     * 	Businesskey(业务标识)
     启动流程实例时，指定的businesskey，就会在act_ru_execution #流程实例的执行表中存储businesskey。
     Businesskey：业务标识，通常为业务表的主键，业务标识和流程实例一一对应。业务标识来源于业务系统。存储业务标识就是根据业务标识来关联查询业务系统的数据。
     比如：请假流程启动一个流程实例，就可以将请假单的id作为业务标识存储到activiti中，
     将来查询activiti的流程实例信息就可以获取请假单的id从而关联查询业务系统数据库得到请假单信息。
     * <p>时间：2021年1月22日-下午4:54:56</p>
     * @author xg
     * @return void
     */
    public void startProcessInstanceToBussinessKey(String processDefinitionId, String bussinessKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, bussinessKey);
        logger.info("流程定义ID: {}", processInstance.getProcessDefinitionId());
        logger.info("流程实例ID: {}", processInstance.getId());
        logger.info("BussinessKey: {}", processInstance.getBusinessKey()) ;
    }

    /**
     *  <p>
     *  	设置assignee的取值，用户可以在界面上设置流程的执行人
     *  </p>
     *  <p>时间：2021年1月22日-下午8:30:39</p>
     * @author xg
     * @param processDefinitionId
     * @return void
     */
    public void startProcessInstanceAssignVariables(String processDefinitionId, Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
        logger.info("流程定义ID: {}", processInstance.getProcessDefinitionId());
        logger.info("流程实例ID: {}", processInstance.getId());
        logger.info("BussinessKey: {}", processInstance.getBusinessKey()) ;
    }

    /**
     *  <p>
     *  	查询指派关联的用户任务
     *  </p>
     *  <p>时间：2021年1月23日-上午11:39:56</p>
     * @author xg
     * @param assignee 关联用户
     * @return List<Task>
     */
    public List<Task> queryTasks(String assignee) {
        TaskQuery query = taskService.createTaskQuery() ;
        return query.taskAssignee(assignee).orderByTaskCreateTime().asc().list() ;
    }

    public void executionTask(Map<String, Object> variables, String instanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult() ;
        if (task == null) {
            logger.error("任务【{}】不存在", instanceId) ;
            throw new RuntimeException("任务【" + instanceId + "】不存在") ;
        }
        taskService.complete(task.getId(), variables) ;
    }
}
