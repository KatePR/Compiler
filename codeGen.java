import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class codeGen {
    public static void main(String args[]) throws FileNotFoundException
    {
        new Parser().Program();
    }
}

enum tokenType       // give a name to each terminal
{ mainToken,    valToken,     refToken,   intToken,
    readToken,    writeToken,   callToken,  ifToken,
    elseToken,    endifToken,   whileToken, id,
    number,       leftParen,    rightParen, leftBrace,
    rightBrace,   comma,        semicolon,  assignment,
    equality,     notEqual,     less,       lessEqual,
    greater,      greaterEqual, plus,       minus,
    times,        slash
};

class Globs
{
    static String[] tokenNameStrings=
            { "mainToken    ", "valToken     ", "refToken     ", "intToken     ",
                    "readToken    ", "writeToken   ", "callToken    ", "ifToken      ",
                    "elseToken    ", "endifToken   ", "whileToken   ", "id           ",
                    "number       ", "leftParen    ", "rightParen   ", "leftBrace    ",
                    "rightBrace   ", "comma        ", "semicolon    ", "assignment   ",
                    "equality     ", "notEqual     ", "less         ", "lessEqual    ",
                    "greater      ", "greaterEqual ", "plus         ", "minus        ",
                    "times        ", "slash        "
            };



    static void error(int code)
    { switch ( code )
    { case  0: System.out.println("error type 0 at line "+
            SourceFileReader.linecount+
            ": program incomplete\n");
        System.exit(0);

        case  1: System.out.println("error type 1 at line "+
                SourceFileReader.linecount+
                ": unknown symbol\n");
            System.exit(0);


        case  2: System.out.println("error type 2 at line "+
                SourceFileReader.linecount+
                ": 'main' expected\n");
            System.exit(0);

        case  3: System.out.println("error type 3 at line "+
                SourceFileReader.linecount+
                ": '(' expected\n");
            System.exit(0);
        case  4: System.out.println("error type 4 at line "+
                SourceFileReader.linecount+
                ": ')' expected\n");
            System.exit(0);

        case 5: System.out.println("error type 5 at line "+
                SourceFileReader.linecount+
                ": 'id' expected\n");
            System.exit(0);

        case 6: System.out.println("error type 6 at line "+
                SourceFileReader.linecount+
                ": 'val' expected\n");
            System.exit(0);

        case 7: System.out.println("error type 7 at line "+
                SourceFileReader.linecount+
                ": 'ref' expected\n");
            System.exit(0);

        case 8: System.out.println("error type 8 at line "+
                SourceFileReader.linecount+
                ": ',' expected\n");
            System.exit(0);

        case 9: System.out.println("error type 9 at line "+
                SourceFileReader.linecount+
                ": ';' expected\n");
            System.exit(0);

        case 10: System.out.println("error type 10 at line "+
                SourceFileReader.linecount+
                ": 'int' expected\n");
            System.exit(0);

        case 11: System.out.println("error type 11 at line "+
                SourceFileReader.linecount+
                ": '{' expected\n");
            System.exit(0);

        case 12: System.out.println("error type 12 at line "+
                SourceFileReader.linecount+
                ": '}' expected\n");
            System.exit(0);

        case 13: System.out.println("error type 13 at line "+
                SourceFileReader.linecount+
                ": 'readToken' expected\n");
            System.exit(0);

        case 14: System.out.println("error type 14 at line "+
                SourceFileReader.linecount+
                ": 'writeToken' expected\n");
            System.exit(0);

        case 15: System.out.println("error type 15 at line "+
                SourceFileReader.linecount+
                ": 'callToken' expected\n");
            System.exit(0);

        case 16: System.out.println("error type 16 at line "+
                SourceFileReader.linecount+
                ": 'ifToken' expected\n");
            System.exit(0);

        case 17: System.out.println("error type 17 at line "+
                SourceFileReader.linecount+
                ": 'whileToken' expected\n");
            System.exit(0);

        case 18: System.out.println("error type 18 at line "+
                SourceFileReader.linecount+
                ": 'number' expected\n");
            System.exit(0);

        case 19: System.out.println("error type 19 at line "+
                SourceFileReader.linecount+
                ": '>=' expected\n");
            System.exit(0);

        case 20: System.out.println("error type 20 at line "+
                SourceFileReader.linecount+
                ": '>' expected\n");
            System.exit(0);

        case 21: System.out.println("error type 21 at line "+
                SourceFileReader.linecount+
                ": '==' expected\n");
            System.exit(0);

        case 22: System.out.println("error type 22 at line "+
                SourceFileReader.linecount+
                ": '!=' expected\n");
            System.exit(0);

        case 23: System.out.println("error type 23 at line "+
                SourceFileReader.linecount+
                ": '-' expected\n");
            System.exit(0);

        case 24: System.out.println("error type 24 at line "+
                SourceFileReader.linecount+
                ": '+' expected\n");
            System.exit(0);

        case 25: System.out.println("error type 25 at line "+
                SourceFileReader.linecount+
                ": '*' expected\n");
            System.exit(0);

        case 26: System.out.println("error type 26 at line "+
                SourceFileReader.linecount+
                ": '/' expected\n");
            System.exit(0);

        case 27: System.out.println("error type 27 at line "+
                SourceFileReader.linecount+
                ": 'endif' expected\n");
            System.exit(0);

        case 28: System.out.println("error type 28 at line "+
                SourceFileReader.linecount+
                ": 'else' expected\n");
            System.exit(0);

        case 29: System.out.println("error type 29 at line "+
                SourceFileReader.linecount+
                ": '<' expected\n");
            System.exit(0);

        case 30: System.out.println("error type 30 at line "+
                SourceFileReader.linecount+
                ": '<=' expected\n");
            System.exit(0);

        case 31: System.out.println("error type 31 at line "+
                SourceFileReader.linecount+
                ": '=' expected\n");
            System.exit(0);

        case 32: System.out.println("error type 32 at line "+
                SourceFileReader.linecount+
                ": main or id expected\n");
            System.exit(0);

        case 33: System.out.println("error type 33 at line "+
                SourceFileReader.linecount+
                ": val or ref expected\n");
            System.exit(0);

        case 34: System.out.println("error type 34 at line "+
                SourceFileReader.linecount+
                ": val, ref, ), or , expected\n");
            System.exit(0);

        case 35: System.out.println("error type 35 at line "+
                SourceFileReader.linecount+
                ": int, {, }, ;, read, write, call, if, while, orf id expected\n");
            System.exit(0);

        case 36: System.out.println("error type 36 at line "+
                SourceFileReader.linecount+
                ": {, ;, read, write, call, if, while, id, or } expected\n");
            System.exit(0);

        case 37: System.out.println("error type 37 at line "+
                SourceFileReader.linecount+
                ": ==, !=, >, >=, <, or <= expected\n");
            System.exit(0);

        case 38: System.out.println("error type 38 at line "+
                SourceFileReader.linecount+
                ": - or + expected\n");
            System.exit(0);

        case 39: System.out.println("error type 39 at line "+
                SourceFileReader.linecount+
                ": ;, ), ==, !=, >, >=, <, <=, -, or plus expected\n");
            System.exit(0);

        case 40: System.out.println("error type 40 at line "+
                SourceFileReader.linecount+
                ": ;, ), ==, !=, >, >=, <, <=, -, +, /, or * expected\n");
            System.exit(0);

        case 41: System.out.println("error type 41 at line "+
                SourceFileReader.linecount+
                ": (, number, or id expected\n");
            System.exit(0);

        case 42: System.out.println("error type 42 at line "+
                SourceFileReader.linecount+
                ": ), id, or ',' expected\n");
            System.exit(0);

        case 43: System.out.println("error type 43 at line "+
                SourceFileReader.linecount+
                ": {, }, ;, read, write, call, if, while, or id expected\n");
            System.exit(0);

        case 44: System.out.println("error type 44 at line "+
                SourceFileReader.linecount+
                ": endif or else expected\n");
            System.exit(0);

        case 45: System.out.println("error type 45 at line "+
                SourceFileReader.linecount+
                ": ;, id, read, write, {, while, or if expected\n");
            System.exit(0);

        case 46: System.out.println("error type 46 at line "+
                SourceFileReader.linecount+
                ": +, -, id, (, or number expected\n");
            System.exit(0);

            //***add cases for other syntax errors***

    } //end switch

    } //end function error

}  //end Globs


