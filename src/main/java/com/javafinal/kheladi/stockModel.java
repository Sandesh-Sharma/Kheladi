package com.javafinal.kheladi;

public class stockModel {
    private String symbol;
    private String name;
    private int ltp;
    private String sector;
    private String priceChange;

    public stockModel(String symbol, String name, int ltp, String sector,String priceChange){
        this.name = name;
        this.symbol = symbol;
        this.ltp = ltp;
        this.sector = sector;
        this.priceChange= priceChange;
    }
    public String getSymbol(){return symbol;}
    public String getName(){return name;}
    public int getLtp(){return ltp;}
    public String getSector(){return sector; }
    public String getPriceChange(){return priceChange;}

}
