public class Main {

  public static void main(String[] args) {
    StringBuilder text = new StringBuilder("""
        Велика вежа стояла на вершині гори, і вид з неї був просто неймовірний! Як далеко \
        тягнеться цей туман, що оповив все навколо? Ранкове сонце теплом обіймало землю, чи може \
        бути щось прекрасніше? Вітер дув настільки легко, що здавалось, ніби він лагідно торкався \
        кожного листочка. Десь далеко чулося тихе дзюрчання струмка, а чи могли туристи оминути це \
        місце? Вони зупинялися тут, щоб відчути гармонію природи. Кожен знаходив тут свій спокій...\
        """);
    final char START_CHAR = 'в', END_CHAR = 'т';
    final boolean IS_CASE_SENSITIVE = false;

    try {
      deleteSubstrings(text, START_CHAR, END_CHAR, IS_CASE_SENSITIVE);
      System.out.println(text);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }

  public static void deleteSubstrings(StringBuilder text, char startChar, char endChar,
      boolean isCaseSensitive) {
    if (text == null) {
      throw new IllegalArgumentException("Текстовий рядок не може дорівнювати null.");
    }
    if (!(Character.isLetter(startChar) && Character.isLetter(endChar))) {
      throw new IllegalArgumentException("Початковий та кінцевий символи мають бути літерами.");
    }

    int i = 0;
    while (i < text.length()) {
      int sentenceEnd = findSentenceEnd(text, i);

      int start = findChar(text, startChar, i, sentenceEnd, isCaseSensitive);
      int end = findChar(text, endChar, sentenceEnd, i, isCaseSensitive);

      if (start != -1 && end != -1 && start < end) {
        text.delete(start, end + 1);
        sentenceEnd -= (end + 1 - start);
      }

      i = sentenceEnd + 1;
    }
  }

  private static int findSentenceEnd(StringBuilder text, int start) {
    int sentenceEnd = text.length() - 1;
    for (char punctuation : new char[]{'.', '!', '?'}) {
      int pos = text.indexOf(String.valueOf(punctuation), start);
      if (pos != -1 && pos < sentenceEnd) {
        sentenceEnd = pos;
      }
    }
    return sentenceEnd;
  }

  private static int findChar(StringBuilder text, char targetChar, int start, int end,
      boolean isCaseSensitive) {
    boolean isReversed = (end - start) < 0;
    int step = isReversed ? -1 : 1;

    for (int i = start; isReversed == (i >= end); i += step) {
      char currentChar = text.charAt(i);

      if (!isCaseSensitive) {
        targetChar = Character.toLowerCase(targetChar);
        currentChar = Character.toLowerCase(currentChar);
      }

      if (currentChar == targetChar) {
        return i;
      }
    }
    return -1;
  }
}
