package com.pnikosis.html2markdown.md;

import spock.lang.Specification
import spock.lang.Unroll;

class MDLinesSpec extends Specification {
    def 'should not add two empty lines on empty array'() {
        given:
        MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
        MDLines mdLines = new MDLines();

        when:
        mdLines.add(line)
        mdLines.add(line)

        then:
        mdLines.size() == 1
        mdLines.get(0).isEmpty()
    }

    def 'should not add consecutive empty lines'() {
        given:
        MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
        MDLines mdLines = new MDLines();

        when:
        mdLines.add(MDLine.MDLineType.None, 0, "some content");
        mdLines.add(line)
        mdLines.add(line)

        then:
        mdLines.size() == 2
    }

    def 'should add non consecutive empty lines'() {
        given:
        MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
        MDLines mdLines = new MDLines();

        when:
        mdLines.add(MDLine.MDLineType.None, 0, "some content");
        mdLines.add(line)
        mdLines.add(MDLine.MDLineType.None, 0, "some content");
        mdLines.add(line)

        then:
        mdLines.size() == 4
    }

    def 'should add on index 0 an empty line'() {
        given:
        MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
        MDLines mdLines = new MDLines();

        when:
        mdLines.add(MDLine.MDLineType.None, 0, "some content");
        mdLines.add(line)
        mdLines.add(MDLine.MDLineType.None, 0, "some more content");
        mdLines.add(0, line)

        then:
        mdLines.size() == 4
        mdLines.get(0).isEmpty()
        mdLines.get(1).getContent().equals("some content")
        mdLines.get(2).isEmpty()
        mdLines.get(3).getContent().equals("some more content")
    }

    @Unroll
    def 'should not add on indexed insertion #pos empty line'() {
        given:
        MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
        MDLines mdLines = new MDLines();

        when:
        mdLines.add(MDLine.MDLineType.None, 0, "some content");
        mdLines.add(line)
        mdLines.add(MDLine.MDLineType.None, 0, "some more content");
        mdLines.add(index, line)

        then:
        mdLines.size() == 3

        where:
        pos      | index
        "before" | 1
        "after"  | 2
    }

    def 'should not add empty lines on addAll() in empty MDLines'() {
        given:
        List<MDLine> lines = new ArrayList<>();
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        MDLines mdLines = new MDLines()

        when:
        mdLines.addAll(lines)

        then:
        lines.size() == 3
        mdLines.size() == 0
    }

    def 'should not add duplicated consecutive empty lines on addAll()'() {
        given:
        List<MDLine> lines = new ArrayList<>();
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "extra content")
        MDLines mdLines = new MDLines().addLine(MDLine.MDLineType.None, 0, "some content")

        when:
        mdLines.addAll(lines)

        then:
        mdLines.size() == 3
        mdLines.get(0).getContent().equals("some content")
        mdLines.get(1).isEmpty()
        mdLines.get(2).getContent().equals("extra content")
    }

    def 'should not add duplicated consecutive empty lines in between on addAll()'() {
        given:
        List<MDLine> lines = new ArrayList<>();
        lines << new MDLine(MDLine.MDLineType.None, 0, "first line")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "last line")
        MDLines mdLines = new MDLines()

        when:
        mdLines.addAll(lines)

        then:
        lines.size() == 5
        mdLines.size() == 3
    }

    def 'should not add indexed duplicated consecutive empty lines in between on addAll()'() {
        given:
        MDLine emptyLine = new MDLine(MDLine.MDLineType.None, 0, "");
        MDLines mdLines = new MDLines()
        mdLines.add(MDLine.MDLineType.None, 0, "some content");
        mdLines.add(emptyLine)
        mdLines.add(MDLine.MDLineType.None, 0, "some more content");
        mdLines.add(emptyLine)
        List<MDLine> lines = new ArrayList<>();
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "first line")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "last line")

        when:
        mdLines.addAll(2, lines)

        then:
        mdLines.size() == 7
        mdLines.get(0).getContent().equals("some content")
        mdLines.get(1).getContent().equals("")
        mdLines.get(2).getContent().equals("first line")
        mdLines.get(3).getContent().equals("")
        mdLines.get(4).getContent().equals("last line")
        mdLines.get(5).getContent().equals("some more content")
        mdLines.get(6).getContent().equals("")
    }

    def 'should not add indexed empty consecutive lines in between on addAll()'() {
        given:
        MDLine emptyLine = new MDLine(MDLine.MDLineType.None, 0, "");
        MDLines mdLines = new MDLines()
        mdLines.add(MDLine.MDLineType.None, 0, "some content");
        mdLines.add(emptyLine)
        mdLines.add(MDLine.MDLineType.None, 0, "some more content");
        mdLines.add(emptyLine)
        List<MDLine> lines = new ArrayList<>();
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")

        when:
        mdLines.addAll(2, lines)

        then:
        mdLines.size() == 4
        mdLines.get(0).getContent().equals("some content")
        mdLines.get(1).getContent().equals("")
        mdLines.get(2).getContent().equals("some more content")
        mdLines.get(3).getContent().equals("")
    }

    def 'should not add indexed trailing empty consecutive lines in between on addAll()'() {
        given:
        MDLine emptyLine = new MDLine(MDLine.MDLineType.None, 0, "");
        MDLines mdLines = new MDLines()
        mdLines.add(MDLine.MDLineType.None, 0, "some content");
        mdLines.add(emptyLine)
        mdLines.add(MDLine.MDLineType.None, 0, "some more content");
        mdLines.add(emptyLine)
        List<MDLine> lines = new ArrayList<>();
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "interesting line")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")
        lines << new MDLine(MDLine.MDLineType.None, 0, "")

        when:
        mdLines.addAll(1, lines)

        then:
        mdLines.size() == 6
        mdLines.get(0).getContent().equals("some content")
        mdLines.get(1).getContent().equals("")
        mdLines.get(2).getContent().equals("interesting line")
        mdLines.get(3).getContent().equals("")
        mdLines.get(4).getContent().equals("some more content")
        mdLines.get(5).getContent().equals("")
    }
}
