/* Generated By:JavaCC: Do not edit this line. TurtleParser.java */
/*
 * (c) Copyright 2006 Hewlett-Packard Development Company, LP
 * All rights reserved.
 */

package com.hp.hpl.jena.n3.turtle.parser ;

import com.hp.hpl.jena.n3.turtle.ParserBase ;
import com.hp.hpl.jena.graph.* ;

public class TurtleParser extends ParserBase implements TurtleParserConstants {

// --- Entry point
  final public void parse() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PREFIX:
      case BASE:
      case TRUE:
      case FALSE:
      case INTEGER:
      case DECIMAL:
      case DOUBLE:
      case STRING_LITERAL1:
      case STRING_LITERAL2:
      case STRING_LITERAL_LONG1:
      case STRING_LITERAL_LONG2:
      case IRIref:
      case PNAME_NS:
      case PNAME_LN:
      case BLANK_NODE_LABEL:
      case VAR:
      case LPAREN:
      case NIL:
      case LBRACE:
      case LBRACKET:
      case ANON:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      Statement();
    }
    jj_consume_token(0);
  }

  final public void Statement() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PREFIX:
    case BASE:
      Directive();
      break;
    case TRUE:
    case FALSE:
    case INTEGER:
    case DECIMAL:
    case DOUBLE:
    case STRING_LITERAL1:
    case STRING_LITERAL2:
    case STRING_LITERAL_LONG1:
    case STRING_LITERAL_LONG2:
    case IRIref:
    case PNAME_NS:
    case PNAME_LN:
    case BLANK_NODE_LABEL:
    case VAR:
    case LPAREN:
    case NIL:
    case LBRACE:
    case LBRACKET:
    case ANON:
      TriplesSameSubject();
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(DOT);
  }

  final public void Directive() throws ParseException {
                     Token t ; String iri ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PREFIX:
      jj_consume_token(PREFIX);
      t = jj_consume_token(PNAME_NS);
      iri = IRI_REF();
      String s = fixupPrefix(t.image, t.beginLine, t.beginColumn) ;
      setPrefix(t.beginLine, t.beginColumn, s, iri) ;
      break;
    case BASE:
      t = jj_consume_token(BASE);
      iri = IRI_REF();
      setBase(iri, t.beginLine, t.beginColumn) ;
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

// N3

// ---- TRIPLES
// <<<<< SPARQL extract
  final public void TriplesSameSubject() throws ParseException {
                              Node s ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case INTEGER:
    case DECIMAL:
    case DOUBLE:
    case STRING_LITERAL1:
    case STRING_LITERAL2:
    case STRING_LITERAL_LONG1:
    case STRING_LITERAL_LONG2:
    case IRIref:
    case PNAME_NS:
    case PNAME_LN:
    case BLANK_NODE_LABEL:
    case VAR:
    case NIL:
    case LBRACE:
    case ANON:
      s = VarOrTerm();
      PropertyListNotEmpty(s);
      break;
    case LPAREN:
    case LBRACKET:
      // Any of the triple generating syntax elements
        s = TriplesNode();
      PropertyList(s);
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void PropertyList(Node s) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case KW_A:
    case IRIref:
    case PNAME_NS:
    case PNAME_LN:
    case ARROW:
      PropertyListNotEmpty(s);
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
  }

// >>>>> SPARQL extract

// Non-recursive for Turtle long PropertyList tests
  final public void PropertyListNotEmpty(Node s) throws ParseException {
                                      Node p ;
    p = Verb();
    ObjectList(s, p);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SEMICOLON:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_2;
      }
      jj_consume_token(SEMICOLON);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case KW_A:
      case IRIref:
      case PNAME_NS:
      case PNAME_LN:
      case ARROW:
        p = Verb();
        ObjectList(s, p);
        break;
      default:
        jj_la1[6] = jj_gen;
        ;
      }
    }
  }

// Non-recursive for Turtle long PropertyList tests
  final public void ObjectList(Node s, Node p) throws ParseException {
                                   Node o ;
    Object(s, p);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_3;
      }
      jj_consume_token(COMMA);
      Object(s, p);
    }
  }

  final public void Object(Node s, Node p) throws ParseException {
                               Node o ;
    o = GraphNode();
    Triple t = new Triple(s,p,o) ;
    emitTriple(token.beginLine, token.beginColumn, t) ;
  }

