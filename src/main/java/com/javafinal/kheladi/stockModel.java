package com.javafinal.kheladi;

public class stockModel {
    private String symbol;
    private String name;
    private int ltp;
    private String sector;

    public stockModel(String symbol, String name, int ltp, String sector){
        this.name = name;
        this.symbol = symbol;
        this.ltp = ltp;
        this.sector = sector;
    }
    public String getSymbol(){return symbol;}
    public String getName(){return name;}
    public int getLtp(){return ltp;}
    public String getSector(){return sector; }



}
