package org.apache.batik.css.parser;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.LexicalUnit;

import java.io.IOException;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 19.08.2009
 * @Time: 11:07:53
 */
public class CustomParser extends Parser{
    public void parseStyleDeclaration(InputSource inputSource) throws CSSException, IOException {
        super.parseStyleDeclaration(inputSource);
    }

    protected void parseStyleDeclarationInternal() throws CSSException, IOException {
        super.parseStyleDeclarationInternal();
    }

    public void parseStyleDeclaration(String s) throws CSSException, IOException {
        super.parseStyleDeclaration(s);
    }

    protected void parseStyleDeclaration(boolean inSheet) throws CSSException {
        do
            switch(current) {
                case 0: // '\0'
                    if(inSheet)
                        throw createCSSParseException("eof");
                    else
                        return;

                case 2: // '\002'
                    if(!inSheet) {
                        throw createCSSParseException("eof.expected");
                    } else {
                        nextIgnoreSpaces();
                        return;
                    }

                case 8: // '\b'
                    nextIgnoreSpaces();
                    break;

                default:
                    throw createCSSParseException("identifier");

                case 20: // '\024'
                    String name = scanner.getStringValue();
                    if(nextIgnoreSpaces() != 16)
                        throw createCSSParseException("colon");
                    nextIgnoreSpaces();
                    LexicalUnit exp = null;
                    try {
                        exp = parseExpression(false);
                    }
                    catch(CSSParseException e) {
                        reportError(e);
                    }
                    if(exp != null) {
                        boolean important = false;
                        if(current == 23) {
                            important = true;
                            nextIgnoreSpaces();
                        }
                        documentHandler.property(name, exp, important);
                    }
                    break;
            }
        while(true);
    }

    protected void parseRuleSet() {
        super.parseRuleSet();
    }

    protected int nextIgnoreSpaces() {
        try {
            do {
                scanner.clearBuffer();
                current = scanner.next();
                switch(current) {
                    case 18: // '\022'
                        documentHandler.comment(scanner.getStringValue());
                        break;

                    default:
                        return current;

                    case 17: // '\021'
                        break;
                }
            } while(true);
        }
        catch(ParseException e) {
            errorHandler.error(createCSSParseException(e.getMessage()));
        }
        return current;
    }

    protected LexicalUnit parseExpression(boolean param) {
        LexicalUnit result = parseTerm(null);
        LexicalUnit curr = result;
        do {
            boolean op;
            do {
                op = false;
                switch(current) {
                    case 6: // '\006'
                        op = true;
                        curr = CSSLexicalUnit.createSimple((short)0, curr);
                        nextIgnoreSpaces();
                        break;

                    case 3: // '='
                        op = true;
                        curr = CSSLexicalUnit.createString( (short)35,"=", curr);
                        nextIgnoreSpaces();
                        break;

                    case 10: // '\n'
                        op = true;
                        curr = CSSLexicalUnit.createSimple((short)4, curr);
                        nextIgnoreSpaces();
                        break;
                }
                if(!param)
                    break;
                if(current == 15)
                    if(op)
                        throw createCSSParseException("token", new Object[] {
                                new Integer(current)
                        });
                    else
                        return result;
                curr = parseTerm(curr);
            } while(true);
            switch(current) {
                case 0: // '\0'
                case 2: // '\002'
                case 8: // '\b'
                case 23: // '\027'
                    if(op)
                        throw createCSSParseException("token", new Object[] {
                                new Integer(current)
                        });
                    else
                        return result;
            }
            curr = parseTerm(curr);
        } while(true);
    }

