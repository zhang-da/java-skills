package com.da.learn.flowable.mytest;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootTest
public class MyTest {
    @Autowired
    private ProcessEngine processEngine;

    public void myTestDeploy() {
        // Deploy
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("holiday-request.bpmn20.xml").deploy();


        // Query
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());
    }

    public void myTestStartProcessInstance() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Who are you?");
        String employee = scanner.nextLine();

        System.out.println("How many holidays do you want to request?");
        Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

        System.out.println("Why do you need them?");
        String description = scanner.nextLine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
    }

    public void myTestQuery() {
        Scanner scanner = new Scanner(System.in);

        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
        }

        System.out.println("Which task would you like to complete?");
        int taskIndex = Integer.parseInt(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee") + " wants " + processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");
    }

    public void myTestCompleteTask(Task task) {
        Scanner scanner = new Scanner(System.in);

        boolean approved = scanner.nextLine().toLowerCase().equals("y");
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", approved);

        TaskService taskService = processEngine.getTaskService();
        taskService.complete(task.getId(), variables);
    }


    /**
     * return:
     * startEvent took 1 milliseconds
     * approveTask took 2638 milliseconds
     * decision took 3 milliseconds
     * externalSystemCall took 1 milliseconds
     *
     * @param processInstance
     */
    public void myTestHistoryData(ProcessInstance processInstance) {
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).finished().orderByHistoricActivityInstanceEndTime().asc().list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId() + " took " + activity.getDurationInMillis() + " milliseconds");
        }
    }
}
