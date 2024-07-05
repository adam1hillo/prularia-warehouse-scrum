"use strict"
import {byId, toon, verberg, setText} from "./util.js";


const artikelenDataFromStorage = JSON.parse(localStorage.getItem("artikelen"));
const leveringsBonDataFromStorage = JSON.parse(localStorage.getItem("leveringsbonData"));


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
let magazijnPlaceForAllArtikel ;

const response = await fetch("artikelen/findAllPlaceForDelivery", {
    method: "POST",
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify(artikelDataForFetch)
});
if (response.ok) {
    const responsebody = await response.json();
    //TODO remove log
    // console.log(responsebody)
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

    tableInvullen(artikelDataForHtml);

    bevestigButton.disabled = true;


    bevestigButton.addEventListener('click', async () => {

        const data = {
            "leveranciersId": Number(leveringsBonDataFromStorage.leveranciersId),
            "leveringsbonNummer": leveringsBonDataFromStorage.leveringsbonNummer,
            "leveringsbondatum" : leveringsBonDataFromStorage.leveringsbonDatum ,
            "leverDatum":leveringsBonDataFromStorage.leverdatum,
            "artikelIdEnAfgekeurdList": artikelEnAfgekeurdData,
            "magazijnPlaatsList":magazijnPlaceForAllArtikel
        }

        // console.log(data);

        const response = await fetch("leveringen/create", {
            method: "POST",
            headers: {
                            "Content-type": "application/json"
                        },
            body: JSON.stringify(data)
        });
        console.log(response);
        if (response.ok) {
            window.location.href = "bevestigingspaginaLevering.html";
             //const inkomendeLeveringsId = await response.json();
            // console.log(inkomendeLeveringsId);
            // console.log(magazijnPlaceForAllArtikel);

            /*const responseForPlaceUpdate = await fetch("artikelen/updateAllPlaceForDelivery", {
                method: "PATCH",
                headers: {
                    "Content-type": "application/json"
                },
                body: JSON.stringify(magazijnPlaceForAllArtikel)
            });

            if (responseForPlaceUpdate.ok) {
                const listForUpdatedMagazijnPlaces = await responseForPlaceUpdate.json();
                console.log(listForUpdatedMagazijnPlaces);
            } else {
                toon("storing");
            }*/

        } else {
            toon("storing");
        }

       // window.location.href = "bevestiging.html";
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





