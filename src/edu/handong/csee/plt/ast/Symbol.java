package edu.handong.csee.plt.ast;

public class Symbol extends AST {
    String Symbol = " ";
	
	public Symbol(String Symbol){
		this.Symbol = Symbol;
	}
	
	public String getSymbol() {	
		return Symbol;
	}
	
	public String getASTCode() {
		return "(id '" + Symbol +")";
	}
}
