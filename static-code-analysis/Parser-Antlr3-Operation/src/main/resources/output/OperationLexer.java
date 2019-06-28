// $ANTLR 3.4 /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g 2019-06-07 15:29:43
 package org.se.lab; 

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class OperationLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__19=19;
    public static final int COMMA=4;
    public static final int COMMENT=5;
    public static final int IDENTIFIER=6;
    public static final int LETTER=7;
    public static final int LINE_COMMENT=8;
    public static final int LPAREN=9;
    public static final int RPAREN=10;
    public static final int SEMI=11;
    public static final int WS=12;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public OperationLexer() {} 
    public OperationLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public OperationLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g"; }

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:4:7: ( 'char' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:4:9: 'char'
            {
            match("char"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:5:7: ( 'double' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:5:9: 'double'
            {
            match("double"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:6:7: ( 'float' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:6:9: 'float'
            {
            match("float"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:7:7: ( 'int' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:7:9: 'int'
            {
            match("int"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:8:7: ( 'long' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:8:9: 'long'
            {
            match("long"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:9:7: ( 'short' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:9:9: 'short'
            {
            match("short"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:10:7: ( 'void' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:10:9: 'void'
            {
            match("void"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:68:7: ( ',' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:68:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:69:6: ( ';' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:69:8: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SEMI"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:70:8: ( '(' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:70:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:71:8: ( ')' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:71:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:75:2: ( 'A' .. 'Z' | 'a' .. 'z' | '_' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "IDENTIFIER"
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:81:2: ( LETTER ( LETTER | '0' .. '9' )* )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:81:4: LETTER ( LETTER | '0' .. '9' )*
            {
            mLETTER(); 


            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:81:11: ( LETTER | '0' .. '9' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '0' && LA1_0 <= '9')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IDENTIFIER"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:85:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:85:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 



            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:85:14: ( options {greedy=false; } : . )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='*') ) {
                    int LA2_1 = input.LA(2);

                    if ( (LA2_1=='/') ) {
                        alt2=2;
                    }
                    else if ( ((LA2_1 >= '\u0000' && LA2_1 <= '.')||(LA2_1 >= '0' && LA2_1 <= '\uFFFF')) ) {
                        alt2=1;
                    }


                }
                else if ( ((LA2_0 >= '\u0000' && LA2_0 <= ')')||(LA2_0 >= '+' && LA2_0 <= '\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:85:42: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            match("*/"); 



            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:89:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:89:7: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 



            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:89:12: (~ ( '\\n' | '\\r' ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '\u0000' && LA3_0 <= '\t')||(LA3_0 >= '\u000B' && LA3_0 <= '\f')||(LA3_0 >= '\u000E' && LA3_0 <= '\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:89:26: ( '\\r' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\r') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:89:26: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }


            match('\n'); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:92:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:92:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||(input.LA(1) >= '\f' && input.LA(1) <= '\r')||input.LA(1)==' ' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:8: ( T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | COMMA | SEMI | LPAREN | RPAREN | IDENTIFIER | COMMENT | LINE_COMMENT | WS )
        int alt5=15;
        switch ( input.LA(1) ) {
        case 'c':
            {
            int LA5_1 = input.LA(2);

            if ( (LA5_1=='h') ) {
                int LA5_15 = input.LA(3);

                if ( (LA5_15=='a') ) {
                    int LA5_24 = input.LA(4);

                    if ( (LA5_24=='r') ) {
                        int LA5_31 = input.LA(5);

                        if ( ((LA5_31 >= '0' && LA5_31 <= '9')||(LA5_31 >= 'A' && LA5_31 <= 'Z')||LA5_31=='_'||(LA5_31 >= 'a' && LA5_31 <= 'z')) ) {
                            alt5=12;
                        }
                        else {
                            alt5=1;
                        }
                    }
                    else {
                        alt5=12;
                    }
                }
                else {
                    alt5=12;
                }
            }
            else {
                alt5=12;
            }
            }
            break;
        case 'd':
            {
            int LA5_2 = input.LA(2);

            if ( (LA5_2=='o') ) {
                int LA5_16 = input.LA(3);

                if ( (LA5_16=='u') ) {
                    int LA5_25 = input.LA(4);

                    if ( (LA5_25=='b') ) {
                        int LA5_32 = input.LA(5);

                        if ( (LA5_32=='l') ) {
                            int LA5_39 = input.LA(6);

                            if ( (LA5_39=='e') ) {
                                int LA5_44 = input.LA(7);

                                if ( ((LA5_44 >= '0' && LA5_44 <= '9')||(LA5_44 >= 'A' && LA5_44 <= 'Z')||LA5_44=='_'||(LA5_44 >= 'a' && LA5_44 <= 'z')) ) {
                                    alt5=12;
                                }
                                else {
                                    alt5=2;
                                }
                            }
                            else {
                                alt5=12;
                            }
                        }
                        else {
                            alt5=12;
                        }
                    }
                    else {
                        alt5=12;
                    }
                }
                else {
                    alt5=12;
                }
            }
            else {
                alt5=12;
            }
            }
            break;
        case 'f':
            {
            int LA5_3 = input.LA(2);

            if ( (LA5_3=='l') ) {
                int LA5_17 = input.LA(3);

                if ( (LA5_17=='o') ) {
                    int LA5_26 = input.LA(4);

                    if ( (LA5_26=='a') ) {
                        int LA5_33 = input.LA(5);

                        if ( (LA5_33=='t') ) {
                            int LA5_40 = input.LA(6);

                            if ( ((LA5_40 >= '0' && LA5_40 <= '9')||(LA5_40 >= 'A' && LA5_40 <= 'Z')||LA5_40=='_'||(LA5_40 >= 'a' && LA5_40 <= 'z')) ) {
                                alt5=12;
                            }
                            else {
                                alt5=3;
                            }
                        }
                        else {
                            alt5=12;
                        }
                    }
                    else {
                        alt5=12;
                    }
                }
                else {
                    alt5=12;
                }
            }
            else {
                alt5=12;
            }
            }
            break;
        case 'i':
            {
            int LA5_4 = input.LA(2);

            if ( (LA5_4=='n') ) {
                int LA5_18 = input.LA(3);

                if ( (LA5_18=='t') ) {
                    int LA5_27 = input.LA(4);

                    if ( ((LA5_27 >= '0' && LA5_27 <= '9')||(LA5_27 >= 'A' && LA5_27 <= 'Z')||LA5_27=='_'||(LA5_27 >= 'a' && LA5_27 <= 'z')) ) {
                        alt5=12;
                    }
                    else {
                        alt5=4;
                    }
                }
                else {
                    alt5=12;
                }
            }
            else {
                alt5=12;
            }
            }
            break;
        case 'l':
            {
            int LA5_5 = input.LA(2);

            if ( (LA5_5=='o') ) {
                int LA5_19 = input.LA(3);

                if ( (LA5_19=='n') ) {
                    int LA5_28 = input.LA(4);

                    if ( (LA5_28=='g') ) {
                        int LA5_35 = input.LA(5);

                        if ( ((LA5_35 >= '0' && LA5_35 <= '9')||(LA5_35 >= 'A' && LA5_35 <= 'Z')||LA5_35=='_'||(LA5_35 >= 'a' && LA5_35 <= 'z')) ) {
                            alt5=12;
                        }
                        else {
                            alt5=5;
                        }
                    }
                    else {
                        alt5=12;
                    }
                }
                else {
                    alt5=12;
                }
            }
            else {
                alt5=12;
            }
            }
            break;
        case 's':
            {
            int LA5_6 = input.LA(2);

            if ( (LA5_6=='h') ) {
                int LA5_20 = input.LA(3);

                if ( (LA5_20=='o') ) {
                    int LA5_29 = input.LA(4);

                    if ( (LA5_29=='r') ) {
                        int LA5_36 = input.LA(5);

                        if ( (LA5_36=='t') ) {
                            int LA5_42 = input.LA(6);

                            if ( ((LA5_42 >= '0' && LA5_42 <= '9')||(LA5_42 >= 'A' && LA5_42 <= 'Z')||LA5_42=='_'||(LA5_42 >= 'a' && LA5_42 <= 'z')) ) {
                                alt5=12;
                            }
                            else {
                                alt5=6;
                            }
                        }
                        else {
                            alt5=12;
                        }
                    }
                    else {
                        alt5=12;
                    }
                }
                else {
                    alt5=12;
                }
            }
            else {
                alt5=12;
            }
            }
            break;
        case 'v':
            {
            int LA5_7 = input.LA(2);

            if ( (LA5_7=='o') ) {
                int LA5_21 = input.LA(3);

                if ( (LA5_21=='i') ) {
                    int LA5_30 = input.LA(4);

                    if ( (LA5_30=='d') ) {
                        int LA5_37 = input.LA(5);

                        if ( ((LA5_37 >= '0' && LA5_37 <= '9')||(LA5_37 >= 'A' && LA5_37 <= 'Z')||LA5_37=='_'||(LA5_37 >= 'a' && LA5_37 <= 'z')) ) {
                            alt5=12;
                        }
                        else {
                            alt5=7;
                        }
                    }
                    else {
                        alt5=12;
                    }
                }
                else {
                    alt5=12;
                }
            }
            else {
                alt5=12;
            }
            }
            break;
        case ',':
            {
            alt5=8;
            }
            break;
        case ';':
            {
            alt5=9;
            }
            break;
        case '(':
            {
            alt5=10;
            }
            break;
        case ')':
            {
            alt5=11;
            }
            break;
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '_':
        case 'a':
        case 'b':
        case 'e':
        case 'g':
        case 'h':
        case 'j':
        case 'k':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 't':
        case 'u':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt5=12;
            }
            break;
        case '/':
            {
            int LA5_13 = input.LA(2);

            if ( (LA5_13=='*') ) {
                alt5=13;
            }
            else if ( (LA5_13=='/') ) {
                alt5=14;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 13, input);

                throw nvae;

            }
            }
            break;
        case '\t':
        case '\n':
        case '\f':
        case '\r':
        case ' ':
            {
            alt5=15;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 5, 0, input);

            throw nvae;

        }

        switch (alt5) {
            case 1 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:10: T__13
                {
                mT__13(); 


                }
                break;
            case 2 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:16: T__14
                {
                mT__14(); 


                }
                break;
            case 3 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:22: T__15
                {
                mT__15(); 


                }
                break;
            case 4 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:28: T__16
                {
                mT__16(); 


                }
                break;
            case 5 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:34: T__17
                {
                mT__17(); 


                }
                break;
            case 6 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:40: T__18
                {
                mT__18(); 


                }
                break;
            case 7 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:46: T__19
                {
                mT__19(); 


                }
                break;
            case 8 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:52: COMMA
                {
                mCOMMA(); 


                }
                break;
            case 9 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:58: SEMI
                {
                mSEMI(); 


                }
                break;
            case 10 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:63: LPAREN
                {
                mLPAREN(); 


                }
                break;
            case 11 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:70: RPAREN
                {
                mRPAREN(); 


                }
                break;
            case 12 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:77: IDENTIFIER
                {
                mIDENTIFIER(); 


                }
                break;
            case 13 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:88: COMMENT
                {
                mCOMMENT(); 


                }
                break;
            case 14 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:96: LINE_COMMENT
                {
                mLINE_COMMENT(); 


                }
                break;
            case 15 :
                // /home/student/github/teiniker-lectures-securedesign/static-code-analysis/Parser-Antlr3-Operation/src/main/resources/Operation.g:1:109: WS
                {
                mWS(); 


                }
                break;

        }

    }


 

}