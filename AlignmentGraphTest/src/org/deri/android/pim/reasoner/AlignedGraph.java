package org.deri.android.pim.reasoner;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import deri.org.store.BDBGraph;
import deri.org.store.BDBStore;
import deri.org.store.Configuration;

public class AlignedGraph	extends GraphBase{
	AlignmentRule	rule;
	MasterNode	masterNode;
	private BDBStore store;
	public Configuration conf;
	
	static int i =0;
	public AlignedGraph(String appName,AlignmentRule rule) {
		conf 		=	new Configuration(appName);
		store		=	new BDBStore(conf);
		
		this.rule	=	rule;
		masterNode	=	new MasterNode(appName);
	}

	public static String DEBUG	=	"System.out";
	
	
	
	@Override
	public void add(Triple t){
		if (rule.modify(t.getPredicate())!=null){
//			long a = System.currentTimeMillis();
			Node s = masterNode.addToMasterNode(t.getSubject());
//			System.out.println("Modify s: " + (System.currentTimeMillis() - a));
			
//			a = System.currentTimeMillis();
			Node p = rule.modify(t.getPredicate());
//			System.out.println("Modify p: " + (System.currentTimeMillis() - a));
			
//			a = System.currentTimeMillis();
			Node o =  masterNode.addToMasterNode(t.getObject());
//			System.out.println("Modify o: " + (System.currentTimeMillis() - a));
			
			Triple addt	=	new Triple(s,p,o);
			
			
			store.add(addt);
			
		}
			
		if (t.getPredicate().getNameSpace().equals( OWL.getURI())) {
			Triple addt	=	new Triple(masterNode.addToMasterNode(t.getSubject()),
					   		(t.getPredicate()),
					   		masterNode.addToMasterNode(t.getObject()));

			store.add(addt);
		}
		
		if (t.getPredicate().getNameSpace().equals(RDFS.getURI())) {
			Triple addt	=	new Triple(masterNode.addToMasterNode(t.getSubject()),
									   (t.getPredicate()),
									   masterNode.addToMasterNode(t.getObject()));
			store.add(addt);
		}
		
		if (t.getPredicate().getNameSpace().equals(RDF.getURI())) {
			Triple addt	=	new Triple(masterNode.addToMasterNode(t.getSubject()),
									   (t.getPredicate()),
									   masterNode.addToMasterNode(t.getObject()));
	
			store.add(addt);
		}
	}
	
	
	@Override()
	public void sync(){
		masterNode.sync();
		super.sync();
	}
	
	@Override
	public void close(){
		rule.close();
		masterNode.close();
		super.close();
	}
	
	public void addRule(Triple t){};
	public void addRule(BDBGraph g){};
	public void addRule(String URL){}


	@Override
	protected ExtendedIterator<Triple> graphBaseFind(TripleMatch m) {
		return  store.find(m.asTriple());
	};
}
