public class BabboNatale implements Runnable {
    public void run() {
        try {
            while (true) {
                PoloNord.babboNatale.acquire();
                PoloNord.mutex.acquire();

                if (PoloNord.renne == 9) {
                    System.out.println("Babbo Natale: 9 renne tornate! Preparo la slitta. (Priorita' massima)");
                    PoloNord.renne = 0; // Azzera il contatore per il prossimo anno
                    for (int i = 0; i < 9; i++) {
                        PoloNord.renneSem.release();
                    }
                    PoloNord.mutex.release();
                    
                    Thread.sleep(3000);
                    System.out.println("Babbo Natale: Regali distribuiti. Torno a dormire.");
                } else if (PoloNord.elfi == 3) {
                    System.out.println("Babbo Natale: 3 elfi hanno bisogno di me. Li aiuto.");
                    for (int i = 0; i < 3; i++) {
                        PoloNord.elfiSem.release();
                    }
                    PoloNord.mutex.release();
                    
                    Thread.sleep(1000);
                    System.out.println("Babbo Natale: Problema degli elfi risolto. Torno a dormire.");
                } else {
                    PoloNord.mutex.release();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}