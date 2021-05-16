package com.example.UrlopyProjekt.servlets;

import com.example.UrlopyProjekt.DBUtil.DBUtilPracownik;
import com.example.UrlopyProjekt.Urlop;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/PracownikServlet")
public class PracownikServlet extends HttpServlet {

    private DataSource dataSource;
    private DBUtilPracownik dbUtil;

    public PracownikServlet() {
        // Obtain our environment naming context
        Context initCtx = null;
        try {
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            // Look up our data source
            dataSource = (DataSource)
                    envCtx.lookup("jdbc/urlopy_web_app");


        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException{

        try {

            // odczytanie zadania
            String command = request.getParameter("command");

            if (command == null)
                command = "LIST";

            switch (command) {

                case "ADDURLOP":
                    addUrlop(request, response);
                    break;

                case "SENDURLOP":
                    sendUrlop(request, response);
                    break;

                case "LIST":
                    listUrlopy(request, response);
                    break;

                case "MODIFY":
                    modifyUrlop(request, response);
                    break;

                case "UPDATE":
                    updateUrlop(request, response);
                    break;

                case "DELETE":
                    deleteUrlop(request, response);
                    break;

                case "MAIN":
                    mainPracownik(request, response);
                    break;


                default:
                    addUrlop(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        try {

            String command = request.getParameter("command");

            if(command==null)
                command="login";

            switch(command) {
                case "register":
                    register(request, response);
                    break;

                default:


                    response.setContentType("text/html");

                    String name = request.getParameter("loginInput");
                    System.out.println("przeczytalem ze login to: " + name);
                    String password = request.getParameter("passwordInput");

                    System.out.println("przeczytalem ze haslo to: " + password);

                    try {
                        boolean flag = verify(name, password);
                        System.out.println("czy weryfikacja przeszla pomyślnie? :" + flag);
                        if (flag) {
//                            RequestDispatcher dispatcher = request.getRequestDispatcher("main-pracownik.jsp");
//                            String nazwisko = dbUtil.getNazwisko();
//                            System.out.println("pana nazwisko:" + nazwisko);
//                            // dodanie listy do obiektu zadania
//                            request.setAttribute("nazwisko", nazwisko);
//
//                            dispatcher.forward(request, response);
//                            System.out.println("wyslano response do main-pracownik.jsp");
                            mainPracownik(request, response);
                        } else {
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/logowanie-pracownik.html");
                            dispatcher.include(request, response);

                        }
                    } catch (Exception e) {

                        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
                        dispatcher.include(request, response);
                    }
            }
        }catch (Exception e){
            throw new ServletException(e);
        }

    }


    private void mainPracownik(HttpServletRequest request, HttpServletResponse response) {

        RequestDispatcher dispatcher = request.getRequestDispatcher("main-pracownik.jsp");
        String nazwisko = dbUtil.getNazwisko();
        System.out.println("pana nazwisko:" + nazwisko);
        // dodanie listy do obiektu zadania
        request.setAttribute("nazwisko", nazwisko);

        try {
            dispatcher.forward(request, response);
            System.out.println("wyslano response do main-pracownik.jsp");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deleteUrlop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        System.out.println("servlet usuwanie");

        int id = Integer.parseInt(request.getParameter("urlopID"));
        System.out.println("id usuwanego: "+id);



        // uaktualnienie danych w BD
        try {
            dbUtil.deleteUrlop(id);
            request.setAttribute("odpowiedz", "Przeslano prosbe o usunięcie urlopu ");

        } catch (SQLException throwables) {
            request.setAttribute("odpowiedz", "Operacja nie powiodla sie");
        }

        listUrlopy(request, response);
    }

    private void updateUrlop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        System.out.println("servlet modyfikowanie");

        // odczytanie danych z formularza
        int id = Integer.parseInt(request.getParameter("urlopID"));
        System.out.println("id urlopu modyfikowanego: "+id);
        String poczatek_new = request.getParameter("poczatek_new");
        System.out.println("pocz new urlopu modyfikowanego: "+poczatek_new);
        String koniec_new = request.getParameter("koniec_new");
        System.out.println("koniec new urlopu modyfikowanego: "+koniec_new);



        // uaktualnienie danych w BD
        try {
            dbUtil.updateUrlop(id, poczatek_new, koniec_new);
            request.setAttribute("odpowiedz", "Przeslano prosbe o przyznanie urlopu ");

        } catch (SQLException throwables) {
            request.setAttribute("odpowiedz", "Operacja nie powiodla sie");
        }


        request.setAttribute("urlopID", id);
        request.setAttribute("poczatek", request.getParameter("poczatek"));
        request.setAttribute("koniec", request.getParameter("koniec"));
        request.setAttribute("dni", dbUtil.getDni_uropu());

        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("modmod-pracownik.jsp");

        // przekazanie do JSP
        dispatcher.forward(request, response);

    }

    private void modifyUrlop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // odczytanie danych z formularza
        int id = Integer.parseInt(request.getParameter("urlopID"));
        String poczatek = request.getParameter("poczatek");
        String koniec = request.getParameter("koniec");

        // dodanie listy do obiektu zadania
        request.setAttribute("urlopID", id);
        request.setAttribute("poczatek", poczatek);
        request.setAttribute("koniec", koniec);
        request.setAttribute("dni", dbUtil.getDni_uropu());


        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("/modmod-pracownik.jsp");

        // przekazanie do JSP
        dispatcher.forward(request, response);

    }

    private void listUrlopy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String stan = null;
        String zaakceptowane = null;

        List<Urlop> urlopy = null;

        try {

            stan = request.getParameter("stan");
            zaakceptowane = request.getParameter("zaakceptowane");

            urlopy = dbUtil.getUrlopy(stan, zaakceptowane);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // dodanie listy do obiektu zadania
        request.setAttribute("urlopy", urlopy);
        request.setAttribute("dni", dbUtil.getDni_uropu());

        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("/mod-pracownik.jsp");

        // przekazanie do JSP
        dispatcher.forward(request, response);

    }

    private void sendUrlop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String poczatek = request.getParameter("poczatek");
        String koniec = request.getParameter("koniec");
        System.out.println("poczatek (format daty): "+poczatek);

        try {
            dbUtil.sendUrlop(poczatek, koniec);
            request.setAttribute("odpowiedz", "Przeslano prosbe o przyznanie urlopu ");

        }catch(Exception e){
            request.setAttribute("odpowiedz", "Operacja nie powiodla sie");
        }


        addUrlop(request,response);

    }

    private void addUrlop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int dni = dbUtil.getDni_uropu();

        System.out.println("dni urlopu: "+dni);


        request.setAttribute("DniUrlopu", dni);


        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("plan-pracownik.jsp");

        // przekazanie do JSP
        dispatcher.forward(request, response);
    }



    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("rejestracja");

        response.setContentType("text/html");

        String name = request.getParameter("loginInput");
        String imie = request.getParameter("nameInput");
        System.out.println("przeczytalem ze login to: " + name);
        String password = request.getParameter("passwordInput");
        String password2 = request.getParameter("passwordInput2");

        if(!password2.equals(password)){
            throw new ServletException(new Exception());
        }
        int rok = Integer.parseInt(request.getParameter("yearInput"));

        dbUtil.addPracownik(imie, name, password, rok);
        dbUtil.verify(name, password);


        try{
            RequestDispatcher dispatcher = request.getRequestDispatcher("main-pracownik.jsp");
            String nazwisko = dbUtil.getNazwisko();
            System.out.println("pana nazwisko:" + nazwisko);
            // dodanie listy do obiektu zadania
            request.setAttribute("nazwisko", nazwisko);

            dispatcher.forward(request, response);
            System.out.println("wyslano response do main-pracownik.jsp");
        } catch (IOException e) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
            dispatcher.include(request, response);
        }
    }

    private boolean verify(String name, String password) {

        return dbUtil.verify(name, password);
    }

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            dbUtil = new DBUtilPracownik(dataSource);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
