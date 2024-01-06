package com.company.librarym;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class Management {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    protected static final String url = "jdbc:mysql://";
    protected static final String username = "";
    protected static final String password = "";
    public Connection connection = this.getConnection();
    public Statement state = null;
    public ResultSet result = null;
    public PreparedStatement ps = null;
    public String sql;
    public String sql1;

    //드라이버로딩
    public Management() {

        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException throwables) {
            System.out.println("연결사유 : " + throwables.getMessage());
        }

    }
    //커넥션 (연결항목)
    public Connection getConnection(){
         Connection connection = null;
         try{
             connection = DriverManager.getConnection(url,username,password);
             state = connection.createStatement();
             state.executeUpdate("use kjc0411");
         }
         catch (SQLException e){
             System.out.println("연결사유 : " + e.getMessage());
         }
         return connection;
    }

    //목록 실행 리스트
    public void list() {
        sql = "select * from tb_books";
        try {
            getConnection();
            result = state.executeQuery(sql);
            //연결 및 결과값 result 에 저장

            System.out.println("제목 \t\t\t\t\t\t\t저자\t\t가격 \t\tisbn \t\t\t\t\t\t대출여부");
            while(result.next()) {
                String col1 = result.getString("book_name");
                String col2 = result.getString("author");
                String col3 = result.getString("price");
                String col4 = result.getString("isbn");
                String col5 = result.getString("Loan_or_not");
                System.out.print(String.format("%-25s", col1)+String.format("%s\t", col2)+String.format("%-10s", col3)+String.format("%-30s", col4)+String.format("%-10s", col5));
                System.out.println();
            }
            System.out.println();
            result.close();
            state.close();
            connection.close();

        }
        catch (SQLException throwables) {
            System.out.println("접속실패");
            System.out.println("사유 : " + throwables.getMessage());
        }

    }

    //데이터베이스 추가 항목
    public void add(){
        Scanner sc = new Scanner(System.in);
        //result 값으로 변환 가능 생각해보기
        try {
            getConnection();
            sql = "select * from tb_books where isbn =?";
            int i = 0;
            System.out.print("책명:");
            String b_name = sc.next();
            System.out.print("저자:");
            String author = sc.next();
            System.out.print("가격:");
            String price = sc.next();
            System.out.print("isbn:");
            String isbn = sc.next();
            String loan = "NOT";

            ps = connection.prepareStatement(sql);
            ps.setString(1,isbn);
            result = ps.executeQuery();
            //result에는 isbn으로 검색해서 책 정보 저장
            while (result.next()) {
                if(result.getString(4).equals(isbn)){
                    System.out.println("이미 등록 된 책 입니다.");
                    i = 1;
                }
            }

            if(i == 0) {
                sql1 = "insert into tb_books(book_name,author,price,isbn,Loan_or_not) value(?,?,?,?,?)";
                ps = connection.prepareStatement(sql1);
                ps.setString(1, b_name);
                ps.setString(2, author);
                ps.setString(3, price);
                ps.setString(4, isbn);
                ps.setString(5, loan);
                System.out.println("책명:" + b_name + "저자:" + author + "가격:" + price + "isbn:" + isbn);
            }

            ps.execute();
            state.close();
            result.close();
            connection.close();

        }
        catch (SQLException throwables) {
            System.out.println("접속실패");
            System.out.println("사유 : " + throwables.getMessage());
        }

    }

    //데이터베이스 삭제 구문
    public void delete() {

        Scanner sc = new Scanner(System.in);

        try {
            getConnection();
            sql = "select * from tb_books where book_name=?";
            ps = connection.prepareStatement(sql);
            System.out.print("책명:");
            String b_name = sc.next();
            ps.setString(1, b_name);
            result = ps.executeQuery();
            //result 에 책명으로 검색한 결과값 저장

            String re2;
            while (result.next()) {
                re2 = result.getString(5);
                //re2에 result 5번값 받기(not or loan)
                if (re2.equals("NOT")){
                    sql1 = "delete from tb_books where book_name=?";
                    ps = connection.prepareStatement(sql1);
                    ps.setString(1,b_name);
                    System.out.println("책이 정상적으로삭제되었습니다.");
                }
                else{
                    System.out.println("대출중인 책은 삭제하실수 없습니다.");
                }
            }
                ps.execute();
                state.close();
                connection.close();
        }
        catch(SQLException throwables){
                System.out.println("접속실패");
                System.out.println("사유 : " + throwables.getMessage());
        }

    }

    //데이터베이스 검색 항목
    public void search(){
        Scanner sc = new Scanner(System.in);
        try {
            getConnection();

            sql = "select * from tb_books where book_name=?";

            System.out.print("책명:");
            String b_name = sc.next();
            ps = connection.prepareStatement(sql);
            ps.setString(1, b_name);
            result = ps.executeQuery();
            //원하는 책명을 받아서 ps에 데이터값 대조후 result값에 저장해서 출력함

            ArrayList<String> books = new ArrayList<String>();
            while (result.next()) {
                books.add(result.getString("book_name"));
                books.add(result.getString("author"));
                books.add(result.getString("price"));
                books.add(result.getString("isbn"));
                books.add(result.getString("Loan_or_not"));
                System.out.println("책명: "+books.get(0)+"\t저자: "+books.get(1)+"\t가격: "+books.get(2)+"\tisbn: "+books.get(3)+"\t대출 현황: "+books.get(4));
            }

            result.close();
            state.close();
            connection.close();

        } catch (SQLException throwables) {
            System.out.println("접속실패");
            System.out.println("사유 : " + throwables.getMessage());
        }
    }


}




