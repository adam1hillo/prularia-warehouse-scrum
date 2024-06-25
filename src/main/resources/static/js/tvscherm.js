"use strict";
import {setText, toon} from "./util.js";
//const response = await fetch("bestellingen/aantal");
const response = await fetch("./aantalbestellingen.json");
console.log(response);
if (response.ok) {
    const body = await response.text();
    setText("aantal", body);
} else {
    toon("storing");
}