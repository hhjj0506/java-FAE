package edu.handong.csee.plt.ast;

public class NumV extends FAEVal {
    String num = "0";
	
	public NumV(String num){
		this.num = num;
	}
	
	public String getStrNum() {
		return num;
	}
	
	public String getASTCode() {
		return "(numV " + num +")";
	}
}
