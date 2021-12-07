package com.da.learn.activiti.domain;

import lombok.Getter;
import lombok.Setter;
import org.activiti.engine.repository.Model;

import java.util.Date;


@Getter
@Setter
public class ActModel {

  private String id;
  private String name;
  private String key;
  private String category;
  private Date createTime;
  private Date lastUpdateTime;
  private Integer version;
  private String metaInfo;
  private String deploymentId;
  private String tenantId;
  private boolean hasEditorSource;


  public ActModel() {
  }

  public ActModel(Model model) {
    this.id = model.getId();
    this.name = model.getName();
    this.key = model.getKey();
    this.category = model.getCategory();
    this.createTime = model.getCreateTime();
    this.lastUpdateTime = model.getLastUpdateTime();
    this.version = model.getVersion();
    this.metaInfo = model.getMetaInfo();
    this.deploymentId = model.getDeploymentId();
    this.tenantId = model.getTenantId();
    this.hasEditorSource = model.hasEditorSource();
  }
}
