public class Driver {
    final static String FILE_NAME_ANSWER_LIST = "/Users/rio/Documents/dev/git/ciccc/mini_project/javaHangman/javaHangman/src/static/cities.txt";

    public static void main(String[] args) {
        Game game = new Game(FILE_NAME_ANSWER_LIST);
        game.Hangman();
    }
}
