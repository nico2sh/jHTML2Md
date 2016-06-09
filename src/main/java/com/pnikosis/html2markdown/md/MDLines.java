package com.pnikosis.html2markdown.md;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MDLines extends ArrayList<MDLine> {
  public void add(MDLine.MDLineType lineType, int level, String content) {
    this.add(new MDLine(lineType, level, content));
  }

  public MDLines addLine(MDLine mdLine) {
    this.add(mdLine);
    return this;
  }

  public MDLines addLine(MDLine.MDLineType lineType, int level, String content) {
    this.add(lineType, level, content);
    return this;
  }

  @Override
  public boolean add(MDLine mdLine) {
    if (mdLine.isEmpty()) {
      if (size() > 0) {
        MDLine lastLine = get(size() - 1);
        return !lastLine.isEmpty() && super.add(mdLine);
      }
    }
    return super.add(mdLine);
  }

  @Override
  public void add(int index, MDLine mdLine) {
    rangeCheckForAdd(index);

    if (mdLine.isEmpty()) {
      if (size() > index) {
        boolean surroundedByEmpty = false;
        if (index > 0) {
          surroundedByEmpty = get(index - 1).isEmpty();
        }
        if (index < size() - 1 && !surroundedByEmpty) {
          surroundedByEmpty = get(index).isEmpty();
        }
        if (surroundedByEmpty) {
          return;
        }
      }
    }

    super.add(index, mdLine);
  }

  @Override
  public boolean addAll(Collection<? extends MDLine> collection) {
    boolean startingEmpty = true;
    if (size() > 0) {
      startingEmpty = get(size() - 1).isEmpty();
    }

    List<MDLine> cleanCollection = cleanCollection(collection, startingEmpty);
    return super.addAll(cleanCollection);
  }

  @Override
  public boolean addAll(int index, Collection<? extends MDLine> collection) {
    rangeCheckForAdd(index);

    boolean startingEmpty = false;
    if (index > 0) {
      startingEmpty = get(index - 1).isEmpty();
    }
    boolean endingEmpty = get(index).isEmpty();

    List<MDLine> cleanCollection = cleanCollection(collection, startingEmpty);
    if (endingEmpty && cleanCollection.get(cleanCollection.size() - 1).isEmpty()) {
      cleanCollection.remove(cleanCollection.size() - 1);
    }

    return cleanCollection.size() != 0 && super.addAll(index, cleanCollection);

  }

  private List<MDLine> cleanCollection(Collection<? extends MDLine> collection, boolean fromEmpty) {
    List<MDLine> newLines = new ArrayList<>();

    boolean previousEmpty = fromEmpty;
    for (MDLine mdLine : collection) {
      boolean currentEmpty = mdLine.isEmpty();
      if (!(previousEmpty && currentEmpty)) {
        newLines.add(mdLine);
      }
      previousEmpty = currentEmpty;
    }

    return newLines;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < size(); i++) {
      String line = get(i).toString().trim();
      result.append(line);
      if (i < size() - 1) {
        result.append("\n");
      }
    }

    return result.toString();
  }

  private void rangeCheckForAdd(int index) {
    if (index > size() || index < 0)
      throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
  }

  private String outOfBoundsMsg(int index) {
    return "Index: " + index + ", Size: " + size();
  }
}
