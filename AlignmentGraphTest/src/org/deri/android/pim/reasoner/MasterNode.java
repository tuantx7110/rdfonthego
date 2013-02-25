package org.deri.android.pim.reasoner;

import android.util.Log;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL;
import deri.org.store.BDBGraph;

//==============================================================================================================
//
//
//
//
//
//==============================================================================================================
public class MasterNode {
	
	public static final String DEBUG	=	"System.out";
	
	private BDBGraph masterStore;
	
	public MasterNode(String appName){
		masterStore	=	new BDBGraph("masterStore" + appName);
	}
		
	public  Node getMasterNode(Node node){
		checkOpen();
		Triple t	=	new Triple(node, OWL.sameAs.asNode(), Node.ANY);
		ExtendedIterator<Triple> trI;
		trI			=	masterStore.find(t);
		if (trI.hasNext()){
			return trI.next().getObject();
		}
		return node;
	}
	
	void mergeTwoMasterNode(Node personNode1, Node personNode2){
		if (!getMasterNode(personNode1).equals(getMasterNode(personNode2))){
			checkOpen();
			Triple t	=	new Triple (Node.ANY, OWL.sameAs.asNode(), getMasterNode(personNode2));
		
			ExtendedIterator<Triple> trI;
			trI	=	masterStore.find(t);
			while(trI.hasNext()){
				Triple deletingTriple	=	trI.next();
				Triple addingTriple    =    new Triple(deletingTriple.getSubject(), OWL.sameAs.asNode(), getMasterNode(personNode1));
				masterStore.add(addingTriple);
				masterStore.delete(deletingTriple);
			}
		}
	}
	
	public  Node addToMasterNode(Node node){
		if (node.isURI()){
			checkOpen();
			Node masterNode	=	getMasterNode(node);
			Triple t		= 	new Triple(node, OWL.sameAs.asNode(), masterNode);
			masterStore.add(t);
			return masterNode;
		}		
		return node;
	}
	
	private  void checkOpen(){
		if ((masterStore == null)||(masterStore.isClosed())) {masterStore =	new BDBGraph("masterNode");}
	};
	
	public void sync(){
		masterStore.sync();
	}
	
	public  void close(){
		masterStore.close();
	}
	
	
	
	
	
	public  void Test(){
		checkOpen();
//		Log.d("");
		ExtendedIterator<Triple> trI;
		trI	=	masterStore.listTriples();
		
		while (trI.hasNext()){
			Log.d(DEBUG, trI.next().toString());
		}
		masterStore.close();
	}
}
