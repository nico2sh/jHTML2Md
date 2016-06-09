package com.pnikosis.html2markdown.md;

import spock.lang.Specification
import spock.lang.Unroll;

class MDLineSpec extends Specification {
    private static final String CONTENT = "SOME CONTENT"

    @Unroll
    def 'should create correctly a level #level line when having #spaces leading spaces'() {
        given:
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spaces; i++) {
            sb.append(" ");
        }
        sb.append(CONTENT)
        MDLine mdLine = MDLine.create(sb.toString())
        level = mdLine.getLevel();

        where:
        spaces | level
        0      | 0
        1      | 0
        2      | 0
        3      | 0
        4      | 1
        5      | 1
        6      | 1
        7      | 1
    }

    @Unroll
    def 'should be unordered list #isOrdered when giving content in a list starting with #prefix'() {
        given:
        String lineContent = prefix + " " + CONTENT
        MDLine mdLine = MDLine.create(lineContent)
        isOrdered = mdLine.getLineType().equals(MDLine.MDLineType.Unordered)

        where:
        prefix | isOrdered
        "*"    | true
        "-"    | true
        "+"    | true
        "."    | false
        ""     | false
        "x"    | false
    }

}
