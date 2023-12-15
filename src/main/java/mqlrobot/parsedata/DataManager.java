package mqlrobot.parsedata;

import lombok.Getter;
import mqlrobot.parsedata.excel.ExcelReader;
import mqlrobot.parsedata.model.RelevantTradingMetrics;
import mqlrobot.parsedata.model.filter.AdvancedTradingMetricsFilter;
import mqlrobot.parsedata.model.TradingMetrics;
import mqlrobot.parsedata.model.filter.BasicTradingMetricsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class DataManager
{
    @Autowired
    private ExcelReader excelReader;
    @Autowired
    private AdvancedTradingMetricsFilter advancedFilter;
    @Autowired
    private BasicTradingMetricsFilter basicFilter;
    @Getter
    private List<RelevantTradingMetrics> tradingMetricsList = new ArrayList<>();

    public void storePositiveTradingMetricsResultsToDiskFromFile(String filePath)
    {
        List<TradingMetrics> tradingMetrics = excelReader.getTradingMetricsFromFile(filePath);
        List<RelevantTradingMetrics> relevantTradingMetrics = tradingMetrics.stream().map(TradingMetrics::toRelevantTradingMetrics).toList();

        relevantTradingMetrics = applyPredicates(relevantTradingMetrics, getBasicFilterPredicates());

        tradingMetricsList.addAll(relevantTradingMetrics);

        System.out.println("Size: " + relevantTradingMetrics.size());
        System.out.println(relevantTradingMetrics);
    }

    private List<Predicate<RelevantTradingMetrics>> getAdvancedFilterPredicates() {
        List<Predicate<RelevantTradingMetrics>> list = new ArrayList<>();

        if (advancedFilter.getProfitMin() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getProfit() >= advancedFilter.getProfitMin());
        }
        if (advancedFilter.getProfitMax() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getProfit() < advancedFilter.getProfitMax());
        }

        if (advancedFilter.getEquityDdPercentageMin() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getEquityDdPercentage() >= advancedFilter.getEquityDdPercentageMin());
        }
        if (advancedFilter.getEquityDdPercentageMax() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getEquityDdPercentage() < advancedFilter.getEquityDdPercentageMax());
        }

        if (advancedFilter.getTradesMin() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getTrades() >= advancedFilter.getTradesMin());
        }
        if (advancedFilter.getTradesMax() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getTrades() < advancedFilter.getTradesMax());
        }

        if (advancedFilter.getDistTpbTpsMin() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getDistTpbTps() >= advancedFilter.getDistTpbTpsMin());
        }
        if (advancedFilter.getDistTpbTpsMax() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getDistTpbTps() < advancedFilter.getDistTpbTpsMax());
        }

        if (advancedFilter.getResultMin() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getResult() >= advancedFilter.getResultMin());
        }
        if (advancedFilter.getResultMax() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getResult() < advancedFilter.getResultMax());
        }

        if (advancedFilter.getMaxOpenPositionsMin() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getMaxOpenPositions() >= advancedFilter.getMaxOpenPositionsMin());
        }
        if (advancedFilter.getMaxOpenPositionsMax() != null) {
            list.add(tradingMetrics1 -> tradingMetrics1.getMaxOpenPositions() < advancedFilter.getMaxOpenPositionsMax());
        }

        // Add similar checks for other fields...

        return list;
    }


    private List<Predicate<RelevantTradingMetrics>> getBasicFilterPredicates()
    {
        List<Predicate<RelevantTradingMetrics>> list = new ArrayList<>();

        Predicate<RelevantTradingMetrics> positiveProfit = tradingMetrics1 -> tradingMetrics1.getProfit() > basicFilter.getProfitAtLeast();
        list.add(positiveProfit);

        Predicate<RelevantTradingMetrics> notSoRisckyDrawDown = tradingMetrics1 -> tradingMetrics1.getEquityDdPercentage() < basicFilter.getEquityDdPercentageLessThan();
        list.add(notSoRisckyDrawDown);

        return list;
    }

    private List<RelevantTradingMetrics> applyPredicates(List<RelevantTradingMetrics> tradingMetrics, List<Predicate<RelevantTradingMetrics>> predicates)
    {
        for (Predicate<RelevantTradingMetrics> predicate : predicates)
        {
            tradingMetrics = tradingMetrics.stream().filter(predicate).collect(Collectors.toList());
        }

        return tradingMetrics;
    }

    public List<RelevantTradingMetrics> getFilteredTradingMetrics(){
        return applyPredicates(tradingMetricsList, getAdvancedFilterPredicates());
    }
}
