package md2html;

import java.io.PrintWriter;
import java.util.*;

/**
 * @author: Muhammadjon Hakimov
 * created: 15.02.2019 16:01:57
 */

public class MdParser {
    private MdSource readSource;
    private PrintWriter writeSource;

    protected MdParser(FileMdSource readSource, PrintWriter writeSource) {
        this.readSource = readSource;
        this.writeSource = writeSource;
    }

    protected void parse() throws MdException {
        readSource.nextChar();
        while (readSource.getChar() != MdSource.END) {
            skipEmptyLines();
            parseValue();
        }
    }

    private void parseValue() throws MdException {
        skipEmptyLines();
        int numberOfLattices = 0;
        while (readSource.getChar() == '#') {
            numberOfLattices++;
            readSource.nextChar();
        }

        if (readSource.getChar() == ' ' && numberOfLattices > 0) {
            writeSource.print("<h" + numberOfLattices + ">");
            readSource.nextChar();
            writeBlock(parseBlock());
            writeSource.print("</h" + numberOfLattices + ">\n");
        } else {
            writeSource.print("<p>");
            for (int i = 0; i < numberOfLattices; i++) {
                writeSource.print('#');
            }
            writeBlock(parseBlock());
            writeSource.print("</p>\n");
        }
    }

    private String parseBlock() throws MdException {
        StringBuilder block = new StringBuilder();
        block = readBlock(block);
        while (Character.isWhitespace(block.toString().charAt((block.toString().length()) - 1))) {
            block.deleteCharAt((block.toString().length()) - 1);
        }
        return block.toString();
    }

    private StringBuilder readBlock(StringBuilder sb) throws MdException {
        do {
            sb.append(readSource.getChar());
            readSource.nextChar();
            boolean f = false;
            if (isNewLine()) {
                f = true;
                sb.append("\n");
                readSource.nextChar();
            }
            if (f && isNewLine()) {
                readSource.nextChar();
                break;
            }
        } while (readSource.getChar() != MdSource.END);
        return sb;
    }

    private void writeBlock(String block) {
        Map<String, Integer> assignment =  new HashMap<>();
        assignment.put("*", 0);
        assignment.put("_", 0);
        assignment.put("*1", 0);
        assignment.put("_1", 0);
        assignment.put("*2", 0);
        assignment.put("_2", 0);
        assignment.put("`", 0);
        assignment.put("\\", 0);
        assignment.put("-", 0);
        for (int i = 0; i < block.length(); i++) {
            String c = Character.toString(block.charAt(i));
            if (assignment.containsKey(c)) {
                switch (c) {
                    case "*":
                    case "_":
                        if (i + 1 < block.length() && Character.toString(block.charAt(i + 1)).equals(c)) {
                            i++;
                            assignment.put(c + "2", assignment.get(c + "2") + 1);
                        } else {
                            assignment.put(c + "1", assignment.get(c + "1") + 1);
                        }
                        break;
                    case "`":
                        assignment.put(c, assignment.get(c) + 1);
                        break;
                    case "\\":
                        i++;
                        break;
                    case "-":
                        i++;
                        assignment.put(c, assignment.get(c) + 1);
                        break;
                }
            }
        }
        for (Map.Entry<String, Integer> pair : assignment.entrySet()) {
            if (pair.getValue() % 2 == 1) {
                assignment.put(pair.getKey(), pair.getValue() - 1);
            }
        }

        Map <String, String> specialSymbols =  new HashMap<>();
        specialSymbols.put("<", "&lt;");
        specialSymbols.put(">", "&gt;");
        specialSymbols.put("&", "&amp;");

        for (int i = 0; i < block.length(); i++) {
            String c = Character.toString(block.charAt(i));
            if (c.equals("*") || c.equals("_")) {
                if (i + 1 < block.length() && Character.toString(block.charAt(i + 1)).equals(c)
                        && assignment.get(c + "2") > 0) {
                    if (assignment.get(c + "2") % 2 == 0) {
                        writeSource.print("<strong>");
                    } else {
                        writeSource.print("</strong>");
                    }
                    i++;
                    assignment.put(c + "2", assignment.get(c + "2") - 1);
                } else {
                    if (assignment.get(c + "1") > 0) {
                        if (assignment.get(c + "1") % 2 == 0) {
                            writeSource.print("<em>");
                        } else {
                            writeSource.print("</em>");
                        }
                        assignment.put(c + "1", assignment.get(c + "1") - 1);
                    } else {
                        writeSource.print(block.charAt(i));
                    }
                }
            } else if (c.equals("`")) {
                if (assignment.get(c) > 0) {
                    if (assignment.get(c) % 2 == 0) {
                        writeSource.print("<code>");
                    } else {
                        writeSource.print("</code>");
                    }
                    assignment.put(c, assignment.get(c) - 1);
                }
            } else if (c.equals("-")) {
                if (i + 1 < block.length() && Character.toString(block.charAt(i + 1)).equals(c)
                        && assignment.get(c) > 0) {
                    if (assignment.get(c) % 2 == 0) {
                        writeSource.print("<s>");
                    } else {
                        writeSource.print("</s>");
                    }
                    i++;
                    assignment.put(c, assignment.get(c) - 1);
                } else {
                    writeSource.print(block.charAt(i));
                }
            } else if (specialSymbols.containsKey(c)) {
                writeSource.print(specialSymbols.get(c));
            } else if (!c.equals("\\")) {
                writeSource.print(block.charAt(i));
            }
        }
    }

    private void skipEmptyLines() throws MdException {
        while (isNewLine()) {
            readSource.nextChar();
        }
    }

    private boolean isNewLine() throws MdException {
        if (readSource.getChar() == '\r') {
            readSource.nextChar();
            return (readSource.getChar() == '\n');
        }
        return false;
    }
}
