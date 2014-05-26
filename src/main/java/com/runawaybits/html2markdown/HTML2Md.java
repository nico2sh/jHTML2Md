package com.runawaybits.html2markdown;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import com.runawaybits.html2markdown.MDLine.MDLineType;

public class HTML2Md {
	private static int indentation = -1;
	private static boolean orderedList = false;

	public static String convert(String theHTML, String baseURL) {
		Document doc = Jsoup.parse(theHTML, baseURL);

		String md = parseDocument(doc);
		return md;
	}

	public static String convert(URL url, int timeoutMillis) throws IOException {
		Document doc = Jsoup.parse(url, timeoutMillis);

		String md = parseDocument(doc);
		return md;
	}

	private static String parseDocument(Document dirtyDoc) {
		indentation = -1;

		String title = dirtyDoc.title();

		Whitelist whitelist = Whitelist.relaxed();
		Cleaner cleaner = new Cleaner(whitelist);

		Document doc = cleaner.clean(dirtyDoc);
		doc.outputSettings().escapeMode(EscapeMode.xhtml);

		if (!title.trim().equals("")) {
			return "# " + title + "\n\n" + getTextContent(doc);
		} else {
			return getTextContent(doc);
		}
	}

	private static String getTextContent(Element element) {
		ArrayList<MDLine> lines = new ArrayList<MDLine>();

		List<Node> children = element.childNodes();
		for (Node child : children) {
			if (child instanceof TextNode) {
				TextNode textNode = (TextNode) child;
				MDLine line = getLastLine(lines);
				if (line.getContent().equals("")) {
					if (!textNode.isBlank()) {
						line.append(textNode.text());
					}
				} else {
					line.append(textNode.text());
				}

				/*
				 * if(isInNewLine(elementProcessed) &&
				 * textNode.text().trim().equals("")){
				 *
				 * } else { elementProcessed.append(textNode.text()); }
				 */
			} else if (child instanceof Element) {
				Element childElement = (Element) child;
				processElement(childElement, lines);
			} else {
				System.out.println();
			}
		}

		int blanklines = 0;
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i).toString().trim();
			if (line.equals("")) {
				blanklines++;
			} else {
				blanklines = 0;
			}
			if (blanklines < 2) {
				result.append(line);
				if (i < lines.size() - 1) {
					result.append("\n");
				}
			}
		}

		return result.toString();

	}

	private static void processElement(Element element, ArrayList<MDLine> lines) {
		Tag tag = element.tag();

		String tagName = tag.getName();
		if (tagName.equals("div")) {
			div(element, lines);
		} else if (tagName.equals("p")) {
			p(element, lines);
		} else if (tagName.equals("br")) {
			br(element, lines);
		} else if (tagName.matches("^h[0-9]+$")) {
			h(element, lines);
		} else if (tagName.equals("strong") || tagName.equals("b")) {
			strong(element, lines);
		} else if (tagName.equals("em")) {
			em(element, lines);
		} else if (tagName.equals("hr")) {
			hr(element, lines);
		} else if (tagName.equals("a")) {
			a(element, lines);
		} else if (tagName.equals("img")) {
			img(element, lines);
		} else if (tagName.equals("code")) {
			code(element, lines);
		} else if (tagName.equals("ul")) {
			ul(element, lines);
		} else if (tagName.equals("ol")) {
			ol(element, lines);
		} else if (tagName.equals("li")) {
			li(element, lines);
		} else {
			MDLine line = getLastLine(lines);
			line.append(getTextContent(element));
		}
	}

	private static MDLine getLastLine(ArrayList<MDLine> lines) {
		MDLine line;
		if (lines.size() > 0) {
			line = lines.get(lines.size() - 1);
		} else {
			line = new MDLine(MDLineType.None, 0, "");
			lines.add(line);
		}

		return line;
	}

	private static void div(Element element, ArrayList<MDLine> lines) {
		MDLine line = getLastLine(lines);
		String content = getTextContent(element);
		if (!content.equals("")) {
			if (!line.getContent().trim().equals("")) {
				lines.add(new MDLine(MDLineType.None, 0, ""));
				lines.add(new MDLine(MDLineType.None, 0, content));
				lines.add(new MDLine(MDLineType.None, 0, ""));
			} else {
				if (!content.trim().equals(""))
					line.append(content);
			}
		}
	}

	private static void p(Element element, ArrayList<MDLine> lines) {
		MDLine line = getLastLine(lines);
		if (!line.getContent().trim().equals(""))
			lines.add(new MDLine(MDLineType.None, 0, ""));
		lines.add(new MDLine(MDLineType.None, 0, ""));
		lines.add(new MDLine(MDLineType.None, 0, getTextContent(element)));
		lines.add(new MDLine(MDLineType.None, 0, ""));
		if (!line.getContent().trim().equals(""))
			lines.add(new MDLine(MDLineType.None, 0, ""));
	}

	private static void br(Element element, ArrayList<MDLine> lines) {
		MDLine line = getLastLine(lines);
		if (!line.getContent().trim().equals(""))
			lines.add(new MDLine(MDLineType.None, 0, ""));
	}

	private static void h(Element element, ArrayList<MDLine> lines) {
		MDLine line = getLastLine(lines);
		if (!line.getContent().trim().equals(""))
			lines.add(new MDLine(MDLineType.None, 0, ""));

		int level = Integer.valueOf(element.tagName().substring(1));
		switch (level) {
		case 1:
			lines.add(new MDLine(MDLineType.Head1, 0, getTextContent(element)));
			break;
		case 2:
			lines.add(new MDLine(MDLineType.Head2, 0, getTextContent(element)));
			break;
		default:
			lines.add(new MDLine(MDLineType.Head3, 0, getTextContent(element)));
			break;
		}

		lines.add(new MDLine(MDLineType.None, 0, ""));
		lines.add(new MDLine(MDLineType.None, 0, ""));
	}

	private static void strong(Element element, ArrayList<MDLine> lines) {
		MDLine line = getLastLine(lines);
		line.append("**");
		line.append(getTextContent(element));
		line.append("**");
	}

	private static void em(Element element, ArrayList<MDLine> lines) {
		MDLine line = getLastLine(lines);
		line.append("*");
		line.append(getTextContent(element));
		line.append("*");
	}

	private static void hr(Element element, ArrayList<MDLine> lines) {
		lines.add(new MDLine(MDLineType.None, 0, ""));
		lines.add(new MDLine(MDLineType.HR, 0, ""));
		lines.add(new MDLine(MDLineType.None, 0, ""));
	}

	private static void a(Element element, ArrayList<MDLine> lines) {
		MDLine line = getLastLine(lines);
		line.append("[");
		line.append(getTextContent(element));
		line.append("]");
		line.append("(");
		String url = element.attr("href");
		line.append(url);
		String title = element.attr("title");
		if (!title.equals("")) {
			line.append(" \"");
			line.append(title);
			line.append("\"");
		}
		line.append(")");
	}

	private static void img(Element element, ArrayList<MDLine> lines) {
		MDLine line = getLastLine(lines);
		/*
		 * if (!line.isList() && !line.getContent().equals("")) { lines.add(new
		 * MDLine(MDLineType.None, 0, "")); line = lines.get(lines.size() - 1);
		 * }
		 */

		line.append("![");
		String alt = element.attr("alt");
		line.append(alt);
		line.append("]");
		line.append("(");
		String url = element.attr("src");
		line.append(url);
		String title = element.attr("title");
		if (!title.equals("")) {
			line.append(" \"");
			line.append(title);
			line.append("\"");
		}
		line.append(")");
		// lines.add(new MDLine(MDLineType.None, 0, ""));
	}

	private static void code(Element element, ArrayList<MDLine> lines) {
		lines.add(new MDLine(MDLineType.None, 0, ""));
		MDLine line = new MDLine(MDLineType.None, 0, "    ");
		line.append(getTextContent(element).replace("\n", "    "));
		lines.add(line);
		lines.add(new MDLine(MDLineType.None, 0, ""));
	}

	private static void ul(Element element, ArrayList<MDLine> lines) {
		lines.add(new MDLine(MDLineType.None, 0, ""));
		indentation++;
		orderedList = false;
		MDLine line = new MDLine(MDLineType.None, 0, "");
		line.append(getTextContent(element));
		lines.add(line);
		indentation--;
		lines.add(new MDLine(MDLineType.None, 0, ""));
	}

	private static void ol(Element element, ArrayList<MDLine> lines) {
		lines.add(new MDLine(MDLineType.None, 0, ""));
		indentation++;
		orderedList = true;
		MDLine line = new MDLine(MDLineType.None, 0, "");
		line.append(getTextContent(element));
		lines.add(line);
		indentation--;
		lines.add(new MDLine(MDLineType.None, 0, ""));
	}

	private static void li(Element element, ArrayList<MDLine> lines) {
		MDLine line;
		if (orderedList) {
			line = new MDLine(MDLineType.Ordered, indentation,
					getTextContent(element));
		} else {
			line = new MDLine(MDLineType.Unordered, indentation,
					getTextContent(element));
		}
		lines.add(line);
		// lines.add(new MDLine(MDLineType.None, 0, ""));
	}
}
