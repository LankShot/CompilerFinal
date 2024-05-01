package intercode.ast;

import intercode.visitor.*;
import intercode.lexer.*;

public class TypeNode extends Node{

    public Type basic;
    public ArrayTypeNode array = null;
    public boolean isArray = false;

    public TypeNode(){

    }

    public TypeNode (Type basic, ArrayTypeNode array){
        this.basic = basic;
        this.array = array;
    }

    public void accept(ASTVisitor v){

        v.visit(this);
    }
}
