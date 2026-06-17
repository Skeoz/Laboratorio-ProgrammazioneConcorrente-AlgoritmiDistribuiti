import java.io.*;
import java.net.*;

public class Produttore {
    public static void main(String[] args) throws Exception {
        // Da cambiare ===> 8082 per ServerIllimitato e 8081 per ServerLimitato
        Socket s = new Socket("localhost", 8082);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        out.print("producer\n");
        out.flush();
        String risposta = in.readLine();
        
        if ("okprod".equals(risposta)) {
            System.out.println("Server pronto. Invio la stringa...");
            out.print("Stringa_di_test_generica\n");
            out.flush();
            System.out.println("Conferma dal server: " + in.readLine()); 
        } else {
            System.out.println("Errore protocollo: " + risposta);
        }
        s.close();
    }
}