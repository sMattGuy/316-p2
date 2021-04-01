import java.io.*;

public class Parser{
	
}

//class definitions
abstract class classDefList{
	
}
class classDef extends classDefList{
	className classname;
	classBody classbody;
}
class multipleClassDef extends classDefList{
	classDef classdef;
	classDefList classdeflist;
}
class className extends classDef{
	String id;
}
class classBody extends classDef{
	fieldVarList fieldvarlist;
	funDefList fundeflist;
}
//field var list
abstract class fieldVarList{
	
}
class multiFieldVarList extends fieldVarList{
	fieldVar fieldvar;
	fieldVarList fieldvarlist;
}
class emptyFieldVarList{
	
}
class fieldVar extends fieldVarList{
	String id;
}
//function definition
abstract class funDefList extends fieldVarList{
	
}
class multiFunDefList extends fieldVarList{
	funDef fundef;
	funDefList fundeflist;
}
class emptyFunDefList extends fieldVarList{
	
}
class funDef extends funDefList{
	header head;
	exp expression;
}
class header extends funDef{
	funName funname;
	parameterList paramlist;
}
class funName extends header{
	String id;
}
//parameter
abstract class parameterList extends header{
	
}
class multiParameterList extends parameterList{
	parameter param;
	parameterList paramlist;
}
class emptyParameterList extends parameterList{
	
}
class parameter extends parameterList{
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
	expList explist;
}
//expresion 
abstract class expList extends funCall{
	
}
class multiExpList extends expList{
	exp expression;
	expList explist;
}
class emptyExpList extends expList{
	
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