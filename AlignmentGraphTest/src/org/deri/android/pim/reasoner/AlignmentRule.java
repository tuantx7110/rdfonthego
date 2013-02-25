package org.deri.android.pim.reasoner;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;

import deri.org.store.BDBGraph;

public class AlignmentRule {
	
	public BDBGraph vocabularyStore;
	
	public AlignmentRule(String appName){
		vocabularyStore	=	new BDBGraph("vocabulary" + appName);
	}
	
	public Node modify(Node node){
		checkOpen();
		Triple t	=	new Triple(node, OWL.sameAs.asNode(), Node.ANY);
		
		ExtendedIterator<Triple> trI;
		
		trI	=	vocabularyStore.find(t);
			
		if (trI.hasNext()) {return trI.next().getObject();}
		return null;
		
	}
	
//	public void addRule(BDBGraph graph);
//	public void addRule(InputStream input);
//	public void addRule(File file);
	
	private void checkOpen(){
		if ((vocabularyStore==null)||(vocabularyStore.isClosed())){
			vocabularyStore	=	new BDBGraph("RulesBase");
		}
	}
	
	public void close(){
		vocabularyStore.close();
	}
	//=============================================================================//
	static final String DOAC 	=	"http://ramonantonio.net/doac/0.1/#";
	//Rule can be added from ....graph,link, etc,
	public void addRule(){
		checkOpen();
	    
		String	UFACEBOOK = "http://facebook.com/schema/user#";
		String	PFACEBOOK = "http://facebook.com/schema/page#";
		String  GFACEBOOK = "http://facebook.com/schema/grous#";
        String	SFACEBOOK = "http://facebook.com/schema/";  
		
		Node a	=	Node.createURI(UFACEBOOK + "friend");
		Node b  = 	OWL.sameAs.asNode();
		Node c	=	Node.createURI("http://xmlns.com/foaf/0.1/knows");
		Triple t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a	=	Node.createURI(UFACEBOOK + "name");
		b  = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/name");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		 
		a  =   Node.createURI(UFACEBOOK + "first_name");
		b  = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/firstName");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		 
		a  =   Node.createURI(UFACEBOOK + "middle_name");
		b  = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/surname");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a  =   Node.createURI(UFACEBOOK + "last_name");
		b  = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/lastName");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		 
		a   =   Node.createURI(UFACEBOOK + "gender");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/gender");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(UFACEBOOK + "link");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/weblog");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(UFACEBOOK + "birthday");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/birthday");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(UFACEBOOK + "email");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/mbox");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(UFACEBOOK + "groups");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/member");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(GFACEBOOK + "name");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/name");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(UFACEBOOK + "interests");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/topic_interest");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(UFACEBOOK + "username");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/nick");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(SFACEBOOK + "school");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(DOAC + "organization" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(SFACEBOOK + "degree");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(DOAC + "title" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(SFACEBOOK + "year");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(DOAC + "end-date" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		
		a   =   Node.createURI(UFACEBOOK + "eudacation");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(DOAC + "eudacation" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		//TODO
		
		a   =   Node.createURI(UFACEBOOK + "work");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(DOAC + "experience" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(SFACEBOOK + "employer");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(DOAC + "organization" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(SFACEBOOK + "position");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(DOAC + "title" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(SFACEBOOK + "start_date");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(DOAC + "start-date" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(SFACEBOOK + "end_date");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(DOAC + "end-date" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(SFACEBOOK + "location");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(VCARD.getURI() + "locality" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(UFACEBOOK + "locality");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(VCARD.getURI() + "locality" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(UFACEBOOK + "website");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/homepage" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(PFACEBOOK + "name");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(RDFS.getURI() + "label" );
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(PFACEBOOK + "category");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI("http://xmlns.com/foaf/0.1/topic");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		
		a   =   Node.createURI(UFACEBOOK + "languages");
		b   = 	OWL.sameAs.asNode();
		c	=	Node.createURI(DOAC+"LanguageSkill");
		t	=	new Triple(a,b,c);
		vocabularyStore.add(t);
		

		
//		a   =   Node.createURI(UFACEBOOK + "religion");
//		a   =   Node.createURI(UFACEBOOK + "languages");
//		a   =   Node.createURI(UFACEBOOK + "bio");
//		a   =   Node.createURI(UFACEBOOK + "political");
//		a   =   Node.createURI(UFACEBOOK + "favorite_athletes");
//		a   =   Node.createURI(UFACEBOOK + "relationship_status");
//		a   =   Node.createURI(UFACEBOOK + "favorite_teams");
//		a   =   Node.createURI(UFACEBOOK + "quotes");
//		a   =   Node.createURI(UFACEBOOK + "inspirational_people");

		vocabularyStore.sync();


	}
	
	
	
}
