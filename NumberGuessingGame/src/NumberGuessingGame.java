import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner sc = new Scanner(System.in);

        int randomNumber = random.nextInt(100) + 1;
        System.out.println("Enter your guess (1-100): ");

        while (true){
            int playerGuess = sc.nextInt();
            if (playerGuess == randomNumber) {
                System.out.println("Correct! You win!");
                break;
            } else if (playerGuess > randomNumber) {
                System.out.println("The number is higher. Guess again");
            } else {
                System.out.println("The number is lower. Guess again");
            }
        }
    }
}
