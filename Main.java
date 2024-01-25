package middleclicker.io;

import java.util.Scanner;

public class Main {

    private static int kFactor = 32,
            cFactor = 400,
            minimumElo = 100,
            initialElo = 1000,
            currentElo = initialElo;

    public static void main(String[] args) {
        int opponentElo, matchStatus;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            // e.g. Win 1000 or Lose 1000
            opponentElo = Integer.parseInt(input.split(" ")[1]);
            matchStatus = status(input.split(" ")[0]);

            elo(currentElo, opponentElo, matchStatus);
        }
    }

    private static void elo(int personalElo, int opponentElo, int matchStatus) {
        double expected_A = 1 / (1 + Math.pow(10d, (double)(opponentElo-personalElo)/cFactor)); // Expected match result
        float actual_A = 0f; // Actual match result
        if (matchStatus == 0) {
            actual_A = 0f;
        } else if (matchStatus == 2) {
            actual_A = 1f;
        } else {
            actual_A = 0.5f;
        }

        currentElo = (int) Math.floor(personalElo + kFactor*(actual_A - expected_A));

        if (currentElo < minimumElo) {
            currentElo = 100;
        }

        if (currentElo >= personalElo) { // Current Elo: 1016 (+16)
            System.out.println("Current Elo: " + currentElo + " (+" + (currentElo-personalElo) + ")");
        } else {
            System.out.println("Current Elo: " + currentElo + " (-" + (personalElo-currentElo) + ")");
        }
    }

    private static int status(String status) {
        if (status.equalsIgnoreCase("win")) {
            return 2;
        }
        if (status.equalsIgnoreCase("lose")) {
            return 0;
        }
        return 1; // Draw
    }

}