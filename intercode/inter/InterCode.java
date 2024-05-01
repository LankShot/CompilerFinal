package intercode.inter;

import intercode.ast.*;
import intercode.inter.*;
import intercode.lexer.*;
import intercode.parser.*;
import intercode.typechecker.*;
import intercode.visitor.*;
import java.util.*;

public class InterCode extends ASTVisitor{

    public TypeChecker checker  = null;
    public CompilationUnit cu = null;
    public List<StatementNode> assignmentNodes = new ArrayList<StatementNode>();

    int level = 0;
    String indent = "...";

    public InterCode (TypeChecker checker){

        this.checker = checker;
        cu = checker.cu;
        visit(cu);
    }

    public InterCode(){
        visit(this.checker.cu);
    }

    ////////////////////////////////////////
    //  Utility mothods
    ////////////////////////////////////////

    void error(String s){

        println(s);
        exit(1);
    }
    void exit(int n){
        System.exit(n);
    }
    

    void print(String s){
        System.out.print(s);
    }

    void println(String s){
        System.out.println(s);
    }

    void printSpace(){
        System.out.print(" ");
    }

    ////////////////////

    public void visit (CompilationUnit n) {
        System.out.println("InterCode Starts ...");
        System.out.println();
        System.out.println();
        System.out.println("CompilationUnit");

        n.block.accept(this) ;
    }

    public void visit (BlockStatementNode n) {

        System.out.println("BlockStatementNode");

        for (DeclarationNode decl : n.decls){
            decl.accept(this);
        }
        List<StatementNode> tempList = new ArrayList<StatementNode>(assignmentNodes);
        assignmentNodes = new ArrayList<StatementNode>();
            
        for(StatementNode stmt : n.stmts){
            stmt.accept(this);
        }
        n.assigns = new ArrayList<StatementNode>(assignmentNodes);
        assignmentNodes = new ArrayList<StatementNode>(tempList);
        

    }

    public void visit (Declarations n){

        if (n.decls != null){
            n.decl.accept(this);
            n.decls.accept(this);
        }
    }

    public void visit (DeclarationNode n) {

        System.out.println("DeclarationNode");

        n.type.accept(this);

        n.id.accept(this);
    }

    public void visit (TypeNode n) {

          
        System.out.println("TypeNode: " + n.basic);

        if (n.array != null){
             
            n.array.accept(this);
             
        }

    }

    public void visit (ArrayTypeNode n) {

          
        System.out.println("ArrayTypeNode: " + n.size);

        if (n.type != null){
             
            n.type.accept(this);
             
        }

    }

    public void visit (Statements n){

        if (n.stmts != null){

            n.stmt.accept(this);
            n.stmts.accept(this);
        }
    }

    public void visit(ParenthesesNode n){
          
        System.out.println("ParenthesesNode");
        if(n.expr instanceof TrueNode){
            n.isTrueNode = true;
        }
         
        n.expr.accept(this);
         
    }

    public void visit(AssignmentNode n){

          
        System.out.println("AssignmentNode");

         
        n.left.accept(this);
        


        if (n.right instanceof IdentifierNode){
            ((IdentifierNode)n.right).accept(this);
        }
        else if(n.right instanceof NumNode){
            ((NumNode)n.right).accept(this);
        }
        else if(n.right instanceof RealNode){
            ((RealNode)n.right).accept(this);
        }
        else if(n.right instanceof ArrayAccessNode){
            ((ArrayAccessNode)n.right).accept(this);
        }
        else if(n.right instanceof ParenthesesNode){
            ((ParenthesesNode)n.right).accept(this);
        }
        else{
            ((BinExprNode)n.right).accept(this);
        }
        LabelNode temp = TempNode.newTemp();
        AssignmentNode assign = new AssignmentNode(temp,n.right);
        AssignmentNode assign2 = new AssignmentNode(n.left,temp);
        assign.inAssigns = true;
        assign2.inAssigns = true;
        assignmentNodes.add(assign);
        assignmentNodes.add(assign2);

    }
    
