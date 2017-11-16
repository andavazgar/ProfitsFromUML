package com.horstmann.violet.product.diagram.classes.node;

import com.horstmann.violet.product.diagram.classes.node.ClassNode;

// An inner class Relation is declared so that AbstractNode and AbstractGraph can
// use it to implement the constrain detection
public class NodeRelation 
{
	private String relation = null;
	private ClassNode node = null;
	
	public NodeRelation()
	{}
	
	public NodeRelation(String relation, ClassNode node)
	{
		this.relation = relation;
		this.node = node;
	}

	public ClassNode getNode()
	{
		return this.node;
	}
	
	public String getRelation()
	{
		return this.relation;
	}
	
	public void showRelationInfo()
	{
		System.out.println("Relationship: " + relation);
		System.out.println("Node : " + node);
	}
}
