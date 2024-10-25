# Inside Out: One Day Journey

Ez a játék egy nagyon rövid történet alapú játék. A játékos az *Inside
Out (magyarul: Agymanók)* világában fogja irányítani a főszereplőt,
Riley-t egy napon keresztül. Korlátolt lesz az események száma
(körülbelül 5-6 esemény lesz), mivel csak egy napig fogjuk irányítani
Riley érzelmeit. Egy esemény a következőképpen néz ki: A játékos előtt
ismertetve lesz az alaphelyzet, majd az 5 választási lehetőség közül
(Amit az 5 érzelem, Derű, Bánat, Undor Düh, Félelem ajánl fel a maga
habitusában) válasz egyet. Minden esemény után változik 2 változó: Riley
kedve és kapcsolatai (Ez állítja össze a játékos végső pontszámát).

# Funkciók

-   Események megjelenítése: Egy kép, szöveg, ami leírja a helyzetet,
    alatta 5 opció (grafikus formában)

-   Következmények: Riley hangulata és kapcsolatai egy 0-100 -ig terjedő
    skálán mozog. A játék elején 50-ről indul. Minden esemény után (a
    választásunktól függően) ezek a változók megváltoznak

-   Többféle befejezés: Ezeknek a változóknak a függvényében a 9
    lehetséges közül egy befejezést ad nekünk a játék

-   Befejezések és pontszámok elmentése: Névvel el lehet menteni a
    befejezéseket, és később is meg lehet nézni azokat

# Technikai megoldások

-   Események tárolása: Az eseményeket egy JSON fájlba fogjuk tárolni és
    elmenteni azokat. Az esemény osztály az alábbiakból fog felállni:

    -   Esemény leírása (String)

    -   Az érzelmek válaszajánlásai (String tömb)

    -   Riley hangulatára való hatás (int tömb)

    -   Riley kapcsolataira való hatás (int tömb)

-   Végeredmények tárolása:

    -   Befejezés (Esemény osztály)

    -   Végső pontszám (double)

-   Végső pontszám kiszámítása:

    -   (Hangulat+Kapcsolat)/2

