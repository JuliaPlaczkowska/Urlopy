package com.example.UrlopyProjekt.servlets;

import com.example.UrlopyProjekt.DBUtil.DBUtilKierownik;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/KierownikServlet")
public class KierownikServlet extends HttpServlet {

    private DataSource dataSource;
    private DBUtilKierownik dbUtil;

    public KierownikServlet() {
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

                case "ACCEPT":
                    acceptUrlop(request, response);
                    break;

                case "LIST":
                    listUrlopy(request, response);

                case "MODIFY":
                    modifyUrlop(request, response);


                case "REJECT":
                    deleteUrlop(request, response);

                case "MAIN":
                    mainKierownik(request, response);


                default:
                    addUrlop(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    private void acceptUrlop(HttpServletRequest request, HttpServletResponse response) {

        // odczytanie danych z formularza
        int id_urlopu = Integer.parseInt(request.getParameter("urlopID"));
        String stan = request.getParameter("stan");


        // uaktualnienie danych w BD
        try {
            if(stan.equals("new")){
                dbUtil.acceptUrlop(id_urlopu);
            }
            else if(stan.equals("delete")){
                deleteUrlop(request, response);
            }
            else{
            dbUtil.updateUrlop(id_urlopu);
            }
            request.setAttribute("odpowiedz", "Zaakceptowano ");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            request.setAttribute("odpowiedz", "Operacja nie powiodla sie");
        } catch (ServletException e) {
            e.printStackTrace();
            request.setAttribute("odpowiedz", "Operacja nie powiodla sie");
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("odpowiedz", "Operacja nie powiodla sie");
        }


        listUrlopy(request, response);

    }

    private void deleteUrlop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




        int id = Integer.parseInt(request.getParameter("urlopID"));
        System.out.println("id usuwanego modyfikowanego: "+id);



        // uaktualnienie danych w BD
        try {
            dbUtil.deleteUrlop(id);
            request.setAttribute("odpowiedz", "Usunięto urlop");
            request.setAttribute("stan", "accepted");
            request.setAttribute("zaakceptowane", "true");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            request.setAttribute("odpowiedz", "Operacja nie powiodla sie");
        }

        listUrlopy(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {


                    response.setContentType("text/html");

                    String name = request.getParameter("loginInput");
                    System.out.println("przeczytalem ze login to: " + name);
                    String password = request.getParameter("passwordInput");

                    System.out.println("przeczytalem ze haslo to: " + password);

                    try {
                        boolean flag = verify(name, password);
                        System.out.println("czy weryfikacja przeszla pomyślnie? :" + flag);
                        if (flag) {
                            mainKierownik(request, response);
                        } else {
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/logowanie-Kierownik.html");
                            dispatcher.include(request, response);

                        }
                    } catch (Exception e) {

                        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
                        dispatcher.include(request, response);
                    }



    }


    private void mainKierownik(HttpServletRequest request, HttpServletResponse response) {

        RequestDispatcher dispatcher = request.getRequestDispatcher("main-kierownik.jsp");
        String nazwisko = dbUtil.getNazwisko();
        System.out.println("pana nazwisko:" + nazwisko);
        // dodanie listy do obiektu zadania
        request.setAttribute("nazwisko", nazwisko);

        try {
            dispatcher.forward(request, response);
            System.out.println("wyslano response do main-kierownik.jsp");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    private void modifyUrlop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // odczytanie danych z formularza
        int id_pracownika = Integer.parseInt(request.getParameter("pracownikID"));
        int id = Integer.parseInt(request.getParameter("urlopID"));
        String poczatek = request.getParameter("poczatek");
        String koniec = request.getParameter("koniec");

        // dodanie listy do obiektu zadania
        request.setAttribute("urlopID", id);
        request.setAttribute("poczatek", poczatek);
        request.setAttribute("koniec", koniec);
        request.setAttribute("dni", dbUtil.getDni_uropu(id_pracownika));


        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("/modmod-kierownik.jsp");

        // przekazanie do JSP
        dispatcher.forward(request, response);

    }

    private void listUrlopy(HttpServletRequest request, HttpServletResponse response) {


        String stan = null;
        String zaakceptowane = null;

        List<Urlop> urlopy = null;

        try {

            stan = request.getParameter("stan");
            zaakceptowane = request.getParameter("zaakceptowane");

            urlopy = dbUtil.getUrlopy(stan, zaakceptowane);

            System.out.println("dostalem liste urlopow");

            // dodanie listy do obiektu zadania
            request.setAttribute("urlopy", urlopy);

            // dodanie request dispatcher
            RequestDispatcher dispatcher = request.getRequestDispatcher("/wnioski-kierownik.jsp");
            System.out.println("wyslalem liste do wnioski-kierownik.jsp");
            // przekazanie do JSP
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

//    private void sendUrlop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//
//        String poczatek = request.getParameter("poczatek");
//        String koniec = request.getParameter("koniec");
//        System.out.println("poczatek (format daty): "+poczatek);
//
//        try {
//            dbUtil.sendUrlop(poczatek, koniec);
//            request.setAttribute("odpowiedz", "Przeslano prosbe o przyznanie urlopu ");
//
//        }catch(Exception e){
//            request.setAttribute("odpowiedz", "Operacja nie powiodla sie");
//        }
//
//
//        addUrlop(request,response);
//
//    }

    private void addUrlop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int id_pracownika = Integer.parseInt(request.getParameter("pracownikID"));

        int dni = dbUtil.getDni_uropu(id_pracownika);

        System.out.println("dni urlopu: "+dni);


        request.setAttribute("DniUrlopu", dni);


        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("plan-kierownik.jsp");

        // przekazanie do JSP
        dispatcher.forward(request, response);
    }





    private boolean verify(String name, String password) {

        return dbUtil.verify(name, password);
    }

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            dbUtil = new DBUtilKierownik(dataSource);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
