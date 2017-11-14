package com.statistics.classModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassDiagramStats {
    
    private String diagramName;
    private int numOfClasses;
    private int numOfRelationships;
    private Map<String, ClassNodeStats> classes = new HashMap<>();
    
    public ClassDiagramStats(String diagramName) {
        this.diagramName = diagramName;
        this.numOfClasses = 0;
        this.numOfRelationships = 0;
    }
    
    public Set<String> getClassNames() {
        
        return classes.keySet();
    }
    
    public ClassNodeStats addClassNode(String classNodeName) {
        ClassNodeStats node = new ClassNodeStats();
    
        classes.put(classNodeName, node);
        
        return classes.get(classNodeName);
    }
    
    public ClassNodeStats addClassNode(String classNodeName, int numOfAttributes, int numOfMethods, int numOfCBO) {
        ClassNodeStats node = new ClassNodeStats(numOfAttributes, numOfMethods, numOfCBO);
    
        classes.put(classNodeName, node);
    
        return classes.get(classNodeName);
    }
    
    public ClassNodeStats getClassNode(String classNodeName) {
        
        ClassNodeStats classNode;
        
        if (classes.containsKey(classNodeName)) {
            classNode = classes.get(classNodeName);
        }
        else {
            classNode = this.addClassNode(classNodeName);
        }
        
        return classNode;
    }
    
    public void generateGeneralStatistics() {
        calculateNumOfClasses();
        calculateNumOfRelationships();
    }
    
    private void calculateNumOfClasses() {
        numOfClasses = classes.size();
    }
    
    private void calculateNumOfRelationships() {
        int relationships = 0;
        
        for (Map.Entry<String, ClassNodeStats> node : classes.entrySet()) {
            relationships += node.getValue().getNumOfCBO();
        }
    
        numOfRelationships = relationships;
    }
}
