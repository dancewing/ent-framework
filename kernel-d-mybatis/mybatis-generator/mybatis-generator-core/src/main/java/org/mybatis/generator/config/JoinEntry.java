package org.mybatis.generator.config;


import org.apache.commons.lang3.tuple.Pair;
import java.util.List;

public class JoinEntry {
    private String leftTable;
    private String targetProject;
    private String targetPackage;
    private List<Pair<String, JoinTarget>> details;

    public JoinEntry() {
    }

    public JoinEntry(String leftTable, String targetProject, String targetPackage, List<Pair<String, JoinTarget>> details) {
        this.leftTable = leftTable;
        this.targetProject = targetProject;
        this.targetPackage = targetPackage;
        this.details = details;
    }

    public static String getJoinResultMapId(String javaTableName) {
        return "Join" + javaTableName + "Result";
    }

    public void validate() {
        if (isEmpty(leftTable)) {
            throw new RuntimeException("The left table participating in join operation cannot be empty");
        }
        if (isEmpty(targetProject)) {
            throw new RuntimeException("The target project path to store xml mapper cannot be empty");
        }
        if (isEmpty(targetPackage)) {
            throw new RuntimeException("The target package path to store xml mapper cannot be empty");
        }
        if (details == null || details.isEmpty()) {
            throw new RuntimeException("The join target table detail cannot be empty");
        }
        for (Pair<String, JoinTarget> detail : details) {
            String leftTableColumn = detail.getLeft();
            if (isEmpty(leftTableColumn)) {
                throw new RuntimeException("The column of left table participating in join operation cannot be empty");
            }
            detail.getRight().validate();
        }
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public String getLeftTable() {
        return leftTable;
    }

    public void setLeftTable(String leftTable) {
        this.leftTable = leftTable;
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public List<Pair<String, JoinTarget>> getDetails() {
        return details;
    }

    public void setDetails(List<Pair<String, JoinTarget>> details) {
        this.details = details;
    }
}