// <<<<< SPARQL extract
  final public Node Verb() throws ParseException {
                Node p ; String iri ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IRIref:
    case PNAME_NS:
    case PNAME_LN:
      iri = IRIref();
                      p = createNode(iri) ;
      break;
    case KW_A:
      jj_consume_token(KW_A);
              p = nRDFtype ;
      break;
    case ARROW:
      jj_consume_token(ARROW);
        p = nLogImplies ;
        if ( strictTurtle )
          throwParseException("=> (log:implies) not legal in Turtle",
                          token.beginLine, token.beginColumn ) ;
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return p ;}
    throw new Error("Missing return statement in function");
  }

// -------- Triple expansions

// Anything that can stand in a node slot and which is
// a number of triples
  final public Node TriplesNode() throws ParseException {
                       Node n ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAREN:
      n = Collection();
                     {if (true) return n ;}
      break;
    case LBRACKET:
      n = BlankNodePropertyList();
                                {if (true) return n ;}
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Node BlankNodePropertyList() throws ParseException {
    jj_consume_token(LBRACKET);
      Node n = createBNode() ;
    PropertyListNotEmpty(n);
    jj_consume_token(RBRACKET);
      {if (true) return n ;}
    throw new Error("Missing return statement in function");
  }

// ------- RDF collections

// Code not as SPARQL/ARQ because of output ordering.
  final public Node Collection() throws ParseException {
      Node listHead = nRDFnil ; Node lastCell = null ; Node n ;
    jj_consume_token(LPAREN);
    label_4:
    while (true) {
      Node cell = createBNode() ;
      if ( listHead == nRDFnil )
         listHead = cell ;
      if ( lastCell != null )
        emitTriple(token.beginLine, token.beginColumn,
                   new Triple(lastCell, nRDFrest,  cell)) ;
      n = GraphNode();
      emitTriple(token.beginLine, token.beginColumn,
                 new Triple(cell, nRDFfirst,  n)) ;
      lastCell = cell ;
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TRUE:
      case FALSE:
      case INTEGER:
      case DECIMAL:
      case DOUBLE:
      case STRING_LITERAL1:
      case STRING_LITERAL2:
      case STRING_LITERAL_LONG1:
      case STRING_LITERAL_LONG2:
      case IRIref:
      case PNAME_NS:
      case PNAME_LN:
      case BLANK_NODE_LABEL:
      case VAR:
      case LPAREN:
      case NIL:
      case LBRACE:
      case LBRACKET:
      case ANON:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_4;
      }
    }
    jj_consume_token(RPAREN);
     if ( lastCell != null )
       emitTriple(token.beginLine, token.beginColumn,
                  new Triple(lastCell, nRDFrest,  nRDFnil)) ;
     {if (true) return listHead ;}
    throw new Error("Missing return statement in function");
  }

