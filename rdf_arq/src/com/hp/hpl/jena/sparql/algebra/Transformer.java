/*
 * (c) Copyright 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * (c) Copyright 2010 Talis Information Ltd.
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.sparql.algebra;

import java.util.ArrayList ;
import java.util.Iterator ;
import java.util.List ;
import java.util.Stack ;

import com.hp.hpl.jena.sparql.algebra.op.Op0 ;
import com.hp.hpl.jena.sparql.algebra.op.Op1 ;
import com.hp.hpl.jena.sparql.algebra.op.Op2 ;
import com.hp.hpl.jena.sparql.algebra.op.OpN ;
import com.hp.hpl.jena.sparql.util.ALog ;

public class Transformer
{
    private static Transformer singleton = new Transformer();
    
    public static Transformer get() { return singleton; }
    
    public static void set(Transformer value) { Transformer.singleton = value; }
    
    public static Op transform(Transform transform, Op op)
    { return get().transformation(transform, op, null, null) ; }
    
    public static Op transform(Transform transform, Op op, OpVisitor beforeVisitor, OpVisitor afterVisitor)
    {
        return get().transformation(transform, op, beforeVisitor, afterVisitor) ;
    }

    protected Op transformation(Transform transform, Op op, OpVisitor beforeVisitor, OpVisitor afterVisitor)
    {
        ApplyTransformVisitor v = new ApplyTransformVisitor(transform) ;
        return transformation(v, op, beforeVisitor, afterVisitor) ;
    }
    
    protected Op transformation(ApplyTransformVisitor transformApply,
                             Op op, OpVisitor beforeVisitor, OpVisitor afterVisitor)
    {
        if ( op == null )
        {
            ALog.warn(this, "Attempt to transform a null Op - ignored") ;
            return op ;
        }

        OpWalker.walk(op, transformApply, beforeVisitor, afterVisitor) ;
        Op r = transformApply.result() ;
        return r ;
    }

    
    protected Transformer() { }
    
    protected static boolean noDupIfSame = true ;
    
    public static
    class ApplyTransformVisitor extends OpVisitorByType
    {
        protected final Transform transform ;
        private final Stack<Op> stack = new Stack<Op>() ;
        protected final Op pop() { return stack.pop(); }
        
        protected final void push(Op op)
        { 
             stack.push(op) ;
        }
        
        public ApplyTransformVisitor(Transform transform)
        { 
            this.transform = transform ;
        }
        
        public final Op result()
        { 
            if ( stack.size() != 1 )
                ALog.warn(this, "Stack is not aligned") ;
            return pop() ; 
        }
    
        @Override
        protected void visit0(Op0 op)
        {
            push(op.apply(transform)) ;
        }
        
        @Override
        protected void visit1(Op1 op)
        {
            Op subOp = null ;
            if ( op.getSubOp() != null )
                subOp = pop() ;
            push(op.apply(transform, subOp)) ;
        }
    
        @Override
        protected void visit2(Op2 op)
        { 
            Op left = null ;
            Op right = null ;
    
            if ( op.getRight() != null )
                right = pop() ;
            if ( op.getLeft() != null )
                left = pop() ;
            Op opX = op.apply(transform, left, right) ; 
            push(opX) ;
        }
        
        @Override
        protected void visitN(OpN op)
        {
            List<Op> x = new ArrayList<Op>(op.size()) ;
            
            for ( Iterator<Op> iter = op.iterator() ; iter.hasNext() ; )
            {
                iter.next();
                Op r = pop() ;
                if ( r != null )x.add(0, r) ;
            }
            Op opX = op.apply(transform, x) ;  
            push(opX) ;
        }
    }
}