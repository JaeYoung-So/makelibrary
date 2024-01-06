package com.company.librarym;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class Book extends Management {


    @Override
    public void list() {
        super.list();
    }

    @Override
    public void add() {
        super.add();
    }

    @Override
    public void delete() {
        super.delete();
    }

    @Override
    public void search() {
        super.search();
    }
    //책 대출항목(유저 아이디 추가 필요)
    public void loan() {
        Scanner sc = new Scanner(System.in);
        try {
            getConnection();

            sql = "select * from tb_books where book_name=?";

            System.out.print("책명:");
            String b_name = sc.next();
            ps = connection.prepareStatement(sql);
            ps.setString(1, b_name);
            result = ps.executeQuery();
            String re1;

            while (result.next()) {
                re1 = result.getString(5);
                if (re1.equals("NOT")) {
                    sql1 = "update tb_books set Loan_or_not=? where book_name=?";
                    ps = connection.prepareStatement(sql1);
                    String not = "Loan";
                    ps.setString(1, not);
                    ps.setString(2, b_name);
                    ps.execute();
                    System.out.println("대출이 완료되었습니다.");
                } else {
                    System.out.println("이미 대출중인 책 입니다");
                }

            }

            result.close();
            state.close();
            connection.close();

        } catch (SQLException throwables) {
            System.out.println("접속실패");
            System.out.println("사유 : " + throwables.getMessage());
        }
    }
    //책 반납항목
    public void return_book() {

        Scanner sc = new Scanner(System.in);
        try {
            getConnection();
            System.out.print("책명:");
            String b_name = sc.next();
            sql = "select * from tb_books where book_name=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, b_name);
            result = ps.executeQuery();
            String re2;

            while (result.next()) {
                re2 = result.getString(5);
                if (re2.equals("Loan")) {
                    sql1 = "update tb_books set Loan_or_not=? where book_name=?";
                    ps = connection.prepareStatement(sql1);
                    String not = "NOT";
                    ps.setString(1, not);
                    ps.setString(2, b_name);
                    ps.execute();
                    System.out.println("반납이 완료되었습니다.");
                } else {
                    System.out.println("이미 반납한 책 입니다");
                }
            }
            result.close();
            ps.execute();
            state.close();
            connection.close();
        } catch (SQLException throwables) {
            System.out.println("접속실패");
            System.out.println("사유 : " + throwables.getMessage());
        }

    }

}

