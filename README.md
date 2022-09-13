## Progetto universitario 'markslist' per Sistemi Informativi sul Web
### Descrizione
Il progetto non stabilisce una entry innovativa nel mondo del web, ma una possibile alternativa agli shop online, offrendo una semplicità sostanziale. L'applicazione si ispira all'omonimo 'craigslist', un sito di vendita prettamente utilizzato negli Stati Uniti che offre un'interfaccia ricca e diretta nell'ambito di interesse. Sebbene questo sia disponibile in Italia, il suo utilizzo è marginale o del tutto sconosciuto. Nel tentativo di proporre un sistema simile e anche più semplice, 'markslist' propone la landing page direttamente sulla prima pagina degli annunci. Il progetto resta un prototipo e in quanto tale non supporta molte delle funzionalità di filtraggio, di riordinamento e così via, basilari negli shop odierni.
### Casi d'uso
Il sistema prevede i seguenti casi d'uso:
#### UC1
##### Consultazione Rapida Inserzioni - Attore primario: Chiunque
1. L'utente ha intenzione di consultare le inserzioni attive nel sistema.
2. L'utente accede alla pagina principale del sistema, senza alcuna necessità di autenticazione. Il sistema mostra tutte le inserzioni dalla più recente alla meno recente.

#### UC2
##### Consultazione dei dettagli di una specifica inserzione attiva - Attore primario: Chiunque
1. L'utente ha intenzione di consultare una specifica inserzione attiva nel sistema.
2. L'utente accede alla pagina principale del sistema, senza alcuna necessità di autenticazione. Il sistema mostra tutte le inserzioni dalla più recente alla meno recente.
3. L'utente sceglie l'inserzione di interesse. Il sistema mostra i dettagli dell'inserzione.

#### UC3
##### Inserimento di una nuova inserzione - Attore primario: Utente Iscritto
1. L'utente iscritto ha intenzione di pubblicare una nuova inserzione.
2. L'utente iscritto si autentica mediante username e password. Il sistema mostra la pagina principale.
3. L'utente sceglie l'attività 'Crea Inserzione'. Il sistema mostra un form da compilare.
4. L'utente compila i campi e aggiunge una immagine. Il sistema registra i dati e mette l'inserzione in attesa di approvazione.

#### UC4
##### Approvazione di una inserzione in attesa - Attore primario: Amministratore
1. L'amministratore ha intenzione di approvare una inserzione in attesa.
2. L'amministratore si autentica mediante username e password. Il sistema mostra la pagina principale.
3. L'amministratore sceglie l'attività 'Pannello di Amministrazione'. Il sistema mostra l'elenco delle attività di amministrazione disponibili.
4. L'amministratore sceglie l'attività 'Visualizza/Approva inserzioni'. Il sistema mostra l'elenco delle inserzioni approvate ed in attesa.
5. L'amministratore sceglie l'attività 'Approva Inserzione' dell'inserzione di interesse. Il sistema registra l'informazione e rende disponibile la visibilità dell'insezione di riferimento al pubblico.

#### UC5
##### Creazione di un nuovo amministratore - Attore primario: Amministratore
1. L'amministratore ha intenzione di approvare una inserzione in attesa.
2. L'amministratore si autentica mediante username e password. Il sistema mostra la pagina principale.
3. L'amministratore sceglie l'attività 'Pannello di Amministrazione'. Il sistema mostra l'elenco delle attività di amministrazione disponibili.
4. L'amministratore sceglie l'attività 'Crea nuovo amministratore' . Il sistema mostra un form da compilare.
5. L'amministratore compila il form, compresa la password. Il sistema registra le informazioni e crea un nuovo amministratore. Il sistema mostra i dati appena registrati all'amministratore che ha effettuato l'operazione.

#### UCX
##### Visualizzazione contenuti digitali, 'Login' e 'Registrazione' sono casi d'uso immediati.

