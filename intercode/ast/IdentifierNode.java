package intercode.ast;

import intercode.visitor.*;
import intercode.lexer.*;

public class IdentifierNode extends ExprNode{
    
    public String id;
    public Word w;
    public Type type;
    public boolean isArray = false;
    

    public IdentifierNode(){

    }

    public IdentifierNode(Word w){
        this.id = w.lexeme;
        this.w = w;
    }

    public IdentifierNode(Word w, Type type){
        this.id = w.lexeme;
        this.w = w;
        this.type = type;
    }

    public void accept(ASTVisitor v){
        v.visit(this);
    }

    void printNode(){
        System.out.println("IdentifierNode: " + id);
    }
}
