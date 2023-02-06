Ce fichier contient et contiendra des remarques de suivi sur votre
projet tant sur la conception que sur l'automatisation de tests. Un nouveau
suivi est indiqué par une nouvelle section datée.

Certaines remarques demandent des actions de votre part, vous les
repérerez par une case à cocher.

- []  Action (à réaliser) 

Merci de nous indiquer que vous avez pris en compte la remarque en
cochant la case. N'hésitez pas à écrire dans ce fichier et à nous
exposer votre point de vue.

- [x] Action (réalisée)
    - RÉPONSE et éventuelles remarques de votre part, 


15/11/2021 - Suivi 1 :
----
- Bon démarrage, bravo ! Vous avez bien commencé à travailler sur le projet. Il y a certains suggestions pour améliorer votre travail.
- [x] Modifiez le fichier readme principal afin d'ajouter les nom et prénoms des membres de l'équipe.
- Remarques sur les fichiers suivi/*.ods :
    - [x] Il manque le detail sur les pré-fixés et post-fixés ainsi que les entrées et les sorties (de manière explicite) pour chaque test. L'idée est de réutiliser votre analyse pour réaliser l'automatisation de manière plus directe.
    - [x] Suggestion : ajoutez une colonne aux tables qui indique le fichier de test et la méthode associé a chaque test afin que je puisse mieux regarder. 

- Remarque test fonctionnel "emprunter document" :
    - [x] Il manque de test pour les limites de nombre maximal d'emprunts pour le client selon sa catégorie. Testez aussi pour le première emprunt, quand le client à zéro emprunts. 
    - [x] Il manque compléter les tests pour les résultats des cas de succès de l'emprunt (tests 3.A, 3.B, 3.C) : vérifier la durée ainsi que le tarif de l'emprunt (fiche emprunt) qui dépend du type de document et la catégorie du client
    - [x] De plus, il faut vérifier le nombre d'emprunts du client et l'état du document qui vient d'être emprunté ("emprunté").
    - [x] Il manque un cas où le client change de catégorie.
    - [x] Testez un client qui n'a pas renouvelé sa situation "tarif réduite" ou "abonné".
    
- Remarque test fonctionnel "restituer document" :
    - [x] Pour les tests des lignes 38, 39 et 40, il faut aussi vérifier le nombre d'emprunts du client concerné, l'état du document changé à "pas emprunté",  et l'état de la fiche d'emprunt.
    - [x] Testez les limites : quand le client a réalisé le nombre maximal et minimal d'emprunts.
    - [x] Testez les limites de dates de restitution. Pour cela, exécutez la méthode qui met à jour les états de fiches d'emprunts (dépassés ou pas). 
    - [x] Testez un client qui change de catégorie.
    - [x] Testez un client qui n'a pas renouvelé sa situation "tarif réduite" ou "abonné".

- Remarque test fonctionnel "mettre consultable un document" :
    - [x] Test 1 : si le document est inexistant, il faut rien faire (regardez le diagramme de séquences).
    - [x] Test 2 : testez l'état du document concerné.
    
- Code jUnit remarque générale :
    - Bon travail, je vois que vous avez bien avancé sur l'automatisation.
    - [x] Suggestion : dans les commentaires de chaque test, vous pouvez ajouter le code de test qui vous avez ajouté dans les fichiers .ods.

- Analyse de résultats :
    - [x] Il manque un rapport des résultats obtenues après l'exécution des tests, c'est-à-dire quels sont les tests qui ont échoués et qui ont passés. Et un analyse de pourquoi vous croyez que ces tests ont échoués.

01/12/2021 - Suivi 2 :
----
- Vous avez travaillé sur les fichier d'analyse qui correspond aux tests fonctionnels. Ce n'est pas encore fini mais il y a un avancement, bon travail.
- Je n'ai trouvé aucun fichier par rapport aux tests structurels, alors rien à signaler par rapport à ça.
- [x] Téléversez les fichiers qui correspondent aux autres types d'analyse de tests. Je regarderai d'abord l'analyse et après l'automatisation (code junit).

07/12/2021 - Suivi 3 :
----
- Test fonctionnels :
    - cas d'utilisation "emprunter document" :
        - [x] Pour les tests CT7.1, CT7.2, CT7.3, la colonne résultat doit indiquer ce qu'on attend après la réussite du cas d'utilisation.
        - [x] Pour les tests CT7.1, CT7.2, CT7.3, l'objectif de "différent bornes" correspond aux tests au limites ? Si oui, indiquez borne vide (0), borne plein (max) et autre. 
        - [x] Test combinatoire : 3 catégories du client * 3 types de documents. Ce n'est pas grave si l'automatisation n'est pas complet mais l'analyse doit être exhaustif. Vous avez déjà considéré les types de document mais pas les catégories du client.
        
    - cas d'utilisation "restituer document" :
        - [x] Pour les tests CT23.1, CT23.2, CT23.3, la colonne résultat doit indiquer ce qu'on attend après la réussite du cas d'utilisation.
        - [x] Pour les tests CT23.1, CT23.2, CT23.3, l'objectif de "différent bornes" correspond aux tests au limites ? (Pareil que le cas précédent)
        - [x] Pour les tests CT24.1, CT24.2, CT24.3, la colonne résultat doit indiquer ce qu'on attend après la réussite du cas d'utilisation, particulièrement il faut vérifier l'état du client (client peut emprunter si c'était le seul document à rendre en retard sinon l'état ne change pas). 
        - [x] Pareil que le cas précedents par rapport aux **tests combinatoires**.
    
    - cas d'utilisation "mettre consultable" :
        - [x] Je ne comprends pas la différence du test CT1 et CT2, je pense que l'objectif est le même, il faut décrire le post-fixé à attendre, par exemple que l'état du doc est consultable. 
        - [x] Il faut ajouter une classe d'équiv. pour un document emprunté.
        - [x] Il faut ajouter une classe d'équiv. pour un document déjà consultable, le résultat ne change pas l'état du document (pas d'exception à lever).
        - [x] Considérez les 3 types de documents.
    
- Test structurels (flot de contrôle) :
    - méthode "modifier client" :
        - [x] remarque générale : l'analyse symbolique prend juste de valeurs symboliques tel que nom0, prenom0, etc (pas de valeurs tel que 'nomClient'..). Les conditions que vous allez étudier donnent les caractéristiques qui doivent accomplir le variables pour parcourir le chemin, par exemple le nom = null.
        - [x] suggestion : créez une feuille par chaque critère de couverture, surtout pour l'analyse symbolique.
        - critères all-node est OK : 
            - [x] vous pouvez ajouter les codes aux trois chemins (ex. ch1, ch2, ch3)
        - critères all-arcs :
            - remarque générale : n'est pas nécesaire de mettre les arcs, on peut les exprimer juste comme une séquence de noeuds.
            - [x] réutilisez les 3 chemins précédents.
            - [x] ajoutez un chemin pour couvrir l'arc (1169, sortie)
        - critères all-paths :
            - [x] il y a 5 conditions (if) à couvrir, regardez tous les combinaisons possibles, par exemple quand la première condition (le client n'existe pas) est vrai un seul chemins est possible, sinon 48 autres chemins sont possibles.
    - méthode "emprunter document" :
        - Rien à regarder.
    - méthode "chercher emprunt" :
        - Rien à regarder.

- Test structurels (flot de données) :
    - [x] graphe est ok pour la méthode "modifier client", il manque l'analyse.

- Test mutationnel (emprunter) :
    - [x] les mutants sont ok, il manque évaluer les tests que vous avez déjà implémenté (mettez juste le code de test qui vous avez identifié en utilisant la technique test fonctionnel ou structurel) et indiquer si le test tue ou pas le mutant. Choisissez plus ou moins 10 tests avec différents objectifs.