class SourceFileReader
{
    static int  linecount=1;  // number of lines read
    Scanner source;
    char letter;
    boolean EOF;

    SourceFileReader() throws FileNotFoundException
    {
        source=new Scanner(new File("source.bc"));
        source.useDelimiter("");
        letter=' ';
        EOF=false;
    }

    void getCharacter()
    { if (EOF) Globs.error(0);   //program incomplete, halt compiler
        if (! source.hasNext())
        { EOF=true; //sets end-of-file flag
            letter=' ';
            return;
        }
        letter=source.next().charAt(0);   //reads one character from file

        System.out.print(letter); //for debugging

        if ( letter == '\n') { letter= ' ';   linecount++; }
        else if ( letter == '\t'||letter=='\r') letter= ' ';
    } // end getCharacter
} //end SourceFileReader

class LexAnalyzer { //***for debugging only***
    PrintWriter lexout;
    // end ***for debugging only***
    tokenType token = null;    // lookahead token
    String lexeme;        // spelling of identifier token
    int value;         // value of number token

    SourceFileReader source;

    LexAnalyzer() throws FileNotFoundException {
        source = new SourceFileReader();
        //***for debugging only***
        lexout = new PrintWriter("lexout.txt");
        // end ***for debugging only***
    }

    class reservedWord {
        String spelling;
        tokenType kind;

        reservedWord(String s, tokenType t) {
            spelling = s;
            kind = t;
        }

    }

