grammar Rinne;


program
  : (statement)* EOF
  ;

statement
  : functionDefinition
  | variableDeclaration
  | assignment
  | ifStatement
  | whileStatement
  | doWhileStatement
  | funcCall COMMA
  | returnStatement
  | druckeStatement
  ;


returnStatement
  : GEBE conditionalExpression ZURÜCK COMMA
  ;

druckeStatement
  : DRUCKE conditionalExpression COMMA
  ;

variableDeclaration
  : variableName=IDENTIFIER ALS type (ODER SO COMMA | INIT initialValue=conditionalExpression COMMA)  # typedVariableDeclaration
  | variableName=IDENTIFIER INIT initialValue=conditionalExpression COMMA                             # untypedVariableDeclaration
  ;

assignment
  : variableName=IDENTIFIER ASSIGN conditionalExpression COMMA
  ;

ifStatement
  : WENN LPAREN condition=conditionalExpression RPAREN (thenBlock+=statement)* ( ANSONSTEN (elseBlock+=statement)* )? PERIOD
  ;

whileStatement
  : WÄHREND LPAREN conditionalExpression RPAREN (statement)* PERIOD
  ;

doWhileStatement
  : WIEDERHOLE (statement)* SOLANGE LPAREN conditionalExpression RPAREN COMMA
  ;

functionDefinition
  : DEKLARIERE functionName=IDENTIFIER LPAREN MIT formalParameters RPAREN (statement)* PERIOD
  ;

formalParameters
  : OHNE
  | formalParameter ( UND formalParameter )*
  ;

formalParameter
  : parameterName=IDENTIFIER ALS type
  ;


funcCall
  : BITTE functionName=IDENTIFIER (MIT (actualParameters+=conditionalExpression)+)?
  ;

conditionalExpression
  : rhs=conditionalAndExpression
  | lhs=conditionalExpression operator=LOR rhs=conditionalAndExpression
  ;

conditionalAndExpression
  : rhs=equalityExpression
  | lhs=conditionalAndExpression operator=LAND rhs=equalityExpression
  ;

equalityExpression
  : rhs=relationalExpression
  | lhs=equalityExpression operator=(EQ|NEQ) rhs=relationalExpression
  ;

relationalExpression
  : rhs=additiveExpression
  | lhs=relationalExpression operator=(LT|LE|GT|GE) rhs=additiveExpression
  ;

additiveExpression
  : rhs=multiplicativeExpression
  | lhs=additiveExpression operator=(PLUS|MINUS) rhs=multiplicativeExpression
  ;

multiplicativeExpression
  : rhs=unaryExpression
  | lhs=multiplicativeExpression operator=(STAR|DIV|MOD) rhs=unaryExpression
  ;

unaryExpression
  : operator=(PLUS|MINUS|NOT) expr=primary
  | expr=primary
  ;

primary
  : funcCall                                                 # functionCall
  | variableName=IDENTIFIER                                  # variableReference
  | literal=LONG_LITERAL                                     # longLiteral
  | literal=DOUBLE_LITERAL                                   # doubleLiteral
  | literal=STRING_LITERAL                                   # stringLiteral
  | LPAREN conditionalExpression RPAREN                      # parenthesizedExpression
  | WAHR                                                     # booleanTrue
  | FALSCH                                                   # booleanFalse
  ;

type
  : FLIEßZAHL
  | GANZZAHL
  | SCHNUR
  | WAHRHEITSWERT
  ;


LONG_LITERAL   : [0-9]+ ;
DOUBLE_LITERAL : [0-9]+ '.' [0-9]* | '.' [0-9]+ ;
STRING_LITERAL : '"' ( ~["\n\r] | '\\"' )* '"' ;


NOT : '!' ;

LT : '<' ;
LE : '<=' ;
GT : '>' ;
GE : '>=' ;

EQ : '==' ;
NEQ : '!=' ;

LAND : '&&' ;
LOR  : '||' ;

STAR  : '*' ;
DIV   : '/' ;
MOD   : '%' ;
PLUS  : '+' ;
MINUS : '-' ;

LPAREN     : '(' ;
RPAREN     : ')' ;
COMMA      : ',' ;
PERIOD     : '.' ;

ALS        : 'als' ;
ANSONSTEN  : 'ansonsten' ;
BITTE      : 'bitte' ;
DEKLARIERE : 'deklariere' ;
DRUCKE     : 'drucke' ;
FALSCH     : 'falsch' ;
FEHLER     : 'Fehler' ;
FLIEßZAHL  : 'Fließzahl' ;
GANZZAHL   : 'Ganzzahl' ;
GEBE       : 'gebe' ;
JA         : 'ja' ;
MIT        : 'mit' ;
NEIN       : 'nein' ;
ODER       : 'oder' ;
OHJE       : 'ohje' ;
OHNE       : 'ohne' ;
SCHNUR     : 'Schnur' ;
SO         : 'so' ;
SOLANGE    : 'solange' ;
UND        : 'und' ;
WAHR       : 'wahr' ;
WAHRHEITSWERT : 'Wahrheitswert' ;
WENN       : 'wenn' ;
WIEDERHOLE : 'wiederhole' ;
WÄHREND    : 'während' ;
ZURÜCK     : 'zurück' ;

INIT       : ':=' ;
ASSIGN     : '=' ;

NUMBER     : [0-9]+;
IDENTIFIER : [a-zA-Z][a-zA-Z0-9]* ;

WHITESPACE : [ \t\r\n]+ -> channel(HIDDEN) ;
