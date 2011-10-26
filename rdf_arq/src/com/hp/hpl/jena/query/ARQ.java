/*
 * (c) Copyright 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.query;

import com.hp.hpl.jena.sparql.ARQConstants ;
import com.hp.hpl.jena.sparql.engine.main.StageBuilder ;
import com.hp.hpl.jena.sparql.expr.nodevalue.XSDFuncOp ;
import com.hp.hpl.jena.sparql.util.Context ;
import com.hp.hpl.jena.sparql.util.Symbol ;

/**
 * ARQ - miscellaneous settings
 * @author  Andy Seaborne
 */
// modified from arq 2.8.5

public class ARQ
{
    public static final String arqIRI = "http://jena.hpl.hp.com/#arq" ;

    public static final String arqNS = "http://jena.hpl.hp.com/ARQ#" ;
    
    public static final String arqSymbolPrefix = "arq" ;
    
    public static final Symbol strictSPARQL =	ARQConstants.allocSymbol("strictSPARQL") ;
    
    public static final Symbol constantBNodeLabels =  ARQConstants.allocSymbol("constantBNodeLabels") ;
    
    public static final Symbol enablePropertyFunctions =	ARQConstants.allocSymbol("enablePropertyFunctions") ;

    public static final Symbol enableExecutionTimeLogging =	ARQConstants.allocSymbol("enableExecutionTimeLogging") ;

    public static final Symbol outputGraphBNodeLabels =	ARQConstants.allocSymbol("outputGraphBNodeLabels") ;

    public static final Symbol inputGraphBNodeLabels =	ARQConstants.allocSymbol("inputGraphBNodeLabels") ;
    

    public static void enableBlankNodeResultLabels() { enableBlankNodeResultLabels(true) ; }
    
    public static void enableBlankNodeResultLabels(boolean val)
    { 
        	Boolean.valueOf(val) ;
        	Boolean b = Boolean.valueOf(val) ;
        	globalContext.set(inputGraphBNodeLabels, b) ;
        	globalContext.set(outputGraphBNodeLabels, b) ;
    }

    public static final Symbol enableRomanNumerals = ARQConstants.allocSymbol("romanNumerals") ;

    public static final Symbol stageGenerator = ARQConstants.allocSymbol("stageGenerator") ;

    public static final Symbol hideNonDistiguishedVariables = ARQConstants.allocSymbol("hideNonDistiguishedVariables") ;

    public static final Symbol useSAX = ARQConstants.allocSymbol("useSAX") ;

    public static final Symbol regexImpl =  ARQConstants.allocSymbol("regexImpl") ;
    
    public static final Symbol javaRegex =  ARQConstants.allocSymbol("javaRegex") ;
    
    public static final Symbol xercesRegex =  ARQConstants.allocSymbol("xercesRegex") ;
    
    public static void enableOptimizer(boolean state){
        enableOptimizer(ARQ.getContext(), state) ;
    }
    
    public static void enableOptimizer(Context context, boolean state){
        context.set(ARQ.optimization, state) ;
    }
    
    public static final Symbol optimization = ARQConstants.allocSymbol("optimization") ;
    
    public static final Symbol optFilterPlacement = ARQConstants.allocSymbol("optFilterPlacement") ;
    
    @Deprecated
    public static final Symbol filterPlacement = ARQConstants.allocSymbol("optFilterPlacement") ;
    
    public static final Symbol optFilterEquality = ARQConstants.allocSymbol("optFilterEquality") ;

    public static final Symbol optTermStrings = ARQConstants.allocSymbol("optTermStrings") ;

    public static final Symbol optFilterConjunction = ARQConstants.allocSymbol("optFilterConjunction") ;

    public static final Symbol optFilterExpandOneOf = ARQConstants.allocSymbol("optFilterExpandOneOf") ;

    public static final Symbol optFilterDisjunction = ARQConstants.allocSymbol("optFilterDisjunction") ;
    
    public static final Symbol propertyFunctions = ARQConstants.allocSymbol("propertyFunctions") ;
    
    public static final Symbol strictGraph = ARQConstants.allocSymbol("strictGraph") ;
    
    public static final Symbol extensionValueTypes = ARQConstants.allocSymbol("extensionValueTypesExpr") ;

    public static final Symbol generateToList = ARQConstants.allocSymbol("generateToList") ;

    public static void setStrictMode() { setStrictMode(ARQ.getContext()) ; }
    
    public static void setStrictMode(Context context)
    {
        XSDFuncOp.strictDateTimeFO = true ;
        
        context.set(hideNonDistiguishedVariables, 		true) ;
        context.set(strictGraph,           		     	true) ;
        context.set(strictSPARQL,              			true) ;
        context.set(extensionValueTypes,        		false) ;
        context.set(constantBNodeLabels,        		false) ;
        context.set(enablePropertyFunctions,    		false) ;
        context.set(generateToList,             		true) ;
        context.set(regexImpl,                  		xercesRegex) ;
        context.set(filterPlacement,            		false) ;
    }
    
    public static boolean isStrictMode()       { return ARQ.getContext().isTrue(strictSPARQL) ; }
    
    public static void setNormalMode() { setNormalMode(ARQ.getContext()) ; }
        
    public static void setNormalMode(Context context)
    {
        XSDFuncOp.strictDateTimeFO = false ;

        context.set(strictSPARQL,                  "false") ; 
        context.set(constantBNodeLabels,           "true") ;
        context.set(enablePropertyFunctions,       "true") ;
        context.set(strictGraph,                   "false") ;
        context.set(enableRomanNumerals,           "false") ;
        context.set(regexImpl,                     javaRegex) ;
        context.set(filterPlacement,                true) ;
    }
    
    // ----------------------------------
    
    public static final String PATH = "com.hp.hpl.jena.sparql";
   
    public static final String NAME = "ARQ";
   
    private static boolean initialized = false ;

    private static Context globalContext = null ;

    public static synchronized void init()
    { 
        if ( initialized )
            return ;
        initialized = true ;
        globalContext = defaultSettings() ;
        StageBuilder.init() ;
     }
    
 
    static { init() ; }
    
    private static Context defaultSettings()    
    {
        Context context = new Context() ;
        setNormalMode(context) ;
        return context ; 
    }

    public static Context getContext()
    { 
        return globalContext ;
    }
    
    public static void set(Symbol symbol, boolean value)  { getContext().set(symbol, value) ; }
    public static void setTrue(Symbol symbol)             { getContext().setTrue(symbol) ; }
    public static void setFalse(Symbol symbol)            { getContext().setFalse(symbol) ; }
    public static void unset(Symbol symbol)               { getContext().unset(symbol) ; }
   
    public static boolean isTrue(Symbol symbol)           { return getContext().isTrue(symbol) ; }
    public static boolean isFalse(Symbol symbol)          { return getContext().isFalse(symbol) ; }
    public static boolean isTrueOrUndef(Symbol symbol)    { return getContext().isTrueOrUndef(symbol) ; }
    public static boolean isFalseOrUndef(Symbol symbol)   { return getContext().isFalseOrUndef(symbol) ; }

}