public class LineIterator {
    private int position = 0;
    private String[] lines = {};

    public LineIterator(String source) {
        this.lines = splitLines(source);
    }

    public boolean hasNextLine() {
        return this.position < this.lines.length;
    }

    public String readNextLine() {
        String currentLine = this.lines[this.position];
        this.position++;
        return currentLine;
    }

    private String[] splitLines(String source) {
        return source.split("\n");
    }
}









