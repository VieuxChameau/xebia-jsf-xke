package fr.xebia.xke.jsfdemo.enums;

public enum SlotType {

    contest, debate, demo, dojo, formal, handson, other, quickie, training;

    public String i18n() {
        return "slot.type." + this.name();
    }
}
