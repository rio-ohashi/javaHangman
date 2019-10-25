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
    final int LIMIT_WRONG = 10;
    private String answerStr = "";

    Game(String fileName) {
        ArrayList<String> answerList = getListFromFile(fileName);
        answerStr = answerList.get(new Random().nextInt(answerList.size()));
    }

    public void Hangman() {
        UserOutput userOutput = new UserOutput();

        ArrayList<String> originalAnswer = new ArrayList<>();
        ArrayList<String> currentAnswer = new ArrayList<>();
        ArrayList<String> wrongInputs = new ArrayList<>();

        IntStream.range(0, answerStr.length()).forEach(i->{
            originalAnswer.add(String.valueOf(answerStr.charAt(i)));
            currentAnswer.add(answerStr.charAt(i) != ' ' ? "_" : " ");
        });

        userOutput.Start(convertToStringByList(currentAnswer));

        while (!originalAnswer.equals(currentAnswer) &&
                wrongInputs.size() != LIMIT_WRONG) {

            String in = new Scanner(System.in).nextLine();
            if (!in.matches("[a-zA-Z]")) {
                userOutput.Valid();
                continue;
            }

            if (originalAnswer.contains(in)) {
                int index = 0;
                for (String s : originalAnswer) {
                    if (s.equals(in)) currentAnswer.set(index, in);
                    index++;
                }

                if (originalAnswer.equals(currentAnswer)) {
                    userOutput.EndSucceed(answerStr);
                } else {
                    userOutput.Continue(
                            convertToStringByList(currentAnswer),
                            wrongInputs.size(),
                            convertToStringByList(wrongInputs));
                }
            } else {
                wrongInputs.add(in + " ");

                if (wrongInputs.size() == LIMIT_WRONG) {
                    userOutput.EndFailure(
                            wrongInputs.size(),
                            convertToStringByList(wrongInputs),
                            answerStr);
                } else {
                    userOutput.Continue(
                            convertToStringByList(currentAnswer),
                            wrongInputs.size(),
                            convertToStringByList(wrongInputs));
                }
            }
        }
    }

    private String convertToStringByList(List<String> answer) {
        String s = "";
        for (String str : answer) s += str;
        return s;
    }

    public ArrayList<String> getListFromFile(String file_name) {
        ArrayList<String> CITIES = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file_name)));
            CITIES = new ArrayList<>(bufferedReader.lines().collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CITIES;
    }
}