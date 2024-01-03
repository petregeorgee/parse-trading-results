package mqlrobot.parsedata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RelevantTradingMetrics
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

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

    private String period;
    private String tradingPair;

    public RelevantTradingMetrics()
    {
    }

    public RelevantTradingMetrics(double result, double profit, double equityDdPercentage, double trades, double distBS, double distTpbTps, double volumeValue, double multiplier, double maxOpenPositions, boolean maxOpenCloseAll)
    {
        this.result = result;
        this.profit = profit;
        this.equityDdPercentage = equityDdPercentage;
        this.trades = trades;
        this.distBS = distBS;
        this.distTpbTps = distTpbTps;
        this.volumeValue = volumeValue;
        this.multiplier = multiplier;
        this.maxOpenPositions = maxOpenPositions;
        this.maxOpenCloseAll = maxOpenCloseAll;
    }

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