    reservedWord[] reservedWords =
            {new reservedWord("call", tokenType.callToken),
                    new reservedWord("else", tokenType.elseToken),
                    new reservedWord("endif", tokenType.endifToken),
                    new reservedWord("if", tokenType.ifToken),
                    new reservedWord("int", tokenType.intToken),
                    new reservedWord("main", tokenType.mainToken),
                    new reservedWord("read", tokenType.readToken),
                    new reservedWord("ref", tokenType.refToken),
                    new reservedWord("val", tokenType.valToken),
                    new reservedWord("while", tokenType.whileToken),
                    new reservedWord("write", tokenType.writeToken)
            };

    void getToken() {

        int i = 0, k;
        while (source.letter == ' ') source.getCharacter();  //skip blanks

        String cases = "unknown";

        if (Character.isLetter(source.letter)){
            cases = "letter";
        }
        else if(Character.isDigit(source.letter)){
            cases = "number";
        }
        else {
            cases = Character.toString(source.letter);
        }

        switch (cases){
            //case for letters
            case  "letter":
                String lexemeName = "";

                while (Character.isLetter(source.letter) || Character.isDigit(source.letter)){
                    lexemeName += Character.toString(source.letter);
                    token = tokenType.id;
                    source.getCharacter();
                }
                //single character id
                if(lexemeName.length() == 1){
                    token = tokenType.id;
                }
                //will not recognize symbols that are uppercase
                if(!lexemeName.equals(lexemeName.toLowerCase())) {
                    Globs.error(1);
                }

                //checks to see if the string is a reserved word
                for(int x = 0; x < reservedWords.length; x++){
                    if (reservedWords[x].spelling.equals(lexemeName)){
                        token = reservedWords[x].kind;
                    }
                }
                lexeme = lexemeName;
                break;

            //case for numbers
            case "number":
                String intValue = "";
                while (Character.isDigit(source.letter)){
                    intValue += source.letter;
                    source.getCharacter();
                }
                value = Integer.valueOf(intValue);
                token = tokenType.number;
                break;

            //cases for tokens
            case "(":
                token = tokenType.leftParen;
                source.getCharacter();
                break;

            case ")":
                token = tokenType.rightParen;
                source.getCharacter();
                break;

            case "{":
                token = tokenType.leftBrace;
                source.getCharacter();
                break;

            case "}":
                token = tokenType.rightBrace;
                source.getCharacter();
                break;

            case ",":
                token = tokenType.comma;
                source.getCharacter();
                break;

            case ";":
                token = tokenType.semicolon;
                source.getCharacter();
                break;

            case "=":
                String tempString = "";
                while(source.letter == '=' || source.letter == '='){
                    tempString += source.letter;
                    source.getCharacter();
                }
                if(tempString.equals("=")){
                    token = tokenType.assignment;
                }else if(tempString.equals("==")){
                    token = tokenType.equality;
                }
                else{
                    Globs.error(1);
                }
                break;

            case "!":
                String tempString2 = "";
                while(source.letter == '=' || source.letter == '!'){
                    tempString2 += source.letter;
                    source.getCharacter();
                }
                if(tempString2.equals("!=")) {
                    token = tokenType.notEqual;
                } else{
                    Globs.error(1);
                }
                break;

            case "<":
                String tempString3 = "";
                while(source.letter == '=' || source.letter == '<'){
                    tempString3 += source.letter;
                    source.getCharacter();
                }
                if(tempString3.equals("<=")) {
                    token = tokenType.lessEqual;
                }
                else if(tempString3.equals("<")){
                    token = tokenType.less;
                }else{
                    Globs.error(1);
                }
                break;

            case ">":
                String tempString4 = "";
                while(source.letter == '=' || source.letter == '>'){
                    tempString4 += source.letter;
                    source.getCharacter();
                }
                if(tempString4.equals(">=")) {
                    token = tokenType.greaterEqual;

                }
                else if(tempString4.equals(">")){
                    token = tokenType.greater;
                }else{
                    Globs.error(1);
                }
                break;

            case "+":
                token = tokenType.plus;
                source.getCharacter();
                break;

            case "-":
                token = tokenType.minus;
                source.getCharacter();
                break;

            case "*":
                token = tokenType.times;
                source.getCharacter();
                break;

            case "/":
                token = tokenType.slash;
                source.getCharacter();
                break;

            default:
                Globs.error(1);
                break;

        }
    } //end function getToken
} //end class LexAnalyzer

class Parser {

    LexAnalyzer lexan;
    PrintWriter lexout;
    String tableName;
    SymbolTable symbolTable;
    TargetCode target;

    //Constructor
    Parser() throws FileNotFoundException {
        lexan = new LexAnalyzer();
        lexan.getToken();
        symbolTable = new SymbolTable();

        lexout = new PrintWriter("lexout.txt");
        PrintOut();

        target = new TargetCode();

    }

//this variable will be set to true only when parsing the main function
//if it is true, the parser will not read a lookahead token after the main
//function is parsed completely.

    boolean noLookAhead = false;

//***define parser functions***

    void PrintOut() {
        lexout.print(Globs.tokenNameStrings[lexan.token.ordinal()]);
        if (lexan.token == tokenType.id) lexout.print("     " + lexan.lexeme);
        if (lexan.token == tokenType.number) lexout.print("     " + lexan.value);
        lexout.println();
        lexout.flush();
    }

