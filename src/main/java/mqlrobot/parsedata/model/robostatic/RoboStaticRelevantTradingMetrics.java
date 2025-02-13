package mqlrobot.parsedata.model.robostatic;

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
public class RoboStaticRelevantTradingMetrics
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

    @JsonProperty("volumeValue")
    private double volumeValue;

    @JsonProperty("tpValue")
    private double tpValue;

    @JsonProperty("slValue")
    private double slValue;

    @JsonProperty("multiplier")
    private double multiplier;

    private String period;
    private String tradingPair;
    private String timeframe;

    public RoboStaticRelevantTradingMetrics()
    {
    }

    public RoboStaticRelevantTradingMetrics(double result, double profit, double equityDdPercentage, double trades, double volumeValue, double tpValue, double slValue, double multiplier, String period, String tradingPair, String timeframe) {
        this.result = result;
        this.profit = profit;
        this.equityDdPercentage = equityDdPercentage;
        this.trades = trades;
        this.volumeValue = volumeValue;
        this.tpValue = tpValue;
        this.slValue = slValue;
        this.multiplier = multiplier;
        this.period = period;
        this.tradingPair = tradingPair;
        this.timeframe = timeframe;
    }

    @Override
    public String toString() {
        return "RoboStaticRelevantTradingMetrics{" +
                "id=" + id +
                ", result=" + result +
                ", profit=" + profit +
                ", equityDdPercentage=" + equityDdPercentage +
                ", trades=" + trades +
                ", volumeValue=" + volumeValue +
                ", tpValue=" + tpValue +
                ", slValue=" + slValue +
                ", multiplier=" + multiplier +
                ", period='" + period + '\'' +
                ", tradingPair='" + tradingPair + '\'' +
                ", timeframe='" + timeframe + '\'' +
                '}';
    }
}
