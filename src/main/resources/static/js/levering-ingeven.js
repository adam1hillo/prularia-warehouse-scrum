"use strict"

import {byId, toon, verberg} from "./util.js";

const tableWrapper = byId("table-wrapper");
const tableBody = byId("toegevoegdeArtikelenBody");
const btnNextWrapper = byId("buttonVolgende");
const btnNext = byId("btn-next");
const leverancierSelect = byId("leverancier-select");
const leveringsbonnummerInput = byId("leveringsbonnummer-input");
const leveringsbondatumInput = byId("leveringsbondatum-input");
const leverdatumInput = byId("leverdatum-input");

const inputs = [leverancierSelect, leveringsbonnummerInput, leveringsbondatumInput, leverdatumInput];

const checkInputs = () => {
    let allFilled = true;

    inputs.forEach(input => {
        if (input.type === 'select-one') {
            if (input.value === '') {
                allFilled = false;
            }
        } else {
            if (input.value.trim() === '') {
                allFilled = false;
            }
        }
    });

    btnNext.disabled = !allFilled;
};

inputs.forEach(input => {
    input.addEventListener('input', checkInputs);
});

checkInputs();

const leveringsbonData = {
    // leveranciersId,
    // leveringsbonNummer,
    // leveringsbonDatum,
    // leverdatum
};

const artikelen = [
    // {
    //     ean,
    //     artikelId,
    //     artikelNaam,
    //     aantal,
    //     aantalGoedgekeurd,
    //     aantalAfgekeurd
    // }
];

localStorage.clear();
maxLimiteDate();


btnNext.addEventListener('click', (e) => {
    saveLeveringsbonData();
    saveArticlesData();
    window.location.href = 'goedgekeurdeArtikelen.html';
});

function saveArticlesData() {
    const rows = document.querySelectorAll('.artikel');

    rows.forEach(row => {
        const artikel = {
            id: row.id,
            ean: row.childNodes[0].textContent,
            naam: row.childNodes[1].textContent,
            aantal: row.childNodes[2].textContent,
            goedgekeurd: Number(byId(row.childNodes[0].textContent).value),
            afgekeurd: row.childNodes[4].textContent
        }

        artikelen.push(artikel);
    });

    localStorage.setItem('artikelen', JSON.stringify(artikelen));
}


function saveLeveringsbonData() {
    leveringsbonData.leveranciersId = leverancierSelect.value;
    leveringsbonData.leveringsbonNummer = leveringsbonnummerInput.value;
    leveringsbonData.leveringsbonDatum = new Date(leveringsbondatumInput.value);
    leveringsbonData.leverdatum = new Date(leverdatumInput.value);

    localStorage.setItem('leveringsbonData', JSON.stringify(leveringsbonData));
}


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
        tr.id = artikel.artikelId;
        tr.classList.add("artikel");
        tr.addEventListener('click', (e) => handleClickRow(e));
        const eanCodeTd = document.createElement("td");
        eanCodeTd.innerText = artikel.ean;
        tr.appendChild(eanCodeTd);

        const artikelNaamTd = document.createElement("td");
        artikelNaamTd.innerText = artikel.naam;
        tr.appendChild(artikelNaamTd);

        const aantalTd = document.createElement("td");
        const aantalArtikelen = byId("aantal").value
        aantalTd.innerText = aantalArtikelen;
        tr.appendChild(aantalTd);

        // Add inputbox for # goedgekeurd
        const goedgekeurdTd = document.createElement("td");
        const goedgekeurdInput = document.createElement('input');
        goedgekeurdInput.min = 0;
        goedgekeurdInput.max = Number(aantalTd.innerText);
        goedgekeurdInput.type = "number";
        goedgekeurdInput.placeholder = 0;
        goedgekeurdInput.classList.add("input-smaller");
        goedgekeurdInput.id = artikel.ean;

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
    const goedgekeurdInput = e.target;
    if (goedgekeurdInput.value !== "") {
        if (Number(goedgekeurdInput.value) < Number(goedgekeurdInput.min)) {
            goedgekeurdInput.value = goedgekeurdInput.min;
        }
        if (Number(goedgekeurdInput.value) > Number(goedgekeurdInput.max)) {
            goedgekeurdInput.value = goedgekeurdInput.max;
        }
    }

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

function hide(element) {
    element.hidden = true;
}

function deleteRow(rowElement) {
    rowElement.remove();

    if (tableBody.rows.length === 0) {
        hide(tableWrapper);
        hide(btnNextWrapper);
    }
}

function maxLimiteDate() {


    let today = new Date();
    let dd = today.getDate();
    let mm = today.getMonth() + 1; //January is 0!
    let yyyy = today.getFullYear();

    if (dd < 10) {
        dd = '0' + dd;
    }

    if (mm < 10) {
        mm = '0' + mm;
    }

    today = yyyy + '-' + mm + '-' + dd;
    leveringsbondatumInput.setAttribute("max", today);
    leverdatumInput.setAttribute("max", today);
}