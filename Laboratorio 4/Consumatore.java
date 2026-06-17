import java.io.*;
import java.net.*;

public class Consumatore {
    public static void main(String[] args) throws Exception {
        // Da cambiare ===> 8082 per ServerIllimitato e 8081 per ServerLimitato
        Socket s = new Socket("localhost", 8082);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        out.print("consumer\n");
        out.flush();
        String risposta = in.readLine();
        
        if ("okcons".equals(risposta)) {
            System.out.println("In attesa della stringa...");
            System.out.println("Stringa consumata: " + in.readLine());
        } else {
            System.out.println("Errore protocollo: " + risposta);
        }
        s.close(); 
    }
}