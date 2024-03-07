package fincode.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class StockFullInfoVO {


    String ts_code;
    String name;
    String list_date;
    Integer stock_id;
    String area;
    double  close;
    double  high;
    double  low;
    double  open;
    double  pct_chg;
    double  pre_close;
    double  amount;
    double  change;
    double  vol;
    Integer industry_id;
    String enname;
    Integer time;
    public StockFullInfoVO(){}
    public  StockFullInfoVO(StockDetailPO detail,StockPricePO price){
        stock_id=detail.getStock_id();
        ts_code=detail.getTs_code();
        name=detail.getName();
        list_date=detail.getList_date();
        area=detail.getArea();
        industry_id=detail.getIndustry_id();
        close=price.getClose();
        high=price.getHigh();
        low=price.getLow();
        open=price.getOpen();
        pct_chg=price.getPct_chg();
        pre_close=price.getPre_close();
        amount=price.getAmount();
        change=price.getChange();
        vol=price.getVol();
        enname=price.getCompanyId();
        time=price.getTime();
    }
}
