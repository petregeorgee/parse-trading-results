package mqlrobot.parsedata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelevantTradingMetrics
{
    @JsonProperty("Result")
    private double result;

    @JsonProperty("Profit")
    private double profit;

    @JsonProperty("Equity DD %")
    private double equityDdPercentage;

    @JsonProperty("Trades")
    private double trades;

    @JsonProperty("Dist_B_S")
    private double distBS;

    @JsonProperty("Dist_TPB_TPS")
    private double distTpbTps;

    @JsonProperty("Volume_Value")
    private double volumeValue;

    @JsonProperty("Multiplier")
    private double multiplier;

    @JsonProperty("Max_Open_Positions")
    private double maxOpenPositions;

    @JsonProperty("Max_Open_Close_All")
    private boolean maxOpenCloseAll;

    @Override
    public String toString()
    {
        return "RelevantTradingMetrics{" +
                "result=" + result +
                ", profit=" + profit +
                ", equityDdPercentage=" + equityDdPercentage +
                ", trades=" + trades +
                ", distBS=" + distBS +
                ", distTpbTps=" + distTpbTps +
                ", volumeValue=" + volumeValue +
                ", multiplier=" + multiplier +
                ", maxOpenPositions=" + maxOpenPositions +
                ", maxOpenCloseAll=" + maxOpenCloseAll +
                '}';
    }
}
