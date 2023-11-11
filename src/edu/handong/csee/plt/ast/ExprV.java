package edu.handong.csee.plt.ast;

public class ExprV extends FAEVal {
    AST exprV = new AST();
	DefrdSub ds = new DefrdSub();
	FAEVal value = new FAEVal();
	
	public ExprV(AST expr, DefrdSub ds, FAEVal value) {
		this.exprV = expr;
		this.ds = ds;
		this.value = value;
	}
	
	public AST getExpr() {
		return exprV;
	}
	
	public DefrdSub getDS() {
		return ds;
	}
	
	public FAEVal getValue() {
		return value;
	}

	public String getASTCode() {
		return "(exprV " +  exprV.getASTCode() + " " + ds.getASTCode() + " " + value.getASTCode() + ")";
	}
}
