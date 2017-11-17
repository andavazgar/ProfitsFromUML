package com.constraints.classModel;

import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.classes.node.ClassNode;
import com.horstmann.violet.product.diagram.classes.node.NodeRelation;

import java.util.ArrayList;


public class CheckClassModelConstraints {
    private GraphFile graphFile;
    private IGraph graph;
    private ArrayList<ClassNode> nodes = new ArrayList<ClassNode>();
    private ArrayList<IEdge> edges;
    
    // CheckClassModelConstraints constructor
    public CheckClassModelConstraints(GraphFile graphFile) {
        this.graphFile = graphFile;
        this.graph = graphFile.getGraph();
        this.edges = new ArrayList<>(graph.getAllEdges());
        
        for (INode inode : graph.getAllNodes()) {
            if (inode instanceof ClassNode) {
                ClassNode classNode = (ClassNode) inode;
                this.nodes.add(classNode);
            }
        }
    }
    
    public void constraintDetect() {
        
        ArrayList<NodeRelation> relations;
        String notification = "";
        
        // Record all the relations between all nodes in the class diagram
        for (IEdge iedge : edges) {
            String edgeMessage = iedge.getClass().toString();
            int position = edgeMessage.indexOf("edge");
            String edgeType = edgeMessage.substring(position + 5);
            
            if (edgeType.equals("CompositionEdge") || edgeType.equals("AggregationEdge") || edgeType.equals("InheritanceEdge")) {
                // check if the nodes on the two sides are ClassNode
                INode startNode = iedge.getStartNode();
                INode endNode = iedge.getEndNode();
                
                if (startNode instanceof ClassNode && endNode instanceof ClassNode) {
                    NodeRelation relation = new NodeRelation(edgeType, (ClassNode) endNode);
                    ((ClassNode) startNode).addRelation(relation);
                }
            }
        }
        
        // Start checking the constrain for each node
        for (ClassNode node : nodes) {
            relations = node.getRelationsArr();
            
            if (relations != null) {
                // Test 1: check if a class has multiple different recursive relationships
                int compositionCtr = 0;
                int aggregationCtr = 0;
                String notificationMessage = "";
                
                for (NodeRelation relationship : relations) {
                    // test1 : starts
                    if (relationship.getNode().getId() == node.getId()) {
                        if (relationship.getRelation().equals("CompositionEdge")) {
                            compositionCtr++;
                        }
    
                        if (relationship.getRelation().equals("AggregationEdge")) {
                            aggregationCtr++;
                        }
                    }
                    // end of test1
                    
                    
                    // test2 & test3: starts
                    if (relationship.getNode().getId() != node.getId()) {
                        // if the node at the other side of the edge also has the same relationship with the current node
                        if (relationship.getNode().searchRelation(node, relationship.getRelation())) {
                            String nodeName1 = node.getName().toString();
                            String nodeName2 = relationship.getNode().getName().toString();
    
                            if (nodeName1.length() == 0) {
                                nodeName1 = "Unnamed Class";
                            }
                            if (nodeName2.length() == 0) {
                                nodeName2 = "Unnamed Class";
                            }
                            
                            if (relationship.getRelation().equals("AggregationEdge")) {
                                notificationMessage = "Node: [" + nodeName1 + "] "
                                        + "has bidirectional aggregation relationship with Node: ["
                                        + nodeName2 + "]";
                                notification = notification + notificationMessage + "\n";
                            }
                            
                            if (relationship.getRelation().equals("CompositionEdge")) {
                                notificationMessage = "Node: [" + nodeName1 + "] "
                                        + "has bidirectional composition relationship with Node: ["
                                        + nodeName2 + "]";
                                notification = notification + notificationMessage + "\n";
                            }
                            
                            if (relationship.getRelation().equals("InheritanceEdge")) {
                                if (node.searchRelation(relationship.getNode(), "CompositionEdge") || relationship.getNode().searchRelation(node, "CompositionEdge")) {
                                    notificationMessage = "Node: [" + nodeName1 + "] "
                                            + "has bidirectional inheritance with composite relationship with Node: ["
                                            + nodeName2 + "]";
                                    notification = notification + notificationMessage + "\n";
                                }
                            }
                        }
                    }
                    // end of test2 & test3
                    
                }
                
                // test1 : result analysis
                if (compositionCtr + aggregationCtr > 1) {
                    String nodeName = node.getName().toString();
                    
                    if (nodeName.length() == 0) {
                        nodeName = "Unnamed Class";
                    }
                    
                    if (compositionCtr == 0) {
                        notificationMessage = "Node: [" + nodeName + "] has multiple aggregation relationships";
                        notification = notification + notificationMessage + "\n";
                    }
                    else if (aggregationCtr == 0) {
                        notificationMessage = "Node: [" + nodeName + "] has multiple composition relationships";
                        notification = notification + notificationMessage + "\n";
                    }
                    else {
                        notificationMessage = "Node: [" + nodeName + "] has both aggregation and composition relationships";
                        notification = notification + notificationMessage + "\n";
                    }
                }
                
            }
            
        }
        
        // generate the detection result
        if (!notification.equals("")) {
            //System.out.println(notification); // print out the notification message
            DisplayWarning.DisplayDetectionMessage("Warning", notification);
        }
        
        // reset all the relations array in each node
        for (ClassNode node : nodes) {
            node.resetRelationsArr();
        }
    }
    
}
