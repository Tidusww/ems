package com.ly.ems.model.dispatch;

/**
 * Created by tidus on 2018/9/23.
 */
public class DispatchRelVo extends DispatchRel {
    private String groupName;
    private String projectName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
