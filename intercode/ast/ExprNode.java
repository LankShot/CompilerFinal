package intercode.ast ;

import intercode.visitor.* ;
import intercode.lexer.*;


public class ExprNode extends Node {

    public Type type = null;

    public ExprNode () {

    }

    public void accept(ASTVisitor v) {

        v.visit(this);
    }
}