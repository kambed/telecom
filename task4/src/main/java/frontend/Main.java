package frontend;

import backend.*;
import backend.Port;

import javax.sound.sampled.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] portsList = PortManager.getPortsNameList();
        System.out.println("Wybierz port:");
        for (String port : portsList) {
            System.out.println(port); //Display port list
        }
        System.out.print("Wybór: ");
        int port = (new Scanner(System.in)).nextInt(); //Choose port

        System.out.print("Nadajnik / Odbiornik: ");
        String no = (new Scanner(System.in)).nextLine(); //Choose if sender of receiver
        AudioFormat audioFormat = Audio.getAudioFormat(); //Initialize audio format
        switch (no) {
            case "Odbiornik" -> { //if receiver
                try {
                    SourceDataLine speakerLine = Audio.getTargetDataLineForPlay(audioFormat); //get speaker line
                    speakerLine.start(); //start speaker
                    try (ReceiverPort receiverPort = (ReceiverPort) PortManager.inicializePort(port, speakerLine)) { //inicialize port
                        System.out.println("Rozpoczęto nasłuchiwanie portu.");
                        while (true) { //waiting for transmission
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case "Nadajnik" -> { //if sender
                try {
                    System.out.println("Rozpoczęto transmisje mikrofonu.");
                    TargetDataLine microphoneLine = Audio.getTargetDataLineForRecord(audioFormat); //get microphone stream
                    microphoneLine.start(); //start microphone
                    byte[] data = new byte[microphoneLine.getBufferSize()];
                    try (Port senderPort = PortManager.inicializePort(port, null)) { //inicialize port
                        AudioTransmitter at = new AudioTransmitter(senderPort);
                        while (true) {
                            microphoneLine.read(data, 0, data.length); //read data from microphone
                            at.addToBuffer(data); //send to buffer
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            default -> System.out.println("Wybrano nieprawidłową opcję"); //incorrect option
        }
    }


}