    void MainFunction() {
        symbolTable.a1();
        lexan.getToken();   //get past mainToken
        PrintOut();
        if (lexan.token != tokenType.leftParen) Globs.error(3);
        lexan.getToken();
        PrintOut();
        if (lexan.token != tokenType.rightParen) Globs.error(4);
        lexan.getToken();
        PrintOut();
        noLookAhead = true;
        FunctionBody();
    }

    //***define other parser functions***

    void Program() {
        int numCix = 0;
        numCix = target.cix;
        target.gen(target.cix, TargetCode.operation.jmp, 0);  //a1
        while(lexan.token == tokenType.id) {
            Function();
        } if (lexan.token == tokenType.mainToken) {
            target.code[numCix].disp = target.cix;  //a2
            MainFunction();
        } else {
            Globs.error(32);
        }
    }

    void Function() {
        FunctionHeading();
        FunctionBody();
    }

    void FunctionHeading() {

        symbolTable.b1(lexan.lexeme);

        tableName = "";

        if (lexan.token == tokenType.id) {
            lexan.getToken();
            PrintOut();
        } else {
            Globs.error(5);
        }

        if (lexan.token == tokenType.leftParen) {
            lexan.getToken();
            PrintOut();
        } else {
            Globs.error(3);
        }

        if (lexan.token == tokenType.valToken || lexan.token == tokenType.refToken) {
            symbolTable.b2(lexan.lexeme);
            lexan.getToken();
            PrintOut();

            symbolTable.b3(lexan.lexeme);
            if (lexan.token == tokenType.id) {
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(5);
            }
            while (lexan.token == tokenType.comma) {
                lexan.getToken();
                PrintOut();

                symbolTable.b2(lexan.lexeme);
                if (lexan.token == tokenType.valToken) {
                    lexan.getToken();
                    PrintOut();
                } else if (lexan.token == tokenType.refToken) {
                    lexan.getToken();
                    PrintOut();
                } else {
                    Globs.error(33);
                }
                symbolTable.b5(lexan.lexeme);
                if (lexan.token == tokenType.id) {
                    lexan.getToken();
                    PrintOut();
                } else {
                    Globs.error(5);
                }
            }
        } else {
            //blank
        }
        symbolTable.b6();
        if (lexan.token == tokenType.rightParen) {
            lexan.getToken();
            PrintOut();
        } else {
            Globs.error(34);
        }
    }

