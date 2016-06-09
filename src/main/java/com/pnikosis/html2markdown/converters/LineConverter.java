package com.pnikosis.html2markdown.converters;

import com.pnikosis.html2markdown.extractor.TextExtractor;
import com.pnikosis.html2markdown.md.MDLines;
import org.jsoup.nodes.Element;

public abstract class LineConverter {
  public static MDLines convert(Element element, TextExtractor textExtractor) {
    LineConverter lineConverter = getConverter(element);
    return lineConverter.convert(textExtractor.extract(element.childNodes()));
  }

  private static LineConverter getConverter(Element element) {
    String tag = element.tagName();

    switch (tag) {
      case "div":
        return new DivLineConverter();
      case "p":
        return new PLineConverter();
      case "br":
        return new BrLineConverter();
      case "strong":
      case "b":
        return new StrongLineConverter();
      case "em":
        return new EmphasisLineConverter();
      case "hr":
        return new HorizontalRuleLineConverter();
      case "a":
        return new ALineConverter(element.attr("href"), element.attr("title"));
      case "img":
        return new ImgLineConverter(element.attr("src"), element.attr("alt"), element.attr("title"));
      case "code":
        return new CodeLineConverter();
    }

    if (tag.matches("^h[0-9]+$")) {
      int level = Integer.valueOf(element.tagName().substring(1));
      return new HLineConverter(level);
    }

    return new TextConverter();
  }

  protected abstract MDLines convert(String content);
}
