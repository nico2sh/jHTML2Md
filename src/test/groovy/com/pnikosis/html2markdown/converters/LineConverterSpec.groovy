package com.pnikosis.html2markdown.converters

import com.pnikosis.html2markdown.extractor.TextExtractor
import com.pnikosis.html2markdown.md.MDLines
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import org.jsoup.parser.Tag
import spock.lang.Specification
import spock.lang.Unroll

class LineConverterSpec extends Specification {
    private static final String BASE_URL = ""
    private static final String CONTENT = "Some content"
    private static final String CONTENT_2 = "More content"

    @Unroll
    def 'on #headerTag should create a markdown header #mdPrefix correctly'() {
        given:
        Element element = new Element(Tag.valueOf(headerTag), BASE_URL);
        element.append(CONTENT);

        when:
        String text = LineConverter.convert(element, getTextExtractor()).toString();

        then:
        text.equals(mdPrefix + " " + CONTENT);

        where:
        headerTag | mdPrefix
        "h1"      | "#"
        "h2"      | "##"
        "h3"      | "###"
        "h4"      | "###"
        "h10"     | "###"
    }

    def 'should convert code blocks to indented text correctly'() {
        given:
        Element element = new Element(Tag.valueOf("code"), BASE_URL);
        element.append(CONTENT)

        when:
        MDLines lines = LineConverter.convert(element, getTextExtractor())

        then:
        lines.size() == 3
        lines.get(0).isEmpty()
        lines.get(1).getLevel() == 1
        lines.get(1).getContent().equals(CONTENT)
        lines.get(2).isEmpty()
    }

    def 'should convert multi line code blocks to indented text correctly'() {
        given:
        Element element = new Element(Tag.valueOf("code"), BASE_URL);
        element.text(CONTENT + System.lineSeparator() + CONTENT_2)

        when:
        MDLines lines = LineConverter.convert(element, getTextExtractor())

        then:
        lines.size() == 4
        lines.get(0).isEmpty()
        lines.get(1).getLevel() == 1
        lines.get(1).getContent().equals(CONTENT)
        lines.get(2).getLevel() == 1
        lines.get(2).getContent().equals(CONTENT_2)
        lines.get(3).isEmpty()
    }

    def TextExtractor getTextExtractor() {
        return new TextExtractor() {
            @Override
            String extract(List<Node> nodes) {
                return ((TextNode) nodes.get(0)).getWholeText();
            }
        }
    }
}