    protected LexicalUnit parseTerm(LexicalUnit prev) {
        boolean plus = true;
        boolean sgn = false;
        switch(current) {
            case 5: // '\005'
                plus = false;
                // fall through

            case 4: // '\004'
                next();
                sgn = true;
                // fall through

            default:
                switch(current) {
                    case 24: // '\030'
                        String sval = scanner.getStringValue();
                        if(!plus)
                            sval = "-" + sval;
                        long lVal = Long.parseLong(sval);
                        if(lVal >= -2147483648L && lVal <= 2147483647L) {
                            int iVal = (int)lVal;
                            nextIgnoreSpaces();
                            return CSSLexicalUnit.createInteger(iVal, prev);
                        }
                        // fall through

                    case 54: // '6'
                        return CSSLexicalUnit.createFloat((short)14, number(plus), prev);

                    case 42: // '*'
                        return CSSLexicalUnit.createFloat((short)23, number(plus), prev);

                    case 45: // '-'
                        return CSSLexicalUnit.createFloat((short)21, number(plus), prev);

                    case 44: // ','
                        return CSSLexicalUnit.createFloat((short)22, number(plus), prev);

                    case 46: // '.'
                        return CSSLexicalUnit.createFloat((short)17, number(plus), prev);

                    case 37: // '%'
                        return CSSLexicalUnit.createFloat((short)19, number(plus), prev);

                    case 38: // '&'
                        return CSSLexicalUnit.createFloat((short)20, number(plus), prev);

                    case 39: // '\''
                        return CSSLexicalUnit.createFloat((short)18, number(plus), prev);

                    case 36: // '$'
                        return CSSLexicalUnit.createFloat((short)15, number(plus), prev);

                    case 35: // '#'
                        return CSSLexicalUnit.createFloat((short)16, number(plus), prev);

                    case 47: // '/'
                        return CSSLexicalUnit.createFloat((short)28, number(plus), prev);

                    case 48: // '0'
                        return CSSLexicalUnit.createFloat((short)30, number(plus), prev);

                    case 49: // '1'
                        return CSSLexicalUnit.createFloat((short)29, number(plus), prev);

                    case 43: // '+'
                        return CSSLexicalUnit.createFloat((short)32, number(plus), prev);

                    case 40: // '('
                        return CSSLexicalUnit.createFloat((short)31, number(plus), prev);

                    case 41: // ')'
                        return CSSLexicalUnit.createFloat((short)33, number(plus), prev);

                    case 50: // '2'
                        return CSSLexicalUnit.createFloat((short)34, number(plus), prev);

                    case 34: // '"'
                        return dimension(plus, prev);

                    case 52: // '4'
                        return parseFunction(plus, prev);
                }
                break;
        }
        if(sgn)
            throw createCSSParseException("token", new Object[] {
                    new Integer(current)
            });
        switch(current) {
            case 19: // '\023'
            {
                String val = scanner.getStringValue();
                nextIgnoreSpaces();
                return CSSLexicalUnit.createString((short)36, val, prev);
            }

            case 20: // '\024'
            {
                String val = scanner.getStringValue();
                nextIgnoreSpaces();
                if(val.equalsIgnoreCase("inherit"))
                    return CSSLexicalUnit.createSimple((short)12, prev);
                else
                    return CSSLexicalUnit.createString((short)35, val, prev);
            }

            case 51: // '3'
            {
                String val = scanner.getStringValue();
                nextIgnoreSpaces();
                return CSSLexicalUnit.createString((short)24, val, prev);
            }

            case 27: // '\033'
            {
                return hexcolor(prev);
            }
        }
        throw createCSSParseException("token", new Object[] {
                new Integer(current)
        });
    }

