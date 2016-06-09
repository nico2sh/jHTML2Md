package com.pnikosis.html2markdown.converters;

import com.pnikosis.html2markdown.md.MDLine;
import com.pnikosis.html2markdown.md.MDLines;

/* package */ class CodeLineConverter extends LineConverter {
  @Override
  public MDLines convert(String content) {
    String[] contentLines = content.split("\\r?\\n", 0);
    MDLines mdLines = new MDLines();
    mdLines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    for (String line : contentLines) {
      mdLines.add(new MDLine(MDLine.MDLineType.None, 1, line));
    }
    mdLines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    return mdLines;
  }
}
