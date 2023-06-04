grammar Formula;
prog: expr EOF;
expr: NUMERIC # number
	| ID # variable
    | '(' expr ')' # wrappedExp
    | func # function
	| expr op=(TIMES | DIV | REM) expr # timesDiv
    | expr op=(PLUS | MINUS) expr # plusMinus
    | expr INVALID_OP expr # invalidOp
	;

ID: [\p{Alpha}\p{General_Category=Other_Letter}] [_\p{Alnum}\p{General_Category=Other_Letter}]*;
func: ID '('expr (',' expr)*')';

// operator
PLUS        : '+';
MINUS       : '-';
TIMES       : '*';
DIV         : '/';
REM         : '%';
INVALID_OP : ~[ ()+\-\\*%\p{Alnum}\p{General_Category=Other_Letter}]*;

// setting
NEWLINE     : [\r\n]+ -> skip;
NUMERIC     : ('-')?[0-9]+('.'[0-9]+)?;
WS          : [ \t\r\n]+ -> skip;
