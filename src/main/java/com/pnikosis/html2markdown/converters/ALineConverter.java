package com.pnikosis.html2markdown.converters;

import com.pnikosis.html2markdown.md.MDLine;
import com.pnikosis.html2markdown.md.MDLines;

/* package */ class ALineConverter extends LineConverter {
  private final String url;
  private final String title;

  /* package */ ALineConverter(String url, String title) {
    this.url = url;
    this.title = title;
  }

  @Override
  public MDLines convert(String content) {
    String mdLink = "[" + content + "](" + url;
    if (title != null && title.length() > 0) {
      mdLink = mdLink + " \"" + title + "\"";
    }
    mdLink = mdLink + ")";
    return new MDLines().addLine(MDLine.MDLineType.None, 0, mdLink);
  }
}

