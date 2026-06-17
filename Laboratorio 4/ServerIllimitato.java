import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class ServerIllimitato {
    private static final LinkedList<String> fifo = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8082);
        System.out.println("Server ILLIMITATO in ascolto su porta 8082...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
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