    protected LexicalUnit parseFunction(boolean positive, LexicalUnit prev) {
        String name = scanner.getStringValue();
        nextIgnoreSpaces();
        LexicalUnit params = parseExpression(true);
        if(current != 15)
            throw createCSSParseException("token", new Object[] {
                    new Integer(current)
            });
        nextIgnoreSpaces();
        label0:
        switch(name.charAt(0)) {
            default:
                break;

            case 82: // 'R'
            case 114: // 'r'
            {
                LexicalUnit lu;
                if(name.equalsIgnoreCase("rgb")) {
                    lu = params;
                    if(lu != null)
                        switch(lu.getLexicalUnitType()) {
                            case 13: // '\r'
                            case 23: // '\027'
                                lu = lu.getNextLexicalUnit();
                                if(lu != null)
                                    switch(lu.getLexicalUnitType()) {
                                        case 0: // '\0'
                                            lu = lu.getNextLexicalUnit();
                                            if(lu != null)
                                                switch(lu.getLexicalUnitType()) {
                                                    case 13: // '\r'
                                                    case 23: // '\027'
                                                        lu = lu.getNextLexicalUnit();
                                                        if(lu != null)
                                                            switch(lu.getLexicalUnitType()) {
                                                                case 0: // '\0'
                                                                    lu = lu.getNextLexicalUnit();
                                                                    if(lu != null)
                                                                        switch(lu.getLexicalUnitType()) {
                                                                            case 13: // '\r'
                                                                            case 23: // '\027'
                                                                                lu = lu.getNextLexicalUnit();
                                                                                if(lu == null)
                                                                                    return CSSLexicalUnit.createPredefinedFunction((short)27, params, prev);
                                                                                break;
                                                                        }
                                                                    break;
                                                            }
                                                        break;
                                                }
                                            break;
                                    }
                                break;
                        }
                    break;
                }
                if(!name.equalsIgnoreCase("rect"))
                    break;
                lu = params;
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    case 14: // '\016'
                    case 24: // '\030'
                    case 25: // '\031'
                    case 26: // '\032'
                    case 27: // '\033'
                    case 28: // '\034'
                    case 29: // '\035'
                    case 30: // '\036'
                    case 31: // '\037'
                    case 32: // ' '
                    case 33: // '!'
                    case 34: // '"'
                    default:
                        break label0;

                    case 13: // '\r'
                        if(lu.getIntegerValue() != 0)
                            break label0;
                        lu = lu.getNextLexicalUnit();
                        break;

                    case 35: // '#'
                        if(!lu.getStringValue().equalsIgnoreCase("auto"))
                            break label0;
                        lu = lu.getNextLexicalUnit();
                        break;

                    case 15: // '\017'
                    case 16: // '\020'
                    case 17: // '\021'
                    case 18: // '\022'
                    case 19: // '\023'
                    case 20: // '\024'
                    case 21: // '\025'
                    case 22: // '\026'
                    case 23: // '\027'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    default:
                        break label0;

                    case 0: // '\0'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    case 14: // '\016'
                    case 24: // '\030'
                    case 25: // '\031'
                    case 26: // '\032'
                    case 27: // '\033'
                    case 28: // '\034'
                    case 29: // '\035'
                    case 30: // '\036'
                    case 31: // '\037'
                    case 32: // ' '
                    case 33: // '!'
                    case 34: // '"'
                    default:
                        break label0;

                    case 13: // '\r'
                        if(lu.getIntegerValue() != 0)
                            break label0;
                        lu = lu.getNextLexicalUnit();
                        break;

                    case 35: // '#'
                        if(!lu.getStringValue().equalsIgnoreCase("auto"))
                            break label0;
                        lu = lu.getNextLexicalUnit();
                        break;

                    case 15: // '\017'
                    case 16: // '\020'
                    case 17: // '\021'
                    case 18: // '\022'
                    case 19: // '\023'
                    case 20: // '\024'
                    case 21: // '\025'
                    case 22: // '\026'
                    case 23: // '\027'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    default:
                        break label0;

                    case 0: // '\0'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    case 14: // '\016'
                    case 24: // '\030'
                    case 25: // '\031'
                    case 26: // '\032'
                    case 27: // '\033'
                    case 28: // '\034'
                    case 29: // '\035'
                    case 30: // '\036'
                    case 31: // '\037'
                    case 32: // ' '
                    case 33: // '!'
                    case 34: // '"'
                    default:
                        break label0;

                    case 13: // '\r'
                        if(lu.getIntegerValue() != 0)
                            break label0;
                        lu = lu.getNextLexicalUnit();
                        break;

                    case 35: // '#'
                        if(!lu.getStringValue().equalsIgnoreCase("auto"))
                            break label0;
                        lu = lu.getNextLexicalUnit();
                        break;

                    case 15: // '\017'
                    case 16: // '\020'
                    case 17: // '\021'
                    case 18: // '\022'
                    case 19: // '\023'
                    case 20: // '\024'
                    case 21: // '\025'
                    case 22: // '\026'
                    case 23: // '\027'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    default:
                        break label0;

                    case 0: // '\0'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    case 14: // '\016'
                    case 24: // '\030'
                    case 25: // '\031'
                    case 26: // '\032'
                    case 27: // '\033'
                    case 28: // '\034'
                    case 29: // '\035'
                    case 30: // '\036'
                    case 31: // '\037'
                    case 32: // ' '
                    case 33: // '!'
                    case 34: // '"'
                    default:
                        break label0;

                    case 13: // '\r'
                        if(lu.getIntegerValue() != 0)
                            break label0;
                        lu = lu.getNextLexicalUnit();
                        break;

                    case 35: // '#'
                        if(!lu.getStringValue().equalsIgnoreCase("auto"))
                            break label0;
                        lu = lu.getNextLexicalUnit();
                        break;

                    case 15: // '\017'
                    case 16: // '\020'
                    case 17: // '\021'
                    case 18: // '\022'
                    case 19: // '\023'
                    case 20: // '\024'
                    case 21: // '\025'
                    case 22: // '\026'
                    case 23: // '\027'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    return CSSLexicalUnit.createPredefinedFunction((short)38, params, prev);
                break;
            }

            case 67: // 'C'
            case 99: // 'c'
            {
                LexicalUnit lu;
                if(name.equalsIgnoreCase("counter")) {
                    lu = params;
                    if(lu == null)
                        break;
                    switch(lu.getLexicalUnitType()) {
                        default:
                            break label0;

                        case 35: // '#'
                            lu = lu.getNextLexicalUnit();
                            break;
                    }
                    if(lu == null)
                        break;
                    switch(lu.getLexicalUnitType()) {
                        default:
                            break label0;

                        case 0: // '\0'
                            lu = lu.getNextLexicalUnit();
                            break;
                    }
                    if(lu == null)
                        break;
                    switch(lu.getLexicalUnitType()) {
                        default:
                            break label0;

                        case 35: // '#'
                            lu = lu.getNextLexicalUnit();
                            break;
                    }
                    if(lu == null)
                        return CSSLexicalUnit.createPredefinedFunction((short)25, params, prev);
                    break;
                }
                if(!name.equalsIgnoreCase("counters"))
                    break;
                lu = params;
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    default:
                        break label0;

                    case 35: // '#'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    default:
                        break label0;

                    case 0: // '\0'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    default:
                        break label0;

                    case 36: // '$'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    default:
                        break label0;

                    case 0: // '\0'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    default:
                        break label0;

                    case 35: // '#'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    return CSSLexicalUnit.createPredefinedFunction((short)26, params, prev);
                break;
            }

            case 65: // 'A'
            case 97: // 'a'
            {
                if(!name.equalsIgnoreCase("attr"))
                    break;
                LexicalUnit lu = params;
                if(lu == null)
                    break;
                switch(lu.getLexicalUnitType()) {
                    default:
                        break label0;

                    case 35: // '#'
                        lu = lu.getNextLexicalUnit();
                        break;
                }
                if(lu == null)
                    return CSSLexicalUnit.createString((short)37, params.getStringValue(), prev);
                break;
            }
        }
        return CSSLexicalUnit.createFunction(name, params, prev);
    }
}
