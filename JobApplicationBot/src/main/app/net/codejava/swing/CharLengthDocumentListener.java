/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.codejava.swing;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * Listen to changes made to a document.
 * 
 * @author Oracle
 *
 */
public class CharLengthDocumentListener implements DocumentListener {

    private JTextArea _changeLog;
    private String _NEWLINE = "\n";

    public CharLengthDocumentListener(JTextArea changeLog) {
        _changeLog = changeLog;
    }

    /**
     * Override insertUpdate method, which is called when text is inserted into the
     * listened-to document.
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        displayEditInfo(e);
    }

    /**
     * Override the removeUpdate method, which is called when text is removed from
     * the listened-to document.
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        displayEditInfo(e);
    }

    /**
     * Override the changedUpdate method, which is called when the style of some of
     * the text in the listened-to document changes.
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        displayEditInfo(e);
    }

    /**
     * Keep track of the number of characters typed.
     * 
     * @param e The event.
     */
    private void displayEditInfo(DocumentEvent e) {
        Document document = e.getDocument();
        int changeLength = e.getLength();
        _changeLog.append(e.getType().toString() + ": " + changeLength + " character"
                + ((changeLength == 1) ? ". " : "s. ") + " Text length = " + document.getLength() + "." + _NEWLINE);
    }
}
