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
        tr.id = artikel.ean;
        tr.addEventListener('click', (e) => handleClickRow(e));
        const eanCodeTd = document.createElement("td");
        eanCodeTd.innerText = artikel.ean;
        tr.appendChild(eanCodeTd);

        const artikelNaamTd = document.createElement("td");
        artikelNaamTd.innerText = artikel.naam;
        tr.appendChild(artikelNaamTd);

        const aantalTd = document.createElement("td");
        aantalTd.innerText = byId("aantal").value;
        tr.appendChild(aantalTd);

        // Add inputbox for # goedgekeurd
        const goedgekeurdTd = document.createElement("td");
        const goedgekeurdInput = document.createElement('input');
        goedgekeurdInput.min = 0;
        goedgekeurdInput.max = Number(aantalTd.innerText);
        goedgekeurdInput.type = "number";
        goedgekeurdInput.placeholder = 0;
        goedgekeurdInput.classList.add("input-smaller");

        goedgekeurdInput.addEventListener('input', (e) => handleChangeGoedgekeurd(e));

        goedgekeurdTd.appendChild(goedgekeurdInput);
        tr.appendChild(goedgekeurdTd);

        // Add TD for afgekeurd
        const afgekeurdTd = document.createElement('td');
        afgekeurdTd.innerText = 0;
        tr.appendChild(afgekeurdTd);


        artikelenBody.appendChild(tr);


    } else if (response.status === 404) {
        toon("verkeerdeEAN");
    } else {
        toon("storing")
    }
}

function handleChangeGoedgekeurd(e) {
    updateAfgekeurdValue(e);
}

function handleClickRow(e) {
    const selectedRow = e.target.parentElement;

    if (e.ctrlKey) {
        deleteRow(selectedRow);
    }
}

function updateAfgekeurdValue(e) {
    const rowElement = e.target.parentElement.parentNode;
    const afgekeurdTd = rowElement.lastChild;
    const goedgekeurdInput = e.target;
    const aantalArtikelen = Number(rowElement.childNodes.item(2).innerText);

    afgekeurdTd.innerText = aantalArtikelen - Number(goedgekeurdInput.value);
}

function deleteRow(rowElement) {
    rowElement.remove();
}