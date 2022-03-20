package pl.tele.frontend;

import pl.tele.backend.Port;
import pl.tele.backend.PortManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String[] portsList = PortManager.getPortsNameList();
        System.out.println("Wybierz port:");
        for (String port : portsList) {
            System.out.println(port);
        }
        System.out.print("Wybór: ");
        try (Port port = PortManager.getPort(s.nextInt())) {
            System.out.println("Komunikacja rozpoczęta, możesz wysłać wiadomość i/lub oczekiwać na przychodzącą:");
            while (true) {
                port.send((new Scanner(System.in)).nextLine());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}