package com.pnikosis.html2markdown.converters;

import com.pnikosis.html2markdown.md.MDLine;
import com.pnikosis.html2markdown.md.MDLines;

/* package */ class HLineConverter extends LineConverter {
  private final int level;

  /* package */ HLineConverter(int level) {
    this.level = level;
  }

  @Override
  public MDLines convert(String content) {
    switch (level) {
      case 1:
        return new MDLines().addLine(MDLine.MDLineType.Head1, 0, content);
      case 2:
        return new MDLines().addLine(MDLine.MDLineType.Head2, 0, content);
      case 3:
        return new MDLines().addLine(MDLine.MDLineType.Head3, 0, content);
      default:
        return new MDLines().addLine(MDLine.MDLineType.Head3, 0, content);
    }
  }
}