// -------- Nodes in a graph pattern or template
  final public Node GraphNode() throws ParseException {
                     Node n ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case INTEGER:
    case DECIMAL:
    case DOUBLE:
    case STRING_LITERAL1:
    case STRING_LITERAL2:
    case STRING_LITERAL_LONG1:
    case STRING_LITERAL_LONG2:
    case IRIref:
    case PNAME_NS:
    case PNAME_LN:
    case BLANK_NODE_LABEL:
    case VAR:
    case NIL:
    case LBRACE:
    case ANON:
      n = VarOrTerm();
                    {if (true) return n ;}
      break;
    case LPAREN:
    case LBRACKET:
      n = TriplesNode();
                      {if (true) return n ;}
      break;
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Node VarOrTerm() throws ParseException {
                    Node n = null ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case VAR:
      n = Var();
      break;
    case TRUE:
    case FALSE:
    case INTEGER:
    case DECIMAL:
    case DOUBLE:
    case STRING_LITERAL1:
    case STRING_LITERAL2:
    case STRING_LITERAL_LONG1:
    case STRING_LITERAL_LONG2:
    case IRIref:
    case PNAME_NS:
    case PNAME_LN:
    case BLANK_NODE_LABEL:
    case NIL:
    case ANON:
      n = GraphTerm();
      break;
    case LBRACE:
      n = Formula();
      break;
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return n ;}
    throw new Error("Missing return statement in function");
  }

  final public Node Formula() throws ParseException {
                  Token t ;
    t = jj_consume_token(LBRACE);
                   startFormula(t.beginLine, t.beginColumn) ;
    TriplesSameSubject();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DOT:
        ;
        break;
      default:
        jj_la1[13] = jj_gen;
        break label_5;
      }
      jj_consume_token(DOT);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TRUE:
      case FALSE:
      case INTEGER:
      case DECIMAL:
      case DOUBLE:
      case STRING_LITERAL1:
      case STRING_LITERAL2:
      case STRING_LITERAL_LONG1:
      case STRING_LITERAL_LONG2:
      case IRIref:
      case PNAME_NS:
      case PNAME_LN:
      case BLANK_NODE_LABEL:
      case VAR:
      case LPAREN:
      case NIL:
      case LBRACE:
      case LBRACKET:
      case ANON:
        TriplesSameSubject();
        break;
      default:
        jj_la1[14] = jj_gen;
        ;
      }
    }
    t = jj_consume_token(RBRACE);
                   endFormula(t.beginLine, t.beginColumn) ;
        {if (true) return null ;}
    throw new Error("Missing return statement in function");
  }