    public void visit (IdentifierNode n) {

        //printIndent();
          
        System.out.println("IdentifierNode: " + n.id);
        //println(" ;") ;

    }

    public void visit (NumNode n) {

        //printIndent();
          
        System.out.println("NumNode: " + n.value);
        //println(" ;") ;

    }
    
    public void visit (RealNode n) {

        //printIndent();
          
        System.out.println("RealNode: " + n.value);
        //println(" ;") ;

    }

    public void visit (BinExprNode n) {

          
        System.out.println("BinExprNode: " + n.op);

        Type leftType = null;
        IdentifierNode leftId = null;
        //printIndent();
         

        if (n.left instanceof IdentifierNode){
            ((IdentifierNode)n.left).accept(this);
            leftId = (IdentifierNode)n.left;
            leftType = leftId.type;
        }
        else if(n.left instanceof NumNode){
            ((NumNode)n.left).accept(this);
        }
        else if (n.right instanceof RealNode){
            ((RealNode)n.right).accept(this);
        }
        else if(n.left instanceof ArrayAccessNode){
            ((ArrayAccessNode)n.left).accept(this);
            LabelNode temp = TempNode.newTemp();
            AssignmentNode assign2 = new AssignmentNode(temp,n.left);
            assign2.inAssigns = true;
            assignmentNodes.add(assign2);
            n.left = temp;
        }
        else if(n.left instanceof ParenthesesNode){
            ((ParenthesesNode)n.left).accept(this);
        }
        else{
            ((BinExprNode)n.left).accept(this);
        }
        Type rightType = null;

        if (n.right != null){
            if (n.right instanceof IdentifierNode){
                ((IdentifierNode)n.right).accept(this);
                IdentifierNode rightId = (IdentifierNode)n.right;
                rightType = rightId.type;
            }
            else if(n.right instanceof NumNode){
                ((NumNode)n.right).accept(this);
            }
            else if (n.right instanceof RealNode){
                ((RealNode)n.right).accept(this);
            }
            else if(n.right instanceof ArrayAccessNode){
                ((ArrayAccessNode)n.right).accept(this);
                LabelNode temp = TempNode.newTemp();
                AssignmentNode assign2 = new AssignmentNode(temp,n.right);
                assign2.inAssigns = true;
                assignmentNodes.add(assign2);
                n.right = temp;
            }
            else if(n.right instanceof ParenthesesNode){
                ((ParenthesesNode)n.right).accept(this);
            }
            else{
                ((BinExprNode)n.right).accept(this);
            }
        }
        else{
            print("");
        }

    }

    public void visit (WhileStatementNode n) {
          
        System.out.println("WhileStatementNode");
        
        n.startLabel = LabelNode.newLabel();
        n.stopLabel = LabelNode.newLabel();
        assignmentNodes.add(n);
        List<StatementNode> tempList = new ArrayList<StatementNode>(assignmentNodes);
        assignmentNodes = new ArrayList<StatementNode>();
        n.cond.accept(this);
        /*IdentifierNode temp = TempNode.newTemp();
        ParenthesesNode cond = n.cond;
        ExprNode expr = null;

        if (cond.expr instanceof BinExprNode){
            expr = (BinExprNode)cond.expr;
        }
        else if( cond.expr instanceof TrueNode){
            expr = (TrueNode)cond.expr;
            temp.isTrueNode = true;
        }
        else if(cond.expr instanceof FalseNode){
            expr = (FalseNode)cond.expr;
        }

        AssignmentNode assign = new AssignmentNode(temp,expr);
        n.assigns.add(assign);

        n.cond.expr = temp;*/
        n.stmt.accept(this);
        n.assigns = new ArrayList<StatementNode>(assignmentNodes);
        assignmentNodes = new ArrayList<StatementNode>(tempList);

    }

