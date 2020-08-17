package com.otago.otagovirtualid;

public class Papers {
    String paper1;
    String paper2;
    String paper3;
    String paper4;

    public Papers(String paper1, String paper2, String paper3, String paper4) {
        this.paper1 = paper1;
        this.paper2 = paper2;
        this.paper3 = paper3;
        this.paper4 = paper4;
    }

    public String getPaper1() {
        return paper1;
    }

    public void setPaper1(String paper1) {
        this.paper1 = paper1;
    }

    public String getPaper2() {
        return paper2;
    }

    public void setPaper2(String paper2) {
        this.paper2 = paper2;
    }

    public String getPaper3() {
        return paper3;
    }

    public void setPaper3(String paper3) {
        this.paper3 = paper3;
    }

    public String getPaper4() {
        return paper4;
    }

    public void setPaper4(String paper4) {
        this.paper4 = paper4;
    }

    @Override
    public String toString() {
        return "Papers{" +
                "paper1='" + paper1 + '\'' +
                ", paper2='" + paper2 + '\'' +
                ", paper3='" + paper3 + '\'' +
                ", paper4='" + paper4 + '\'' +
                '}';
    }
}
