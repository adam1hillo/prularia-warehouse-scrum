"use strict"

import {byId, toon, verberg} from "./util.js";

await getLeveranciers();
byId("artikelToevoegen").onclick = voegArtikelToe;


async function getLeveranciers() {
    const response = await fetch("leveranciers");
    if (response.ok) {
        const leveranciers = await response.json();

        const leveranciersLijst = byId("leverancier-select");
        for (const leverancier of leveranciers) {
            const option = document.createElement("option");
            option.innerText = leverancier.naam;
            option.value = leverancier.leveranciersId;
            leveranciersLijst.appendChild(option);
        }
    } else {
        toon("storing");
    }
}

async function voegArtikelToe() {
    verberg("verkeerdeEAN");
    verberg("storing");
    verberg("verkeerdeAantal");
    const ean = byId("ean").value;

    if (ean.length !== 5) {
        toon("verkeerdeEAN");
        return;
    }

    if (!byId("aantal").checkValidity()) {
        toon("verkeerdeAantal");
        return;
    }

    const response = await fetch(`artikelen/metEanLastFive/${ean}`);
    if (response.ok) {
        const artikel = await response.json();
        if (byId("table-wrapper").hidden) {
            toon("table-wrapper")
            toon("buttonVolgende");
        }
        const artikelenBody = byId("toegevoegdeArtikelenBody");
        const tr = artikelenBody.insertRow();
        const eanCodeTd = document.createElement("td");
        eanCodeTd.innerText = artikel.ean;
        tr.appendChild(eanCodeTd);
        const artikelNaamTd = document.createElement("td");
        artikelNaamTd.innerText = artikel.naam;
        tr.appendChild(artikelNaamTd);
        const aantalTd = document.createElement("td");
        aantalTd.innerText = byId("aantal").value;
        tr.appendChild(aantalTd);
        artikelenBody.appendChild(tr);
    } else if (response.status === 404) {
        toon("verkeerdeEAN");
    } else {
        toon("storing")
    }
}