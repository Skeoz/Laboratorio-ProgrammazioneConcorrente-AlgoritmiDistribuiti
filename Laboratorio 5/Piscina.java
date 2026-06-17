import java.util.concurrent.Semaphore;


/*
 * MODIFICA PER EVITARE IL DEADLOCK ===> PUNTO 3
 * * PROBLEMA: L'ordine originale (prima spogliatoio, poi armadietto) crea 
 * un'attesa circolare. Chi entra occupa gli spogliatoi aspettando 
 * gli armadietti, mentre chi ha finito di nuotare tiene in ostaggio gli 
 * armadietti aspettando gli spogliatoi. 
 * * SOLUZIONE: Invertire l'acquisizione all'ingresso (prima armadietto, 
 * poi spogliatoio). Questo spezza il ciclo: nessuno può bloccare uno 
 * spogliatoio se non si è già garantito un armadietto.
 */

public class Piscina {
    static Semaphore spogliatoi;
    static Semaphore armadietti;

    public static void main(String[] args) {
        int numSpogliatoi = 2; 
        int numArmadietti = 1; 
        int numClienti = 10;

        spogliatoi = new Semaphore(numSpogliatoi);
        armadietti = new Semaphore(numArmadietti);

        System.out.println("Piscina aperta: " + numSpogliatoi + " spogliatoi, " + numArmadietti + " armadietti.");

        for (int i = 1; i <= numClienti; i++) {
            new Thread(new Cliente(i)).start();
        }
    }

    static class Cliente implements Runnable {
        int id;

        Cliente(int id) { this.id = id; }

        private void simulaAzione(String azione, long tempo) {
            System.out.println("Cliente " + id + ": " + azione);
            try { Thread.sleep(tempo); } catch (InterruptedException e) { }
        }

        @Override
        public void run() {
            try {
                armadietti.acquire();
                System.out.println("Cliente " + id + ": (b) Ha preso la chiave di un armadietto");

                spogliatoi.acquire();
                System.out.println("Cliente " + id + ": (a) Ha preso la chiave di uno spogliatoio");

                simulaAzione("(c) Si cambia nello spogliatoio", 1000);
                System.out.println("Cliente " + id + ": (d) Libera lo spogliatoio (fisicamente)");
                simulaAzione("(e) Mette i suoi vestiti nell'armadietto", 500);

                spogliatoi.release();
                System.out.println("Cliente " + id + ": (f) Rida la chiave dello spogliatoio");

                simulaAzione("(g) Nuota...", 3000);

                spogliatoi.acquire();
                System.out.println("Cliente " + id + ": (h) Ha ripreso la chiave dello spogliatoio");

                simulaAzione("(i) Recupera i suoi vestiti nell'armadietto", 500);
                simulaAzione("(j) Si riveste nello spogliatoio", 1000);
                System.out.println("Cliente " + id + ": (k) Libera lo spogliatoio (fisicamente)");

                spogliatoi.release();
                armadietti.release();
                System.out.println("Cliente " + id + ": (l) Rida le chiavi. Esce.");

            } catch (InterruptedException e) {
                System.err.println("Errore per il cliente " + id);
            }
        }
    }
}