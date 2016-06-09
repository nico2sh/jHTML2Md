package com.pnikosis.html2markdown.extractor;

import java.util.List;
import org.jsoup.nodes.Node;

public interface TextExtractor {
  String extract(List<Node> nodes);
}
