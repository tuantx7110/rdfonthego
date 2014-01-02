/*
 * (c) Copyright 2009 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.sparql.algebra.opt;

import com.hp.hpl.jena.sparql.algebra.OpVisitor;
import com.hp.hpl.jena.sparql.algebra.op.OpAssign;
import com.hp.hpl.jena.sparql.algebra.op.OpBGP;
import com.hp.hpl.jena.sparql.algebra.op.OpConditional;
import com.hp.hpl.jena.sparql.algebra.op.OpDiff;
import com.hp.hpl.jena.sparql.algebra.op.OpDistinct;
import com.hp.hpl.jena.sparql.algebra.op.OpFilter;
import com.hp.hpl.jena.sparql.algebra.op.OpGraph;
import com.hp.hpl.jena.sparql.algebra.op.OpJoin;
import com.hp.hpl.jena.sparql.algebra.op.OpLabel;
import com.hp.hpl.jena.sparql.algebra.op.OpLeftJoin;
import com.hp.hpl.jena.sparql.algebra.op.OpNull;
import com.hp.hpl.jena.sparql.algebra.op.OpProcedure;
import com.hp.hpl.jena.sparql.algebra.op.OpProject;
import com.hp.hpl.jena.sparql.algebra.op.OpPropFunc;
import com.hp.hpl.jena.sparql.algebra.op.OpSequence;
import com.hp.hpl.jena.sparql.algebra.op.OpTable;
import com.hp.hpl.jena.sparql.algebra.op.OpTriple;
import com.hp.hpl.jena.sparql.algebra.op.OpUnion;
import com.hp.hpl.jena.sparql.util.Context;

/**
 * @author  Mr_Anh
 */
public class OpVisitorExprPrepare implements OpVisitor
{
    /**
	 * @uml.property  name="context"
	 * @uml.associationEnd  
	 */
    final private Context context ;

    public OpVisitorExprPrepare(Context context)
    { this.context = context ; }
    
    @Override
    public void visit(OpFilter opFilter)
    {
        opFilter.getExprs().prepareExprs(context) ;
    }
    
    // Assignment
    // ProcEval
    
    @Override
    public void visit(OpLeftJoin opLeftJoin)
    {
        if ( opLeftJoin.getExprs() != null )
            opLeftJoin.getExprs().prepareExprs(context) ;
    }

	@Override
	public void visit(OpBGP opBGP) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpTriple opTriple) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpTable opTable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpNull opNull) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpProcedure opProc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpPropFunc opPropFunc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpGraph opGraph) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpLabel opLabel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpJoin opJoin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpSequence opSequence) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpDiff opDiff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpUnion opUnion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpConditional opCondition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpDistinct opDistinct) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpAssign opAssign) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OpProject opProject) {
		// TODO Auto-generated method stub
		
	}
}

/*
 * (c) Copyright 2009 Hewlett-Packard Development Company, LP
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