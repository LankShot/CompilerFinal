package intercode.visitor ;

import intercode.ast.*;
import intercode.inter.*;


public class ASTVisitor {

    public void visit (CompilationUnit n) {

        n.block.accept(this) ;
    }

    public void visit(BlockStatementNode n){
        for (DeclarationNode decl : n.decls){
            decl.accept(this);
        }
        for( StatementNode stmt : n.stmts){
            stmt.accept(this);
        }
    }

    public void visit (Declarations n){
        if (n.decls != null){
            n.decl.accept(this);
            n.decls.accept(this);
        }
    }

    public void visit (DeclarationNode n){
        n.type.accept(this);
        n.id.accept(this);
    }

    public void visit(TypeNode n){

        n.array.accept(this);
    }

    public void visit(ArrayTypeNode n){

        n.type.accept(this);
    }

    public void visit (Statements n){
        if (n.stmts != null){
            if(n.stmt instanceof WhileStatementNode){
                ((WhileStatementNode)n.stmt).accept(this);
            } else if (n.stmt instanceof IfStatementNode){
                ((IfStatementNode)n.stmt).accept(this);
            } else if (n.stmt instanceof BreakStatementNode){
                ((BreakStatementNode)n.stmt).accept(this);
            } else if (n.stmt instanceof DoWhileStatementNode){
                ((DoWhileStatementNode)n.stmt).accept(this);
            }
            n.stmts.accept(this);
        }
    }
    

    public void visit (ParenthesesNode n){

        n.expr.accept(this);
    }

    public void visit(AssignmentNode n){
        n.left.accept(this);

        if (n.right instanceof IdentifierNode){
            ((IdentifierNode)n.right).accept(this);
        }
        else if (n.right instanceof NumNode){
            ((NumNode)n.right).accept(this);
        }
        else if (n.right instanceof RealNode){
            ((RealNode)n.right).accept(this);
        }
        else{
            ((BinExprNode)n.right).accept(this);
        }

    }

    public void visit(BinExprNode n){

    }

    public void visit (IdentifierNode n) {

    }

    public void visit (NumNode n) {

    }
    public void visit (RealNode n) {

    }
    public void visit (WhileStatementNode n) {
        n.cond.accept(this);
        n.stmt.accept(this);
    }
    public void visit (IfStatementNode n) {
        n.cond.accept(this);
        n.stmt.accept(this);

        if(n.else_stmt != null){
            n.else_stmt.accept(this);
        }
    }

    public void visit (DoWhileStatementNode n) {
        n.stmt.accept(this);
        n.cond.accept(this);
    }

    public void visit(ArrayAccessNode n){

    }

    public void visit(ArrayDimsNode n){
        n.size.accept(this);

        if(n.dim != null){
            n.dim.accept(this);
        }
    }
    public void visit (BreakStatementNode n) {

    }
    public void visit (ExprNode n) {

    }
    public void visit (TrueNode n) {

    }
    public void visit (FalseNode n) {

    }
    public void visit (Node n) {

    }
    public void visit (StatementNode n){

    }
    public void visit (GotoNode n){

    }

    public void visit (LabelNode n){
       
    }

    public void visit (TempNode n){
        
    }

}