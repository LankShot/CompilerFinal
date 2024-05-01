package intercode.unparser;

import intercode.visitor.*;
import intercode.parser.*;
import intercode.ast.*;
import intercode.inter.*;

import java.io.* ;


public class Unparser extends ASTVisitor{
    public InterCode inter = null;
    private FileOutputStream out;
    private BufferedOutputStream bin;
    private LabelNode loopLabel = null;

    public Unparser(InterCode inter){
        this.inter = inter;
        setupIOStream();
        visit(this.inter.cu);
        try {
            bin.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Unparser(){
        visit(this.inter.cu);
        try {
            bin.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setupIOStream(){
        try{
            out = new FileOutputStream("intercode/output.txt");
            bin = new BufferedOutputStream(out);
        }
        catch(IOException e){
            System.out.println("IOException");
        }
    }


    ////////////////////////////////////////
    //  Utility mothods
    ////////////////////////////////////////

    void print(String s) {
        byte b[] = s.getBytes();
        try {
            bin.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void println(String s){
        s += "\n";
        print(s);
        indent = 3;
    }

    void printSpace(){
        print(" ");
    }

    int indent = 3;
    void indentUp(){
        indent ++;
    }

    void indentDown(){
        indent --;
    }

    void printIndent(){
        String s = "";
        for (int i = 0; i < indent; i++){
            s += "   ";
        }

        print(s);
    }

    ////////////////////

    public void visit (CompilationUnit n) {

        n.block.accept(this) ;
    }

    public void visit (BlockStatementNode n) {
       
        
        /*for (DeclarationNode decl : n.decls){
            decl.accept(this);
        }
        for( StatementNode stmt : n.stmts){
            stmt.accept(this);
        }*/
        for (StatementNode assign : n.assigns){
            assign.accept(this);
        }
        
    }

    public void visit (Declarations n){

        if (n.decls != null){

            n.decl.accept(this);
            n.decls.accept(this);
        }
    }

    public void visit (DeclarationNode n){
        
    }

    public void visit (TypeNode n){

        print(n.basic.toString());

        if(n.array != null)
            n.array.accept(this);
    }

    public void visit(ArrayTypeNode n){
        print("[");
        print("" + n.size);
        print("]");

        if(n.type != null)
            n.type.accept(this);
    }

    public void visit (Statements n){

        if (n.stmts != null){

            n.stmt.accept(this);

            n.stmts.accept(this);
        }
    }

    public void visit(StatementNode n){

    }

    public void visit(ParenthesesNode n){
        n.expr.accept(this);
    }

    public void visit(AssignmentNode n){
        if(n.inAssigns){
            printIndent();
            n.left.accept(this);
            
            print(" = ");
            
            if (n.right instanceof IdentifierNode){
                ((IdentifierNode)n.right).accept(this);
            }
            else if (n.right instanceof NumNode){
                ((NumNode)n.right).accept(this);
            }
            else if (n.right instanceof ParenthesesNode){
                ((ParenthesesNode)n.right).accept(this);
            }
            else if (n.right instanceof ArrayAccessNode){
                ((ArrayAccessNode)n.right).accept(this);
            }
            else if (n.right instanceof RealNode){
                ((RealNode)n.right).accept(this);
            }
            else if (n.right instanceof TrueNode){
                ((TrueNode)n.right).accept(this);
            }
            else if(n.right instanceof ArrayDimsNode){
                ((ArrayDimsNode)n.right).accept(this);
            }
            else{
                ((BinExprNode)n.right).accept(this);
            }

            println(" ;");
        }
    }
    
    public void visit (IdentifierNode n) {

        //printIndent();
        print(n.id);
        //println(" ;") ;

    }

    public void visit (NumNode n) {

        //printIndent();
        print("" + n.value);
        //println(" ;") ;

    }

    public void visit (RealNode n) {

        //printIndent();
        print("" + n.value);
        //println(" ;") ;

    }

    public void visit (BinExprNode n) {

        //printIndent();
        if (n.left instanceof IdentifierNode){
            ((IdentifierNode)n.left).accept(this);
        }
        else if(n.left instanceof NumNode){
            ((NumNode)n.left).accept(this);
        }
        else if(n.left instanceof RealNode){
            ((RealNode)n.left).accept(this);
        }
        else if (n.left instanceof ArrayAccessNode){
            ((ArrayAccessNode)n.left).accept(this);
        }
        else if(n.left instanceof ParenthesesNode){
            ((ParenthesesNode)n.left).accept(this);
        }
        else{
            ((BinExprNode)n.left).accept(this);
        }
        if (n.op != null){
            print(" "+ n.op.toString() + " ");
        }

        if(n.right != null){
            if (n.right instanceof IdentifierNode){
                ((IdentifierNode)n.right).accept(this);
            }
            else if(n.right instanceof NumNode){
                ((NumNode)n.right).accept(this);
            }
            else if(n.right instanceof RealNode){
                ((RealNode)n.right).accept(this);
            }
            else if (n.right instanceof ArrayAccessNode){
                ((ArrayAccessNode)n.right).accept(this);
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
        
        //println(" ;") ;

    }
    public void visit (WhileStatementNode n) {
        print(n.startLabel.id + ":");
        
        indentDown();
        
        if(!n.cond.isTrueNode){
            printIndent();
            print("ifFalse ");
            n.cond.accept(this);
            println(" goto "+n.stopLabel.id);
        }
        if(loopLabel==null){
            loopLabel = n.stopLabel;
        }

        n.stmt.accept(this);
        for (StatementNode assign : n.assigns){
            assign.accept(this);
        }
        if(!n.cond.isTrueNode){
            printIndent();
            n.cond.accept(this);
            print(" ");
        }
        else{
            printIndent();
        }
        
        println("goto " + n.startLabel.id);
        print(n.stopLabel.id + ":");

        indentDown();
        if (loopLabel == n.stopLabel){
            loopLabel = null;
        }
    }

    public void visit (IfStatementNode n) {
        for (StatementNode assign : n.assigns){
            assign.accept(this);
        }
        printIndent();
        print("ifFalse ");
        n.cond.accept(this);

        println(" goto " + n.falseLabel.id);

        n.stmt.accept(this);

        print(n.falseLabel.id + ":");
        indentDown();
        if(n.else_stmt != null){
            n.else_stmt.accept(this);
        }
    }

    public void visit (DoWhileStatementNode n) {
        
        print(n.startLabel.id + ":");
        
        indentDown();
        
        if(loopLabel==null){
            loopLabel = n.stopLabel;
        }

        n.stmt.accept(this);
        for (StatementNode assign : n.assigns){
            assign.accept(this);
        }
        /*if(!((IdentifierNode)n.cond.expr).isTrueNode){
            printIndent();
            n.cond.accept(this);
            print(" ");
        }*/
        printIndent();
        print("if ");
        n.cond.accept(this);

        
        println(" goto " + n.startLabel.id);
        print(n.stopLabel.id + ":");

        indentDown();
        if (loopLabel == n.stopLabel){
            loopLabel = null;
        }

    }

    public void visit (ArrayAccessNode n){
        
        n.id.accept(this);
        
        
        print("[");
        n.index.accept(this);
        print("]");
    }

    public void visit (ArrayDimsNode n){

        n.size.accept(this);
        if (n.dim != null){
            n.dim.accept(this);
        }
    }

    public void visit (BreakStatementNode n) {
        printIndent();
        println("goto "+ loopLabel.id);
    }
    public void visit (TrueNode n) {
        print("true");
    }
    public void visit (FalseNode n) {
        print("false");
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
