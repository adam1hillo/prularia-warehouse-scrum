"use strict";
import {byId, toon, verberg, setText} from "./util.js";

verbergArtikelEnFouten();
// todo get id from localStorage and pass it to the function
const idVanArtikel = sessionStorage.getItem("artikelId");

toonArtikelDetail();

function verbergArtikelEnFouten() {
    verberg("artikelTable");
    verberg("nietGevonden");
    verberg("storing");
}


async function toonArtikelDetail() {
    const response = await fetch(`artikelen/${idVanArtikel}`);
    //const response = await fetch(`artikelen/6`);
    if (response.ok) {
        const artikel = await response.json();
        toon("artikelTable");
        setText("artikelNaamVoorHeader", artikel.naam);
        setText("naam", artikel.naam);
        setText("id", artikel.artikelId);
        setText("prijs", `${artikel.prijs} EUR`);
        setText("gewicht",  `${artikel.gewichtInGram/1000} kg`);
        setText("voorraad", artikel.voorraad);
        setText("maximumVoorraad", artikel.maximumVoorraad);
        setText("maxAantalInMagazijnPlaats", artikel.maxAantalInMagazijnPlaats);


        artikel.magazijnPlaatsen.forEach((element) => addRow(element));

    } else {
        if (response.status === 404) {
            toon("nietGevonden");
        } else {
            toon("storing");
        }
    }
}

function addRow(data) {
    const tableBody = byId("nestedTableBody");
    const newRow = document.createElement('tr');

        const newCellVoorRij = document.createElement('td');
        const newCellVoorRek = document.createElement('td');
        const newCellVoorAantal = document.createElement('td');

        newCellVoorRij.textContent = data.rij;
        newCellVoorRek.textContent = data.rek;
        newCellVoorAantal.textContent = data.aantal;
        newRow.appendChild(newCellVoorRij);
        newRow.appendChild(newCellVoorRek);
        newRow.appendChild(newCellVoorAantal);


    tableBody.appendChild(newRow);
}

