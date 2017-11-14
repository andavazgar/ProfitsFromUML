package com.statistics.classModel;

public class ClassNodeStats {
    
    private int attributes;
    private int methods;
    private int CBO;
    
    public ClassNodeStats() {
    
    }
    
    public ClassNodeStats(int attributes, int methods, int CBO) {
        this.attributes = attributes;
        this.methods = methods;
        this.CBO = CBO;
    }
    
    public int getNumOfAttributes() {
        return attributes;
    }
    
    public void setNumOfAttributes(int attributes) {
        this.attributes = attributes;
    }
    
    public int getNumOfMethods() {
        return methods;
    }
    
    public void setNumOfMethods(int methods) {
        this.methods = methods;
    }
    
    public int getNumOfCBO() {
        return CBO;
    }
    
    public void setNumOfCBO(int CBO) {
        this.CBO = CBO;
    }
}
