package com.example.UrlopyProjekt;

public class Pracownik {

    private int id;
    private String nazwisko;
    private String rok_zatrudnienia;
    private String login;
    private String haslo;
    private int dni_uropu;

    public Pracownik(int id, String nazwisko, String rok_zatrudnienia, String login, String haslo, int dni_uropu) {
        this.id = id;
        this.nazwisko = nazwisko;
        this.rok_zatrudnienia = rok_zatrudnienia;
        this.login = login;
        this.haslo = haslo;
        this.dni_uropu = dni_uropu;
    }

    public Pracownik() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getRok_zatrudnienia() {
        return rok_zatrudnienia;
    }

    public void setRok_zatrudnienia(String rok_zatrudnienia) {
        this.rok_zatrudnienia = rok_zatrudnienia;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public int getDni_uropu() {
        return dni_uropu;
    }

    public void setDni_uropu(int dni_uropu) {
        this.dni_uropu = dni_uropu;
    }
}
