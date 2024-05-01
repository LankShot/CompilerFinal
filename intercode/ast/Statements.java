package intercode.ast;

import intercode.visitor.* ;

public class Statements extends Node {

    public Statements stmts;
    public StatementNode stmt;

    public Statements () {
        
    }

    public Statements(Statements stmts, StatementNode stmt){
        this.stmts = stmts;
        this.stmt = stmt;
    }

    public void accept(ASTVisitor v) {

        v.visit(this);
    }
}