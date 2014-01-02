/*
 * (c) Copyright 2010 Talis Systems Ltd.
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.sparql.expr.aggregate;

import com.hp.hpl.jena.graph.Node ;
import com.hp.hpl.jena.sparql.engine.Renamer ;
import com.hp.hpl.jena.sparql.engine.binding.Binding ;
import com.hp.hpl.jena.sparql.expr.Expr ;
import com.hp.hpl.jena.sparql.expr.NodeValue ;
import com.hp.hpl.jena.sparql.function.FunctionEnv ;
//import com.hp.hpl.jena.sparql.sse.writers.WriterExpr ;
import com.hp.hpl.jena.sparql.util.ExprUtils ;
import com.hp.hpl.jena.sparql.util.Utils ;

/**
 * @author  Mr_Anh
 */
public class AggGroupConcatDistinct extends AggregatorBase
{
    /**
	 * @uml.property  name="expr"
	 * @uml.associationEnd  
	 */
    private final Expr expr ;
    private final String separatorSeen ;
    /**
	 * @uml.property  name="separator"
	 */
    private final String separator ;

    public AggGroupConcatDistinct(Expr expr, String separator)
    { 
        this(expr, 
             ( separator != null ) ? separator : AggGroupConcat.SeparatorDefault ,
             separator) ;
//        this.expr = expr ; 
//        this.separatorSeen = separator ;
//        this.separator =  ( separator != null ) ? separator : AggGroupConcat.SeparatorDefault ; 
    }

    private AggGroupConcatDistinct(Expr expr, String separator, String separatorSeen)
    {
        this.expr = expr ; 
        this.separatorSeen = separatorSeen ;
        this.separator = separator ; 
    }
    
    public Aggregator copy() { return new AggGroupConcatDistinct(expr, separator, separatorSeen) ; }
    public Aggregator copyRename(Renamer renamer) { return new AggGroupConcat(expr.copySubstitute(null, false, renamer), separator) ; }

    
    
    @Override
    public String toPrefixString()
    {
        String x = "(group_concat distinct " ;
        
//        if ( separatorSeen != null )
//        {
//            String y = StrUtils.escapeString(separatorSeen) ;
//            x = x+"(separator '"+y+"') " ;
//        }
//        x = x+WriterExpr.asString(expr)+")" ;
        return x ; 
    }

    @Override
    protected Accumulator createAccumulator()
    { 
        return new AccGroupConcatDistinct(expr) ;
    }

    /**
	 * @return
	 * @uml.property  name="expr"
	 */
    protected final Expr getExpr() { return expr ; }
    /**
	 * @return
	 * @uml.property  name="separator"
	 */
    protected final String getSeparator() { return separator ; }

    public boolean equalsAsExpr(Aggregator other)
    {
        if ( ! ( other instanceof AggGroupConcatDistinct ) )
            return false ;
        AggGroupConcatDistinct agg = (AggGroupConcatDistinct)other ;
        return Utils.equal(agg.getSeparator(),getSeparator()) && agg.getExpr().equals(getExpr()) ;
    } 

    /* null is SQL-like. */ 
    @Override
    public Node getValueEmpty()     { return null ; } 

    // ---- Accumulator
    /**
	 * @author  Mr_Anh
	 */
    class AccGroupConcatDistinct extends AccumulatorDistinctExpr
    {
        // Sample: first evaluation of the expression that is not an error.
        /**
		 * @uml.property  name="sampleSoFar"
		 * @uml.associationEnd  
		 */
        private NodeValue sampleSoFar = null ;

        public AccGroupConcatDistinct(Expr expr) { super(expr) ; }

        @Override
        public void accumulateDistinct(NodeValue nv, Binding binding, FunctionEnv functionEnv)
        { 
        }

        @Override
        protected void accumulateError(Binding binding, FunctionEnv functionEnv)
        {}
        
        public NodeValue getValue()
        { return sampleSoFar ; }
    }
}

/*
 * (c) Copyright 2010 Talis Systems Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */