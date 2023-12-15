package mqlrobot.parsedata.model.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class AdvancedTradingMetricsFilter
{
    private Double resultMin;
    private Double resultMax;
    private Double profitMin;
    private Double profitMax;
    private Double equityDdPercentageMin;
    private Double equityDdPercentageMax;
    private Double tradesMin;
    private Double tradesMax;
    private Double distTpbTpsMin;
    private Double distTpbTpsMax;
    private Double maxOpenPositionsMin;
    private Double maxOpenPositionsMax;

    public AdvancedTradingMetricsFilter()
    {
        this.resultMin = 70_000.0;
        this.profitMin = 20_000.0;
        this.equityDdPercentageMax = 19.0;
        this.tradesMin = 300.0;
        this.distTpbTpsMin = 3200.0;
        this.maxOpenPositionsMax = 6.0;
    }
}
