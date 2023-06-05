import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import token.Token;

class Main {
    public static void main(String[] args) throws LexicalAnalysisException {
        String filePath = "C:\\Users\\Acer\\Third_course\\Geom\\Lab1_Lexer\\resources\\example.js";
        String source = getCodeSource(filePath);

        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(source);
        tokens.forEach((token -> System.out.println(token.toString())));
    }
    private static String getCodeSource(String path) {
        StringBuilder fileContents = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContents.append(line).append("\n");
            }
        } catch (Exception ep) {};
        return fileContents.toString();
    }
}