package org.apache.batik.css.parser;

import org.apache.batik.util.io.NormalizingReader;
import org.apache.batik.util.io.StreamNormalizingReader;
import org.apache.batik.util.io.StringNormalizingReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;


public class Scanner {

    protected NormalizingReader reader;
    protected int current, position, type, start;
    protected char buffer[];
    protected int end, blankCharacters;

    public Scanner(Reader r) throws ParseException {
        buffer = new char[128];
        try {
            reader = new StreamNormalizingReader(r);
            current = nextChar();
        }
        catch(IOException e) {
            throw new ParseException(e);
        }
    }

    public Scanner(InputStream is, String enc) throws ParseException {
        buffer = new char[128];
        try {
            reader = new StreamNormalizingReader(is, enc);
            current = nextChar();
        }
        catch(IOException e) {
            throw new ParseException(e);
        }
    }

    public Scanner(String s) throws ParseException {
        buffer = new char[128];
        try {
            reader = new StringNormalizingReader(s);
            current = nextChar();
        }
        catch(IOException e) {
            throw new ParseException(e);
        }
    }

    public int getLine() {
        return reader.getLine();
    }

    public int getColumn() {
        return reader.getColumn();
    }

    public char[] getBuffer() {
        return buffer;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void clearBuffer() {
        if(position <= 0) {
            position = 0;
        } else {
            buffer[0] = buffer[position - 1];
            position = 1;
        }
    }

    public int getType() {
        return type;
    }

    public String getStringValue() {
        return new String(buffer, start, end - start);
    }

    public void scanAtRule() throws ParseException {
        label0:
        while(true)
            try {
                switch(current) {
                    default:
                        nextChar();
                        break;

                    case 123: // '{'
                        int brackets = 1;
                        label1:
                        do {
                            nextChar();
                            switch(current) {
                                default:
                                    break;

                                case -1:
                                    break label1;

                                case 125: // '}'
                                    if(--brackets <= 0)
                                        break label1;
                                    break;

                                case 123: // '{'
                                    brackets++;
                                    break;
                            }
                        } while(true);
                        // fall through

                    case -1:
                    case 59: // ';'
                        end = position;
                        break label0;
                }
            }
            catch(IOException e) {
                throw new ParseException(e);
            }
    }

    public int next() throws ParseException {
        blankCharacters = 0;
        start = position - 1;
        nextToken();
        end = position - endGap();
        return type;
    }

    protected int endGap() {
        int result = current != -1 ? 1 : 0;
        switch(type) {
            case 19: // '\023'
            case 42: // '*'
            case 43: // '+'
            case 52: // '4'
                result++;
                break;

            case 18: // '\022'
            case 35: // '#'
            case 36: // '$'
            case 37: // '%'
            case 38: // '&'
            case 39: // '\''
            case 40: // '('
            case 41: // ')'
            case 44: // ','
            case 45: // '-'
            case 46: // '.'
                result += 2;
                break;

            case 47: // '/'
            case 48: // '0'
            case 50: // '2'
                result += 3;
                break;

            case 49: // '1'
                result += 4;
                break;
        }
        return result + blankCharacters;
    }

    protected void nextToken() throws ParseException {
        try {
            switch(current) {
                case -1:
                    type = 0;
                    return;

                case 123: // '{'
                    nextChar();
                    type = 1;
                    return;

                case 125: // '}'
                    nextChar();
                    type = 2;
                    return;

                case 61: // '='
                    nextChar();
                    type = 3;
                    return;

                case 43: // '+'
                    nextChar();
                    type = 4;
                    return;

                case 44: // ','
                    nextChar();
                    type = 6;
                    return;

                case 59: // ';'
                    nextChar();
                    type = 8;
                    return;

                case 62: // '>'
                    nextChar();
                    type = 9;
                    return;

                case 91: // '['
                    nextChar();
                    type = 11;
                    return;

                case 93: // ']'
                    nextChar();
                    type = 12;
                    return;

                case 42: // '*'
                    nextChar();
                    type = 13;
                    return;

                case 40: // '('
                    nextChar();
                    type = 14;
                    return;

                case 41: // ')'
                    nextChar();
                    type = 15;
                    return;

                case 58: // ':'
                    nextChar();
                    type = 16;
                    return;

                case 9: // '\t'
                case 10: // '\n'
                case 12: // '\f'
                case 13: // '\r'
                case 32: // ' '
                    do
                        nextChar();
                    while(ScannerUtilities.isCSSSpace((char)current));
                    type = 17;
                    return;

                case 47: // '/'
                    nextChar();
                    if(current != 42) {
                        type = 10;
                        return;
                    }
                    nextChar();
                    start = position - 1;
                    do {
                        while(current != -1 && current != 42)
                            nextChar();
                        do
                            nextChar();
                        while(current != -1 && current == 42);
                    } while(current != -1 && current != 47);
                    if(current == -1) {
                        throw new ParseException("eof", reader.getLine(), reader.getColumn());
                    } else {
                        nextChar();
                        type = 18;
                        return;
                    }

                case 39: // '\''
                    type = string1();
                    return;

                case 34: // '"'
                    type = string2();
                    return;

                case 60: // '<'
                    nextChar();
                    if(current != 33)
                        throw new ParseException("character", reader.getLine(), reader.getColumn());
                    nextChar();
                    if(current == 45) {
                        nextChar();
                        if(current == 45) {
                            nextChar();
                            type = 21;
                            return;
                        }
                    }
                    throw new ParseException("character", reader.getLine(), reader.getColumn());

                case 45: // '-'
                    nextChar();
                    if ( (current == 109)||(current ==119)) break; //todo:sc add c0nd && current != 109  119
                    if(current != 45) {
                        type = 5;
                        return;
                    }
                    nextChar();
                    if(current == 62) {
                        nextChar();
                        type = 22;
                        return;
                    } else {
                        throw new ParseException("character", reader.getLine(), reader.getColumn());
                    }

                case 124: // '|'
                    nextChar();
                    if(current == 61) {
                        nextChar();
                        type = 25;
                        return;
                    } else {
                        throw new ParseException("character", reader.getLine(), reader.getColumn());
                    }

                case 126: // '~'
                    nextChar();
                    if(current == 61) {
                        nextChar();
                        type = 26;
                        return;
                    } else {
                        throw new ParseException("character", reader.getLine(), reader.getColumn());
                    }

                case 35: // '#'
                    nextChar();
                    if(ScannerUtilities.isCSSNameCharacter((char)current)) {
                        start = position - 1;
                        do {
                            nextChar();
                            while(current == 92)  {
                                nextChar();
                                escape();
                            }
                        } while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                        type = 27;
                        return;
                    } else {
                        throw new ParseException("character", reader.getLine(), reader.getColumn());
                    }

                case 64: // '@'
                    nextChar();
                    switch(current) {
                        case 67: // 'C'
                        case 99: // 'c'
                            start = position - 1;
                            if(isEqualIgnoreCase(nextChar(), 'h') && isEqualIgnoreCase(nextChar(), 'a') && isEqualIgnoreCase(nextChar(), 'r') && isEqualIgnoreCase(nextChar(), 's') && isEqualIgnoreCase(nextChar(), 'e') && isEqualIgnoreCase(nextChar(), 't')) {
                                nextChar();
                                type = 30;
                                return;
                            }
                            break;

                        case 70: // 'F'
                        case 102: // 'f'
                            start = position - 1;
                            if(isEqualIgnoreCase(nextChar(), 'o') && isEqualIgnoreCase(nextChar(), 'n') && isEqualIgnoreCase(nextChar(), 't') && isEqualIgnoreCase(nextChar(), '-') && isEqualIgnoreCase(nextChar(), 'f') && isEqualIgnoreCase(nextChar(), 'a') && isEqualIgnoreCase(nextChar(), 'c') && isEqualIgnoreCase(nextChar(), 'e')) {
                                nextChar();
                                type = 31;
                                return;
                            }
                            break;

                        case 73: // 'I'
                        case 105: // 'i'
                            start = position - 1;
                            if(isEqualIgnoreCase(nextChar(), 'm') && isEqualIgnoreCase(nextChar(), 'p') && isEqualIgnoreCase(nextChar(), 'o') && isEqualIgnoreCase(nextChar(), 'r') && isEqualIgnoreCase(nextChar(), 't')) {
                                nextChar();
                                type = 28;
                                return;
                            }
                            break;

                        case 77: // 'M'
                        case 109: // 'm'
                            start = position - 1;
                            if(isEqualIgnoreCase(nextChar(), 'e') && isEqualIgnoreCase(nextChar(), 'd') && isEqualIgnoreCase(nextChar(), 'i') && isEqualIgnoreCase(nextChar(), 'a')) {
                                nextChar();
                                type = 32;
                                return;
                            }
                            break;

                        case 80: // 'P'
                        case 112: // 'p'
                            start = position - 1;
                            if(isEqualIgnoreCase(nextChar(), 'a') && isEqualIgnoreCase(nextChar(), 'g') && isEqualIgnoreCase(nextChar(), 'e')) {
                                nextChar();
                                type = 33;
                                return;
                            }
                            break;

                        default:
                            if(!ScannerUtilities.isCSSIdentifierStartCharacter((char)current))
                                throw new ParseException("identifier.character", reader.getLine(), reader.getColumn());
                            start = position - 1;
                            break;
                    }
                    do {
                        nextChar();
                        while(current == 92)  {
                            nextChar();
                            escape();
                        }
                    } while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                    type = 29;
                    return;

                case 33: // '!'
                    do
                        nextChar();
                    while(current != -1 && ScannerUtilities.isCSSSpace((char)current));
                    if(isEqualIgnoreCase(current, 'i') && isEqualIgnoreCase(nextChar(), 'm') && isEqualIgnoreCase(nextChar(), 'p') && isEqualIgnoreCase(nextChar(), 'o') && isEqualIgnoreCase(nextChar(), 'r') && isEqualIgnoreCase(nextChar(), 't') && isEqualIgnoreCase(nextChar(), 'a') && isEqualIgnoreCase(nextChar(), 'n') && isEqualIgnoreCase(nextChar(), 't')) {
                        nextChar();
                        type = 23;
                        return;
                    }
                    if(current == -1)
                        throw new ParseException("eof", reader.getLine(), reader.getColumn());
                    else
                        throw new ParseException("character", reader.getLine(), reader.getColumn());

                case 48: // '0'
                case 49: // '1'
                case 50: // '2'
                case 51: // '3'
                case 52: // '4'
                case 53: // '5'
                case 54: // '6'
                case 55: // '7'
                case 56: // '8'
                case 57: // '9'
                    type = number();
                    return;

                case 46: // '.'
                    switch(nextChar()) {
                        case 48: // '0'
                        case 49: // '1'
                        case 50: // '2'
                        case 51: // '3'
                        case 52: // '4'
                        case 53: // '5'
                        case 54: // '6'
                        case 55: // '7'
                        case 56: // '8'
                        case 57: // '9'
                            type = dotNumber();
                            return;
                    }
                    type = 7;
                    return;

                case 85: // 'U'
                case 117: // 'u'
                    nextChar();
                    switch(current) {
                        case 43: // '+'
                            boolean range = false;
                            for(int i = 0; i < 6; i++) {
                                nextChar();
                                switch(current) {
                                    case 63: // '?'
                                        range = true;
                                        break;

                                    default:
                                        if(range && !ScannerUtilities.isCSSHexadecimalCharacter((char)current))
                                            throw new ParseException("character", reader.getLine(), reader.getColumn());
                                        break;
                                }
                            }

                            nextChar();
                            if(range) {
                                type = 53;
                                return;
                            }
                            if(current == 45) {
                                nextChar();
                                if(!ScannerUtilities.isCSSHexadecimalCharacter((char)current))
                                    throw new ParseException("character", reader.getLine(), reader.getColumn());
                                nextChar();
                                if(!ScannerUtilities.isCSSHexadecimalCharacter((char)current)) {
                                    type = 53;
                                    return;
                                }
                                nextChar();
                                if(!ScannerUtilities.isCSSHexadecimalCharacter((char)current)) {
                                    type = 53;
                                    return;
                                }
                                nextChar();
                                if(!ScannerUtilities.isCSSHexadecimalCharacter((char)current)) {
                                    type = 53;
                                    return;
                                }
                                nextChar();
                                if(!ScannerUtilities.isCSSHexadecimalCharacter((char)current)) {
                                    type = 53;
                                    return;
                                }
                                nextChar();
                                if(!ScannerUtilities.isCSSHexadecimalCharacter((char)current)) {
                                    type = 53;
                                    return;
                                } else {
                                    nextChar();
                                    type = 53;
                                    return;
                                }
                            }
                            // fall through

                        case 82: // 'R'
                        case 114: // 'r'
                            nextChar();
                            switch(current) {
                                case 76: // 'L'
                                case 108: // 'l'
                                    nextChar();
                                    switch(current) {
                                        case 40: // '('
                                            do
                                                nextChar();
                                            while(current != -1 && ScannerUtilities.isCSSSpace((char)current));
                                            switch(current) {
                                                case 39: // '\''
                                                    string1();
                                                    blankCharacters += 2;
                                                    for(; current != -1 && ScannerUtilities.isCSSSpace((char)current); nextChar())
                                                        blankCharacters++;

                                                    if(current == -1)
                                                        throw new ParseException("eof", reader.getLine(), reader.getColumn());
                                                    if(current != 41) {
                                                        throw new ParseException("character", reader.getLine(), reader.getColumn());
                                                    } else {
                                                        nextChar();
                                                        type = 51;
                                                        return;
                                                    }

                                                case 34: // '"'
                                                    string2();
                                                    blankCharacters += 2;
                                                    for(; current != -1 && ScannerUtilities.isCSSSpace((char)current); nextChar())
                                                        blankCharacters++;

                                                    if(current == -1)
                                                        throw new ParseException("eof", reader.getLine(), reader.getColumn());
                                                    if(current != 41) {
                                                        throw new ParseException("character", reader.getLine(), reader.getColumn());
                                                    } else {
                                                        nextChar();
                                                        type = 51;
                                                        return;
                                                    }

                                                case 41: // ')'
                                                    throw new ParseException("character", reader.getLine(), reader.getColumn());
                                            }
                                            if(!ScannerUtilities.isCSSURICharacter((char)current))
                                                throw new ParseException("character", reader.getLine(), reader.getColumn());
                                            start = position - 1;
                                            do
                                                nextChar();
                                            while(current != -1 && ScannerUtilities.isCSSURICharacter((char)current));
                                            blankCharacters++;
                                            for(; current != -1 && ScannerUtilities.isCSSSpace((char)current); nextChar())
                                                blankCharacters++;

                                            if(current == -1)
                                                throw new ParseException("eof", reader.getLine(), reader.getColumn());
                                            if(current != 41) {
                                                throw new ParseException("character", reader.getLine(), reader.getColumn());
                                            } else {
                                                nextChar();
                                                type = 51;
                                                return;
                                            }
                                    }
                                    break;
                            }
                            break;
                    }
                    for(; current != -1 && ScannerUtilities.isCSSNameCharacter((char)current); nextChar());
                    if(current == 40) {
                        nextChar();
                        type = 52;
                        return;
                    } else {
                        type = 20;
                        return;
                    }
            }
            if(current == 92)
                do {
                    nextChar();
                    escape();
                } while(current == 92);
            else
            if(!ScannerUtilities.isCSSIdentifierStartCharacter((char)current)) {
                nextChar();
                throw new ParseException("identifier.character", reader.getLine(), reader.getColumn());
            }
            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current))  {
                nextChar();
                while(current == 92)  {
                    nextChar();
                    escape();
                }
            }
            if(current == 40) {
                nextChar();
                type = 52;
                return;
            } else {
                type = 20;
                return;
            }
        }
        catch(IOException e) {
            throw new ParseException(e);
        }
    }

    protected int string1() throws IOException {
        start = position;
        do
            switch(nextChar()) {
                case -1:
                    throw new ParseException("eof", reader.getLine(), reader.getColumn());

                case 92: // '\\'
                    switch(nextChar()) {
                        default:
                            escape();
                            break;

                        case 10: // '\n'
                        case 12: // '\f'
                            break;
                    }
                    break;

                default:
                    if(!ScannerUtilities.isCSSStringCharacter((char)current))
                        throw new ParseException("character", reader.getLine(), reader.getColumn());
                    break;

                case 39: // '\''
                    nextChar();
                    return 19;

                case 34: // '"'
                    break;
            }
        while(true);
    }

    protected int string2() throws IOException {
        start = position;
        do
            switch(nextChar()) {
                case -1:
                    throw new ParseException("eof", reader.getLine(), reader.getColumn());

                case 92: // '\\'
                    switch(nextChar()) {
                        default:
                            escape();
                            break;

                        case 10: // '\n'
                        case 12: // '\f'
                            break;
                    }
                    break;

                default:
                    if(!ScannerUtilities.isCSSStringCharacter((char)current))
                        throw new ParseException("character", reader.getLine(), reader.getColumn());
                    break;

                case 34: // '"'
                    nextChar();
                    return 19;

                case 39: // '\''
                    break;
            }
        while(true);
    }

    protected int number() throws IOException {
        do
            switch(nextChar()) {
                case 46: // '.'
                    switch(nextChar()) {
                        case 48: // '0'
                        case 49: // '1'
                        case 50: // '2'
                        case 51: // '3'
                        case 52: // '4'
                        case 53: // '5'
                        case 54: // '6'
                        case 55: // '7'
                        case 56: // '8'
                        case 57: // '9'
                            return dotNumber();
                    }
                    throw new ParseException("character", reader.getLine(), reader.getColumn());

                case 47: // '/'
                default:
                    return numberUnit(true);

                case 48: // '0'
                case 49: // '1'
                case 50: // '2'
                case 51: // '3'
                case 52: // '4'
                case 53: // '5'
                case 54: // '6'
                case 55: // '7'
                case 56: // '8'
                case 57: // '9'
                    break;
            }
        while(true);
    }

    protected int dotNumber() throws IOException {
        do
            switch(nextChar()) {
                default:
                    return numberUnit(false);

                case 48: // '0'
                case 49: // '1'
                case 50: // '2'
                case 51: // '3'
                case 52: // '4'
                case 53: // '5'
                case 54: // '6'
                case 55: // '7'
                case 56: // '8'
                case 57: // '9'
                    break;
            }
        while(true);
    }

    protected int numberUnit(boolean integer) throws IOException {
        switch(current) {
            case 37: // '%'
                nextChar();
                return 42;

            case 67: // 'C'
            case 99: // 'c'
                switch(nextChar()) {
                    case 77: // 'M'
                    case 109: // 'm'
                        nextChar();
                        if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                            do
                                nextChar();
                            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                            return 34;
                        } else {
                            return 37;
                        }
                }
                for(; current != -1 && ScannerUtilities.isCSSNameCharacter((char)current); nextChar());
                return 34;

            case 68: // 'D'
            case 100: // 'd'
                switch(nextChar()) {
                    case 69: // 'E'
                    case 101: // 'e'
                        switch(nextChar()) {
                            case 71: // 'G'
                            case 103: // 'g'
                                nextChar();
                                if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                                    do
                                        nextChar();
                                    while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                                    return 34;
                                } else {
                                    return 47;
                                }
                        }
                        break;
                }
                for(; current != -1 && ScannerUtilities.isCSSNameCharacter((char)current); nextChar());
                return 34;

            case 69: // 'E'
            case 101: // 'e'
                switch(nextChar()) {
                    case 77: // 'M'
                    case 109: // 'm'
                        nextChar();
                        if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                            do
                                nextChar();
                            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                            return 34;
                        } else {
                            return 36;
                        }

                    case 88: // 'X'
                    case 120: // 'x'
                        nextChar();
                        if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                            do
                                nextChar();
                            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                            return 34;
                        } else {
                            return 35;
                        }
                }
                for(; current != -1 && ScannerUtilities.isCSSNameCharacter((char)current); nextChar());
                return 34;

            case 71: // 'G'
            case 103: // 'g'
                switch(nextChar()) {
                    case 82: // 'R'
                    case 114: // 'r'
                        switch(nextChar()) {
                            case 65: // 'A'
                            case 97: // 'a'
                                switch(nextChar()) {
                                    case 68: // 'D'
                                    case 100: // 'd'
                                        nextChar();
                                        if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                                            do
                                                nextChar();
                                            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                                            return 34;
                                        } else {
                                            return 49;
                                        }
                                }
                                break;
                        }
                        break;
                }
                for(; current != -1 && ScannerUtilities.isCSSNameCharacter((char)current); nextChar());
                return 34;

            case 72: // 'H'
            case 104: // 'h'
                nextChar();
                switch(current) {
                    case 90: // 'Z'
                    case 122: // 'z'
                        nextChar();
                        if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                            do
                                nextChar();
                            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                            return 34;
                        } else {
                            return 41;
                        }
                }
                for(; current != -1 && ScannerUtilities.isCSSNameCharacter((char)current); nextChar());
                return 34;

            case 73: // 'I'
            case 105: // 'i'
                switch(nextChar()) {
                    case 78: // 'N'
                    case 110: // 'n'
                        nextChar();
                        if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                            do
                                nextChar();
                            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                            return 34;
                        } else {
                            return 39;
                        }
                }
                for(; current != -1 && ScannerUtilities.isCSSNameCharacter((char)current); nextChar());
                return 34;

            case 75: // 'K'
            case 107: // 'k'
                switch(nextChar()) {
                    case 72: // 'H'
                    case 104: // 'h'
                        switch(nextChar()) {
                            case 90: // 'Z'
                            case 122: // 'z'
                                nextChar();
                                if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                                    do
                                        nextChar();
                                    while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                                    return 34;
                                } else {
                                    return 50;
                                }
                        }
                        break;
                }
                for(; current != -1 && ScannerUtilities.isCSSNameCharacter((char)current); nextChar());
                return 34;

            case 77: // 'M'
            case 109: // 'm'
                switch(nextChar()) {
                    case 77: // 'M'
                    case 109: // 'm'
                        nextChar();
                        if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                            do
                                nextChar();
                            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                            return 34;
                        } else {
                            return 38;
                        }

                    case 83: // 'S'
                    case 115: // 's'
                        nextChar();
                        if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                            do
                                nextChar();
                            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                            return 34;
                        } else {
                            return 40;
                        }
                }
                for(; current != -1 && ScannerUtilities.isCSSNameCharacter((char)current); nextChar());
                return 34;

            case 80: // 'P'
            case 112: // 'p'
                switch(nextChar()) {
                    case 67: // 'C'
                    case 99: // 'c'
                        nextChar();
                        if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                            do
                                nextChar();
                            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                            return 34;
                        } else {
                            return 44;
                        }

                    case 84: // 'T'
                    case 116: // 't'
                        nextChar();
                        if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                            do
                                nextChar();
                            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                            return 34;
                        } else {
                            return 45;
                        }

                    case 88: // 'X'
                    case 120: // 'x'
                        nextChar();
                        if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                            do
                                nextChar();
                            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                            return 34;
                        } else {
                            return 46;
                        }
                }
                for(; current != -1 && ScannerUtilities.isCSSNameCharacter((char)current); nextChar());
                return 34;

            case 82: // 'R'
            case 114: // 'r'
                switch(nextChar()) {
                    case 65: // 'A'
                    case 97: // 'a'
                        switch(nextChar()) {
                            case 68: // 'D'
                            case 100: // 'd'
                                nextChar();
                                if(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current)) {
                                    do
                                        nextChar();
                                    while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
                                    return 34;
                                } else {
                                    return 48;
                                }
                        }
                        break;
                }
                for(; current != -1 && ScannerUtilities.isCSSNameCharacter((char)current); nextChar());
                return 34;

            case 83: // 'S'
            case 115: // 's'
                nextChar();
                return 43;
        }
        if(current != -1 && ScannerUtilities.isCSSIdentifierStartCharacter((char)current)) {
            do
                nextChar();
            while(current != -1 && ScannerUtilities.isCSSNameCharacter((char)current));
            return 34;
        } else {
            return integer ? 24 : 54;
        }
    }

    protected void escape() throws IOException {
        if(ScannerUtilities.isCSSHexadecimalCharacter((char)current)) {
            nextChar();
            if(!ScannerUtilities.isCSSHexadecimalCharacter((char)current)) {
                if(ScannerUtilities.isCSSSpace((char)current))
                    nextChar();
                return;
            }
            nextChar();
            if(!ScannerUtilities.isCSSHexadecimalCharacter((char)current)) {
                if(ScannerUtilities.isCSSSpace((char)current))
                    nextChar();
                return;
            }
            nextChar();
            if(!ScannerUtilities.isCSSHexadecimalCharacter((char)current)) {
                if(ScannerUtilities.isCSSSpace((char)current))
                    nextChar();
                return;
            }
            nextChar();
            if(!ScannerUtilities.isCSSHexadecimalCharacter((char)current)) {
                if(ScannerUtilities.isCSSSpace((char)current))
                    nextChar();
                return;
            }
            nextChar();
            if(!ScannerUtilities.isCSSHexadecimalCharacter((char)current)) {
                if(ScannerUtilities.isCSSSpace((char)current))
                    nextChar();
                return;
            }
        }
        if(current >= 32 && current <= 126 || current >= 128) {
            nextChar();
            return;
        } else {
            throw new ParseException("character", reader.getLine(), reader.getColumn());
        }
    }

    protected static boolean isEqualIgnoreCase(int i, char c) {
        return i != -1 ? Character.toLowerCase((char)i) == c : false;
    }

    protected int nextChar() throws IOException {
        current = reader.read();
        if(current == -1)
            return current;
        if(position == buffer.length) {
            char t[] = new char[1 + position + position / 2];
            System.arraycopy(buffer, 0, t, 0, position);
            buffer = t;
        }
        return buffer[position++] = (char)current;
    }
}
