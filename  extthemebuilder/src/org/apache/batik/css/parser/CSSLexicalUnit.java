package org.apache.batik.css.parser;

import org.w3c.css.sac.LexicalUnit;

public abstract class CSSLexicalUnit
        implements LexicalUnit {
    protected static class StringLexicalUnit extends CSSLexicalUnit {

        protected String value;

        public String toString(){
            StringBuilder res = new StringBuilder(value);
            res.append(" ");
            LexicalUnit unit = this.getNextLexicalUnit();
            if (null!=unit) {
                res.append(unit.toString());
            }

            return res.toString();
        }

        public String getStringValue() {
            return value;
        }

        public StringLexicalUnit(short t, String val, LexicalUnit prev) {
            super(t, prev);
            value = val;
        }
    }

    protected static class PredefinedFunctionLexicalUnit extends CSSLexicalUnit {

        protected LexicalUnit parameters;

        public String toString(){
            String result=null;
            StringBuilder buffer = new StringBuilder();
            buffer.append(getFunctionName());
            buffer.append("(");
            LexicalUnit unit = parameters;
            buffer.append(unit.toString());
            buffer.append(") ");

            LexicalUnit next = this.getNextLexicalUnit();
            if (null!=next) buffer.append(next.toString());

            result = buffer.toString();
            return result;
        }

        public String getFunctionName() {
            switch(lexicalUnitType) {
            case 27: // '\033'
                return "rgb";

            case 38: // '&'
                return "rect";

            case 25: // '\031'
                return "counter";

            case 26: // '\032'
                return "counters";
        }
            return getFunctionName();
        }

        public LexicalUnit getParameters() {
            return parameters;
        }

        public PredefinedFunctionLexicalUnit(short t, LexicalUnit params, LexicalUnit prev) {
            super(t, prev);
            parameters = params;
        }
    }

    protected static class FunctionLexicalUnit extends CSSLexicalUnit {

        protected String name;
        protected LexicalUnit parameters;

        public String toString(){
            String result=null;
            StringBuilder buffer = new StringBuilder();
            buffer.append(name);
            buffer.append("(");
            LexicalUnit unit = parameters;
            buffer.append(null!=unit?unit.toString():"");
            buffer.append(") ");
            LexicalUnit next = this.getNextLexicalUnit();
            if (null!=next) {
                buffer.append(next.toString());
            }
            result = buffer.toString();
            return result;
        }
        public String getFunctionName() {
            return name;
        }

        public LexicalUnit getParameters() {
            return parameters;
        }

        public FunctionLexicalUnit(String f, LexicalUnit params, LexicalUnit prev) {
            super((short)41, prev);
            name = f;
            parameters = params;
        }
    }

    protected static class DimensionLexicalUnit extends CSSLexicalUnit {

        protected float value;
        protected String dimension;

        public String toString(){
            String result=null;
            StringBuilder buffer = new StringBuilder();
            buffer.append(value);
            String dimUnit = dimension;
            buffer.append(null!=dimUnit?dimUnit:"");
            buffer.append(" ");

            LexicalUnit next = this.getNextLexicalUnit();
            if (null!=next) {
                buffer.append(next.toString());
            }

            result = buffer.toString();
            return result;
        }

        public float getFloatValue() {
            return value;
        }

        public String getDimensionUnitText() {
            return dimension;
        }

        public DimensionLexicalUnit(float val, String dim, LexicalUnit prev) {
            super((short)42, prev);
            value = val;
            dimension = dim;
        }
    }

    protected static class FloatLexicalUnit extends CSSLexicalUnit {

        protected float value;

        public String toString(){
            String result=null;
            StringBuilder buffer = new StringBuilder();
            buffer.append(value);
            buffer.append(" ");
            LexicalUnit next = this.getNextLexicalUnit();
            if (null!=next) {
                buffer.append(next.toString());
            }
            result = buffer.toString();
            return result;
        }
        public float getFloatValue() {
            return value;
        }

        public FloatLexicalUnit(short t, float val, LexicalUnit prev) {
            super(t, prev);
            value = val;
        }
    }

    protected static class IntegerLexicalUnit extends CSSLexicalUnit {

        protected int value;

        public String toString(){
            String result=null;
            StringBuilder buffer = new StringBuilder();
            buffer.append(value);
            buffer.append(" ");
            LexicalUnit next = this.getNextLexicalUnit();
            if (null!=next) {
                buffer.append(next.toString());
            }
            result = buffer.toString();
            return result;
        }
        public int getIntegerValue() {
            return value;
        }

        public IntegerLexicalUnit(int val, LexicalUnit prev) {
            super((short)13, prev);
            value = val;
        }
    }

    protected static class SimpleLexicalUnit extends CSSLexicalUnit {
        public String toString(){
            StringBuilder buffer = new StringBuilder(", ");
            LexicalUnit next = this.getNextLexicalUnit();
            if (null!=next) {
                buffer.append(next.toString());
            }
            return buffer.toString();
        }
        public SimpleLexicalUnit(short t, LexicalUnit prev) {
            super(t, prev);
        }
    }


    public static final String UNIT_TEXT_CENTIMETER = "cm";
    public static final String UNIT_TEXT_DEGREE = "deg";
    public static final String UNIT_TEXT_EM = "em";
    public static final String UNIT_TEXT_EX = "ex";
    public static final String UNIT_TEXT_GRADIAN = "grad";
    public static final String UNIT_TEXT_HERTZ = "Hz";
    public static final String UNIT_TEXT_INCH = "in";
    public static final String UNIT_TEXT_KILOHERTZ = "kHz";
    public static final String UNIT_TEXT_MILLIMETER = "mm";
    public static final String UNIT_TEXT_MILLISECOND = "ms";
    public static final String UNIT_TEXT_PERCENTAGE = "%";
    public static final String UNIT_TEXT_PICA = "pc";
    public static final String UNIT_TEXT_PIXEL = "px";
    public static final String UNIT_TEXT_POINT = "pt";
    public static final String UNIT_TEXT_RADIAN = "rad";
    public static final String UNIT_TEXT_REAL = "";
    public static final String UNIT_TEXT_SECOND = "s";
    public static final String TEXT_RGBCOLOR = "rgb";
    public static final String TEXT_RECT_FUNCTION = "rect";
    public static final String TEXT_COUNTER_FUNCTION = "counter";
    public static final String TEXT_COUNTERS_FUNCTION = "counters";
    protected short lexicalUnitType;
    protected LexicalUnit nextLexicalUnit, previousLexicalUnit;

    protected CSSLexicalUnit(short t, LexicalUnit prev) {
        lexicalUnitType = t;
        previousLexicalUnit = prev;
        if(prev != null)
            ((CSSLexicalUnit)prev).nextLexicalUnit = this;
    }

    public short getLexicalUnitType() {
        return lexicalUnitType;
    }

    public LexicalUnit getNextLexicalUnit() {
        return nextLexicalUnit;
    }

    public void setNextLexicalUnit(LexicalUnit lu) {
        nextLexicalUnit = lu;
    }

    public LexicalUnit getPreviousLexicalUnit() {
        return previousLexicalUnit;
    }

    public void setPreviousLexicalUnit(LexicalUnit lu) {
        previousLexicalUnit = lu;
    }

    public int getIntegerValue() {
        throw new IllegalStateException();
    }

    public float getFloatValue() {
        throw new IllegalStateException();
    }

    public String getDimensionUnitText() {
        switch(lexicalUnitType) {
        case 19: // '\023'
            return "cm";

        case 28: // '\034'
            return "deg";

        case 15: // '\017'
            return "em";

        case 16: // '\020'
            return "ex";

        case 29: // '\035'
            return "grad";

        case 33: // '!'
            return "Hz";

        case 18: // '\022'
            return "in";

        case 34: // '"'
            return "kHz";

        case 20: // '\024'
            return "mm";

        case 31: // '\037'
            return "ms";

        case 23: // '\027'
            return "%";

        case 22: // '\026'
            return "pc";

        case 17: // '\021'
            return "px";

        case 21: // '\025'
            return "pt";

        case 30: // '\036'
            return "rad";

        case 14: // '\016'
            return "";

        case 32: // ' '
            return "s";

        case 24: // '\030'
        case 25: // '\031'
        case 26: // '\032'
        case 27: // '\033'
        default:
            throw new IllegalStateException("No Unit Text for type: " + lexicalUnitType);
    }
    }

    public String getFunctionName() {
        throw new IllegalStateException();
    }

    public LexicalUnit getParameters() {
        throw new IllegalStateException();
    }

    public String getStringValue() {
        throw new IllegalStateException();
    }

    public LexicalUnit getSubValues() {
        throw new IllegalStateException();
    }

    public static CSSLexicalUnit createSimple(short t, LexicalUnit prev) {
        return new SimpleLexicalUnit(t, prev);
    }

    public static CSSLexicalUnit createInteger(int val, LexicalUnit prev) {
        return new IntegerLexicalUnit(val, prev);
    }

    public static CSSLexicalUnit createFloat(short t, float val, LexicalUnit prev) {
        return new FloatLexicalUnit(t, val, prev);
    }

    public static CSSLexicalUnit createDimension(float val, String dim, LexicalUnit prev) {
        return new DimensionLexicalUnit(val, dim, prev);
    }

    public static CSSLexicalUnit createFunction(String f, LexicalUnit params, LexicalUnit prev) {
        return new FunctionLexicalUnit(f, params, prev);
    }

    public static CSSLexicalUnit createPredefinedFunction(short t, LexicalUnit params, LexicalUnit prev) {
        return new PredefinedFunctionLexicalUnit(t, params, prev);
    }

    public static CSSLexicalUnit createString(short t, String val, LexicalUnit prev) {
        return new StringLexicalUnit(t, val, prev);
    }

}
