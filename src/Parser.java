import java.io.*;
import java.util.LinkedList;
import java.util.HashMap;

public class Parser{
	public static void main(String[] args) throws IOException, FileNotFoundException{
		//error if arguments arent 1
		if(args.length != 2){
			System.out.println("Input must be 2 file\nUsage java LexAnalyzer input.txt output");
			return;
		}
		//creation of file read
		File file = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(file));
		//creations of file output
		PrintStream o = new PrintStream(new File(args[1]));
		PrintStream console = System.out;
		System.setOut(o);
		//parse tree classes
		multipleClassDef completeParse = new multipleClassDef();
		completeParse.classInfo = new ClassDefEntry();
		//individual class tree
		multipleClassDef parseStart = completeParse;
		//begin parse
		String token = LexAnalyzer.getToken(br);
		System.out.println(token);
		String[] token_split = token.split("|");
		while(token_split[1] != "EOF"){
			if(token_split[0] == "class"){
				//begin next step of parse
				token = LexAnalyzer.getToken(br);
				token_split = token.split("|");
				if(token_split[1] == "id"){
					parseStart.classdef.classname.id = token_split[0];
					//parse if either superclass or if start of inside;
					token = LexAnalyzer.getToken(br);
					token_split = token.split("|");
					//super class
					if(token_split[0] == ":"){
						token = LexAnalyzer.getToken(br);
						token_split = token.split("|");
						if(token_split[1] == "id"){
							//continue program
							parseStart.classdef.superClassName.id = token_split[0];
							classInfo.superClassName = token_split[0];
							token = LexAnalyzer.getToken(br);
							token_split = token.split("|");
						}
						else{
							System.out.println(token_split[0] + " Syntax Error, class identifier name expected");
							return;
						}
					}
					//open bracket
					if(token_split[0] == "{"){
						token = LexAnalyzer.getToken(br);
						token_split = token.split("|");
						multiFieldVarList fields = parseStart.classdef.classbody.multifieldvarlist;
						while(token_split[1] == "id"){
							//add token to external structure and parse tree
							classInfo.fields.add(token_split[0]);
							fields.fieldvar.id = token_split[0];
							//begin recursive call
							fields = fields.multifieldvarlist;
							token = LexAnalyzer.getToken(br);
							token_split = token.split("|");
						}
						//end of multi recursive, make null
						fields = null;
						//begin function definitions
						multiFunDefList funcdef = parseStart.classdef.classbody.multifundeflist;
						while(token_split[0] == "("){
							//individual function parser
							if(token_split[0] == "("){
								int parenCount = 1;
								token = LexAnalyzer.getToken(br);
								token_split = token.split("|");
								//header
								if(token_split[0] == "("){
									parenCount++;
									token = LexAnalyzer.getToken(br);
									token_split = token.split("|");
									//fun name gotten
									if(token_split[1] == "id"){
										//LL for params
										LinkedList<String> params = new LinkedList<String>();
										funcdef.fundef.head.funname.id = token_split[0];
										//get params
										token = LexAnalyzer.getToken(br);
										token_split = token.split("|");
										//set up recursive for param list
										multiParameterList paramsList = funcdef.fundef.head.multiparamlist
										while(token_split[0] != ")"){
											if(token_split[1] == "id"){
												//add param to tree and LL
												paramsList.param.id = token_split[0];
												params.add(token_split[0]);
												//recursive param list
												paramsList = paramsList.multiparamlist;
												//next token
												token = LexAnalyzer.getToken(br);
												token_split = token.split("|");
											}
											else{
												//error on param name
												System.out.println(token_split[0] + " Syntax Error, expected parameter id");
												return;
											}
										}
										//subtrack param
										parenCount--;
										//terminate params
										paramsList = null;
										//add func def to hashmap
										classInfo.funMap.put(funcdef.fundef.header.funname.id, params);
										//parse expressions
										/**
										note to me:
										do the parse as follows,
										can either be constant, id, keyword, or function expression
										check in this order as it will be easier
										id must be checked against existing id for function, use params LL
										**/
										token = LexAnalyzer.getToken(br);
										token_split = token.split("|");
										//check catagories for expression
										while(parenCount != 0){
											switch(token_split[1]){
												case "id":
													if(params.contains(token_split[0])){
														
													}
													else{
														//error, keyword not defined in function doesnt crash just alerts
													}
												break;
												case "int":
												break;
												case "float":
												break;
												case "floatE":
												break;
												case "keyword_null":
												break;
												case "keyword_this":
												break;
												case "RParen":
													//function expression start
												break;
												case "LParen":
													parenCount--;
												break;
												default:
												//error
											}
										}
									}
									else{
										//error on func name
										System.out.println(token_split[0] + " Syntax Error, expected function name");
										return;
									}
								}
								else{
									//error expected header start
									System.out.println(token_split[0] + " Syntax Error, expected function header");
									return;
								}
							}
							else{
								//error expected function start
							}
						}
						funcdef = null;
						//if no functions or functions ended
						if(token_split[0] == "}"){
							
						}
						//error something other than function start or end of class
						else{
							System.out.println(token_split[0] + " Syntax Error, " + "}"  + "expected");
							return;
						}
					}
					//error
					else{
						System.out.println(token_split[0] + " Syntax Error, " + "{" + "expected");
						return;
					}
				}
				else{
					//error if id not specified
					System.out.println(token_split[0] + " Syntax Error, class identifier name expected");
					return;
				}
			}
			else{
				//error
				System.out.println(token_split[0] + " : Syntax Error, unexpected symbol where " + "class" + " expected");
				return;
			}
			parseStart = parseStart.multiclassdef;
		}
		parseStart = null;
		while(completeParse.multiclassdef != null){
			System.out.println(completeParse.classInfo);
		}
	}
}

