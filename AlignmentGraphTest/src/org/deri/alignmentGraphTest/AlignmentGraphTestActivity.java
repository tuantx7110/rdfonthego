package org.deri.alignmentGraphTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.deri.android.pim.reasoner.AlignedGraph;
import org.deri.android.pim.reasoner.AlignmentRule;
import org.deri.android.pim.reasoner.MasterNode;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;



import android.app.Activity;
import android.os.Bundle;

public class AlignmentGraphTestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        addTriple(100);
//        addTriple(1000);
        addTriple(100);
//        MasterNode mN	=	new MasterNode("10000triples");
//        mN.Test();
    }
    
    public void addTriple(int number){
    	System.out.println("Add" + Integer.toString(number));

    	final String appFolder	=	android.os.Environment.getExternalStorageDirectory()+ "/" +Integer.toString(number) +"/" ;
    	File f =	new File(appFolder);
    	f.mkdirs();
    	try {
    		FileWriter jeBDB		=   new FileWriter(appFolder + "ALWrite"+ Integer.toString(number) + ".txt");
    		BufferedWriter out		=	new BufferedWriter(jeBDB);
	
    	
    		Node s = Node.createURI("http://me");
    	    Node p = Node.createURI("http://facebook.com/schema/user#friend");
	
    	    AlignmentRule	rule	=	new AlignmentRule("friends");
            rule.addRule();
            AlignedGraph	graph = new AlignedGraph("ALLGraph",rule);
        	
            long start = System.currentTimeMillis();
    		for (int i=1;i<number;i++){
    			Node o = Node.createURI("http://"+ i );
	   			Triple t	=	new Triple(s,p,o);
	   			graph.add(t);
	   			if (i%100==0) {
	   				long finish = System.currentTimeMillis();
	   				long time   = finish - start;
	   				System.out.println(i);
	   				out.write(Integer.toString(i)+",");
	   				out.write(Long.toString(time) + "\n");
	   				start = finish;
	   			}
    		}
    
    	graph.sync();
    	graph.close();
    	jeBDB.flush();
    	out.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}