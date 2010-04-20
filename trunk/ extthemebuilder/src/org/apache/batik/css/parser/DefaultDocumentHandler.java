package org.apache.batik.css.parser;

import org.w3c.css.sac.*;

public class DefaultDocumentHandler
        implements DocumentHandler {

    public static final DocumentHandler INSTANCE = new DefaultDocumentHandler();

    protected DefaultDocumentHandler() {
    }

    public void startDocument(InputSource inputsource) throws CSSException {
    }

    public void endDocument(InputSource inputsource) throws CSSException {
    }

    public void comment(String s) throws CSSException {
    }

    public void ignorableAtRule(String s) throws CSSException {
    }

    public void namespaceDeclaration(String s, String s1) throws CSSException {
    }

    public void importStyle(String s, SACMediaList sacmedialist, String s1) throws CSSException {
    }

    public void startMedia(SACMediaList sacmedialist) throws CSSException {
    }

    public void endMedia(SACMediaList sacmedialist) throws CSSException {
    }

    public void startPage(String s, String s1) throws CSSException {
    }

    public void endPage(String s, String s1) throws CSSException {
    }

    public void startFontFace() throws CSSException {
    }

    public void endFontFace() throws CSSException {
    }

    public void startSelector(SelectorList selectorlist) throws CSSException {
    }

    public void endSelector(SelectorList selectorlist) throws CSSException {
    }

    public void property(String s, LexicalUnit lexicalunit, boolean flag) throws CSSException {
    }

}
