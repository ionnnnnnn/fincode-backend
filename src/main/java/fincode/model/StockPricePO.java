package fincode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockPricePO {
    Integer id;
    double  amount;
    double  change;
    double  close;
    String  companyId;
    double  high;
    double  low;
    double  open;
    double  pct_chg;
    double  pre_close;
    double  vol;
    Integer time;
}
