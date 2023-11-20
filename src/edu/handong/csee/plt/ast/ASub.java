package edu.handong.csee.plt.ast;

public class ASub extends DefrdSub {
    String name;
	FAEVal value = new FAEVal();	
	DefrdSub ds = new DefrdSub();
	
	public ASub(String name, FAEVal value, DefrdSub ds) {
		this.name = name;
		this.value = value;
		this.ds = ds;
	}
	
	public ASub(String param, AST fstCall, DefrdSub ds2) {
    }

    public String getName() {
		return name;
	}
	
	public FAEVal getValue() {
		return value;
	}
	
	public DefrdSub getDS() {
		return ds;
	}

	public String getASTCode() {
		return "(aSub '" + this.name + " " + value.getASTCode() + " " + ds.getASTCode() +")";
	}
}
