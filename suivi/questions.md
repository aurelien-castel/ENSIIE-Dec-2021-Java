Q : Pour les tests fonctionnels (notamment pour emprunter & restituer) : Doit-on juste vérifier le retour de la fonction ou faut-il vérifier si les clients ont bien reservé / rendu un document à travers les objects directement (incrémentation/décrémentation du nombre d'emprunt par exemple) ?

R : Oui, il faut vérifier que les client ont bien réservé  / rendu un document.

Q : (voir [tests_modifierClient.ods](tests_modifierClient.ods) et [graphe_modifierClient.svg](graphe_modifierClient.svg)) 
Pour les tests structurels, pensez-vous que tester les chemins all-nodes suffit ? En testant all-nodes, on est sûr de tester tout le code de la méthode. Je souhaiterai avoir votre avis sur cette partie.
R : Il faudra séparer les critères de couvertures, si vous allez réutiliser les chemins vous pouvez ajouter un code pour chaque chemin et indiquer par exemple les chemins que vous avez réutilisé pour couvrir un autre critère. 

Faut-il tester les chemins non faisables lors de tests structurels ?
R : On pourra pas automatiser les chemins non faisables, alors il faut juste les découvrir et rapporter (justifier la raison) les chemin non faisables.

Faut-il tester tous les arguments possibles ?
R : Les arguments qui répondent aux conditions identifiées après avoir fait l'analyse symbolique. Si vous n'arrivez pas à automatiser (coder) ces tests au moins il faut présenter l'analyse (c.à.d. les chemins et l'analyse symbolique qui correspond) et les tests que vous avez identifié en utilisant l'analyse symbolique (au moins un test par chaque critère).

Q : (tests fonctionnels) Concernant la remarque " Il manque le detail sur les pré-fixés et post-fixés ainsi que les entrées et les sorties (de manière explicite) pour chaque test. L'idée est de réutiliser votre analyse pour réaliser l'automatisation de manière plus directe." , pourriez vous donner un exemple concret de ce que vous attendez s'il vous plait ?

R : Pour les préfixés vous pouvez expliquer de manière générale que pour tous les tests il faut créer l'objet médiathèque, vous avez ajouté un clien sa catégorie ... etc. Par rapport au post-fixés, cela correspond aux conditions à vérifier après exécuter les cas d'utilisation, par exemple si l'emprunt est accepté cela implique que le document change d'état à emprunté, le nombre d'emprunts en cours du client est + 1, le nombre d'emprunts du doc est +1, que la fiche d'emprunt a été créée.. 
Par rapport aux entrés et sorties, la colonne résultat attendu correspond aux sorties et les classes d'équivalence donnent l'information pour les entrées. Alors, **en conclusion :** il faudra juste ajouter une explication des préfixes et les consitions à vérifier comme postfixés en cas de réussite de l'exécution du cas d'utilisation.

Q : Pour les tests fonctionnels, sommes nous supposé trouver des erreurs ?

R : Pas nécessairement, mais il faut être exhaustif.

Q : Pour les tests fonctionnels (emprunter et restituer), y a t-il suffisament de test ?

R : Mes commentaires se trouvent dans le fichier suivi/readme.
