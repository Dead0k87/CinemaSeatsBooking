package com.company;


import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cinema implements Runnable {

    private Socket socket;
    List<Integer> list = Stream.iterate(0, n -> n = null).limit(81).collect(Collectors.toList());

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Session started: " + socket.getRemoteSocketAddress());
        try (Scanner scanner = new Scanner(socket.getInputStream());
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        ) {
            if (scanner.hasNextLine()) {
                String strFromClient = scanner.nextLine();
                Integer number = Integer.parseInt(strFromClient);
                if (list.contains(number)) {
                    writer.println("Your desired seat# " + strFromClient + " bought out by someone else" + ", select empty one: " + showList(list));
                } else {
                    list.set(number, number);
                    writer.println("Your desired seat# " + strFromClient + " successfully booked!");
                }
                System.out.println("Session finished: " + socket.getRemoteSocketAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String showList(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n");
        for (int i = 1; i < list.size(); i++) {
            sb.append(list.get(i)).append(" | ");
            if (i % 10 == 0) {
                sb.append("\n\r");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        try (ServerSocket serverSocket = new ServerSocket(8181)) {
            System.out.println("server started...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("new socket connected: " + socket.getRemoteSocketAddress());
                cinema.setSocket(socket);
                new Thread(cinema).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

