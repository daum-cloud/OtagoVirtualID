package com.otago.otagovirtualid;

public class User {
    String authID;
    String email;
    String enrollingPeriod;
    String firstName;
    String lastName;
    Papers papers;
    String password;
    String phone;
    String studentCode;
    String studentID;
    String uRLCurrPhoto;
    String uRLPrevPhoto;
    String uRLProofDoc;

    public User(){

    }

    public User(String authID, String email, String enrollingPeriod, String firstName, String lastName, Papers papers, String password, String phone, String studentCode, String studentID, String uRLCurrPhoto, String uRLPrevPhoto, String uRLProofDoc) {
        this.authID = authID;
        this.email = email;
        this.enrollingPeriod = enrollingPeriod;
        this.firstName = firstName;
        this.lastName = lastName;
        this.papers = papers;
        this.password = password;
        this.phone = phone;
        this.studentCode = studentCode;
        this.studentID = studentID;
        this.uRLCurrPhoto = uRLCurrPhoto;
        this.uRLPrevPhoto = uRLPrevPhoto;
        this.uRLProofDoc = uRLProofDoc;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnrollingPeriod() {
        return enrollingPeriod;
    }

    public void setEnrollingPeriod(String enrollingPeriod) {
        this.enrollingPeriod = enrollingPeriod;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Papers getPapers() {
        return papers;
    }

    public void setPapers(Papers papers) {
        this.papers = papers;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getuRLCurrPhoto() {
        return uRLCurrPhoto;
    }

    public void setuRLCurrPhoto(String uRLCurrPhoto) {
        this.uRLCurrPhoto = uRLCurrPhoto;
    }

    public String getuRLPrevPhoto() {
        return uRLPrevPhoto;
    }

    public void setuRLPrevPhoto(String uRLPrevPhoto) {
        this.uRLPrevPhoto = uRLPrevPhoto;
    }

    public String getuRLProofDoc() {
        return uRLProofDoc;
    }

    public void setuRLProofDoc(String uRLProofDoc) {
        this.uRLProofDoc = uRLProofDoc;
    }

    @Override
    public String toString() {
        return "User{" +
                "authID='" + authID + '\'' +
                ", email='" + email + '\'' +
                ", enrollingPeriod='" + enrollingPeriod + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", papers=" + papers +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", studentCode='" + studentCode + '\'' +
                ", studentID='" + studentID + '\'' +
                ", uRLCurrPhoto='" + uRLCurrPhoto + '\'' +
                ", uRLPrevPhoto='" + uRLPrevPhoto + '\'' +
                ", uRLProofDoc='" + uRLProofDoc + '\'' +
                '}';
    }
}