    public void visit (IfStatementNode n) {
          
        System.out.println("IfStatementNode");
        assignmentNodes.add(n);
        List<StatementNode> tempList = new ArrayList<StatementNode>(assignmentNodes);
        assignmentNodes = new ArrayList<StatementNode>();
        n.cond.accept(this);

        n.falseLabel = LabelNode.newLabel();
         
        n.stmt.accept(this);
         
        n.assigns = new ArrayList<StatementNode>(assignmentNodes);
        assignmentNodes = new ArrayList<StatementNode>(tempList);
        
        if (n.else_stmt != null){
              
            System.out.println("ElseStatementNode");
            n.else_stmt.accept(this);
            assignmentNodes.add(n.else_stmt);
        }
    }
    public void visit (DoWhileStatementNode n) {
          
        System.out.println("DoWhileStatementNode");
        assignmentNodes.add(n);
        List<StatementNode> tempList = new ArrayList<StatementNode>(assignmentNodes);
        assignmentNodes = new ArrayList<StatementNode>();

        n.startLabel = LabelNode.newLabel();
        n.stopLabel = LabelNode.newLabel();
        

        n.stmt.accept(this);


        n.cond.accept(this);
        /*IdentifierNode temp = TempNode.newTemp();
        ParenthesesNode cond = n.cond;
        ExprNode expr = null;

        if (cond.expr instanceof BinExprNode){
            expr = (BinExprNode)cond.expr;
        }
        else if( cond.expr instanceof TrueNode){
            expr = (TrueNode)cond.expr;
            temp.isTrueNode = true;
        }
        else if(cond.expr instanceof FalseNode){
            expr = (FalseNode)cond.expr;
        }

        AssignmentNode assign = new AssignmentNode(temp,expr);
        n.assigns.add(assign);

        n.cond.expr = temp;*/

        

        

        n.assigns = new ArrayList<StatementNode>(assignmentNodes);
        assignmentNodes = new ArrayList<StatementNode>(tempList);
    }
    public void visit(ArrayAccessNode n){
          
        println("ArrayAccessNode");
        n.id.accept(this);
         
         
        n.index.accept(this);
        
        
    }
    public void visit(ArrayDimsNode n){
          
        println("ArrayDimsNode");
        LabelNode temp = TempNode.newTemp();
        
        n.size.accept(this);
        ExprNode expr = null;
        if (n.size instanceof ParenthesesNode){
            ((ParenthesesNode)n.size).accept(this);
            expr = new BinExprNode(new Token('*'),((ParenthesesNode)n.size), new NumNode(new Num(8)));
        }
        else if (n.size instanceof IdentifierNode){

            ((IdentifierNode)n.size).accept(this);
            expr = new BinExprNode(new Token('*'),((IdentifierNode)n.size), new NumNode(new Num(8)));
        }
        else if (n.size instanceof NumNode){

            ((NumNode)n.size).accept(this);
            expr = new BinExprNode(new Token('*'),((NumNode)n.size), new NumNode(new Num(8)));
        }

        else if (n.size instanceof BinExprNode){
            ((BinExprNode)n.size).accept(this);
            expr = new BinExprNode(new Token('*'),((BinExprNode)n.size), new NumNode(new Num(8)));
        }
        AssignmentNode assign = new AssignmentNode(temp,expr);
        n.size = temp;
        assign.inAssigns = true;
        assignmentNodes.add(assign);

        if(n.dim != null){
             
            n.dim.accept(this);
             
        }
        
    }

    public void visit (BreakStatementNode n) {
        n.stopLabel = LabelNode.newLabel();
        System.out.println("BreakNode");
    }

    public void visit (TrueNode n) {
          
        System.out.println("TrueNode");
    }
    public void visit (FalseNode n) {
          
        System.out.println("FalseNode");
    }
    public void visit (GotoNode n){

    }

    public void visit (LabelNode n){

    }

    public void visit (TempNode n){

    }

}
