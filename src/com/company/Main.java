package com.company;
import com.company.librarym.*;
import java.util.Scanner;
import java.sql.*;

public class Main {

    public static void main(String[] args) {

        run();

    }

    public static void run() {
        Scanner sc = new Scanner(System.in);
        Member m = new Member();
        Book b = new Book();

        while(true) {
            System.out.println("1.회원관리 2.도서관리 3. 종료");
            System.out.println("원하는 번호를 입력하시오");
            int n = sc.nextInt();

            if (n == 1) {//회원 관리 항목

                System.out.println("1.회원등록 2.회원 검색 3.회원목록 4.회원탈퇴");
                System.out.println("원하는 번호를 입력하시오");
                n = sc.nextInt();
                if(n==1){
                    m.add();
                }
                else if(n==2){
                    m.search();
                }
                else if(n==3){
                    m.list();
                }
                else if(n==4){
                    m.delete();
                }
                else{
                    System.out.println("잘못입력하셨습니다.");
                }

            }

            else if (n == 2) {//도서관리 항목

                System.out.println("1.도서등록 2.도서 검색 3.도서목록 4.도서대출 5.도서반납 6.도서 삭제\n");
                System.out.println("원하는 번호를 입력하시오");
                n = sc.nextInt();
                if(n==1){
                    b.add();
                }
                else if(n==2){
                    b.search();
                }
                else if(n==3){
                    b.list();
                }
                else if(n==4){
                    b.loan();
                }
                else if(n==5){
                    b.return_book();
                }
                else if(n==6){
                    b.delete();
                }
                else{
                    System.out.println("잘못입력하셨습니다.");
                }
            }

            else if (n == 3) {//종료 항목
                return;
            }

            else {
                System.out.println("잘못입력하셨습니다.");
            }

        }

    }


}
