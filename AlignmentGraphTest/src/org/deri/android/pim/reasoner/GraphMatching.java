package org.deri.android.pim.reasoner;

import android.util.Log;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;

import deri.org.store.BDBGraph;

//=========================================================================================================
// masterNodeMatching -> match all localNode properties to the masterNode
// mergingMasterNode
//
//
//=========================================================================================================

public class GraphMatching {
	
	private BDBGraph graph;
	
	public static final String DEBUG	=	"System.out";
	
	private MasterNode nodeMaster;
	
	private Node RDFType;
	
	public GraphMatching(String appName){
		
	}
	
	public void sameAsMatch(){
		checkOpen();
		
		ExtendedIterator<Triple> trI;
		
		Triple t	=	new Triple(Node.ANY, OWL.sameAs.asNode(), Node.ANY);
		
		trI			=	graph.find(t);
		
		//Eliminate sameAs Triple
		while(trI.hasNext()){
			t		=	trI.next();
			
			if (t.getPredicate().equals(OWL.sameAs.asNode())){
				
				if ( isNodeType(t.getSubject())&&(isNodeType(t.getObject()))){
					
					nodeMaster.mergeTwoMasterNode(t.getSubject(), t.getObject());
					
					masterNodeMatching(t.getSubject());
					
					masterNodeMatching(t.getObject());
					
				}
				graph.delete(t);
			}
		}
		
		
	}
	
	public boolean isNodeType(Node a){
		
		Triple	t	=	new Triple(a, RDF.type.asNode(), RDFType);
		
		return graph.contains(t);
		
	}	
	
	public  void masterNodeMatching(Node localNode){
		if (!localNode.equals(nodeMaster.getMasterNode(localNode))){
			checkOpen();
		
			ExtendedIterator<Triple> trI;
			Triple t	=	new Triple (localNode, Node.ANY, Node.ANY);
		
			trI	=	graph.find(t);
		
			while (trI.hasNext()){
				t			=	trI.next();
				Triple nt	=	new Triple (nodeMaster.getMasterNode(localNode), t.getPredicate(),t.getObject());
				graph.add(nt);
				graph.delete(t);
			}
		
			t	=	new Triple (Node.ANY, Node.ANY, localNode);
		
			trI	=	graph.find(t);
		
			while (trI.hasNext()){
				t			=	trI.next();
				Triple nt	=	new Triple (t.getSubject(), t.getPredicate(), nodeMaster.getMasterNode(localNode));
				graph.add(nt);
				graph.delete(t);
			
			}
		
			graph.sync();
		}
	}
	
	private  void checkOpen(){
		if ((graph==null)||(graph.isClosed())){
			graph =	new BDBGraph("Test");
		}
	}
	
	public  void close(){
		graph.close();
	}

	public  void Test(){
		checkOpen();
		ExtendedIterator<Triple> trI;
		trI	=	graph.listTriples();
		
		while (trI.hasNext()){
			Log.d(DEBUG, trI.next().toString());
		}
		graph.close();
	}
	
}
