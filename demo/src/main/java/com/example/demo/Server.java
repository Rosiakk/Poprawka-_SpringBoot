package com.example.demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    PrintWriter writer;
    public Server() throws IOException {
        serverSocket = new ServerSocket(3000);
    }

    public void listen() throws IOException {
        System.out.println("Server started");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected");

        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
        logInfo(socket);
        socket.close();
        System.out.println("Client disconnected");

    }

    private String[] parseCommand(String command){
        return command.split(" ");
    }

    public void logInfo (Socket socket){

        try (
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true)
        ) {
            output.println("Podaj login:");
            String username = input.readLine();
            System.out.println("Username: " + username);
            output.println("Podaj haslo:");
            String password = input.readLine();
            System.out.println("Password: " + password);

            boolean admin = false;

            if (username.equals("Admin") && password.equals("1234")) {
                output.println("Welcome, " + username);
                output.println("Ban/dzban");
                admin = true;

            } else {
                output.println("Login Failed");
                output.println("chuj nie admin!");
            }

            boolean loop = true;

            if (admin) {
                while (loop) {
                    String[] command = parseCommand(input.readLine());

                    switch (command[0]) {
                        case "ban":
                            AuthController.BanToken(command[1]);
                            System.out.println("Zbanowano token: " + command[1]);
                            output.println("Zbanowano token: " + command[1]);
                            break;
                        case "exit":
                            loop = false;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
