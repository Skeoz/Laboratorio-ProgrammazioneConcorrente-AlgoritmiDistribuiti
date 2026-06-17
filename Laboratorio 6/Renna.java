public class Renna implements Runnable {
    int id;

    public Renna(int id) {
        this.id = id;
    }

    public void run() {
        try {
            while (true) {
                // Simula il tempo passato in vacanza
                Thread.sleep((long) (Math.random() * 10000) + 5000);
                System.out.println("Renna " + id + ": Tornata dalle vacanze nel pacifico meridionale.");

                PoloNord.mutex.acquire();
                PoloNord.renne++;
                if (PoloNord.renne == 9) {
                    System.out.println("Renna " + id + ": Sono l'ultima renna. Sveglio Babbo Natale!");
                    PoloNord.babboNatale.release();
                }
                PoloNord.mutex.release();

                // Attende di essere attaccata alla slitta
                PoloNord.renneSem.acquire();
                System.out.println("Renna " + id + ": Attaccata alla slitta! In consegna.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}