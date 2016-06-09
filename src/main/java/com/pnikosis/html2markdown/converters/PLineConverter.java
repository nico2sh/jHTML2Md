package com.pnikosis.html2markdown.converters;

import com.pnikosis.html2markdown.md.MDLine;
import com.pnikosis.html2markdown.md.MDLines;

/* package */ class PLineConverter extends LineConverter {
  @Override
  public MDLines convert(String content) {
    MDLines mdLines = new MDLines();
    mdLines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    mdLines.add(new MDLine(MDLine.MDLineType.None, 0, content));
    mdLines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    return mdLines;
  }
}
