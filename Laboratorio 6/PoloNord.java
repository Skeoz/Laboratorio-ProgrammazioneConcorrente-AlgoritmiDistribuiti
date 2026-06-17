import java.util.concurrent.Semaphore;

public class PoloNord {
    // Variabili e semafori condivisi e accessibili da tutti
    public static int elfi = 0;
    public static int renne = 0;

    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore babboNatale = new Semaphore(0);
    public static Semaphore renneSem = new Semaphore(0);
    public static Semaphore elfiSem = new Semaphore(0);
    public static Semaphore tornelloElfi = new Semaphore(1);

    public static void main(String[] args) {
        System.out.println("Polo Nord operativo. Babbo Natale dorme.");

        new Thread(new BabboNatale()).start();

        for (int i = 1; i <= 9; i++) {
            new Thread(new Renna(i)).start();
        }

        int numeroElfi = 10; // Puoi variare questo numero a piacimento
        for (int i = 1; i <= numeroElfi; i++) {
            new Thread(new Elfo(i)).start();
        }
    }
}