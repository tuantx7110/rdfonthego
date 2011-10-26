/*
 * (c) Copyright 2004, 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * [See end of file]
 */

package com.hp.hpl.jena.sparql.expr;

import java.util.Collection ;
import java.util.Set ;

import com.hp.hpl.jena.sparql.core.Var ;
import com.hp.hpl.jena.sparql.engine.Renamer ;
import com.hp.hpl.jena.sparql.engine.binding.Binding ;
import com.hp.hpl.jena.sparql.function.FunctionEnv ;

public interface Expr
{
    public static final int CMP_GREATER  		= +1 ;
    public static final int CMP_EQUAL    		=  0 ;
    public static final int CMP_LESS     		= -1 ;
    
    public static final int CMP_UNEQUAL  		= -9 ;
    public static final int CMP_INDETERMINATE  	= 2 ;
    
    public boolean isSatisfied(Binding binding, FunctionEnv execCxt) ;
  
    public Set<Var>  getVarsMentioned() ;
    
    public void varsMentioned(Collection<Var> acc) ;
    
    public NodeValue eval(Binding binding, FunctionEnv env) ;
    
    public Expr copySubstitute(Binding binding) ;

    public Expr copySubstitute(Binding binding, boolean foldConstants, Renamer renamer) ;

    public Expr deepCopy() ;
    
    public boolean isVariable() ;
    
    public String  getVarName() ;
    
    public ExprVar getExprVar() ;
    
    public Var asVar() ;

    public boolean   isConstant() ;
    
    public NodeValue getConstant() ;
    
    public boolean isFunction() ;
    
    public ExprFunction getFunction() ;
    
    public void visit(ExprVisitor visitor) ;//

}

/*
 *  (c) Copyright 2004, 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
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
