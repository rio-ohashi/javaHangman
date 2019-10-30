
public class Message {

    public void Start(String currentAnswer) {
        System.out.print(String.format("Here's the question\n%s\nGuess a letter: ",
                currentAnswer));
    }

    public void Continue(String currentAnswer, int wrongCount, String wrongAnswers) {
        System.out.print(String.format("You are guessing: %s\nYou have guessed (%d) wrong letters: %s\nGuess a letter: ",
                currentAnswer,
                wrongCount,
                wrongAnswers));
    }

    public void EndSucceed(String answer) {
        System.out.println(String.format("You win!\nYou have guessed '%s' correctly.",
                answer));
    }

    public void EndFailure(int wrongCount, String wrongInputs, String answer) {
        System.out.print(String.format("You have guessed (%d) wrong letters: %s\nYou lose!\nThe correct word was '%s'",
                wrongCount,
                wrongInputs,
                answer));
    }

    public void Valid() {
        System.out.println("Enter valid letter!");
    }
}
