package com.pnikosis.html2markdown.converters;

import com.pnikosis.html2markdown.md.MDLine;
import com.pnikosis.html2markdown.md.MDLines;

/* package */ class ImgLineConverter extends LineConverter {
  private final String src;
  private final String alt;
  private final String title;

  /* package */ ImgLineConverter(String src, String alt, String title) {
    this.src = src;
    this.alt = alt;
    this.title = title;
  }

  @Override
  public MDLines convert(String content) {
    String mdLink = "![" + alt + "](" + src;
    if (title != null && title.length() > 0) {
      mdLink = mdLink + " \"" + title + "\"";
    }
    mdLink = mdLink + ")";
    return new MDLines().addLine(MDLine.MDLineType.None, 0, mdLink);
  }
}

