"use strict"
import {byId, toon, verberg, setText} from "./util.js";


const response = await fetch("artikelen/findAllPlaceForDelivery",{
    method: "GET",
    body: JSON.stringify(artikelDataForFetch)
});
if (response.ok) {
    const responsebody = await response.json();
    setText("bestelId", responsebody.bestelId);
    tableInvullen(responsebody.artikelLijn);
    setCheckedCheckboxes()
    const checkedList = document.getElementsByClassName("checked");
    byId("bevestig").disabled = JSON.parse(sessionStorage.getItem("checkboxes")).length !== responsebody.artikelLijn.length;


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

    tableInvullen(data);

    bevestigButton.disabled = true;

    bevestigButton.addEventListener('click', () => {
        window.location.href = "bevestiging.html";
    });
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





