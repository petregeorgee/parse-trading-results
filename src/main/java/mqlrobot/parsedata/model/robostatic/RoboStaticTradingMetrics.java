package mqlrobot.parsedata.model.robostatic;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import mqlrobot.parsedata.model.RelevantTradingMetrics;

@Getter
@Setter
public class RoboStaticTradingMetrics {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("Pass")
    private double pass;

    @JsonProperty("Result")
    private double result;

    @JsonProperty("Profit")
    private double profit;

    @JsonProperty("Expected Payoff")
    private double expectedPayoff;

    @JsonProperty("Profit Factor")
    private double profitFactor;

    @JsonProperty("Recovery Factor")
    private double recoveryFactor;

    @JsonProperty("Sharpe Ratio")
    private double sharpeRatio;

    @JsonProperty("Custom")
    private String custom;

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



    public RoboStaticTradingMetrics() {

    }

    public RoboStaticTradingMetrics(double pass, double result, double profit, double expectedPayoff, double profitFactor, double recoveryFactor, double sharpeRatio, String custom, double equityDdPercentage, double trades, double volumeValue, double tpValue, double slValue, double multiplier) {
        this.pass = pass;
        this.result = result;
        this.profit = profit;
        this.expectedPayoff = expectedPayoff;
        this.profitFactor = profitFactor;
        this.recoveryFactor = recoveryFactor;
        this.sharpeRatio = sharpeRatio;
        this.custom = custom;
        this.equityDdPercentage = equityDdPercentage;
        this.trades = trades;
        this.volumeValue = volumeValue;
        this.tpValue = tpValue;
        this.slValue = slValue;
        this.multiplier = multiplier;
    }

    public static RoboStaticRelevantTradingMetrics toRelevantTradingMetrics(RoboStaticTradingMetrics tradingMetrics) {
        RoboStaticRelevantTradingMetrics relevantMetrics = new RoboStaticRelevantTradingMetrics();
        relevantMetrics.setResult(tradingMetrics.result);
        relevantMetrics.setProfit(tradingMetrics.profit);
        relevantMetrics.setEquityDdPercentage(tradingMetrics.equityDdPercentage);
        relevantMetrics.setTrades(tradingMetrics.trades);
        relevantMetrics.setVolumeValue(tradingMetrics.volumeValue);
        relevantMetrics.setTpValue(tradingMetrics.tpValue);
        relevantMetrics.setSlValue(tradingMetrics.slValue);
        relevantMetrics.setMultiplier(tradingMetrics.multiplier);

        return relevantMetrics;
    }
}
