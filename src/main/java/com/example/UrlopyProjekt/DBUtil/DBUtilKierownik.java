package com.example.UrlopyProjekt.DBUtil;

import com.example.UrlopyProjekt.Pracownik;
import com.example.UrlopyProjekt.Urlop;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtilKierownik extends DBUtil{

    private DataSource dataSource;
    private int id;
    private String nazwisko;

    private String login;
    private String haslo;

    private ArrayList<Pracownik> pracownicy;


    public DBUtilKierownik(DataSource dataSource) throws Exception {

        this.dataSource = dataSource;
        getPracownicy();
    }

    private void getPracownicy() throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Pracownik> pracownicy = new ArrayList<>();

        try {

            // polaczenie z BD
            conn = dataSource.getConnection();

            String sql= "SELECT * FROM pracownicy";

            statement = conn.createStatement();

            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int id = resultSet.getInt("id");
                String nazwisko = resultSet.getString("nazwisko");
                String rok_zatrudnienia = (resultSet.getString("rok_zatrudnienia"));
                String login = (resultSet.getString("login"));
                String haslo = (resultSet.getString("haslo"));
                int dni = (resultSet.getInt("dni_urlopu"));


                    pracownicy.add(new Pracownik(id, nazwisko, rok_zatrudnienia, login, haslo, dni));

                this.pracownicy = pracownicy;
            }

        } finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
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


                //if(zaakceptowane.equals(String.valueOf(zaakceptowaneb))) {

                    Pracownik pracownik = new Pracownik();

                    for(Pracownik p : pracownicy)
                        if(p.getId()==id_pracownicy){
                            pracownik = p;
                            break;
                        }
                    getPracownicy();

                    System.out.println("spelniony warunek");
                    // dodanie do listy nowego obiektu
                    urlopy.add(new Urlop(id, id_pracownicy, data_poczatek, data_koniec,
                            data_poczatek_new, data_koniec_new, stan2, zaakceptowaneb,
                            pracownik.getNazwisko(), pracownik.getRok_zatrudnienia(), pracownik.getDni_uropu()));
                    System.out.println("urlop dodany");
                }
            //}
            System.out.println("wyszedlem z whila");

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
        return urlopy;
    }

    private Urlop getUrlopById(int id) throws SQLException {
        System.out.println("jestem w  get urlopy");

        List<Urlop> urlopy = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Urlop urlop = new Urlop();

        try {

            // polaczenie z BD
            conn = dataSource.getConnection();

            String sql= "SELECT * FROM zarezerwowane";

            statement = conn.createStatement();

            // wykonanie wyrazenia SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                if (resultSet.getInt("id") == id) {
                    // pobranie danych z rzedu
                    int id_pracownicy = resultSet.getInt("id_pracownicy");
                    String data_poczatek = (resultSet.getString("data_poczatek"));
                    String data_koniec = (resultSet.getString("data_koniec"));
                    String data_poczatek_new = (resultSet.getString("data_poczatek_new"));
                    String data_koniec_new = (resultSet.getString("data_koniec_new"));
                    String stan2 = resultSet.getString("stan");
                    boolean zaakceptowaneb = resultSet.getBoolean("zaakceptowane");

                    System.out.println("spelniony warunek");
                    // dodanie do listy nowego obiektu
                    urlop = new Urlop(id, id_pracownicy, data_poczatek, data_koniec,
                            data_poczatek_new, data_koniec_new, stan2, zaakceptowaneb);
                }
            }

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
        return urlop;
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
            String sql = "SELECT * FROM kierownicy where login= \'"+login+"\'";

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
    public void acceptUrlop(int urlopID) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        Urlop urlop = getUrlopById(urlopID);
        String dpn = urlop.getData_poczatek_new();
        String dkn = urlop.getData_koniec_new();
        System.out.println("pobralem nowe daty");

        try {
            // polaczenie z BD
            conn = dataSource.getConnection();


            // zapytanie UPDATE
            String sql = "UPDATE zarezerwowane SET stan=?," +
                    "zaakceptowane = ?" +
                    "WHERE id ="+urlopID;

            statement = conn.prepareStatement(sql);
            statement.setString(1, "accepted");
            statement.setString(2, "1");


            // wykonanie zapytania
            statement.execute();


        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }


    public void updateUrlop(int urlopID) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        Urlop urlop = getUrlopById(urlopID);
        String dpn = urlop.getData_poczatek_new();
        String dkn = urlop.getData_koniec_new();
        System.out.println("pobralem nowe daty");

        try {
            // polaczenie z BD
            conn = dataSource.getConnection();


            // zapytanie UPDATE
            String sql = "UPDATE zarezerwowane SET stan=?, " +
                    "data_koniec = ?, data_poczatek = ?, " +
                    "zaakceptowane = ?, data_koniec_new = ?, data_poczatek_new = ? " +
                    "WHERE id =?";

            statement = conn.prepareStatement(sql);
            statement.setString(1, "accepted");
            statement.setString(2, dpn);
            statement.setString(3, dkn);
            statement.setBoolean(4, true);
            statement.setString(5, null);
            statement.setString(6, null);
            statement.setInt(7, urlopID);


            // wykonanie zapytania
            statement.execute();


        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    public void deleteUrlop(int urlopID) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // polaczenie z BD
            conn = dataSource.getConnection();

            // zapytanie UPDATE
            String sql = "DELETE FROM zarezerwowane WHERE id =?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, urlopID);


            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

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



    public String getLogin() {
        return login;
    }

    public String getHaslo() {
        return haslo;
    }

    public int getDni_uropu(int id_pracownik) {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int dni_uropu = 0;

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
                dni_uropu = resultSet.getInt("dni_urlopu");
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
