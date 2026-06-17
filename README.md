# Programmazione Concorrente e Algoritmi Distribuiti - Laboratori Java

Questo repository raccoglie i progetti e le esercitazioni pratiche sviluppate per il corso di **Programmazione Concorrente e Algoritmi Distribuiti (PCAD)**. Gli elaborati si concentrano sulla risoluzione di problemi classici di sincronizzazione e comunicazione tra processi cooperanti ed eseguiti in modo concorrente, utilizzando sia i meccanismi di sincronizzazione nativi di Java (Monitor/Sincronizzazione primitiva) sia costrutti avanzati (Semafori e Socket).

## Progetti e Architetture Implementate

Il repository è strutturato in quattro moduli principali, ognuno mirato a risolvere specifiche sfide del calcolo concorrente:

### 1. Architettura Client-Server Produttore-Consumatore (TCP Sockets)
Un sistema distribuito basato su socket TCP per la gestione di una coda FIFO condivisa in ambiente multithreading.
* **ServerIllimitato & ServerLimitato**: Gestiscono le richieste concorrenti dei client instanziando un `ClientHandler` su thread dedicati. La sincronizzazione della coda è protetta da monitor gestiti tramite blocchi `synchronized`, `wait()` e `notifyAll()`.
* **Gestione del Buffer**: La versione limitata applica una politica di blocco (*bounded buffer*) con una capacità massima di 3 elementi, costringendo il produttore ad attendere se la coda è piena.
* **Client (`Produttore` e `Consumatore`)**: Comunicano con il rispettivo server tramite stringhe di protocollo dedicate (`producer`/`okprod`, `consumer`/`okcons`) sulla porta 8082 o 8081.

### 2. Il Problema dei Cioccolatini (Sincronizzazione via Monitor)
Implementazione del pattern Produttore-Consumatore applicato a scenari di coordinazione esclusiva.
* Modellazione di una risorsa condivisa (`Scatola`) con mutua esclusione nativa (`synchronized`).
* **Logica di Sincronizzazione**: Un thread `Pasticciere` attende che la scatola sia completamente vuota (`quantita == 0`) prima di inserire un nuovo vassoio di cioccolatini, mentre più thread `Mangiatore` consumano le risorse uno alla volta, notificando il pasticciere solo quando la scatola si svuota.

### 3. Problema della Piscina (Prevenzione del Deadlock tramite Semafori)
Un esercizio pratico sulla gestione e prevenzione dello stallo (Deadlock) utilizzando la classe `java.util.concurrent.Semaphore`.
* **Scenario**: 10 clienti concorrenti devono accedere a risorse limitate (2 spogliatoi e 1 armadietto).
* **Risoluzione del Deadlock**: L'ordine di acquisizione originale generava un'attesa circolare distruttiva. Il problema è stato risolto applicando la tecnica dell'**inversione dell'ordine di acquisizione** delle risorse: imponendo a tutti i thread di acquisire la chiave dell'armadietto *prima* di quella dello spogliatoio, si spezza definitivamente l'attesa circolare.

### 4. Il Problema di Babbo Natale (Sincronizzazione di Gruppo Avanzata)
Implementazione del celebre problema di concorrenza di Allen Downey per la coordinazione di thread con priorità asimmetrica.
* **Priorità delle Renne**: Al ritorno di tutte e 9 le renne dalle vacanze, Babbo Natale viene svegliato con priorità massima per preparare la slitta e consegnare i regali.
* **Sincronizzazione degli Elfi**: Gli elfi lavorano autonomamente ma, se incontrano problemi, devono presentarsi da Babbo Natale rigorosamente in gruppi di esattamente 3 elementi.
* **Uso del Tornello (Turnstile)**: Implementazione di un semaforo di sbarramento (`tornelloElfi`) per bloccare l'accesso ad altri elfi quando un gruppo di tre ha già occupato la sala d'attesa, evitando condizioni di stallo e garantendo che Babbo Natale aiuti solo tre elfi alla volta.

## Competenze e Costrutti Concorrenti Utilizzati

* **Multithreading**: Creazione e ciclo di vita dei thread tramite l'interfaccia `Runnable`.
* **Monitor Nativi**: Controllo della concorrenza a basso livello tramite keyword `synchronized`, metodi di sospensione `wait()` e risveglio di massa `notifyAll()`.
* **Semafori**: Uso di `java.util.concurrent.Semaphore` sia per il conteggio delle risorse disponibili (mutua esclusione e allocazione), sia come barriere di sincronizzazione (meccanismo a tornello).
* **Networking**: Comunicazione client-server inter-processo su rete locale tramite `ServerSocket` e `Socket`.

## Autori
* **Andrea Peri** (Matricola: 5415544)
