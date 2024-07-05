"use strict"
import {byId, toon} from "./util.js";


const artikelenDataFromStorage = JSON.parse(localStorage.getItem("artikelen"));
const leveringsBonDataFromStorage = JSON.parse(localStorage.getItem("leveringsbonData"));


byId("leveringsbonNummer").innerText = leveringsBonDataFromStorage.leveringsbonNummer;

// console.log(artikelenDataFromStorage);
// console.log(leveringsBonDataFromStorage);
let artikelDataForFetch = [];
let artikelEnAfgekeurdData = [];
artikelenDataFromStorage.map(artikel => {
    artikelDataForFetch.push({
        "artikelId": Number(artikel.id),
        "aantal": artikel.goedgekeurd
    })
    artikelEnAfgekeurdData.push({
        "artikelId": Number(artikel.id),
        "afgekeurd": Number(artikel.afgekeurd),
        "goedgekeurd": artikel.goedgekeurd
    })
})

//console.log(artikelDataForFetch);

function findNaamById(id) {
    const product = artikelenDataFromStorage.find(product => product.id === id);
    return product ? product.naam : null;
}

let artikelDataForHtml = [];
let magazijnPlaceForAllArtikel;

const response = await fetch("artikelen/findAllPlaceForDelivery", {
    method: "POST",
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(artikelDataForFetch)
});
if (response.ok) {
    const responsebody = await response.json();

    magazijnPlaceForAllArtikel = responsebody;
    responsebody.map(item => {
        artikelDataForHtml.push({
            "magazijnPlaatsId": item.magazijnPlaatsId,
            "artikelId": item.artikelId,
            "rij": item.rij,
            "rek": item.rek,
            "aantal": item.aantal,
            "artikelNaam": findNaamById(item.artikelId.toString())
        })
    })
} else {
    toon("storing");
}

const tbody = document.getElementById('goedgekeurdeArtikelenBody');
const bevestigButton = document.getElementById('bevestig');

function tableInvullen(data) {
    data.forEach(item => {
        const tr = document.createElement('tr');
        let td = document.createElement('td');

        td.textContent = item.rij;
        tr.appendChild(td);

        td = document.createElement('td');
        td.textContent = item.rek;
        tr.appendChild(td);

        td = document.createElement('td');
        td.textContent = item.artikelNaam;
        tr.appendChild(td);

        td = document.createElement('td');
        td.textContent = item.aantal;
        tr.appendChild(td);

        td = document.createElement('td');
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        td.appendChild(checkbox);
        tr.appendChild(td);

        tbody.appendChild(tr);

        checkbox.onclick = function () {
            getCheckedCheckboxes();
            if (checkbox.checked) {
                tr.classList.add("checked");
                tr.style.background = "#ABD7A8";
            } else {
                tr.classList.remove("checked");
                tr.style.background = "white";
            }
            const checkedList = document.getElementsByClassName("checked");
            byId("bevestig").disabled = checkedList.length !== data.length;
        }
    });
}

//sort artikelDataForHtml
artikelDataForHtml = artikelDataForHtml.sort((a, b) => {
    if (a.rij === b.rij) {
        return a.rek > b.rek ? 1 : (a.rek < b.rek ? -1 : 0);
    } else {
        return a.rij > b.rij ? 1 : (a.rij < b.rij ? -1 : 0);
    }
});

tableInvullen(artikelDataForHtml);

bevestigButton.disabled = true;


bevestigButton.addEventListener('click', async () => {

    const data = {
        "leveranciersId": Number(leveringsBonDataFromStorage.leveranciersId),
        "leveringsbonNummer": leveringsBonDataFromStorage.leveringsbonNummer,
        "leveringsbondatum": leveringsBonDataFromStorage.leveringsbonDatum,
        "leverDatum": leveringsBonDataFromStorage.leverdatum,
        "artikelIdEnAfgekeurdList": artikelEnAfgekeurdData,
        "magazijnPlaatsList": magazijnPlaceForAllArtikel
    }

    const response = await fetch("leveringen/create", {
        method: "POST",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(data)
    });
    // console.log(response);
    if (response.ok) {
        window.location.href = "bevestigingspagina-levering.html";
    } else {
        toon("storing");
    }
});

function getCheckedCheckboxes() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');

    let selectedCheckedBoxes = [];
    checkboxes.forEach((checkbox, index) => {
        if (checkbox.checked) {
            selectedCheckedBoxes.push(index + 1)
        }
    });
    sessionStorage.setItem('checkboxes', JSON.stringify(selectedCheckedBoxes));
}





