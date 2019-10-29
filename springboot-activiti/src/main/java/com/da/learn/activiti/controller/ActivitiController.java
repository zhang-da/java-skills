package com.da.learn.activiti.controller;

import com.da.learn.activiti.domain.ActModel;
import com.da.learn.activiti.domain.ActProcessDefinition;
import com.da.learn.common.page.PageListResult;
import com.da.learn.common.response.R;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.engine.repository.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/act")
@Api(tags = "1.0.0-SNAPSHOT", value = "activiti")
@Slf4j
public class ActivitiController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("syncdata")
    @ApiOperation(value = "同步数据", notes = "同步用户、角色、用户角色关系")
    public R syncdata() {
        // TODO: 2019/10/23

        return R.ok("开发中……");
    }

    @GetMapping(value = "create_model")
    @ApiOperation(value = "新建模型", notes = "新建模型，重定向到绘制流程图的页面")
    public R createModel(HttpServletRequest request, HttpServletResponse response) {
        try {
            Model model = repositoryService.newModel();

            String name = "新建流程";
            String description = "";
            int revision = 1;
            String key = "processKey";

            ObjectNode modelNode = objectMapper.createObjectNode();
            modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

            model.setName(name);
            model.setKey(key);
            model.setMetaInfo(modelNode.toString());

            repositoryService.saveModel(model);
            String id = model.getId();

            //完善ModelEditorSource
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace",
                    "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.set("stencilset", stencilSetNode);
            repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));

            // 编辑流程模型时,只需要直接跳转此url并传递上modelId即可
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + id);
            return R.ok("redirect:/static/modeler.html?modelId=" + id);
        } catch (Exception e) {
            log.error("创建失败，{}", e);
            return R.error("创建失败");
        }
    }


    @GetMapping("update_model/{id}")
    @ApiOperation(value = "修改/查看模型", notes = "修改/查看模型，重定向到绘制流程图的页面")
    public R updateModel(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {

        try {
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + id);
            return R.ok("redirect:/static/modeler.html?modelId=" + id);
        } catch (Exception e) {
            log.error("跳转失败, {}", e);
            return R.error("跳转失败");
        }
    }

    @PostMapping("delete_model")
    @ApiOperation(value = "删除模型", notes = "删除模型")
    public R delModel(String id) {
        repositoryService.deleteModel(id);
        return R.ok();
    }

    @GetMapping(value = "model_list")
    @ApiOperation(value = "模型列表", notes = "模型列表")
    public R<PageListResult<ActModel>> modelList(
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "modelName", required = false) String modelName,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "limit", required = false) Integer limit) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (!StringUtils.isBlank(key)) {
            modelQuery.modelKey(key);
        }
        if (!StringUtils.isBlank(modelName)) {
            modelQuery.modelNameLike("%" + modelName + "%");
        }
        long count = modelQuery.count();
        if (count == 0) {
            return R.ok(PageListResult.emptyResult());
        }
        List<Model> models;
        if (page != null && limit != null) {
            models = modelQuery.listPage(limit * (page - 1), limit);
        } else {
            models = modelQuery.list();
        }

        List<ActModel> list = new ArrayList<>();
        models.forEach(mo -> list.add(new ActModel(mo)));
        return R.ok(new PageListResult((int) count, list));
        // TODO: 2019/10/23 分页需要改一下
    }


    @PostMapping(value = "deploy")
    @ApiOperation(value = "发布流程", notes = "发布流程")
    public R deploy(String id) {
        try {
            Model modelData = repositoryService.getModel(id);
            if (modelData == null) {
                return R.error("模型为空");
            }
            byte[] sourceBytes = repositoryService.getModelEditorSource(modelData.getId());
            if (sourceBytes == null) {
                return R.error("模型为空");
            }

            JsonNode editorNode = new ObjectMapper().readTree(sourceBytes);
            BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
            BpmnModel bpmnModel = bpmnJsonConverter.convertToBpmnModel(editorNode);
            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .enableDuplicateFiltering()
                    .addBpmnModel(modelData.getName().concat(".bpmn20.xml"), bpmnModel);
            Deployment deploy = deploymentBuilder.deploy();

            modelData.setDeploymentId(deploy.getId());
            repositoryService.saveModel(modelData);
        } catch (Exception e) {
            return R.error("发布失败");
        }
        return R.ok();
    }


    @GetMapping(value = "deploy_list")
    @ApiOperation(value = "部署列表", notes = "部署列表")
    public R<PageListResult<ActModel>> deployList(
            @RequestParam(name = "deploymentId", required = false) String deploymentId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "limit", required = false) Integer limit) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService
                .createProcessDefinitionQuery();

        if (!StringUtils.isBlank(deploymentId)) {
            processDefinitionQuery.deploymentId(deploymentId);
        }
        if (!StringUtils.isBlank(name)) {
            processDefinitionQuery.processDefinitionNameLike("%" + name + "%");
        }

        long count = processDefinitionQuery.count();
        if (count == 0) {
            return R.ok(PageListResult.emptyResult());
        }

        List<ProcessDefinition> processDefinitionList;

        if (page != null && limit != null) {
            processDefinitionList = processDefinitionQuery.listPage(limit * (page - 1), limit);
        } else {
            processDefinitionList = processDefinitionQuery.list();
        }

        List<ActProcessDefinition> list = new ArrayList<>();
        processDefinitionList
                .forEach(processDefinition -> list.add(new ActProcessDefinition(processDefinition)));
        return R.ok(new PageListResult((int) count, list));
        // TODO: 2019/10/24 分页参数需要改一下
    }


    @PostMapping("delete_deploy")
    @ApiOperation(value = "删除部署", notes = "删除部署")
    public R delDeploy(String deploymentId) {
        try {
            /**不带级联的删除：只能删除没有启动的流程，如果流程启动，就会抛出异常*/
            repositoryService.deleteDeployment(deploymentId);
            ///**级联删除：不管流程是否启动，都能可以删除（emmm大概是一锅端）*/
            //repositoryService.deleteDeployment(deploymentId, true);
            return R.ok();
        } catch (Exception e) {
            log.error("删除失败");
            return R.error("删除失败");
        }
    }

    @PostMapping(path = "start_process")
    @ApiOperation(value = "根据流程key启动流程", notes = "每一个流程有对应的一个key这个是某一个流程内固定的写在bpmn内的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processKey", value = "流程key", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startUserKey", value = "启动流程的用户", dataType = "String", paramType = "query")
    })
    public R start(@RequestParam("processKey") String processKey,
                   @RequestParam("startUserKey") String startUserKey) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("startUserKey", startUserKey);
        ProcessInstance instance = null;
        try {
            instance = runtimeService.startProcessInstanceByKey(processKey, variables);
        } catch (Exception e) {
            log.error("根据流程key启动流程,异常:{}", e);
            return R.error("启动失败");
        }
        if (instance != null) {
            Map<String, String> result = new HashMap<>();
            // 流程实例ID
            result.put("processId", instance.getId());
            // 流程定义ID
            result.put("processDefinitionKey", instance.getProcessDefinitionId());
            return R.ok(result);
        }
        return R.error("启动失败");
    }

    @GetMapping(path = "query_process_by_key")
    @ApiOperation(value = "根据流程key查询流程实例", notes = "查询流程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processDefinitionKey", value = "流程key", dataType = "String", paramType = "query"),
    })
    public R searchProcessInstance(@RequestParam("processDefinitionKey") String processDefinitionKey) {
        List<ProcessInstance> runningList;
        try {
            ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
            runningList = processInstanceQuery.processDefinitionKey(processDefinitionKey).list();
        } catch (Exception e) {
            log.error("根据流程key查询流程实例,异常:{}", e);
            return R.error("查询失败");
        }

        List<Map<String, String>> resultList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(runningList)) {
            runningList.forEach(s -> {
                Map<String, String> resultMap = new HashMap<>();
                // 流程实例ID
                resultMap.put("processId", s.getId());
                // 流程定义ID
                resultMap.put("processDefinitionKey", s.getProcessDefinitionId());
                resultList.add(resultMap);
            });
        }
        return R.ok(resultList);
    }

    @GetMapping(path = "query_process_by_id")
    @ApiOperation(value = "根据流程ID查询流程实例", notes = "查询流程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processId", value = "流程实例ID", dataType = "String", paramType = "query"),
    })
    public R searchByID(@RequestParam("processId") String processId) {
        ProcessInstance pi = null;
        try {
            pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        } catch (Exception e) {
            log.error("根据流程ID查询流程实例,异常:{}", e);
            return R.error("查询失败");
        }

        if (pi != null) {
            Map<String, String> resultMap = new HashMap<>(2);
            // 流程实例ID
            resultMap.put("processID", pi.getId());
            // 流程定义ID
            resultMap.put("processDefinitionKey", pi.getProcessDefinitionId());
            return R.ok(resultMap);
        }
        return R.notFound();
    }

}
