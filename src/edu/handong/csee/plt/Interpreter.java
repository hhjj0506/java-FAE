package edu.handong.csee.plt;

import edu.handong.csee.plt.ast.AST;
import edu.handong.csee.plt.ast.ASub;
import edu.handong.csee.plt.ast.Add;
import edu.handong.csee.plt.ast.App;
import edu.handong.csee.plt.ast.ClosureV;
import edu.handong.csee.plt.ast.DefrdSub;
import edu.handong.csee.plt.ast.ExprV;
import edu.handong.csee.plt.ast.FAEVal;
import edu.handong.csee.plt.ast.Fun;
import edu.handong.csee.plt.ast.Num;
import edu.handong.csee.plt.ast.NumV;
import edu.handong.csee.plt.ast.Rec;
import edu.handong.csee.plt.ast.Sub;
import edu.handong.csee.plt.ast.Symbol;

public class Interpreter {

	private FAEVal numPlus(FAEVal a, FAEVal b) {  
		String add = "" + 
				(Integer.parseInt(((NumV)(a)).getStrNum()) + (Integer.parseInt(((NumV)b).getStrNum())));		
		NumV num = new NumV(add);				

		return num;
	}

	private FAEVal numMinus(FAEVal a, FAEVal b) {
		String sub = "" + 
				(Integer.parseInt(((NumV)(a)).getStrNum()) - (Integer.parseInt(((NumV)b).getStrNum())));		
		NumV num = new NumV(sub);	

		return num;
	}

	public FAEVal strict(FAEVal value) {					
		if(value instanceof ExprV) {
			ExprV expr = (ExprV)value;		
			if(((NumV)expr.getValue()).getStrNum() == "0") {
				FAEVal v = strict(interp(expr.getExpr(), expr.getDS())); 
				return v;
			}
			else {
				return expr.getValue();
			}
		}
		return value;
	}

	public boolean isRecursion(AST ast) {
        if (ast instanceof Rec) {
            Rec rec = (Rec) ast;
            return containsRecursion(rec.getFunExpr(), rec.getFunExpr()) ||
                    containsRecursion(rec.getFstCall(), rec.getFunExpr());
        }
        return false;
    }

    private boolean containsRecursion(AST current, AST target) {
        if (current == target) {
            return true;
        } else if (current instanceof Rec) {
            Rec rec = (Rec) current;
            return containsRecursion(rec.getFunExpr(), target) || containsRecursion(rec.getFstCall(), target);
        } else if (current instanceof Add) {
            Add add = (Add) current;
            return containsRecursion(add.getLhs(), target) || containsRecursion(add.getRhs(), target);
        }

        return false;
    }

    public AST desugar(AST ast) {
        if (isRecursion(ast)) {
            return desugarRecursion(ast);
        } else {
            return ast;
        }
    }
	
	private AST desugarRecursion(AST ast) {
		return null;
	}

	public FAEVal interp(AST ast, DefrdSub ds) {
				
		if(ast instanceof Num) {		
			NumV num = new NumV(((Num)ast).getStrNum());	
			return num;
		}
		
		if(ast instanceof Add) {
			Add add = (Add)ast;
			return numPlus(interp(add.getLhs(), ds), interp(add.getRhs(), ds));
		}
		
		if(ast instanceof Sub) {
			Sub sub = (Sub)ast;		
			return numMinus(interp(sub.getLhs(), ds), interp(sub.getRhs(), ds));
		}
		
		if(ast instanceof Symbol) {
			Symbol id = (Symbol)ast;
			return Lookup.lookup(id.getSymbol(), ds);
		}
		
		if(ast instanceof Fun) {
			ClosureV fun = new ClosureV(((Fun)ast).getParam(), ((Fun)ast).getFbody() ,ds);
			return fun;  
		}

		if (ast instanceof Rec) {
            Rec rec = (Rec) ast;
            return desugarRecursion(rec, ds);
        }
		
		if(ast instanceof App) {
			App app = (App)ast;

			FAEVal f_val = interp(app.getFundef(), ds);	
			
			NumV _false = new NumV("0");						
			ExprV expr = new ExprV(app.getArg(), ds, _false);
			FAEVal a_val = expr;									
			
			if(f_val instanceof ClosureV) {	
				DefrdSub sCache = new ASub(((ClosureV)f_val).getCparam(), a_val, ((ClosureV)f_val).getCds());
				return interp(((ClosureV)f_val).getCbody(), sCache);	
			} else {
				System.out.println("App Error");
				System.exit(1);
			}
		}
		
		return null;
	}

	private FAEVal desugarRecursion(Rec rec, DefrdSub ds) {
        String name = rec.getName();
        Fun funExpr = (Fun) rec.getFunExpr();
        Fun fstCall = (Fun) rec.getFstCall();

        // Apply the mk-rec template
        AST mkRecTemplate = new App(
                new Fun("mk-rec", new Fun("body-proc", new App(
                        new App(new Symbol("body-proc"), new Fun(name, funExpr.getFbody())), new Symbol(name))))
        );
        FAEVal desugared = interp(mkRecTemplate, ds);

        // Apply the desugared recursion
        App desugaredRecursion = new App(
                new App(new Symbol(name), desugared),
                interp(fstCall.getFbody(), new ASub(fstCall.getParam(), rec.getFstCall(), ds))
        );

        return interp(desugaredRecursion, ds);
    }
}
