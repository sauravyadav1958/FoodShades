package com.example.project.Models;

public class Users
        {
    String username, mail, password, UserID, number;

            public Users( String username, String mail, String password, String UserID, String number) {

                this.username = username;
                this.mail = mail;
                this.password = password;
                this.UserID = UserID;
                this.number = number;
            }
            public Users(){}

            public Users(String username, String mail, String password, String number) {

                this.username = username;
                this.mail = mail;
                this.password = password;
                this.number = number;
            //    this.UserID = UserID;
            }


         public String getNumber() {
             return number;
           }
            public void setNumber(String number) {
                this.number = number;
            }

          public String getUserID() {
              return UserID;
          }
            public void setUserID(String userID) {
                this.UserID = userID;
            }

            public String getUsername() {
                return username;
            }
            public void setUsername(String username) {
                this.username = username;
            }

            public String getMail() {
                return mail;
            }
            public void setMail(String mail) {
                this.mail = mail;
            }

            public String getPassword() {
                return password;
            }
            public void setPassword(String password) {
                this.password = password;
            }

        }
