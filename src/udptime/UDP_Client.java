
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
package udptime;

/**
 *
 * @author Monica Ciuchetti
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

    public class UDP_Client {

        public static void main(String[] args) {
            //numero di porta
            int port = 2000;
            //indirizzo del server
            InetAddress serverAddress;
            //socket UDP
            DatagramSocket dSocket;
            //Datagramma UDP con la richiesta da inviare al server
            DatagramPacket outPacket;
            //Datagramma UDP di risposta ricevuto dal server
            DatagramPacket inPacket;

            //buffer per i dati da inviare
            byte[] buffer;
            //messaggio di richiesta
            String message="RICHIESTA DATA E ORA";
            //messaggio di risposta
            String response;

            try {
                /*
                 * si usa getLocalHost() se il server è sulla stessa macchina locale
                 * altrimenti si deve conoscere l'IP del server
                 */
                serverAddress = InetAddress.getLocalHost();

                System.out.println("Indirizzo del server trovato!");
                dSocket = new DatagramSocket();

                //si prepara il datagramma con i dati da inviare
                outPacket = new DatagramPacket(message.getBytes(), message.length(), serverAddress, port);

                //si inviano i dati
                dSocket.send(outPacket);

                //si prepara il buffer di ricezione
                buffer = new byte[256];
                //e il datagramma UDP per ricevere i dati del buffer
                inPacket = new DatagramPacket(buffer, buffer.length);

                //si accetta il datagramma di risposta
                dSocket.receive(inPacket);

                //si estrae il messaggio
                response = new String(inPacket.getData(), 0, inPacket.getLength());

                System.out.println("Il server ha inviato i dati!");
                System.out.println("Data e ora del server: " + response);

                //si chiude la comunicazione
                dSocket.close();
                System.out.println("Comunicazione chiusa!");
            } catch (UnknownHostException e) {
                System.err.println("Errore DNS");
            } catch (SocketException e){
                System.err.println("Errore Socket");
            }
            catch (IOException e) {
                System.err.println("Errore di I/O");
            }

        }


}
