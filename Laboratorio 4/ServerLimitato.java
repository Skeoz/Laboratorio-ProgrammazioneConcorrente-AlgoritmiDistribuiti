import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class ServerLimitato {
    private static final LinkedList<String> fifo = new LinkedList<>();
    private static final int MAX_SIZE = 3; 

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081); 
        System.out.println("Server LIMITATO (max " + MAX_SIZE + ") in ascolto su porta 8081...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandlerLimitato(clientSocket)).start();
        }
    }

    static class ClientHandlerLimitato implements Runnable {
        private Socket socket;

        public ClientHandlerLimitato(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String type = in.readLine();
                
                if ("producer".equals(type)) {
                    out.print("okprod\n");
                    out.flush();
                    String msg = in.readLine();
                    if (msg != null) {
                        synchronized (fifo) {
                            while (fifo.size() >= MAX_SIZE) {
                                fifo.wait(); 
                            }
                            fifo.addLast(msg);
                            fifo.notifyAll(); 
                        }
                        out.print("okins\n");
                        out.flush();
                    }
                } else if ("consumer".equals(type)) {
                    out.print("okcons\n");
                    out.flush();
                    String msg;
                    synchronized (fifo) {
                        while (fifo.isEmpty()) {
                            fifo.wait(); 
                        }
                        msg = fifo.removeFirst();
                        fifo.notifyAll(); 
                    }
                    out.print(msg + "\n");
                    out.flush();
                }
            } catch (Exception e) {
                System.err.println("Errore: " + e.getMessage());
            }
        }
    }
}