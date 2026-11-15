# TP3 – Mesure du niveau de glycémie (Android)

**Matière :** Développement Mobile
**Filière :** LSI3 – Faculté des Sciences de Sfax (FSS)
**Année universitaire :** 2026-2027
**Enseignante du cours :** Sahar Dammak
**Enseignante TP :** Fourati Héla
**Réalisé par :** Rihab Tarchi

---

## 1. Description

Application Android qui permet à un patient de vérifier si son niveau de glycémie est **normal**, **trop bas** ou **trop élevé**, à partir de :

- son **âge** (choisi avec un `SeekBar`, de 0 à 120 ans) ;
- la **valeur mesurée** en mmol/L (saisie dans un `EditText`) ;
- son état : **à jeun ou non** (deux `RadioButton` dans un `RadioGroup`).

Un clic sur le bouton **Consulter** affiche le résultat dans un `TextView`.

## 1 bis. Lien avec le TP2

Ce TP3 est une **amélioration du TP2**. Le TP2 a permis de concevoir l'interface graphique de l'application (layout `activity_main.xml`).
Dans ce TP3, on **reprend le même projet** et on lui ajoute la logique :

| TP2 | TP3 (cette version) |
|---|---|
| Création de l'interface (SeekBar, RadioGroup, EditText, Button, TextView) | Interface conservée |
| Aucune logique : les vues ne réagissent pas | Récupération des vues avec `findViewById()` dans `init()` |
| Âge affiché statiquement | Âge mis à jour en direct par le listener du `SeekBar` |
| Bouton sans action | Clic sur **Consulter** : calcul et affichage du résultat |

## 2. Objectifs pédagogiques

- Implémenter la logique d'une activité Android (`MainActivity`).
- Récupérer les vues avec `findViewById()` dans une méthode `init()`.
- Implémenter un **listener explicite** sur un `SeekBar` en redéfinissant `onProgressChanged()` de `SeekBar.OnSeekBarChangeListener`.
- Implémenter un **listener explicite** sur un bouton en redéfinissant `onClick()` de `View.OnClickListener`.
- Comprendre la différence entre listener **implicite** (`android:onClick` dans le XML) et **explicite** (code Java).

## 3. Règles de calcul

### À jeun (avant les repas)

| Catégorie | Intervalle normal (mmol/L) |
|---|---|
| Adultes et enfants de plus de 13 ans | 5,0 à 7,2 |
| Enfants de 6 à 12 ans | 5,0 à 10,0 |
| Enfants de moins de 6 ans | 5,5 à 10,0 |

### Après les repas

| Catégorie | Seuil normal |
|---|---|
| Adultes et enfants | Moins de 10,5 mmol/L |

### Messages affichés

- `niveau de glycémie est normale`
- `niveau de glycémie est trop bas`
- `niveau de glycémie est trop élevée`

## 4. Structure du projet

```
app/src/main/
├── java/com/example/tarchi_rihab_mesure_glycemie/
│   └── MainActivity.java
└── res/layout/
    └── activity_main.xml
```

### IDs utilisés dans `activity_main.xml`

| Vue | Type | ID |
|---|---|---|
| Texte de l'âge | `TextView` | `textAge` |
| Choix de l'âge (max = 120) | `SeekBar` | `sbage` |
| Réponse « Oui » | `RadioButton` | `rbtoui` |
| Réponse « Non » | `RadioButton` | `rbtnon` |
| Valeur mesurée | `EditText` | `etValeur` |
| Bouton | `Button` | `btnConsulter` |
| Résultat | `TextView` | `msg` |

## 5. Fonctionnement du code

1. `onCreate()` charge le layout puis appelle `init()`.
2. `init()` récupère toutes les vues avec `findViewById()`.
3. Le listener du `SeekBar` met à jour la variable `age` et le texte `Votre âge : X ans` à chaque changement.
4. Le listener du bouton appelle `consulterGlycemie()`, qui :
    - vérifie que la valeur n'est pas vide (sinon un `Toast` s'affiche) ;
    - détermine l'intervalle selon l'âge et l'état (à jeun ou non) ;
    - affiche le résultat dans le `TextView` `msg`.

## 6. Exemples de tests

| Âge | À jeun | Valeur (mmol/L) | Résultat attendu |
|---|---|---|---|
| 53 | Oui | 6,0 | normale |
| 53 | Oui | 4,2 | trop bas |
| 53 | Oui | 8,0 | trop élevée |
| 8 | Oui | 9,0 | normale |
| 4 | Oui | 5,2 | trop bas |
| 30 | Non | 9,5 | normale |
| 30 | Non | 11,0 | trop élevée |



