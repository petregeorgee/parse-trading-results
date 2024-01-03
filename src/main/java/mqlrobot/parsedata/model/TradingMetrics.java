package mqlrobot.parsedata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradingMetrics {

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


    public TradingMetrics() {

    }

    public TradingMetrics(double pass, double result, double profit, double expectedPayoff,
                          double profitFactor, double recoveryFactor, double sharpeRatio,
                          String custom, double equityDdPercentage, double trades, double distBS,
                          double distTpbTps, double volumeValue, double multiplier,
                          double maxOpenPositions, boolean maxOpenCloseAll) {
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
        this.distBS = distBS;
        this.distTpbTps = distTpbTps;
        this.volumeValue = volumeValue;
        this.multiplier = multiplier;
        this.maxOpenPositions = maxOpenPositions;
        this.maxOpenCloseAll = maxOpenCloseAll;
    }

    public static RelevantTradingMetrics toRelevantTradingMetrics(TradingMetrics tradingMetrics) {
        RelevantTradingMetrics relevantMetrics = new RelevantTradingMetrics();
        relevantMetrics.setResult(tradingMetrics.result);
        relevantMetrics.setProfit(tradingMetrics.profit);
        relevantMetrics.setEquityDdPercentage(tradingMetrics.equityDdPercentage);
        relevantMetrics.setTrades(tradingMetrics.trades);
        relevantMetrics.setDistBS(tradingMetrics.distBS);
        relevantMetrics.setDistTpbTps(tradingMetrics.distTpbTps);
        relevantMetrics.setVolumeValue(tradingMetrics.volumeValue);
        relevantMetrics.setMultiplier(tradingMetrics.multiplier);
        relevantMetrics.setMaxOpenPositions(tradingMetrics.maxOpenPositions);
        relevantMetrics.setMaxOpenCloseAll(tradingMetrics.maxOpenCloseAll);
        return relevantMetrics;
    }
}
