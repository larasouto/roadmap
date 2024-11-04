package com.game.store.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AuditLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String className;
  private String methodName;
  private LocalDateTime executionTime;

  public AuditLog(String className, String methodName, LocalDateTime executionTime) {
    this.className = className;
    this.methodName = methodName;
    this.executionTime = executionTime;
  }

  public AuditLog() {
  }

  public Long getId() {
    return id;
  }

  public String getClassName() {
    return className;
  }

  public String getMethodName() {
    return methodName;
  }

  public LocalDateTime getExecutionTime() {
    return executionTime;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public void setExecutionTime(LocalDateTime executionTime) {
    this.executionTime = executionTime;
  }
}
