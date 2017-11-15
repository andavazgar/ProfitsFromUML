package com.horstmann.violet.product.diagram.abstracts;

import com.horstmann.violet.product.diagram.abstracts.node.INode;

// An inner class Relation is declared so that AbstractNode and AbstractGraph can
// use it to implement the constrain detection
public class NodeRelation 
{
	private String relation = null;
	private INode node = null;
	
	public NodeRelation()
	{}
	
	public NodeRelation(String relation, INode node)
	{
		this.relation = relation;
		this.node = node;
	}

	public INode getNode()
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
