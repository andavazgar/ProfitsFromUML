package com.statistics.classModel;

import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.classes.node.ClassNode;
import com.google.gson.*;

import java.util.ArrayList;


public class StatisticsClassModel {
    
    private GraphFile graphFile;
    private String diagramName;
    private IGraph graph;
    private ArrayList<ClassNode> nodes = new ArrayList<ClassNode>();
    private ArrayList<IEdge> edges;
    private ClassDiagramStats statistics;
    
    public StatisticsClassModel(GraphFile graphFile) {
        
        this.graphFile = graphFile;
        this.diagramName = graphFile.getFilename();
        this.graph = graphFile.getGraph();
        this.edges = new ArrayList<>(graph.getAllEdges());
        statistics = new ClassDiagramStats(this.diagramName);
        
        for (INode inode: graph.getAllNodes()) {
            if (inode instanceof com.horstmann.violet.product.diagram.classes.node.ClassNode) {
                ClassNode classNode = (ClassNode) inode;
    
                this.nodes.add(classNode);
            }
        }
    }
    
    public String generateStatistics() {
        
        for (ClassNode node: nodes) {
            String nodeName = node.getName().toString();
            String[] attributes = node.getAttributes().toString().split("\\|");
            String[] methods = node.getMethods().toString().split("\\|");
            int attributesNum = 0;
            int methodsNum = 0;
            
            if (!attributes[0].isEmpty()) {
                attributesNum = attributes.length;
            }
            
            if (!methods[0].isEmpty()) {
                methodsNum = methods.length;
            }
            
            statistics.addClassNode(nodeName, attributesNum, methodsNum, 0);
        }
        
        for (IEdge iedge: edges) {
            String startNode = ((ClassNode) iedge.getStartNode()).getName().toString();
            String endNode = ((ClassNode) iedge.getEndNode()).getName().toString();
            String dependencyType = iedge.getClass().getName();
            String dependency = dependencyType.substring(dependencyType.lastIndexOf(".") + 1);
            
            int numDependencies = statistics.getClassNode(startNode).getNumOfCBO();
            numDependencies++;
    
            statistics.getClassNode(startNode).setNumOfCBO(numDependencies);
        }
        
        statistics.generateGeneralStatistics();
        
        return serializeStatistics();
    }
    
    private String serializeStatistics() {
        Gson gson = new Gson();
        String json = gson.toJson(statistics);
        
        return json;
    }
}

