package com.pnikosis.html2markdown.converters;

import com.pnikosis.html2markdown.md.MDLine;
import com.pnikosis.html2markdown.md.MDLines;

/* package */ class HorizontalRuleLineConverter extends LineConverter {
  @Override
  public MDLines convert(String content) {
    return new MDLines().addLine(MDLine.MDLineType.HR, 0, "");
  }
}
