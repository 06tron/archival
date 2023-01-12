package mpc.sam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SentenceGrabber {

  private static String text = "";

  // first argument is the full path to the input file
  // second argument is the full path to the output directory
  public static void main(String[] args) throws IOException {
    String textFile;
    String wordFile;
    String outputFolder;
    
    if (args.length == 3) {
      textFile = args[0];
      wordFile = args[1];
      outputFolder = args[2];
    } else if (args.length == 2) {
      textFile = args[0];
      wordFile = args[1];
      outputFolder = ".";
    } else {
      throw new IOException();
    }
    
    List<String> words = new ArrayList<>();
    Map<String, List<String>> sentences = new HashMap<>();
    try (Scanner sc = new Scanner(new File(wordFile))) {
      while (sc.hasNext()) {
        String word = sc.next();
        words.add(word);
        sentences.put(word, new ArrayList<>());
      }
    }

    StringBuilder sb = new StringBuilder();
    try (Scanner sc = new Scanner(new File(textFile))) {
      while (sc.hasNext()) {
        sb.append(sc.next());
      }
    }
    text = sb.toString();

    for (String word : words) {
      List<String> finds = sentences.get(word);
      while (finds.size() == 0 && word.length() > 0) {
        int lastIndex = 0;
        while (true) {
          lastIndex = text.indexOf(word, lastIndex + 1);
          if (lastIndex == -1) {
            break;
          }
          finds.add(getSentence(lastIndex));
        }
        word = word.substring(0, word.length() - 1);
      }
      if (finds.size() == 0) {
        finds.add("no sentence found");
      }
    }

    try (FileWriter fw = new FileWriter(outputFolder + "/output_short.txt")) {
      for (String word : words) {
        String sentence = sentences.get(word).get(0);
        fw.write(word, 0, word.length());
        fw.write("\t", 0, 1);
        fw.write(sentence, 0, sentence.length());
        fw.write("\n", 0, 1);
      }
    }

    try (FileWriter fw = new FileWriter(args[1] + "/output_long.txt")) {
      for (String word : words) {
        for (String sentence : sentences.get(word)) {
          fw.write(word, 0, word.length());
          fw.write("\t", 0, 1);
          fw.write(sentence, 0, sentence.length());
          fw.write("\n", 0, 1);
        }
      }
    }

  }

  private static String getSentence(int index) {
    boolean kutenFound = false;
    boolean inQuote = false;

    int start = -1;
    for (int i = index; i >= 0; i--) {
      char c = text.charAt(i);
      if (!kutenFound && isKuten(c)) {
        start = i + 1;
        kutenFound = true;
      } else if (isCloseQuote(c)) {
        if (!kutenFound) {
          start = i + 1;
        }
        break;
      } else if (isOpenQuote(c)) {
        start = i;
        inQuote = true;
        break;
      }
    }

    int end = -1;
    for (int i = index; i < text.length(); i++) {
      char c = text.charAt(i);
      if (inQuote && isCloseQuote(c)) {
        end = i + 1;
        break;
      } else if (!inQuote && isKuten(c)) {
        end = i + 1;
        break;
      }
    }
    
    return text.substring(start, end);
  }

  private static boolean isKuten(char c) {
    return c == '。';
  }

  private static boolean isOpenQuote(char c) {
    return c == '「' || c == '『';
  }

  private static boolean isCloseQuote(char c) {
    return c == '」' || c == '』';
  }

}
