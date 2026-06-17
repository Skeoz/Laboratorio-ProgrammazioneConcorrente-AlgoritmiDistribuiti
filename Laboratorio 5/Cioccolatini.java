
public class Cioccolatini {
    public static void main(String[] args) {
        Scatola scatola = new Scatola();

        new Thread(new Pasticciere(scatola)).start();

        for (int i = 1; i <= 3; i++) {
            new Thread(new Mangiatore(scatola, i)).start();
        }
    }
}

class Scatola {
    private int quantita = 0;
    private final int P = 5;

    public synchronized void riempi() throws InterruptedException {
        while (quantita > 0) {
            wait();
        }
        System.out.println("Pasticciere: La scatola e' vuota. Inserisco " + P + " cioccolatini!");
        quantita = P;
        notifyAll();
    }

    public synchronized void prendi(int idMangiatore) throws InterruptedException {
        while (quantita == 0) {
            wait();
        }
        quantita--;
        System.out.println("Mangiatore " + idMangiatore + ": Ha preso 1 cioccolatino. Ne restano " + quantita);
        
        if (quantita == 0) {
            notifyAll(); 
        }
    }
}

class Pasticciere implements Runnable {
    private Scatola scatola;

    public Pasticciere(Scatola scatola) {
        this.scatola = scatola;
    }

    @Override
    public void run() {
        try {
            while (true) {
                scatola.riempi();
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Mangiatore implements Runnable {
    private Scatola scatola;
    private int id;

    public Mangiatore(Scatola scatola, int id) {
        this.scatola = scatola;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                scatola.prendi(id);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}