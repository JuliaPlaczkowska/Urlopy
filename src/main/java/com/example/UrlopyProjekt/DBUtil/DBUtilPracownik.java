package com.example.UrlopyProjekt.DBUtil;

import com.example.UrlopyProjekt.Urlop;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtilPracownik extends DBUtil{

    private DataSource dataSource;
    private int id;
    private String nazwisko;
    private int rok_zatrudnienia;
    private String login;
    private String haslo;
    private int dni_uropu;
    private String url ="jdbc:mysql://localhost:3306/obsluga_urlopow?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";


    public DBUtilPracownik(DataSource dataSource) throws Exception {

        this.dataSource = dataSource;
    }

    public boolean verify(String login, String haslo) {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean flag = false;

        try {
            // polaczenie z BD
            conn = dataSource.getConnection();

            // wyrazenie SQL
            String sql = "SELECT * FROM pracownicy where login= \'"+login+"\'";

            statement = conn.prepareStatement(sql);

            statement.execute();
            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                this.id = resultSet.getInt("id");
                this.haslo = resultSet.getString("haslo");
                System.out.println(this.haslo+" =? "+haslo);
                this.nazwisko = resultSet.getString("nazwisko");
                this.rok_zatrudnienia = resultSet.getInt("rok_zatrudnienia");
                this.dni_uropu = resultSet.getInt("dni_urlopu");

                this.login = login;
            }
            if(this.haslo.equals(haslo))
                flag=true;

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }finally {

                // zamkniecie obiektow JDBC
                close(conn, statement, resultSet);
        }

        return flag;
    }

    public void addPracownik(String imie, String login, String haslo, int rok_zatrudnienia)   {

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // polaczenie z BD
            conn = dataSource.getConnection();

            // zapytanie INSERT i ustawienie jego parametrow
            String sql = "INSERT INTO pracownicy(nazwisko,login,haslo,rok_zatrudnienia) " +
                    "VALUES(?,?,?,?)";

            statement = conn.prepareStatement(sql);
            statement.setString(1,imie);
            statement.setString(2, login);
            statement.setString(3, haslo);
            statement.setInt(4, rok_zatrudnienia);


            // wykonanie zapytania
            statement.execute();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            close(conn, statement, null);

        }

    }

    public void sendUrlop(String poczatek, String koniec) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // polaczenie z BD
            conn = dataSource.getConnection();

            // zapytanie INSERT i ustawienie jego parametrow
            String sql = "INSERT INTO zarezerwowane(id_pracownicy,data_poczatek,data_koniec,stan,zaakceptowane) " +
                    "VALUES(?,?,?,?,?)";

            statement = conn.prepareStatement(sql);
            statement.setInt(1,id);
            statement.setString(2, poczatek);
            statement.setString(3, koniec);
            statement.setString(4, "new");
            statement.setBoolean(5, false);


            // wykonanie zapytania
            statement.execute();


        } finally {

            close(conn, statement, null);

        }


    }

    public void updateUrlop(int urlopID, String poczatek_new, String koniec_new) throws SQLException {

        System.out.println("DB modyfikowanie");

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // polaczenie z BD
            conn = dataSource.getConnection();

            // zapytanie UPDATE
            String sql = "UPDATE zarezerwowane SET data_poczatek_new=?, data_koniec_new=?, stan=?," +
                    "zaakceptowane = ? " +
                    "WHERE id =?";

            statement = conn.prepareStatement(sql);
            statement.setString(1, poczatek_new);
            statement.setString(2, koniec_new);
            statement.setString(3, "modified");
            statement.setString(4, "0");
            statement.setInt(5, urlopID);


            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, statement.getResultSet());

        }

    }

    public void deleteUrlop(int urlopID) throws SQLException {

        System.out.println("DB usuwanie");

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // polaczenie z BD
            conn = dataSource.getConnection();

            // zapytanie UPDATE
            String sql = "UPDATE zarezerwowane SET  stan=?," +
                    "zaakceptowane = ? " +
                    "WHERE id =?";

            statement = conn.prepareStatement(sql);
            statement.setString(1, "delete");
            statement.setString(2, "0");
            statement.setInt(3, urlopID);


            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    @Override
    public List<Urlop> getUrlopy(String stan, String zaakceptowane) throws Exception {

        System.out.println("jestem w  get urlopy");

        List<Urlop> urlopy = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = dataSource.getConnection();

            String sql= "SELECT * FROM zarezerwowane";

            statement = conn.createStatement();

            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int id = resultSet.getInt("id");
                int id_pracownicy = resultSet.getInt("id_pracownicy");
                String data_poczatek = (resultSet.getString("data_poczatek"));
                String data_koniec = (resultSet.getString("data_koniec"));
                String data_poczatek_new = (resultSet.getString("data_poczatek_new"));
                String data_koniec_new = (resultSet.getString("data_koniec_new"));
                String stan2 = resultSet.getString("stan");
                boolean zaakceptowaneb = resultSet.getBoolean("zaakceptowane");

                System.out.println("przeczytane z mysql id przacownika i zaakceptowane : "+id_pracownicy+", "+zaakceptowaneb);
                System.out.println("a z programu : "+this.id+", "+zaakceptowane);

                if(id_pracownicy == this.id) {
                    System.out.println("spelniony warunek");
                    // dodanie do listy nowego obiektu
                    urlopy.add(new Urlop(id, id_pracownicy, data_poczatek, data_koniec, data_poczatek_new, data_koniec_new, stan2, zaakceptowaneb));
                }
            }

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
        return urlopy;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public int getId() {
        return id;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getRok_zatrudnienia() {
        return rok_zatrudnienia;
    }

    public String getLogin() {
        return login;
    }

    public String getHaslo() {
        return haslo;
    }

    public int getDni_uropu() {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // polaczenie z BD
            conn = dataSource.getConnection();

            // wyrazenie SQL
            String sql = "SELECT dni_urlopu FROM pracownicy where id= "+this.id;

            statement = conn.prepareStatement(sql);

            statement.execute();
            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                this.dni_uropu = resultSet.getInt("dni_urlopu");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }

        return dni_uropu;
    }


}
