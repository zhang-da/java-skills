package com.da.learn.activiti.controller;

import com.da.learn.activiti.domain.R;
import com.da.learn.activiti.service.HolidayService;
import org.activiti.engine.task.Task;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/holidays")
public class HolidayController {
    @Resource
    private HolidayService holidayService ;

    /**
     *  <p>查询制定key的流程审批</p>
     *  <p>时间：2021年1月23日-上午11:17:02</p>
     * @author xg
     * @param key ProcessDefinitionKey
     * @return R
     */
    @GetMapping("")
    public R lists(String key) {
        return R.success(holidayService.queryProcessDefinitionByKey(key)) ;
    }

    /**
     *  <p>创建请假流程审批(私有)</p>
     *  <p>时间：2021年1月23日-上午10:31:47</p>
     * @author xg
     * @return R
     */
    @GetMapping("/_deploy")
    public R createDeploy() {
        holidayService.createDeploy();
        return R.success() ;
    }

    /**
     *  <p>启动请假审批流程</p>
     *  <p>时间：2021年1月23日-上午10:32:55</p>
     * @author xg
     * @param userId
     * @param processDefinitionId 流程定义Id
     * @return R
     */
    @GetMapping("/start")
    public R startProcess(String userId, String processDefinitionId) {
        Map<String, Object> variables = new HashMap<>() ;
        variables.put("assignee", userId) ;
        holidayService.startProcessInstanceAssignVariables(processDefinitionId, variables) ;
        return R.success() ;
    }

    /**
     *  <p>
     *  	查询指派给我的任务
     *  </p>
     *  <p>时间：2021年1月23日-上午11:41:21</p>
     * @author xg
     * @param userId 用户Id
     * @return R
     */
    @GetMapping("/tasks")
    public R myTasks(String userId) {
        List<Task> list = holidayService.queryTasks(userId) ;
        // 注意这里需要我们自己组装下数据，不然会报错。
        List<Map<String, Object>> result = list.stream().map(task -> {
            Map<String, Object> res = new HashMap<String, Object>() ;
            res.put("id", task.getId()) ;
            res.put("assignee", task.getAssignee()) ;
            res.put("createTime", task.getCreateTime()) ;
            res.put("bussinessKey", task.getBusinessKey()) ;
            res.put("category", task.getCategory()) ;
            res.put("dueDate", task.getDueDate()) ; // 到期日期
            res.put("desc", task.getDescription()) ;
            res.put("name", task.getName()) ;
            res.put("owner", task.getOwner()) ;
            res.put("instanceId", task.getProcessInstanceId()) ;
            res.put("variables", task.getProcessVariables()) ;
            return res ;
        }).collect(Collectors.toList()) ;
        return R.success(result) ;
    }

    /**
     *  <p>
     *  	填写审批单
     *  </p>
     *  <p>时间：2021年1月23日-上午11:57:30</p>
     * @author xg
     * @param Map取值如下
     * @param days 请假天数
     * @param explain 审批单说明
     * @param instanceId 流程实例ID
     * @param assignee 指定下一个流程执行人
     * @return R
     */
    @GetMapping("/apply")
    public R fillApply(@RequestParam Map<String, Object> variables) {
        String instanceId = (String) variables.remove("instanceId") ;
        if (StringUtils.isEmpty(instanceId)) {
            return R.failure("未知任务") ;
        }
        holidayService.executionTask(variables, instanceId);
        return R.success() ;
    }
}
