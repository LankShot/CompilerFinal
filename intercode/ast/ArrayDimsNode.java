package intercode.ast;

import intercode.visitor.*;

public class ArrayDimsNode extends ExprNode{
    public ExprNode size;
    public ArrayDimsNode dim;

    public ArrayDimsNode(){

    }

    public ArrayDimsNode(ExprNode size, ArrayDimsNode dim){

        this.size = size;
        this.dim = dim;
    }

    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