//class definitions
//information class
class ClassDefEntry // symbol table entry for a single ⟨class def⟩
{
	String superClassName; // value is "" if superclass is absent
	LinkedList<String> fields = new LinkedList<String>();
	HashMap<String, LinkedList<String>> funMap = new HashMap<String, LinkedList<String>>();
		// function names mapped to their parameters

	public String toString()
	{
		return "\nsuperclass=" + superClassName + "\nfields=" + fields.toString() + "\nfunctions=" + funMap.toString();
	}
}
//parse classes
class multipleClassDef{
	classDef classdef;
	multipleClassDef multiclassdef;
	ClassDefEntry classInfo;
}
class classDef extends multipleClassDef{
	className classname;
	className superClassName;
	classBody classbody;
}
class className extends classDef{
	String id;
}
class classBody extends classDef{
	multiFieldVarList multifieldvarlist;
	multiFunDefList multifundeflist;
}

//field var list
class multiFieldVarList extends classBody{
	fieldVar fieldvar;
	multiFieldVarList multifieldvarlist;
}
class fieldVar extends multiFieldVarList{
	String id;
}

//function definition
class multiFunDefList extends classBody{
	funDef fundef;
	multiFunDefList multifundeflist;
}
class funDef extends multiFunDefList{
	header head;
	exp expression;
}
class header extends funDef{
	funName funname;
	multiParameterList multiparamlist;
}
class funName extends header{
	String id;
}

//parameter
class multiParameterList extends header{
	parameter param;
	multiParameterList multiparamlist;
}
class parameter extends multiParameterList{
	String id;
}
class exp extends funDef{
	String id;
	int integer;
	float decimal;
	funExp funexp;
}
class funExp extends exp{
	funCall funcall;
	binaryExp binexp;
	cond condition;
	not notType;
}
class funCall extends funExp{
	funName funname;
	multiExpList multiexplist;
}

//expresion 
class multiExpList extends funCall{
	exp expression;
	multiExpList multiexplist;
}
class binaryExp extends funExp{
	arithExp arithexp;
	boolExp boolexp;
	compExp compexp;
	dotExp dotexp;
}
class arithExp extends binaryExp{
	arithOp arithop;
	exp exp1;
	exp exp2;
}
class boolExp extends binaryExp{
	boolOp bollop;
	exp exp1;
	exp exp2;
}
class compExp extends binaryExp{
	compOp compop;
	exp exp1;
	exp exp2;
}
class dotExp extends binaryExp{
	exp exp1;
	exp exp2;
}
class cond extends funExp{
	exp exp1;
	exp exp2;
	exp exp3;
}
class not extends funExp{
	exp exp1;
}
class arithOp extends arithExp{
	String operator;
}
class boolOp extends boolExp{
	String operator;
}
class compOp extends compExp{
	String operator;
}