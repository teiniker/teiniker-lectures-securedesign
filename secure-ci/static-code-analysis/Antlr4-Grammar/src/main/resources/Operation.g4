grammar Operation;

@lexer::header { package org.se.lab; }
@parser::header { package org.se.lab; }

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

prog:	function_declaration+;

function_declaration: declaration_specifiers IDENTIFIER LPAREN parameterList RPAREN SEMI;

parameter
	: declaration_specifiers IDENTIFIER
	; 	

parameterList
	: parameter? (COMMA parameter)*
	;
	
	
declaration_specifiers
	:   (   storage_class_specifier
		|   type_specifier
        |   type_qualifier
        )+
	;
		
type_specifier
	: 'void'
	| 'char'
	| 'short'
	| 'int'
	| 'long'
	| 'float'
	| 'double'
	// ...
	;
	
storage_class_specifier
	: 'extern'
	| 'static'
	| 'auto'
	| 'register'
	; 	

type_qualifier
	: 'const'
	| 'volatile'
	;
		
	
/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

COMMA	: ',' ;
SEMI	: ';' ;
LPAREN	: '(' ;
RPAREN	: ')' ;

LETTER
	:	'A'..'Z'
	|	'a'..'z'
	|	'_'
	;
	
IDENTIFIER
	:	LETTER (LETTER|'0'..'9')*
	;	

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' -> skip
    ;

LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' -> skip
    ;

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') -> skip
    ;
