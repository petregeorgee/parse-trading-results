package mqlrobot.parsedata;

import mqlrobot.parsedata.excel.RoboStaticExcelReader;
import mqlrobot.parsedata.model.RunArguments;
import mqlrobot.parsedata.model.robostatic.RoboStaticRelevantTradingMetrics;
import mqlrobot.parsedata.model.robostatic.RoboStaticTradingMetrics;
import mqlrobot.parsedata.service.RoboStaticTradingMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoboStaticDataManager extends DataManager {

    @Autowired
    private RoboStaticExcelReader excelReader;

    @Autowired
    private RoboStaticTradingMetricsRepository  tradingMetricsRepository;

    public void storePositiveTradingMetricsResultsToDiskFromFile(String filePath)
    {
        List<RoboStaticTradingMetrics> tradingMetrics = excelReader.getTradingMetricsFromFileX(filePath);
        List<RoboStaticRelevantTradingMetrics> relevantTradingMetrics = tradingMetrics.stream().map(RoboStaticTradingMetrics::toRelevantTradingMetrics).toList();
        relevantTradingMetrics.forEach(relevantTradingMetrics1 ->
        {
            relevantTradingMetrics1.setTradingPair(getRunArgument(filePath, RunArguments.TRADING_PAIR));
            relevantTradingMetrics1.setPeriod(getRunArgument(filePath, RunArguments.PERIOD));
            relevantTradingMetrics1.setTimeframe(getRunArgument(filePath, RunArguments.TIMEFRAME));
        });


        tradingMetricsRepository.saveAll(relevantTradingMetrics);

        System.out.println("Imported " + relevantTradingMetrics.size() + " entities from file: " + filePath);
    }

    String getRunArgument(String filePath, RunArguments runArguments)
    {
        filePath = filePath.substring(filePath.lastIndexOf("\\") + 1);
        String[] parts = filePath.replace(".xlsx", "").split("\\^");
        if (parts.length >= 3)
            if (runArguments.equals(RunArguments.PERIOD))
                return parts[parts.length - 3];
            else if (runArguments.equals(RunArguments.TRADING_PAIR))
                return parts[parts.length - 2];
            else if (runArguments.equals(RunArguments.TIMEFRAME))
                return parts[parts.length - 1];
        return "";
    }
}