// >>>>> SPARQL extract
  final public Node Var() throws ParseException {
               Token t ;
    t = jj_consume_token(VAR);
      {if (true) return createVariable(t.image, t.beginLine, t.beginColumn) ;}
    throw new Error("Missing return statement in function");
  }

  final public Node GraphTerm() throws ParseException {
                     Node n ; String iri ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IRIref:
    case PNAME_NS:
    case PNAME_LN:
      iri = IRIref();
                      {if (true) return createNode(iri) ;}
      break;
    case STRING_LITERAL1:
    case STRING_LITERAL2:
    case STRING_LITERAL_LONG1:
    case STRING_LITERAL_LONG2:
      n = RDFLiteral();
                          {if (true) return n ;}
      break;
    case INTEGER:
    case DECIMAL:
    case DOUBLE:
      // Cleaner sign handling in Turtle.
        n = NumericLiteral();
                          {if (true) return n ;}
      break;
    case TRUE:
    case FALSE:
      n = BooleanLiteral();
                          {if (true) return n ;}
      break;
    case BLANK_NODE_LABEL:
    case ANON:
      n = BlankNode();
                          {if (true) return n ;}
      break;
    case NIL:
      jj_consume_token(NIL);
           {if (true) return nRDFnil ;}
      break;
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

// ---- Basic terms
  final public Node NumericLiteral() throws ParseException {
                          Token t ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER:
      t = jj_consume_token(INTEGER);
                  {if (true) return createLiteralInteger(t.image) ;}
      break;
    case DECIMAL:
      t = jj_consume_token(DECIMAL);
                  {if (true) return createLiteralDecimal(t.image) ;}
      break;
    case DOUBLE:
      t = jj_consume_token(DOUBLE);
                 {if (true) return createLiteralDouble(t.image) ;}
      break;
    default:
      jj_la1[16] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

// >>>>> SPARQL extract
// Langtag oddity.
  final public Node RDFLiteral() throws ParseException {
                      Token t ; String lex = null ;
    lex = String();
    String lang = null ; String dt = null ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PREFIX:
    case LANGTAG:
    case DATATYPE:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PREFIX:
      case LANGTAG:
        lang = Langtag();
        break;
      case DATATYPE:
        jj_consume_token(DATATYPE);
        dt = IRIref();
        break;
      default:
        jj_la1[17] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[18] = jj_gen;
      ;
    }
      {if (true) return createLiteral(lex, lang, dt) ;}
    throw new Error("Missing return statement in function");
  }

  final public String Langtag() throws ParseException {
                     Token t ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LANGTAG:
      t = jj_consume_token(LANGTAG);
      break;
    case PREFIX:
      t = jj_consume_token(PREFIX);
      break;
    default:
      jj_la1[19] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    String lang = stripChars(t.image, 1) ; {if (true) return lang ;}
    throw new Error("Missing return statement in function");
  }

// >>>>> SPARQL extract
  final public Node BooleanLiteral() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
      jj_consume_token(TRUE);
            {if (true) return XSD_TRUE ;}
      break;
    case FALSE:
      jj_consume_token(FALSE);
             {if (true) return XSD_FALSE ;}
      break;
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

// <<<<< SPARQL extract
  final public String String() throws ParseException {
                    Token t ;  String lex ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STRING_LITERAL1:
      t = jj_consume_token(STRING_LITERAL1);
                            lex = stripQuotes(t.image) ;
      break;
    case STRING_LITERAL2:
      t = jj_consume_token(STRING_LITERAL2);
                            lex = stripQuotes(t.image) ;
      break;
    case STRING_LITERAL_LONG1:
      t = jj_consume_token(STRING_LITERAL_LONG1);
                                 lex = stripQuotes3(t.image) ;
      break;
    case STRING_LITERAL_LONG2:
      t = jj_consume_token(STRING_LITERAL_LONG2);
                                 lex = stripQuotes3(t.image) ;
      break;
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
      lex = unescapeStr(lex,  t.beginLine, t.beginColumn) ;
      {if (true) return lex ;}
    throw new Error("Missing return statement in function");
  }

  final public String IRIref() throws ParseException {
                    String iri ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IRIref:
      iri = IRI_REF();
                    {if (true) return iri ;}
      break;
    case PNAME_NS:
    case PNAME_LN:
      iri = PrefixedName();
                         {if (true) return iri ;}
      break;
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public String PrefixedName() throws ParseException {
                          Token t ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PNAME_LN:
      t = jj_consume_token(PNAME_LN);
      {if (true) return resolvePName(t.image, t.beginLine, t.beginColumn) ;}
      break;
    case PNAME_NS:
      t = jj_consume_token(PNAME_NS);
      {if (true) return resolvePName(t.image, t.beginLine, t.beginColumn) ;}
      break;
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Node BlankNode() throws ParseException {
                      Token t = null ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BLANK_NODE_LABEL:
      t = jj_consume_token(BLANK_NODE_LABEL);
      {if (true) return createBNode(t.image, t.beginLine, t.beginColumn) ;}
      break;
    case ANON:
      jj_consume_token(ANON);
           {if (true) return createBNode() ;}
      break;
    default:
      jj_la1[24] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public String IRI_REF() throws ParseException {
                     Token t ;
    t = jj_consume_token(IRIref);
    {if (true) return resolveQuotedIRI(t.image, t.beginLine, t.beginColumn) ;}
    throw new Error("Missing return statement in function");
  }

  public TurtleParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[25];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static {
      jj_la1_0();
      jj_la1_1();
      jj_la1_2();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0xde1fc000,0xde1fc000,0xc000,0xde1f0000,0xc0002000,0x0,0xc0002000,0x0,0xc0002000,0x0,0xde1f0000,0xde1f0000,0xde1f0000,0x0,0xde1f0000,0xde1f0000,0x1c0000,0x4000,0x4000,0x4000,0x30000,0x1e000000,0xc0000000,0x80000000,0x0,};
   }
   private static void jj_la1_1() {
      jj_la1_1 = new int[] {0x2b47,0x2b47,0x0,0x2b47,0x40001,0x4000,0x40001,0x8000,0x40001,0x840,0x2b47,0x2b47,0x2307,0x10000,0x2b47,0x2103,0x0,0x4000008,0x4000008,0x8,0x0,0x0,0x1,0x1,0x2002,};
   }
   private static void jj_la1_2() {
      jj_la1_2 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
   }

  public TurtleParser(java.io.InputStream stream) {
     this(stream, null);
  }
  public TurtleParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new TurtleParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  public TurtleParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new TurtleParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  public TurtleParser(TurtleParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  public void ReInit(TurtleParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[67];
    for (int i = 0; i < 67; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 25; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
          if ((jj_la1_2[i] & (1<<j)) != 0) {
            la1tokens[64+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 67; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

}
