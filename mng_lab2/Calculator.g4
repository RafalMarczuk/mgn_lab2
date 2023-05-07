grammar Calculator;


/*ta dziala najlepiej do tej pory */
expression: powExpression | sqrtExpression | multiplyingExpression ((PLUS | MINUS) multiplyingExpression)*;
multiplyingExpression: integralExpression ((MULT | DIV) integralExpression)*;
integralExpression: MINUS INT | INT;
powExpression: integralExpression POW integralExpression;
sqrtExpression: 'sqrt' integralExpression;


INT: [0-9]+ ;
PLUS: '+' ;
MINUS: '-' ;
MULT: '*' ;
DIV: '/' ;
POW: '^' ;
SQRT: 'sqrt' ;
WS : [ \t\r\n]+ -> skip ;

