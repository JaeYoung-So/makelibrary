package com.company.librarym;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Member extends Management{

    //유저 목록 항목
    @Override
    public void list() {


        try {
            getConnection();
            result = state.executeQuery("select * from tb_library_member");

            System.out.println("이름\t\t생년월일\t\t성별\t전화번호\t\t\t이메일");
            while(result.next()) {
                String col1 = result.getString("m_name");
                String col2 = result.getString("date_of_birth");
                String col3 = result.getString("gender");
                String col4 = result.getString("phonenumber");
                String col5 = result.getString("email");
                System.out.println(col1+"\t"+col2+"\t"+col3+"\t"+col4+"\t"+col5);
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

    //유저 추가 항목
    @Override
    public void add() {


        Scanner sc = new Scanner(System.in);
        try {
            int i = 0;
            getConnection();
            sql = "select * from tb_library_member where m_name =? and date_of_birth = ?";

            System.out.print("이름:");
            String m_name = sc.next();
            System.out.print("생년월일:");
            String birth = sc.next();
            System.out.print("성별:");
            String gender = sc.next();
            System.out.print("전화번호:");
            String phonenumber = sc.next();
            System.out.print("이메일:");
            String email = sc.next();

            ps = connection.prepareStatement(sql);
            ps.setString(1,m_name);
            ps.setString(2,birth);
            result = ps.executeQuery();
            ArrayList<String> jung = new ArrayList<String>();
            while(result.next()) {
                jung.add(result.getString("m_name"));
                jung.add(result.getString("date_of_birth"));
                if(jung.get(0).equals(m_name)&&jung.get(1).equals(birth)) {
                    System.out.println(jung.get(0)+"님 은 중복 회원 입니다.");
                    i = 1;
                }
            }
            if(i == 0) {//rs구문으로 변형가능
                sql1 = "insert into tb_library_member(m_name,date_of_birth,gender,phonenumber,email) value(?,?,?,?,?)";
                ps = connection.prepareStatement(sql1);
                ps.setString(1, m_name);
                ps.setString(2, birth);
                ps.setString(3, gender);
                ps.setString(4, phonenumber);
                ps.setString(5, email);
                System.out.println("이름:" + m_name + "생일:" + birth + "성별:" + gender + "전화번호:" + phonenumber + "이메일:" + email);
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

    //유저 삭제 항목
    @Override
    public void delete() {

        Scanner sc = new Scanner(System.in);
        try {
            getConnection();

            sql = "delete from tb_library_member where m_name=?";
            ps = connection.prepareStatement(sql);

            System.out.print("이름:");
            String m_name = sc.next();
            ps.setString(1,m_name);
            ps.execute();
            state.close();
            connection.close();

        }
        catch (SQLException throwables) {
            System.out.println("접속실패");
            System.out.println("사유 : " + throwables.getMessage());
        }
    }

    //유저 검색 항목
    @Override
    public void search() {

        Scanner sc = new Scanner(System.in);
        try {
            getConnection();

            sql = "select * from tb_library_member where m_name=?";

            System.out.print("찾을 회원의 이름: ");
            String m_name = sc.next();
            ps = connection.prepareStatement(sql);
            ps.setString(1, m_name);
            result = ps.executeQuery();
            ArrayList<String> mems = new ArrayList<String>();

            while (result.next()) {
                mems.add(result.getString("m_name"));
                mems.add(result.getString("date_of_birth"));
                mems.add(result.getString("gender"));
                mems.add(result.getString("phonenumber"));
                mems.add(result.getString("email"));
                System.out.println("이름: " + mems.get(0) + "\t생년월일: " + mems.get(1) + "\t성별: " + mems.get(2) + "\t전화번호: " + mems.get(3) + "\t이메일: " + mems.get(4));
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

