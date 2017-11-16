package com.constraints.classModel;

import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.classes.node.ClassNode;
import com.horstmann.violet.product.diagram.classes.node.NodeRelation;
import com.constraints.classModel.DisplayWarning;
import java.util.ArrayList;


public class CheckClassModelConstraints 
{
	private GraphFile graphFile;
	private IGraph graph;
	private ArrayList<ClassNode> nodes = new ArrayList<ClassNode>();
	private ArrayList<IEdge> edges;
	private ArrayList<NodeRelation> relations;
	private String notification = "";
	
	// CheckClassModelConstraints constructor
	public CheckClassModelConstraints(GraphFile graphFile)
	{
		this.graphFile = graphFile;
		this.graph = graphFile.getGraph();
		this.edges = new ArrayList<>(graph.getAllEdges());
		
        for (INode inode: graph.getAllNodes()) 
        {
            if (inode instanceof ClassNode) 
            {
            	System.out.println(inode);
                ClassNode classNode = (ClassNode)inode;
                this.nodes.add(classNode);
            }
        }
	}
	
	public void constraintDetect()
	{	
    	// Record all the relations between all nodes in the class diagram
    	for(IEdge iedge : edges)
    	{ 
    	    String edgeMessage = iedge.getClass().toString();
    	    int position =  edgeMessage.indexOf("edge");
    	    String edgeType = edgeMessage.substring(position + 5);
    	    
    		if(edgeType.equals("CompositionEdge") || edgeType.equals("AggregationEdge") || edgeType.equals("InheritanceEdge"))
    		{
    			// check if the nodes on the two sides are ClassNode
    			INode startNode = iedge.getStartNode();
    			INode endNode = iedge.getEndNode();
    			
    			if(startNode instanceof ClassNode && endNode instanceof ClassNode)
    			{
        			NodeRelation relation = new NodeRelation(edgeType, (ClassNode)endNode);
        			((ClassNode)startNode).setupRelationArr(); 				// initialize the relation array
        			((ClassNode)startNode).addRelation(relation);
    				
    			}
    		}
    	}
    	
    	// Start checking the constrain for each node
    	for(ClassNode node : nodes)
    	{
    		relations = node.getRelationArr();
    		
    		if(relations != null)
    		{
        		// Test 1: check if a class has multiple different recursive relationships
        		int compositionCtr = 0;
        		int aggregationCtr = 0;
        		
        		for(NodeRelation relationship : relations)
        		{
        			// test1 : starts
        			if(relationship.getNode().getId() == node.getId())
        			{
        				if(relationship.getRelation().equals("CompositionEdge"))
        					compositionCtr++;
        					
        				if(relationship.getRelation().equals("AggregationEdge"))
        					aggregationCtr++;
        			}
        			// end of test1
        			

        			// test2 & test3: starts
        			if(relationship.getNode().getId() != node.getId())
        			{
        				// if the node at the other side of the edge also has the same relationship with the current node
        				if(relationship.getNode().searchRelation(node, relationship.getRelation()))
        				{
    						String nodeName1 = node.getName().toString();
    						String nodeName2 = relationship.getNode().getName().toString();
    						
    		    			if(nodeName1.length() == 0)
    		    				nodeName1 = "unNamedClass";
    		    			if(nodeName2.length() == 0)
    		    				nodeName2 = "unNamedClass";
    		    			
        					if(relationship.getRelation().equals("AggregationEdge"))
        					{
            					String notifMessage = "Node: [" + nodeName1 + "] "
            							+ "has bidirection aggregation relationship with Node: [" 
            							+ nodeName2 + "]"; 
            					notification = notification + notifMessage + "\n";
        					}
        					
        					if(relationship.getRelation().equals("CompositionEdge"))
        					{
        						String notifMessage = "Node: [" + nodeName1 + "] "
        								+ "has bidirection composition relationship with Node: [" 
        								+ nodeName2 + "]";
            					notification = notification + notifMessage + "\n";
        					}
        					
        					if(relationship.getRelation().equals("InheritanceEdge"))
        					{
        						if(node.searchRelation(relationship.getNode(), "CompositionEdge") || relationship.getNode().searchRelation(node, "CompositionEdge"))
        						{
        	 						String notifMessage = "Node: [" + nodeName1 + "] "
            								+ "has bidirection inheritance with composite relationship with Node: [" 
            								+ nodeName2 + "]";
                					notification = notification + notifMessage + "\n";
        						}
        					}
        				}
        			}
        			// end of test2 & test3
        			
        		}
        		
        		// test1 : result analysis
    			if(compositionCtr + aggregationCtr > 1)
    			{
					String nodeName = node.getName().toString();
	    			if(nodeName.length() == 0)
	    				nodeName = "unNamedClass";
	    			
    				if(compositionCtr == 0)
    				{
    					String noficationMessage = "Node: [" + nodeName + "] has multiple aggregation relationships";
    					notification = notification + noficationMessage + "\n";
    				}
    				else if(aggregationCtr == 0)
    				{
    					String noficationMessage = "Node: [" + nodeName + "] has multiple composition relationships";
    					notification = notification + noficationMessage + "\n";
    				}
    				else
    				{
    					String noficationMessage = "Node: [" + nodeName + "] has both aggregation and compostion relationships";
    					notification = notification + noficationMessage + "\n";
    				}
    			}
        	
        	}
    		
    	}

    	// generate the detection result
    	if(notification.equals(""))
    	{
    		this.notification = "There is no constrain detected in the class diagram";
    	}
    	
    	//System.out.println(notification); // print out the notification message
    	DisplayWarning.DisplayDetectionMessage("Warning", notification);
    	
    	// reset all the relations array in each node
    	for(ClassNode node : nodes)
    	{
    		node.resetRelationArr();
    	}
    	
    	notification = "";
	}

}
