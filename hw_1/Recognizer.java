/*
    Edward Prokopik
    CSC 135

    Test Cases:
        B$
        BX~0;$
        BI(123>1432)@&$


*/

import java.util.Scanner; 
public class Recognizer {

    static char token;
    static String grammar_string;
    static char[] grammar_chars;
    static int count;
    public static final String FIRST_OF_FUNCTION = "B";
    public static final String FIRST_OF_STATEMT  = "XYZIWROC";
    public static final String FIRST_OF_ASSMT    = "XYZ";
    public static final String FIRST_OF_IFSTMT   = "I";
    public static final String FIRST_OF_LOOP     = "W";
    public static final String FIRST_OF_READ     = "R";
    public static final String FIRST_OF_OUTPUT   = "O";
    public static final String FIRST_OF_FUNCALL  = "C";
    public static final String FIRST_OF_COMPRSN  = "(";
    public static final String FIRST_OF_EXPRSN   = "(XYZ01234567";
    public static final String FIRST_OF_FACTOR   = "(XYZ01234567";
    public static final String FIRST_OF_OPRND    = "(XYZ01234567";
    public static final String FIRST_OF_OPRATR   = "<=>!";
    public static final String FIRST_OF_IDENT    = "XYZ";
    public static final String FIRST_OF_CHAR     = "XYZ01234567";
    public static final String FIRST_OF_INTEGER  = "01234567";
    public static final String FIRST_OF_LETTER   = "XYZ";
    public static final String FIRST_OF_DIGIT    = "0123467";

    public static void main(String[] args) {
        System.out.println("Enter a string followed by a $: ");
        Scanner scanner = new Scanner(System.in);
        String grammar_string = scanner.nextLine();
        grammar_chars = grammar_string.toCharArray();
        count = 0;
        token = grammar_chars[0];
        function();
        System.out.println("Valid String");


    }

    public static boolean match(char t) {
        if (t == token) {
            // token matched and advance the token
            if (token != '$') {
                advanceToken();
            }
            return true;
        } else {
            // token not matched
            error();
            return false;
        }
    }

    public static void error() {
        System.out.println("Invalid String");
        // System.exit(1);
    }

    public static boolean tokenIn(String chars) {
        if (chars.indexOf(token) >= 0) {
            return true;
        } else {
            return false;
        }
    }
    public static void advanceToken() {
        if (count < grammar_chars.length - 1) {
            count++;
            token = grammar_chars[count];
        }
    }

    public static void function() {
        match('B');
        while (tokenIn(FIRST_OF_STATEMT)) {
            System.out.println("hey");
            statemt();
        }

        if (token == '$') {
            match('$');
        } else if (token != 'E') {
            error();
        }
    }

    public static void statemt() {
        if (tokenIn(FIRST_OF_ASSMT)) {
            assmnt();
        } else if (tokenIn(FIRST_OF_IFSTMT)) {
            ifstmt();
        } else if (tokenIn(FIRST_OF_LOOP)) {
            loop();
        } else if (tokenIn(FIRST_OF_READ)) {
            read();
        } else if (tokenIn(FIRST_OF_OUTPUT)) {
            output();
        } else if (tokenIn(FIRST_OF_FUNCALL)) {
            funcall();
        }
    }

    public static void assmnt() {
        ident();
        match('~');
        exprsn();
        match(';');
        
    }

    public static void ifstmt() {
        match('I');
        comprsn();
        match('@');
        while (tokenIn(FIRST_OF_STATEMT)) {
            statemt();
        }
        if (token == '%') {
            match('%');
            while (tokenIn(FIRST_OF_STATEMT)) {
                statemt();
            }
        }
        match('&');

    }

    public static void loop() {
        match('W');
        comprsn();
        match('L');
        while(tokenIn(FIRST_OF_STATEMT)) {
            statemt();
        }
        match('T');

    }

    public static void read() {
        match('R');
        ident();
        while(token == ',') {
            match(',');
            ident();
        }
        match(';');
        

    }

    public static void output() {
        match('O');
        ident();
        while(token == ',') {
            match(',');
            ident();
        }
        match(';');
    }

    public static void funcall() {
        match('C');
        function();
        match('E');
    }

    public static void comprsn() {
        match('(');
        oprnd();
        opratr();
        oprnd();
        match(')');
    }

    public static void exprsn() {
        factor();
        while (token == '+') {
            match('+');
            factor();
        }
    }

    public static void factor() {
        oprnd();
        while (token == '*') {
            match('*');
            oprnd();
        }
    }

    public static void oprnd() {
        if (tokenIn(FIRST_OF_INTEGER)) {
            integer();
        } else if (tokenIn(FIRST_OF_IDENT)) {
            ident();
        } else {
            match('(');
            exprsn();
            match(')');
        }
    }

    public static void opratr() {
        switch(token) {
            case '<':
                match('<');
                break;
            case '=':
                match('=');
                break;
            case '>':
                match('>');
                break;
            default:
                match('!');
        }
    }

    public static void ident() {
        letter();
        while (tokenIn(FIRST_OF_CHAR)) {
            charr();
        }
    }

    public static void charr() {
        if (tokenIn(FIRST_OF_LETTER)) {
            letter();
        } else  {
            digit();
        }
    }

    public static void integer() {
        digit();
        while (tokenIn(FIRST_OF_DIGIT)) {
            digit();
        }
    }

    public static void letter() {
        switch(token) {
            case 'X':
                match('X');
                break;
            case 'Y':
                match('Y');
                break;
            default:
                match('Z');
                break;

        }
    }

    public static void digit() {
        switch(token) {
            case '0':
                match('0');
                break;
            case '1':
                match('1');
                break;
            case '2':
                match('2');
                break;
            case '3':
                match('3');
                break;
            case '4':
                match('4');
                break;
            case '5':
                match('5');
                break;
            case '6':
                match('6');
                break;
            default:
                match('7');
                break;

        }
    }


}