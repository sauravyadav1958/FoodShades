package com.example.project.Models;

public class User
        {
    String firstName, mail, password, UserID, lastName;

            public User(String firstName, String mail, String password, String UserID, String lastName) {

                this.firstName = firstName;
                this.mail = mail;
                this.password = password;
                this.UserID = UserID;
                this.lastName = lastName;
            }
            public User(){}

            public User(String firstName, String mail, String password, String lastName) {

                this.firstName = firstName;
                this.mail = mail;
                this.password = password;
                this.lastName = lastName;
            //    this.UserID = UserID;
            }


         public String getLastName() {
             return lastName;
           }
            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

          public String getUserID() {
              return UserID;
          }
            public void setUserID(String userID) {
                this.UserID = userID;
            }

            public String getFirstName() {
                return firstName;
            }
            public void setFirstName(String firstName) {
                this.firstName = firstName;
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