    void FunctionBody() {

        if (lexan.token == tokenType.leftBrace) {
            symbolTable.c1();
            symbolTable.symTable[symbolTable.ftix].disp = target.cix; //c1
            lexan.getToken();
            PrintOut();
        } else {
            Globs.error(11);
        }

        while (lexan.token == tokenType.intToken) {
            Declaration();
            if(symbolTable.ldisp > 2){  //c2
                target.gen(target.cix, TargetCode.operation.isp,symbolTable.ldisp - 2);
            }
        }
        if (lexan.token == tokenType.leftBrace ||
                lexan.token == tokenType.rightBrace || lexan.token == tokenType.semicolon || lexan.token == tokenType.readToken ||
                lexan.token == tokenType.writeToken || lexan.token == tokenType.callToken ||
                lexan.token == tokenType.ifToken || lexan.token == tokenType.whileToken ||
                lexan.token == tokenType.id) {

        } else {
            Globs.error(35);
        }
        while (lexan.token == tokenType.leftBrace || lexan.token == tokenType.semicolon || lexan.token == tokenType.readToken ||
                lexan.token == tokenType.writeToken || lexan.token == tokenType.callToken ||
                lexan.token == tokenType.ifToken || lexan.token == tokenType.whileToken ||
                lexan.token == tokenType.id) {
            Statement();
        }
        symbolTable.c2();
        target.gen(target.cix, TargetCode.operation.rtn, 0);

        if (lexan.token == tokenType.rightBrace) {
            if (noLookAhead == false) {
                lexan.getToken();
                PrintOut();

            } else if (noLookAhead == true) {
                //blank
                //lexan.getToken();
                PrintOut();
                try {
                    target.writeTarget();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Globs.error(36);
        }

    }

    void Declaration() {
        if (lexan.token == tokenType.intToken) {
            lexan.getToken();
            PrintOut();
        } else {
            Globs.error(10);
        }

        symbolTable.d1(lexan.lexeme);
        if (lexan.token == tokenType.id) {
            lexan.getToken();
            PrintOut();
        } else {
            Globs.error(5);
        }

        while (lexan.token == tokenType.comma) {
            lexan.getToken();
            PrintOut();
            symbolTable.d2(lexan.lexeme);
            if (lexan.token == tokenType.id) {
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(5);
            }
        }

        if (lexan.token == tokenType.semicolon) {
            lexan.getToken();
            PrintOut();
        } else {
            Globs.error(9);
        }
    }

    void Condition() {

        Expression();

        TargetCode.operation op = TargetCode.operation.add;
            if (lexan.token == tokenType.equality){
                op = TargetCode.operation.equ;
                lexan.getToken();
                PrintOut();

            }else if(lexan.token == tokenType.notEqual){
                op = TargetCode.operation.neq;
                lexan.getToken();
                PrintOut();

            }else if(lexan.token == tokenType.greater){
                op = TargetCode.operation.gtr;
                lexan.getToken();
                PrintOut();

            }else if(lexan.token == tokenType.greaterEqual){
                op = TargetCode.operation.geq;
                lexan.getToken();
                PrintOut();

            }else if(lexan.token == tokenType.less){
                op = TargetCode.operation.les;
                lexan.getToken();
                PrintOut();

            }else if(lexan.token == tokenType.lessEqual){
                op = TargetCode.operation.leq;
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(37);
            }
            Expression();
            target.gen(target.cix, op, 0);
        }

    void Expression() {

        TargetCode.operation op;
        char negate = ' ';

        if (lexan.token == tokenType.plus){
            lexan.getToken();
            PrintOut();
        }else if(lexan.token == tokenType.minus) {
            negate = 'Y';
            lexan.getToken();
            PrintOut();
        } else if (lexan.token == tokenType.id || lexan.token == tokenType.leftParen ||
                lexan.token == tokenType.number) {
            //blank
        } else {
            Globs.error(46);
        }
        Term();
        if(negate == 'Y'){
            target.gen(target.cix, TargetCode.operation.neg, 0);
        }

        while (lexan.token == tokenType.plus || lexan.token == tokenType.minus) {
            if(lexan.token == tokenType.plus){
                op = TargetCode.operation.add;
            }else{
                op = TargetCode.operation.sub;
            }
            lexan.getToken();
            PrintOut();
            Term();
            target.gen(target.cix, op, 0);
        }

        if (lexan.token == tokenType.semicolon || lexan.token == tokenType.rightParen ||
                lexan.token == tokenType.equality || lexan.token == tokenType.notEqual ||
                lexan.token == tokenType.greater || lexan.token == tokenType.greaterEqual ||
                lexan.token == tokenType.less || lexan.token == tokenType.lessEqual) {
            //blank
        } else {
            Globs.error(39);
        }
    }

    void Term() {
        TargetCode.operation op;
        Factor();
        while (lexan.token == tokenType.times || lexan.token == tokenType.slash) {
            if(lexan.token == tokenType.times){
                op = TargetCode.operation.mul;
            }else{
                op = TargetCode.operation.dvd;
            }
            lexan.getToken();
            PrintOut();
            Factor();
            target.gen(target.cix, op, 0);
        }
        if (lexan.token == tokenType.semicolon || lexan.token == tokenType.rightParen ||
                lexan.token == tokenType.equality || lexan.token == tokenType.notEqual ||
                lexan.token == tokenType.greater || lexan.token == tokenType.greaterEqual ||
                lexan.token == tokenType.less || lexan.token == tokenType.lessEqual ||
                lexan.token == tokenType.plus || lexan.token == tokenType.minus) {
            //blank
        } else {
            //error for all and / and *
            Globs.error(40);
        }
    }

    void Factor() {
        TargetCode.operation op;
        if (lexan.token == tokenType.leftParen) {
            lexan.getToken();
            PrintOut();
            Expression();
            if (lexan.token == tokenType.rightParen) {
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(4);
            }
        } else if (lexan.token == tokenType.number) {
            target.gen(target.cix, TargetCode.operation.loc, lexan.value);  //i2
            lexan.getToken();
            PrintOut();
        } else if (lexan.token == tokenType.id) {
            symbolTable.e1(lexan.lexeme);

            int idtix = symbolTable.searchVariable(lexan.lexeme);  //i1

            if(symbolTable.symTable[idtix].type == 'r'){
                op = TargetCode.operation.ldi;
            }else{
                op = TargetCode.operation.lod;
            }
            target.gen(target.cix, op, symbolTable.symTable[idtix].disp);
            lexan.getToken();
            PrintOut();
        } else {
            Globs.error(41);
        }
    }

    void Statement() {

        int idtix = 0; //index of an id
        TargetCode.operation op; //opcode for an instruction
        int tempcix1, tempcix2;
        char[] callee = new char[40];
        symbolTable.numActual = 0;

        if (lexan.token == tokenType.callToken) {
            lexan.getToken();
            PrintOut();

            symbolTable.e2(lexan.lexeme);

            //int calleetix = symbolTable.searchFunction(lexan.lexeme);
            //System.out.print("AHHHH!");


            if (lexan.token == tokenType.id) {
                for(int i = 0; i < symbolTable.symTable[symbolTable.calleetix].pcnt; i++){
                   callee[i] = symbolTable.symTable[symbolTable.calleetix].reva[i];
                }
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(5);
            }
            if (lexan.token == tokenType.leftParen) {
                idtix = symbolTable.searchFunction(lexan.lexeme);
                if(callee[0] == 'u'){
                    if(symbolTable.symTable[idtix].type == 'u'){
                        op = TargetCode.operation.lod;
                    }else if(symbolTable.symTable[idtix].type == 'r'){
                        op = TargetCode.operation.ldi;
                    }else{
                        op = TargetCode.operation.lod;
                    }
                }else{
                    if(symbolTable.symTable[idtix].type == 'u'){
                        op = TargetCode.operation.lda;
                    }else if(symbolTable.symTable[idtix].type == 'r'){
                        op = TargetCode.operation.lod;
                    }else{
                        op = TargetCode.operation.lda;
                    }
                }
                target.gen(target.cix, op, symbolTable.symTable[idtix].disp);

                //idtix = symbolTable.tix;
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(3);
            }
            if (lexan.token == tokenType.id) {
                symbolTable.e3(lexan.lexeme);
                lexan.getToken();
                PrintOut();

                int i = 0;
                while (lexan.token == tokenType.comma) {
                    i++;
                    lexan.getToken();
                    idtix = symbolTable.searchVariable(lexan.lexeme);
                    if(callee[i] == 'u'){
                        if(symbolTable.symTable[idtix].type == 'v'){
                            op = TargetCode.operation.lod;
                        }else if(symbolTable.symTable[idtix].type == 'r'){
                            op = TargetCode.operation.ldi;
                        }else{
                            op = TargetCode.operation.lod;
                        }
                    }else{
                        if(symbolTable.symTable[idtix].type == 'v'){
                            op = TargetCode.operation.lda;
                        }else if(symbolTable.symTable[idtix].type == 'r'){
                            op = TargetCode.operation.lod;
                        }else{
                            op = TargetCode.operation.lda;
                        }
                    }
                    target.gen(target.cix, op, symbolTable.symTable[idtix].disp);

                    PrintOut();
                    symbolTable.e3(lexan.lexeme);
                    if (lexan.token == tokenType.id) {
                        lexan.getToken();
                        PrintOut();
                    } else {
                        Globs.error(5);
                    }
                }
            } else {
                //blank
            }
            symbolTable.e4();
            if (lexan.token == tokenType.rightParen) {
                lexan.getToken();
                //e5.3: correct numActual and calleetix (symtable index for callee) still available
                target.gen(target.cix, TargetCode.operation.cal, symbolTable.symTable[symbolTable.calleetix].disp);
                target.gen(target.cix, TargetCode.operation.isp, -symbolTable.numActual);
                PrintOut();
            } else {
                Globs.error(42);
            }
            if (lexan.token == tokenType.semicolon) {
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(9);
            }
        } else if (lexan.token == tokenType.semicolon) {
            lexan.getToken();
            PrintOut();

        } else if (lexan.token == tokenType.id) {  //e2.1
            symbolTable.e1(lexan.lexeme);
            idtix = symbolTable.searchVariable(lexan.lexeme);
            lexan.getToken();
            PrintOut();
            if (lexan.token == tokenType.assignment) {
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(31); //assignment error
            }
            Expression();

            if(symbolTable.symTable[idtix].type == 'r'){    //e2.2
                op = TargetCode.operation.sti;
            }else{
                op = TargetCode.operation.sto;
            }
            target.gen(target.cix, op, symbolTable.symTable[idtix].disp);

            if (lexan.token == tokenType.semicolon) {
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(9);
            }
        } else if (lexan.token == tokenType.readToken) {
            target.gen(target.cix, TargetCode.operation.inp, 0);  //e3.1
            idtix = symbolTable.tix;
            if(symbolTable.symTable[idtix].type == 'r'){
                op = TargetCode.operation.sti;
            }else{
                op = TargetCode.operation.sto;
            }
            target.gen(target.cix, op, symbolTable.symTable[idtix].disp);
            lexan.getToken();
            PrintOut();

            if (lexan.token == tokenType.id) {
                symbolTable.e1(lexan.lexeme);
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(5);
            }
            if (lexan.token == tokenType.semicolon) {
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(9);
            }
        } else if (lexan.token == tokenType.writeToken) {
            lexan.getToken();

            idtix = symbolTable.searchVariable(lexan.lexeme);   //e4.1
            if(symbolTable.symTable[idtix].type == 'r'){
                op = TargetCode.operation.ldi;
            }else{
                op = TargetCode.operation.lod;
            }
            target.gen(target.cix, op, symbolTable.symTable[idtix].disp);
            target.gen(target.cix, TargetCode.operation.out, 0);

            PrintOut();
            if (lexan.token == tokenType.id) {
                symbolTable.e1(lexan.lexeme);
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(5);
            }
            if (lexan.token == tokenType.semicolon) {
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(9);
            }
        } else if (lexan.token == tokenType.leftBrace) {  //do nothing(e8)
            lexan.getToken();
            PrintOut();
            while (lexan.token == tokenType.leftBrace || lexan.token == tokenType.semicolon ||
                    lexan.token == tokenType.readToken || lexan.token == tokenType.writeToken ||
                    lexan.token == tokenType.callToken || lexan.token == tokenType.ifToken ||
                    lexan.token == tokenType.whileToken || lexan.token == tokenType.id) {
                Statement();
            }
            if (lexan.token == tokenType.rightBrace) {
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(43);
                //error for rightBrace and all of the above
            }
        } else if (lexan.token == tokenType.whileToken) {
            tempcix1 = target.cix;   //e7.1
            lexan.getToken();
            PrintOut();
            if (lexan.token == tokenType.leftParen) {
                lexan.getToken();
                PrintOut();
                Condition();
            } else {
                Globs.error(3);
            }
            if (lexan.token == tokenType.rightParen) {
                tempcix2 = target.cix;  //e7.2
                target.gen(target.cix, TargetCode.operation.joz, 0);
                lexan.getToken();
                PrintOut();
                Statement();
                target.gen(target.cix, TargetCode.operation.jmp, tempcix1);  //e7.3
                target.code[tempcix2].disp = target.cix;
            } else {
                Globs.error(4);
            }
        } else if (lexan.token == tokenType.ifToken) {
            lexan.getToken();
            PrintOut();
            if (lexan.token == tokenType.leftParen) {
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(3);
            }
            Condition();
            tempcix1 = target.cix;  //e6.1
            target.gen(target.cix, TargetCode.operation.joz, 0);
            if (lexan.token == tokenType.rightParen) {
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(4);
            }
            Statement();

            if (lexan.token == tokenType.elseToken) {
                tempcix2 = target.cix;  //e6.2
                target.gen(target.cix, TargetCode.operation.jmp, 0);
                target.code[tempcix1].disp = target.cix;
                lexan.getToken();
                PrintOut();
                Statement();
                target.code[tempcix2].disp = target.cix;  //e6.3
            } else {
                //blank
            }
            if (lexan.token == tokenType.endifToken) {
                target.code[tempcix1].disp = target.cix;  //e6.4
                lexan.getToken();
                PrintOut();
            } else {
                Globs.error(44);
            }
        } else {
            Globs.error(45);
        }
    }
}


class SymbolTable {
    int numActual;
    int calleetix;
    //some variables added for symbol table generation

    class SymbolEntry {
        String name;   // lexeme of an identifier
        char type;   // f=function v=variable r=reference u=value
        int disp;   // disp of variable or param, addr of function
        int pcnt;   // parameter count of a function
        char[] reva = new char[20];   // r=reference u=value for each param
    }

    SymbolEntry[] symTable;

    int tix = 0;  //index of next available entry in symTable
    int ftix = -1;   //index of name of current function
    int ldisp;  //displacement for next local in a function

    PrintWriter table; // output file for listing symTable entries for each function

    SymbolTable() throws FileNotFoundException {
        table = new PrintWriter("table.txt");
        symTable = new SymbolEntry[50];
        for (int i = 0; i < 50; i++) //create an SymbolEntry object for each entry
            symTable[i] = new SymbolEntry();
    }

    //***some methods for tableGen***
    //define a method printTable()
    //to print the symbol table for one function
    //the entries from ftix to tix-1 are to be printed
    void printTable() {
        //print what function it is.
        table.println("***symbol table for function " + symTable[ftix].name);
        table.println();
        table.println("indx*" + "\t\t" + "name*type*disp*pcnt*reva");
        table.println();
        for (int i = ftix; i < tix; i++) {
            table.println(i+"*" +"\t\t\t" + symTable[i].name + "*\t\t" + symTable[i].type+ "*\t" +
                    symTable[i].disp + "*\t"+ symTable[i].pcnt + "*\t" + String.valueOf(symTable[i].reva));
        }
        table.flush();
        table.println();
    }

    //define a method searchFunction
    //to search symTable from ftix to 0 for a function name
    int searchFunction(String functionName) {
        int sub1 = -1;
        for (int i = 0; i < ftix + 1; i++) {
            if (symTable[i].name.equals(functionName)) {
                return i;
            }
        }
        return sub1;
    }

    //define a method searchVariable
    //to search symTable from tix-1 to ftix+1 for a variable name
    int searchVariable(String variableName) {
        int sub2 = -1;
        for (int i = ftix + 1; i < tix; i++) {
            if (symTable[i].name.equals(variableName)) {
                return i;
            }
        }
        return sub2;
    }

    void a1(){

        symTable[tix].name = "main";
        symTable[tix].type = 'f';
        symTable[tix].disp = 0;
        symTable[tix].pcnt = 0;
        ftix = tix;
        tix++;
    }

    void b1(String lexemeName){
        if(searchFunction(lexemeName) != -1){
            System.out.print("Duplicate function has been declared.");
            System.exit(0);
        }else{
            symTable[tix].name = lexemeName;
            symTable[tix].type = 'f';
            symTable[tix].disp = 0;
            symTable[tix].pcnt = 0;
            ftix = tix;
            tix++;
        }
    }

    void b2(String lexemeName){

        symTable[ftix].pcnt++;

        if(lexemeName.toLowerCase().equals("val")){
            symTable[ftix].reva[symTable[ftix].pcnt-1] = 'u';
            symTable[tix].type = 'u';

        }
        else{
            symTable[ftix].reva[symTable[ftix].pcnt-1] = 'r';
            symTable[tix].type = 'r';
        }
    }

    void b3(String lexemeName){
        symTable[tix].name = lexemeName;
        tix++;
    }

    void b5(String lexemeName){
        if(searchVariable(lexemeName) != -1){
            System.out.print("Duplicate variable or parameter name.");
            System.exit(0);
        }else{
            symTable[tix].name = lexemeName;
            tix++;
        }
    }

    void b6(){
        int sym = -symTable[ftix].pcnt;
        for(int i = ftix + 1; i <= tix -1; i++){
            symTable[i].disp = sym;
            sym++;
        }
    }

    void c1(){
        ldisp = 2;
    }

    void c2(){
        printTable();
        tix = ftix + 1;
    }

    void d1(String lexemeName){
        if(searchVariable(lexemeName) != -1){
            System.out.print("Duplicate variable or parameter name.");
            System.exit(0);
        }else{
            symTable[tix].name = lexemeName;
            symTable[tix].type = 'v';
            symTable[tix].disp = ldisp;
            tix++;
            ldisp++;
        }
    }

    void d2(String lexemeName){
        if(searchVariable(lexemeName) != -1){
            System.out.print("Duplicate variable or parameter name.");
            System.exit(0);
        }else{
            symTable[tix].name = lexemeName;
            symTable[tix].type = 'v';
            symTable[tix].disp = ldisp;
            tix++;
            ldisp++;
        }
    }

    void e1(String lexemeName){
        if(searchVariable(lexemeName) == -1){
            System.out.print("Undeclared variable.");
            System.exit(0);
        }
    }

    void e2(String lexemeName){
        calleetix = searchFunction(lexemeName);
        if(calleetix == -1){
            System.out.print("Undeclared variable.");
            System.exit(0);
        }
    }

    void e3(String lexemeName) {
        if (searchVariable(lexemeName) == -1) {
            System.out.print("Undeclared variable.");
            System.exit(0);
        }
        numActual++;
    }

    void e4(){
        if(numActual != symTable[calleetix].pcnt){
            System.out.print("Incorrect number of parameters passed.");
            System.exit(0);
        }
    }
}  // end class Parser

//name the class that contains the main method codeGen

//*** some data structures and methods for code generation***
//** create a TargetCode object in the constructor of class Parser
class TargetCode
{
    // enum operation gives a mnemonic name to each op code
    enum operation {  neg, // negate stack[top]
        add, //add top two elements
        sub, //subtract stack[top] from stack[top-1]
        mul, //multiply top two elements
        dvd, //divide stack[top] into stack[top-1]
        equ, //are top two elements equal ?
        neq, //are top two elements unequal ?
        les, //is stack[top-1] < stack[top] ?
        leq, //is stack[top-1] <= stack[top] ?
        gtr, //is stack[top-1] > stack[top] ?
        geq, //is stack[top-1] >= stack[top] ?
        rtn, //return to caller
        loc, //push a constant            *loc const*
        isp, //increase sp by a constant  *isp const*
        jmp, //unconditional jump         *jmp addr *
        joz, //jump if stack[top] is 0    *joz addr *
        lod, //push a variable            *lod disp *
        sto, //pop into a variable        *sto disp *
        ldi, //push indirect              *ldi disp *
        sti, //popi indirect              *sti disp *
        lda, //push address of a variable *lda disp *
        inp, //read into top of stack     *inp*
        out, //output top of stack        *out*
        cal  //call a function            *cal addr *
    }; //end of definition of the type operation

    //struct instruction is the format of machine instructions
    class Instruction
    { operation oper;  //mnemonic opcode
        int disp;        //displacement, constant, or address
    };

    //opTable can be used to convert opcode to/from a string form
    String[] opcodeTable=
            {
                    "neg", "add", "sub", "mul", "dvd",
                    "equ", "neq", "les", "leq", "gtr",
                    "geq", "rtn", "loc", "isp", "jmp",
                    "joz", "lod", "sto", "ldi", "sti",
                    "lda", "inp", "out", "cal"
            };

    Instruction[] code;     //memory for object code
    int cix=0;              //index of next available element in the array code
    PrintWriter target;    //output file for object code

    //***some methods for code generation***
    public TargetCode()
    {
        code=new Instruction[300];
        for (int i=0; i<300; i++) //create a SymbolEntry object for each entry
            code[i]= new Instruction();
    }

    int gen(int i, operation op, int d) {
        code[i].oper = op;
        code[i].disp = d;
        cix++;
        return i;
    }
    //place the instruction *op d* in code[i] and then i++

    void writeTarget() throws FileNotFoundException{
        PrintWriter target = new PrintWriter("target.txt");
        int i = 0;
        while (code[i].oper != null){
            target.print(code[i].oper + " ");
            target.print(code[i].disp);
            target.println();
            i++;
        }
        target.flush();
    }
//write the object code from array code to target file
//called AFTER code generation completes

//***end some data structures and methods for code generation***

} //end class TargetCode
