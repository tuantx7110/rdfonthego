/*
 * (c) Copyright 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * [See end of file]
 */

package com.hp.hpl.jena.sparql.util;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.shared.PrefixMapping;

import com.hp.hpl.jena.sparql.ARQConstants;
import com.hp.hpl.jena.sparql.expr.*;
import com.hp.hpl.jena.sparql.lang.sparql_10.ParseException;
import com.hp.hpl.jena.sparql.lang.sparql_10.SPARQLParser10;
import com.hp.hpl.jena.sparql.lang.sparql_10.SPARQLParser10TokenManager;
import com.hp.hpl.jena.sparql.lang.sparql_10.Token;
import com.hp.hpl.jena.sparql.lang.sparql_10.TokenMgrError;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QueryParseException;

//import org.openjena.atlas.io.IndentedLineBuffer ;
//import org.openjena.atlas.io.IndentedWriter ;


/** Misc support for Expr
 * @author Andy Seaborne
 */

public class ExprUtils
{
 
    public static Expr nodeToExpr(Node n)
    {
        if ( n.isVariable() )
            return new ExprVar(n) ;
        return NodeValue.makeNode(n) ;
    }
    
    public static String joinList(List<Expr> args, String sep)
    {
        if ( args == null )
            return "<<Null list>>" ;
        
        if ( args.size() == 0 )
            return "<<Empty list>>" ;
        
        StringBuffer s = new StringBuffer() ;
        
        boolean first = true ;

        for ( Expr ex :  args )
        {
            if ( ! first ) s.append(sep) ;
            // Values are printed withquoting.
            if (! (ex instanceof NodeValue ))
                          s.append(ex.toString()) ;
            first = false ;
        }
        return s.toString() ;
    }
    
    public static Expr parse(String s)
    {
        return parse(s, ARQConstants.getGlobalPrefixMap()) ;
    }
    
    public static Expr parse(String s, PrefixMapping pmap)
    { 
        Query query = QueryFactory.make() ;
        query.setPrefixMapping(pmap) ;
        return parse(query, s, true) ;
    }

    public static Expr parse(Query query, String s, boolean checkAllUsed)
    {
        try {
            Reader in = new StringReader(s) ;
            SPARQLParser10 parser = new SPARQLParser10(in) ;
            parser.setQuery(query) ;
            Expr expr = parser.Expression() ;
            
            if ( checkAllUsed )
            {
                Token t = parser.getNextToken() ;
                if ( t.kind != SPARQLParser10TokenManager.EOF )
                    throw new QueryParseException("Extra tokens beginning \""+t.image+"\" starting line "+t.beginLine+", column "+t.beginColumn,
                                                  t.beginLine, t.beginColumn) ;
            }
            return expr ;
        } catch (ParseException ex)
        { throw new QueryParseException(ex.getMessage(),
                                        ex.currentToken.beginLine,
                                        ex.currentToken.beginLine) ;
        }
        catch (TokenMgrError tErr)
        {
            throw new QueryParseException(tErr.getMessage(), -1, -1) ;
        }
        catch (Error err)
        {
          
            String tmp = err.getMessage() ;
            if ( tmp == null )
                throw new QueryParseException(err,-1, -1) ;
            throw new QueryParseException(tmp,-1, -1) ;
        }
    }

    public static NodeValue parseNodeValue(String s)
    {
        Node n = NodeFactory.create(s) ;
        NodeValue nv = NodeValue.makeNode(n) ;
        return nv ;
    }
    

    public static String strComparison(int cmp)
    {
        switch (cmp)
        {
            case Expr.CMP_GREATER:   return "GT" ;
            case Expr.CMP_EQUAL:     return "EQ" ;
            case Expr.CMP_LESS:      return "LT" ;
            case Expr.CMP_INDETERMINATE:      return "indeterminate" ;
            default:            return "??" ;
        }
    }
    
   
}

/*
 *  (c) Copyright 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 *  All rights reserved.
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
