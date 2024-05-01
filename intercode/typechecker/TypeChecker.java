package intercode.typechecker;

import intercode.lexer.*;
import intercode.parser.*;
import intercode.visitor.ASTVisitor;
import intercode.ast.*;
import intercode.inter.*;

public class TypeChecker extends ASTVisitor{

    public boolean inLoop;
    public Parser parser  = null;
    public CompilationUnit cu = null;

    int level = 0;
    String indent = "...";

    public TypeChecker (Parser parser){

        this.parser = parser;
        cu = parser.cu;
        visit(cu);
    }

    public TypeChecker(){
        visit(this.parser.cu);
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
        System.out.println("TypeChecker Starts ...");
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

         
        n.expr.accept(this);
        n.type = n.expr.type;
         
    }

    public void visit(AssignmentNode n){

          
        System.out.println("AssignmentNode");

         
        n.left.accept(this);
         
        ExprNode leftId;
        Type leftType;

        if(n.left instanceof ArrayAccessNode){
            leftId = n.left;
            leftType = ((ArrayAccessNode)leftId).id.type;
        }
        else{
            leftId = n.left;
            leftType = ((IdentifierNode)leftId).type;
        }

        println("In TypeChecker, AssignmentNode's left type: " + leftType);

        Type rightType = null;

        if (n.right instanceof IdentifierNode){
            ((IdentifierNode)n.right).accept(this);
            if(((IdentifierNode)n.right).type == null){
                error("Identfier Node's type in Assignment node is null near line" + n.lineNum);
            }
            rightType = ((IdentifierNode)n.right).type;
            println("IdentifierNode right type: "+ rightType.lexeme);
        }
        else if(n.right instanceof NumNode){
            ((NumNode)n.right).accept(this);
            rightType = Type.Int;
        }
        else if(n.right instanceof RealNode){
            ((RealNode)n.right).accept(this);
            rightType = Type.Float;
        }
        else if(n.right instanceof ArrayAccessNode){
            ((ArrayAccessNode)n.right).accept(this);
            rightType = ((ArrayAccessNode)n.right).id.type;
        }
        else if(n.right instanceof ParenthesesNode){
            ((ParenthesesNode)n.right).accept(this);
            rightType = ((ParenthesesNode)n.right).type;
        }
        else{
            ((BinExprNode)n.right).accept(this);
            rightType = ((BinExprNode)n.right).type;
        }
        
        if(leftType == Type.Int && rightType == Type.Float){
            if(leftId instanceof IdentifierNode){
                error("The right-hand side of an assignment is incompatible to the left-hand side " + ((IdentifierNode)leftId).id+ " near line " + n.lineNum);
            }
            else{
                error("The right-hand side of an assignment is incompatible to the left-hand side " +((ArrayAccessNode)leftId).id.id+ " near line " + n.lineNum);
            }
        }
        if((leftType == Type.Bool && rightType != Type.Bool) || (leftType != Type.Bool && rightType == Type.Bool)){
            if(leftId instanceof IdentifierNode){
                error("The right-hand side of an assignment is incompatible to the left-hand side " + ((IdentifierNode)leftId).id+ " near line " + n.lineNum);
            }
            else{
                error("The right-hand side of an assignment is incompatible to the left-hand side " +((ArrayAccessNode)leftId).id.id+ " near line " + n.lineNum);
            }
        }
        
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
            leftType = ((IdentifierNode)leftId).type;
        }
        else if(n.left instanceof NumNode){
            ((NumNode)n.left).accept(this);
            leftType = Type.Int;
        }
        else if (n.right instanceof RealNode){
            ((RealNode)n.right).accept(this);
            leftType = Type.Float;
        }
        else if(n.left instanceof ArrayAccessNode){
            ((ArrayAccessNode)n.left).accept(this);
            leftType = ((ArrayAccessNode)n.left).id.type;
        }
        else if(n.left instanceof ParenthesesNode){
            ((ParenthesesNode)n.left).accept(this);
            leftType = ((ParenthesesNode)n.left).type;
        }
        else{
            ((BinExprNode)n.left).accept(this);
            leftType = ((BinExprNode)n.left).type;
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
                rightType = Type.Int;
            }
            else if (n.right instanceof RealNode){
                ((RealNode)n.right).accept(this);
                rightType = Type.Float;
            }
            else if(n.right instanceof ArrayAccessNode){
                ((ArrayAccessNode)n.right).accept(this);
                rightType = ((ArrayAccessNode)n.right).id.type;
            }
            else if(n.right instanceof ParenthesesNode){
                ((ParenthesesNode)n.right).accept(this);
                leftType = ((ParenthesesNode)n.right).type;
            }
            else{
                ((BinExprNode)n.right).accept(this);
                rightType = ((BinExprNode)n.right).type;
            }
        }
        else{
            print("");
        }
        
        int prec = Parser.getPrecedence(n.op.tag);

        if(prec < 11 && prec > 8){
            if( (leftType == Type.Int || leftType == Type.Float) && (rightType == Type.Int || rightType == Type.Float)){
                n.type = Type.Bool;
            }
            else{
                error("Comparison has to be of Type Float or Int. LeftType: "+ leftType.lexeme+" RightType: "+rightType.lexeme+ " near line " + n.lineNum);
            }
        }
        else if( prec == 8){
            if(leftType == Type.Bool && rightType == Type.Bool){
                n.type = Type.Bool;
            }
            else if( (leftType == Type.Int || leftType == Type.Float) && (rightType == Type.Int || rightType == Type.Float)){
                n.type = Type.Bool;
            }
            else{
                error("Left and Right Types are not compatible in equality statements. LeftType: "+ leftType.lexeme+" RightType: "+rightType.lexeme+ " near line " + n.lineNum);
            }
        }
        else if(prec == 7){
            if(leftType == Type.Bool && rightType == Type.Bool){
                n.type = Type.Bool;
            }
            else{
                error("Left and Right Types must be bool. LeftType: "+ leftType.lexeme+" RightType: "+rightType.lexeme+ " near line " + n.lineNum);
            }
        }
        else if ( prec >= 11){
            n.type = Type.max(leftType,rightType);
            if (n.type == null || n.type == Type.Char){
                error("Arithmetic with operator "+ n.op +" not be computated with non numeric types. LeftType: "+ leftType.lexeme+" RightType: "+rightType.lexeme+ " near line " + n.lineNum);
            }
        }
        else{
            error("Invalid operator " + n.op.toString()+ " near line " + ((BinExprNode)n).lineNum);
        }
        
        //println(" ;") ;

    }

    public void visit (WhileStatementNode n) {
          
        System.out.println("WhileStatementNode");

         
        n.cond.accept(this);
        if(n.cond.type != Type.Bool){
            error("Conditional Statement does not equal a bool near line " + n.lineNum);
        }

        if(inLoop){
            n.stmt.accept(this);
        }
        else{
            inLoop = true;
            n.stmt.accept(this);
            inLoop = false;
        }
        
         
    }

    public void visit (IfStatementNode n) {
          
        System.out.println("IfStatementNode");

        
        n.cond.accept(this);
        if(n.cond.type != Type.Bool){
            error("Conditional Statement does not equal a bool near line " + n.cond.lineNum);
        }

         
        n.stmt.accept(this);
         
        if (n.else_stmt != null){
              
            System.out.println("ElseStatementNode");

             
            n.else_stmt.accept(this);
             
        }
    }
    public void visit (DoWhileStatementNode n) {
          
        System.out.println("DoWhileStatementNode");

        if(inLoop){
            n.stmt.accept(this);
        }
        else{
            inLoop = true;
            n.stmt.accept(this);
            inLoop = false;
        }
         

         
        n.cond.accept(this);
        if(n.cond.type != Type.Bool){
            error("Conditional Statement does not equal a bool near line " + n.lineNum);
        }
         
    }
    public void visit(ArrayAccessNode n){
          
        println("ArrayAccessNode");

         
        n.id.accept(this);
         

        if(n.index.type != Type.Int){
            error("Array index not of Type Int near line" + n.lineNum);
        }
        else{
            n.index.accept(this);
        }
        
         
    }
    public void visit(ArrayDimsNode n){
          
        println("ArrayDimsNode");

         
        n.size.accept(this);
         

        if(n.dim != null){  
            n.dim.accept(this);    
        }
        
    }
    public void visit (BreakStatementNode n) {
        if(inLoop){
            System.out.println("BreakNode");
        }
        else{
            error("Break Statement not in a loop near line " + n.lineNum);
        }
        
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
        print(n.id);
    }

    public void visit (TempNode n){
        print(n.id);
    }


}
