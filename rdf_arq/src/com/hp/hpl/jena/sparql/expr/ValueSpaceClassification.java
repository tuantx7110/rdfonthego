/*
 * (c) Copyright 2010 Talis Information Ltd.
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.sparql.expr;

/**
 * @author   Mr_Anh
 */
enum ValueSpaceClassification {
    /**
	 * @uml.property  name="vSPACE_NODE"
	 * @uml.associationEnd  
	 */
    VSPACE_NODE,
    /**
	 * @uml.property  name="vSPACE_NUM"
	 * @uml.associationEnd  
	 */
    VSPACE_NUM, 
    /**
	 * @uml.property  name="vSPACE_DATETIME"
	 * @uml.associationEnd  
	 */
    VSPACE_DATETIME, 
    /**
	 * @uml.property  name="vSPACE_DATE"
	 * @uml.associationEnd  
	 */
    VSPACE_DATE,
    /**
	 * @uml.property  name="vSPACE_TIME"
	 * @uml.associationEnd  
	 */
    VSPACE_TIME,
    /**
	 * @uml.property  name="vSPACE_DURATION"
	 * @uml.associationEnd  
	 */
    VSPACE_DURATION,
    
    /**
	 * @uml.property  name="vSPACE_G_YEAR"
	 * @uml.associationEnd  
	 */
    VSPACE_G_YEAR,
    /**
	 * @uml.property  name="vSPACE_G_YEARMONTH"
	 * @uml.associationEnd  
	 */
    VSPACE_G_YEARMONTH,
    /**
	 * @uml.property  name="vSPACE_G_MONTHDAY"
	 * @uml.associationEnd  
	 */
    VSPACE_G_MONTHDAY,
    /**
	 * @uml.property  name="vSPACE_G_MONTH"
	 * @uml.associationEnd  
	 */
    VSPACE_G_MONTH,    
    /**
	 * @uml.property  name="vSPACE_G_DAY"
	 * @uml.associationEnd  
	 */
    VSPACE_G_DAY,
    
    /**
	 * @uml.property  name="vSPACE_STRING"
	 * @uml.associationEnd  
	 */
    VSPACE_STRING, /**
	 * @uml.property  name="vSPACE_LANG"
	 * @uml.associationEnd  
	 */
    VSPACE_LANG,
    /**
	 * @uml.property  name="vSPACE_BOOLEAN"
	 * @uml.associationEnd  
	 */
    VSPACE_BOOLEAN,
    /**
	 * @uml.property  name="vSPACE_UNKNOWN"
	 * @uml.associationEnd  
	 */
    VSPACE_UNKNOWN,
    /**
	 * @uml.property  name="vSPACE_DIFFERENT"
	 * @uml.associationEnd  
	 */
    VSPACE_DIFFERENT
}
/*
 * (c) Copyright 2010 Talis Information Ltd.
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