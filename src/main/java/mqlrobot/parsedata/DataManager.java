package mqlrobot.parsedata;

import lombok.Getter;
import mqlrobot.parsedata.excel.ExcelReader;
import mqlrobot.parsedata.model.RelevantTradingMetrics;
import mqlrobot.parsedata.model.filter.AdvancedTradingMetricsFilter;
import mqlrobot.parsedata.model.TradingMetrics;
import mqlrobot.parsedata.model.filter.BasicTradingMetricsFilter;
import mqlrobot.parsedata.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
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
    @Autowired
    private Utils utils;

    public void storePositiveTradingMetricsResultsToDiskFromFile(String filePath)
    {
        List<TradingMetrics> tradingMetrics = excelReader.getTradingMetricsFromFile(filePath);
        List<RelevantTradingMetrics> relevantTradingMetrics = tradingMetrics.stream().map(TradingMetrics::toRelevantTradingMetrics).toList();

        if(basicFilter.isEnabled())
            relevantTradingMetrics = applyPredicates(relevantTradingMetrics, getBasicFilterPredicates());

        tradingMetricsList.addAll(relevantTradingMetrics);

        System.out.println("Importing " + relevantTradingMetrics.size() + " entities from file: " + filePath);
    }

    public void processExcelFiles(String folderPath)
    {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory())
        {
            System.out.println("Invalid folder path: " + folderPath);
            return;
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0)
        {
            System.out.println("No files found in the folder: " + folderPath);
            return;
        }

        for (File file : files)
        {
            if (file.isFile() && file.getName().endsWith(".xlsx"))
            {
                System.out.println("Start scanning the folder: "+ folderPath);
                storePositiveTradingMetricsResultsToDiskFromFile(file.getAbsolutePath());
                utils.deleteAfterFiveSeconds(file.getAbsolutePath());
            }
        }
    }

    private List<Predicate<RelevantTradingMetrics>> getAdvancedFilterPredicates()
    {
        List<Predicate<RelevantTradingMetrics>> list = new ArrayList<>();

        if (advancedFilter.getProfitMin() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getProfit() >= advancedFilter.getProfitMin());
        }
        if (advancedFilter.getProfitMax() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getProfit() < advancedFilter.getProfitMax());
        }

        if (advancedFilter.getEquityDdPercentageMin() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getEquityDdPercentage() >= advancedFilter.getEquityDdPercentageMin());
        }
        if (advancedFilter.getEquityDdPercentageMax() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getEquityDdPercentage() < advancedFilter.getEquityDdPercentageMax());
        }

        if (advancedFilter.getTradesMin() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getTrades() >= advancedFilter.getTradesMin());
        }
        if (advancedFilter.getTradesMax() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getTrades() < advancedFilter.getTradesMax());
        }

        if (advancedFilter.getDistTpbTpsMin() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getDistTpbTps() >= advancedFilter.getDistTpbTpsMin());
        }
        if (advancedFilter.getDistTpbTpsMax() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getDistTpbTps() < advancedFilter.getDistTpbTpsMax());
        }

        if (advancedFilter.getResultMin() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getResult() >= advancedFilter.getResultMin());
        }
        if (advancedFilter.getResultMax() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getResult() < advancedFilter.getResultMax());
        }

        if (advancedFilter.getMaxOpenPositionsMin() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getMaxOpenPositions() >= advancedFilter.getMaxOpenPositionsMin());
        }
        if (advancedFilter.getMaxOpenPositionsMax() != null)
        {
            list.add(tradingMetrics1 -> tradingMetrics1.getMaxOpenPositions() < advancedFilter.getMaxOpenPositionsMax());
        }

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

    public List<RelevantTradingMetrics> getFilteredTradingMetrics()
    {
        return applyPredicates(tradingMetricsList, getAdvancedFilterPredicates());
    }
}
