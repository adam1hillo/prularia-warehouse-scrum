insert into Bestellijnen(bestelId, artikelId, aantalBesteld) values
-- testbestelling 1: (aantal=1, gewicht=1)
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf1'),
 (select artikelId from Artikelen where naam='testartikel1'), 1),
-- testbestelling 2: (aantal=2, gewicht=3)
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf2'),
 (select artikelId from Artikelen where naam='testartikel1'), 1),
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf2'),
 (select artikelId from Artikelen where naam='testartikel2'), 1),
-- testbestelling 3: (aantal=4, gewicht=9)
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf3'),
 (select artikelId from Artikelen where naam='testartikel1'), 1),
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf3'),
 (select artikelId from Artikelen where naam='testartikel2'), 1),
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf3'),
 (select artikelId from Artikelen where naam='testartikel3'), 2),
-- testbestelling 4: (aantal=10, gewicht=21)
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf4'),
 (select artikelId from Artikelen where naam='testartikel1'), 5),
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf4'),
 (select artikelId from Artikelen where naam='testartikel2'), 2),
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf4'),
 (select artikelId from Artikelen where naam='testartikel3'), 1),
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf4'),
 (select artikelId from Artikelen where naam='testartikel4'), 1),
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf4'),
 (select artikelId from Artikelen where naam='testartikel5'), 1),
-- testbestelling 5: (aantal=10, gewicht=10)
((select bestelId from Bestellingen where bedrijfsnaam='testbedrijf5'),
 (select artikelId from Artikelen where naam='testartikel1'), 10);