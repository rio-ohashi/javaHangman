import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    final String FILE_NAME_ANSWER_LIST = "/Users/rio/Documents/dev/git/ciccc/mini_project/javaHangman/javaHangman/src/static/cities.txt";
    final int LIMIT_WRONG = 10;
    private ArrayList<String> CITIES;
    private String answer = "";
    int matchCount;
    int existCount;

    private ArrayList<String> getAnswersList(String file_name) {
        ArrayList<String> CITIES = null;
        try {
            File file = new File(file_name);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            List<String> list = bufferedReader.lines().collect(Collectors.toList());
            CITIES = new ArrayList<>(list);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return CITIES;
    }

    Game() {
        CITIES = getAnswersList(FILE_NAME_ANSWER_LIST);
        answer = CITIES.get(new Random().nextInt(CITIES.size()));
    }

    public void Hangman() {
        ArrayList<String> answerList = new ArrayList<>();
        ArrayList<String> currentAnswer = new ArrayList<>();
        ArrayList<String> wrongAnswers = new ArrayList<>();
        IntStream.range(0, answer.length())
                .forEach(index -> {
                    answerList.add(String.valueOf(answer.charAt(index)));
                    currentAnswer.add(answer.charAt(index) != ' ' ? "_" : " ");
                });
        int answerLength = answerList.size() - (int) answerList.stream().filter(c -> c.equals(" ")).count();
        int currentCorrectLength = 0;
        int wrongCount = 0;

        System.out.print(String.format("Here's the question\n%s\nGuess a letter: ",
                convertToStringByList(currentAnswer)));

        while (answerLength > currentCorrectLength) {
            String in = new Scanner(System.in).nextLine();
            if (!in.matches("[a-zA-Z]")) {
                System.out.println("Enter valid letter!");
            } else {
                matchCount = (int) answerList.stream().filter(s -> s.matches(in)).count();

                if (matchCount > 0) {
                    existCount = (int) currentAnswer.stream().filter(s -> s.matches(in)).count();
                    if (currentAnswer.stream().filter(s -> s.matches(in)).count() == 0) {
                        currentCorrectLength += matchCount;
                    }
                    int index = 0;
                    for (String s : answerList) {
                        if (s.equals(in)) {
                            currentAnswer.set(index, in);
                        }
                        index++;
                    }
                    PrintContinue(convertToStringByList(currentAnswer),
                            wrongCount,
                            convertToStringByList(wrongAnswers));
                } else {
                    wrongCount++;
                    // 失敗修了
                    if (wrongCount == LIMIT_WRONG) {
                        System.out.print(String.format("You have guessed (%d) wrong letters: %s\nYou lose!\nThe correct word was '%s'",
                                wrongCount,
                                convertToStringByList(wrongAnswers),
                                answer));
                        break;
                    }
                    // 失敗継続
                    else {
                        wrongAnswers.add(in + " ");
                        PrintContinue(convertToStringByList(currentAnswer), wrongCount, convertToStringByList(wrongAnswers));
                    }
                }
            }
        }
        System.out.println(String.format("You win!\nYou have guessed '%s' correctly.", answer));
    }

    private void PrintContinue(String currentAnswer, int wrongCount, String wrongAnswers) {
        System.out.print(String.format("You are guessing: %s\nYou have guessed (%d) wrong letters: %s\nGuess a letter: ",
                currentAnswer,
                wrongCount,
                wrongAnswers));
    }

    private String convertToStringByList(List<String> answer) {
        String s = "";
        for (String str : answer) s += str;
        return s;
    }
}