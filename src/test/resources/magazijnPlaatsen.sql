insert into
    MagazijnPlaatsen(artikelId, rij, rek, aantal)
values
    ((select artikelId from Artikelen where naam = 'test1'),'Z', 25, 10),
    ((select artikelId from Artikelen where naam = 'test1'),'Z', 26, 10),
    ((select artikelId from Artikelen where naam = 'test1'),'Z', 27, 10),
    ((select artikelId from Artikelen where naam = 'test1'),'Z', 28, 10),
    ((select artikelId from Artikelen where naam = 'test1'),'Z', 29, 10);