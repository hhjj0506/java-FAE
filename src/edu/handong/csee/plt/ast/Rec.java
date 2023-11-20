package edu.handong.csee.plt.ast;

public class Rec extends AST {
    String name;
    AST funExpr;
    AST fstCall;

    public Rec(String name, AST funExpr, AST fstCall) {
        this.name = name;
        this.funExpr = funExpr;
        this.fstCall = fstCall;
    }

    public String getName() {
        return name;
    }

    public AST getFunExpr() {
        return funExpr;
    }

    public AST getFstCall() {
        return fstCall;
    }

    public String getASTCode() {
        return "(rec '" + name + " " + funExpr.getASTCode() + " " + fstCall.getASTCode() + ")";
    }
}
