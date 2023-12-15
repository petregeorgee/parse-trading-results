package mqlrobot.parsedata.model.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class BasicTradingMetricsFilter
{
    private double profitAtLeast;
    private double equityDdPercentageLessThan;

    public BasicTradingMetricsFilter()
    {
        this.profitAtLeast = 0;
        this.equityDdPercentageLessThan = 25;
    }
}
