public class Elfo implements Runnable {
    int id;

    public Elfo(int id) {
        this.id = id;
    }

    public void run() {
        try {
            while (true) {
                // Simula il tempo di lavoro prima di incontrare un problema
                Thread.sleep((long) (Math.random() * 5000) + 2000);

                PoloNord.tornelloElfi.acquire();
                PoloNord.mutex.acquire();
                PoloNord.elfi++;
                System.out.println("Elfo " + id + ": Ho un problema. Siamo in " + PoloNord.elfi + " ad aspettare.");

                if (PoloNord.elfi == 3) {
                    System.out.println("Elfo " + id + ": Siamo in 3. Sveglio Babbo Natale!");
                    PoloNord.babboNatale.release();
                } else {
                    // Se non sono ancora in 3, fa entrare il prossimo elfo nel gruppo
                    PoloNord.tornelloElfi.release();
                }
                PoloNord.mutex.release();

                // Aspetta l'aiuto di Babbo Natale
                PoloNord.elfiSem.acquire();
                System.out.println("Elfo " + id + ": Ricevuto aiuto da Babbo Natale. Torno a lavorare.");

                PoloNord.mutex.acquire();
                PoloNord.elfi--;
                if (PoloNord.elfi == 0) {
                    System.out.println("Elfo " + id + ": Ultimo elfo del gruppo. Lascio passare gli altri.");
                    // L'ultimo elfo sblocca per il prossimo gruppo
                    PoloNord.tornelloElfi.release();
                }
                PoloNord.mutex.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}