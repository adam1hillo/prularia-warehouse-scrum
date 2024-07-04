package be.vdab.scrumjava202406.leveringen;

import java.time.LocalDate;

class InkomendeLevering {
    private final long inkomendeLeveringsId;
    private final long leveranciersId;
    private final String leveringsbonNummer;
    private final LocalDate leveringsbondatum;
    private final LocalDate leverDatum;
    static final long ontvangerPersoneelslidId = 4;

    InkomendeLevering(long inkomendeLeveringsId, long leveranciersId, String leveringsbonNummer, LocalDate leveringsbondatum, LocalDate leverDatum) {
        this.inkomendeLeveringsId = inkomendeLeveringsId;
        this.leveranciersId = leveranciersId;
        this.leveringsbonNummer = leveringsbonNummer;
        this.leveringsbondatum = leveringsbondatum;
        this.leverDatum = leverDatum;
    }

    public long getInkomendeLeveringsId() {
        return inkomendeLeveringsId;
    }

    public long getLeveranciersId() {
        return leveranciersId;
    }

    public String getLeveringsbonNummer() {
        return leveringsbonNummer;
    }

    public LocalDate getLeveringsbondatum() {
        return leveringsbondatum;
    }

    public LocalDate getLeverDatum() {
        return leverDatum;
    }
}
