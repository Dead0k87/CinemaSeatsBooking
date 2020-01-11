package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", 8181));
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String userInputString = userInput();
            writer.println(userInputString);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String userInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter seat number you want (1 - 80)");

        while (true) {
            try {
                String userStr = reader.readLine();
                int num = 0;
                try {
                    num = Integer.parseInt(userStr);
                } catch (NumberFormatException e) {
                    System.out.println("Your input has to be a number value");
                    continue;
                }
                if (!numberIsInRange1to80(num)) {
                    System.out.println("Number is not between 1 and 80 or equals 0");
                    continue;
                } else {
                    reader.close();
                    return userStr;

                }
            } catch (IOException e) {
            }

        }
    }


    private static boolean numberIsInRange1to80(Integer num) {
        return !(num < 0 || num > 80 || num == 0);
    }
}

