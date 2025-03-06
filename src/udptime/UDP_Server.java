package udptime;

/**
 *
 * @author Monica Ciuchetti
 */

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class UDP_Server{

    public static void main(String[] args) {

        /* porta del server maggiore di 1024
         * oppure la porta 13 standard del protocollo Daytime
         */
        int port=2000;

        //oggetto Socket UDP
        DatagramSocket dSocket;
        //datagramma UDP ricevuto dal client
        DatagramPacket inPacket;
        //datagramma UDP di risposta da inviare
        DatagramPacket outPacket;
        //Buffer per il contenuto del segmento da ricevere
        byte[] bufferIn, bufferOut;
        //Testo dei messaggi in I/O
        String messageIn, messageOut;
        //Data e ora correnti
        Date d;

        try {
            //si crea il socket e si associa alla porta specifica
            dSocket = new DatagramSocket(port);
            System.out.println("Apertura porta in corso!");

            while(true){
                System.out.println("Server in ascolto sulla porta " + port + "!\n");
                bufferIn = new byte[256];

                //si crea un datagramma UDP in cui trasportare il buffer di lunghezza length
                inPacket = new DatagramPacket(bufferIn, bufferIn.length);

                //si ricevono i byte dal client e si blocca finchè arrivano i pacchetti
                dSocket.receive(inPacket);

                //si recupera l'indirizzo IP e la porta UDP del client
                InetAddress clientAddress = inPacket.getAddress();
                int clientPort = inPacket.getPort();

                //si stampa a video il messaggio ricevuto dal client
                messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println("SONO IL CLIENT " + clientAddress +
                        ":" + clientPort + "> " + messageIn);

                //si crea un oggetto Date con la data corrente
                d = new Date();
                //si crea il messaggio del server in uscita associandolo alla connessione aperta con il client
                messageOut = d.toString();
                bufferOut = messageOut.getBytes();
                //si crea un datagramma UDP in cui trasportare il messaggio di lunghezza length
                outPacket = new DatagramPacket(bufferOut, bufferOut.length, clientAddress, clientPort);
                //si invia il messaggio al client
                dSocket.send(outPacket);
                System.out.println("Risposta inviata!");
            }
        } catch (BindException e) {
            System.err.println("Errore porta già in uso");
        }
        catch (SocketException e) {
            System.err.println("Errore Socket");
        } catch (IOException e) {
            System.err.println("Errore di I/O");
        }

    }

}
