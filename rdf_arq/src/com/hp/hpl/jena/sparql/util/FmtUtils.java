/*
 * (c) Copyright 2004, 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * [See end of file]
 */

package com.hp.hpl.jena.sparql.util;

import java.net.MalformedURLException;
import com.hp.hpl.jena.iri.IRI;
import com.hp.hpl.jena.iri.IRIFactory;
import com.hp.hpl.jena.iri.IRIRelativize;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.sparql.ARQInternalErrorException;
import com.hp.hpl.jena.sparql.core.Prologue;

//import org.openjena.atlas.io.IndentedLineBuffer;
//import org.openjena.atlas.io.IndentedWriter ;

/**
 * @author    Andy Seaborne
 */

public class FmtUtils
{
    static final String indentPrefix = "  " ;
    public static boolean multiLineExpr = false ;
    public static boolean printOpName = true ;
    
    /**
	 * @uml.property  name="bNode"
	 * @uml.associationEnd  
	 */
    static NodeToLabelMap bNodeMap = new NodeToLabelMapBNode("b", false) ;
    
    
    public static String stringForString(String str)
    {
        StringBuilder sbuff = new StringBuilder() ;
        sbuff.append("\"") ;
        stringEsc(sbuff, str, true) ;
        sbuff.append("\"") ;
        return sbuff.toString() ; 
    }
    

    static public String stringForURI(String uri)
    { return "<"+stringEsc(uri)+">" ; }

    static public String stringForURI(String uri, Prologue prologue)
    {
        return stringForURI(uri, prologue.getBaseURI(), prologue.getPrefixMapping()) ;
    }

    static public String stringForURI(String uri, String baseIRI)
    {
        return stringForURI(uri, baseIRI, null) ;
    }
    

    static public String stringForURI(String uri, PrefixMapping mapping)
    { return stringForURI(uri, null, mapping) ; }
    
    static public String stringForURI(String uri, String base, PrefixMapping mapping)
    {
        if ( mapping != null )
        {
            String pname = prefixFor(uri, mapping) ;
            if ( pname != null )
                return pname ;
        }
        if ( base != null )
        {
            String x = abbrevByBase(uri, base) ;
            if ( x != null ) 
                return "<"+x+">" ;
        }
        return stringForURI(uri) ; 
    }
    
    
    static private int relFlags = IRIRelativize.SAMEDOCUMENT | IRIRelativize.CHILD ;
    
    static public String abbrevByBase(String uri, String base)
    {
        IRI baseIRI = IRIFactory.jenaImplementation().construct(base) ;
        IRI rel = baseIRI.relativize(uri, relFlags) ;
        String r = null ;
        try { r = rel.toASCIIString() ; }
        catch (MalformedURLException  ex) { r = rel.toString() ; }
        return r ;
    }
    
    private static String prefixFor(String uri, PrefixMapping mapping)
    {
        if ( mapping == null ) return null ;
        
        String pname = mapping.shortForm(uri) ;
        if ( pname != uri && checkValidPrefixName(pname) )
            return pname ;
        pname = mapping.qnameFor(uri) ;
        if ( pname != null && checkValidPrefixName(pname) )
            return pname ;
        return null ;
    }
    
    private static boolean checkValidPrefixName(String prefixedName)
    {
        // Split it to get the parts.
        int i = prefixedName.indexOf(':') ;
        if ( i < 0 )
            throw new ARQInternalErrorException("Broken short form -- "+prefixedName) ;
        String p = prefixedName.substring(0,i) ;
        String x = prefixedName.substring(i+1) ; 
        // Check legality
        if ( checkValidPrefix(p) && checkValidLocalname(x) )
            return true ;
        return false ;
    }
    
    private static boolean checkValidPrefix(String prefixStr)
    {
        if ( prefixStr.startsWith("_"))
            // Should .equals?? 
            return false ;
        return checkValidLocalname(prefixStr) ;
    }
    
    private static boolean checkValidLocalname(String localname)
    {
        if ( localname.length() == 0 )
            return true ;
        
        for ( int idx = 0 ; idx < localname.length() ; idx++ )
        {
            char ch = localname.charAt(idx) ;
            if ( ! validPNameChar(ch) )
                return false ;
        }
        
        // Test start and end - at least one character in the name.
        
        if ( localname.endsWith(".") )
            return false ;
        if ( localname.startsWith(".") )
            return false ;
        
        return true ;
    }
    
    private static boolean validPNameChar(char ch)
    {
        if ( Character.isLetterOrDigit(ch) ) return true ;
        if ( ch == '.' )    return true ;
        if ( ch == '-' )    return true ;
        if ( ch == '_' )    return true ;
        return false ;
    }
    
    static boolean applyUnicodeEscapes = false ;
    
    // Take unescape code from ParserBase.
    
    // take a string and make it safe for writing.
    public static String stringEsc(String s)
    { return stringEsc( s, true ) ; }
    
    public static String stringEsc(String s, boolean singleLineString)
    {
        StringBuilder sb = new StringBuilder() ;
        stringEsc(sb, s, singleLineString) ;
        return sb.toString() ;
    }
    
    public static void stringEsc(StringBuilder sbuff, String s)
    { stringEsc( sbuff,  s, true ) ; }

    public static void stringEsc(StringBuilder sbuff, String s, boolean singleLineString)
    {
        int len = s.length() ;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);

            // Escape escapes and quotes
            if (c == '\\' || c == '"' )
            {
                sbuff.append('\\') ;
                sbuff.append(c) ;
                continue ;
            }
            
            // Characters to literally output.
            // This would generate 7-bit safe files 
//            if (c >= 32 && c < 127)
//            {
//                sbuff.append(c) ;
//                continue;
//            }    

            // Whitespace
            if ( singleLineString && ( c == '\n' || c == '\r' || c == '\f' ) )
            {
                if (c == '\n') sbuff.append("\\n");
                if (c == '\t') sbuff.append("\\t");
                if (c == '\r') sbuff.append("\\r");
                if (c == '\f') sbuff.append("\\f");
                continue ;
            }
            
            // Output as is (subject to UTF-8 encoding on output that is)
            
            if ( ! applyUnicodeEscapes )
                sbuff.append(c) ;
            else
            {
                // Unicode escapes
                // c < 32, c >= 127, not whitespace or other specials
                if ( c >= 32 && c < 127 )
                {
                    sbuff.append(c) ;
                }
                else
                {
                    String hexstr = Integer.toHexString(c).toUpperCase();
                    int pad = 4 - hexstr.length();
                    sbuff.append("\\u");
                    for (; pad > 0; pad--)
                        sbuff.append("0");
                    sbuff.append(hexstr);
                }
            }
        }
    }
    
    static public void resetBNodeLabels() { bNodeMap = new NodeToLabelMapBNode("b", false) ; }
    

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
