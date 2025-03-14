package mqlrobot.parsedata;

import mqlrobot.parsedata.excel.ExcelReader;
import mqlrobot.parsedata.model.RelevantTradingMetrics;
import mqlrobot.parsedata.model.RunArguments;
import mqlrobot.parsedata.model.filter.AdvancedTradingMetricsFilter;
import mqlrobot.parsedata.model.TradingMetrics;
import mqlrobot.parsedata.model.filter.BasicTradingMetricsFilter;
import mqlrobot.parsedata.service.TradingMetricsRepository;
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
    protected AdvancedTradingMetricsFilter advancedFilter;
    @Autowired
    protected BasicTradingMetricsFilter basicFilter;
    @Autowired
    private TradingMetricsRepository tradingMetricsRepository;

    public void storePositiveTradingMetricsResultsToDiskFromFile(String filePath)
    {
        List<TradingMetrics> tradingMetrics = excelReader.getTradingMetricsFromFile(filePath);
        List<RelevantTradingMetrics> relevantTradingMetrics = tradingMetrics.stream().map(TradingMetrics::toRelevantTradingMetrics).toList();
        relevantTradingMetrics.forEach(relevantTradingMetrics1 ->
        {
            relevantTradingMetrics1.setTradingPair(getRunArgument(filePath, RunArguments.TRADING_PAIR));
            relevantTradingMetrics1.setPeriod(getRunArgument(filePath, RunArguments.PERIOD));
        });

        if (basicFilter.isEnabled())
            relevantTradingMetrics = applyPredicates(relevantTradingMetrics, getBasicFilterPredicates());

        tradingMetricsRepository.saveAll(relevantTradingMetrics);

        System.out.println("Imported " + relevantTradingMetrics.size() + " entities from file: " + filePath);
    }

    String getRunArgument(String filePath, RunArguments runArguments)
    {
        String[] parts = filePath.replace(".xlsx", "").split("\\^");
        if (parts.length >= 2)
            if (runArguments.equals(RunArguments.PERIOD))
                return parts[parts.length - 2];
            else if (runArguments.equals(RunArguments.TRADING_PAIR))
                return parts[parts.length - 1];
        return "";
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

        System.out.println("Start scanning the folder: " + folderPath);

        for (File file : files)
        {
            if (file.isFile() && file.getName().endsWith(".xlsx"))
            {
                storePositiveTradingMetricsResultsToDiskFromFile(file.getAbsolutePath());
            }
        }
    }

    private List<Predicate<RelevantTradingMetrics>> getAdvancedFilterPredicates()
    {
        List<Predicate<RelevantTradingMetrics>> advancedPredicates = new ArrayList<>();

        if (advancedFilter.getProfitMin() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getProfit() >= advancedFilter.getProfitMin());
        }
        if (advancedFilter.getProfitMax() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getProfit() < advancedFilter.getProfitMax());
        }

        if (advancedFilter.getEquityDdPercentageMin() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getEquityDdPercentage() >= advancedFilter.getEquityDdPercentageMin());
        }
        if (advancedFilter.getEquityDdPercentageMax() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getEquityDdPercentage() < advancedFilter.getEquityDdPercentageMax());
        }

        if (advancedFilter.getTradesMin() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getTrades() >= advancedFilter.getTradesMin());
        }
        if (advancedFilter.getTradesMax() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getTrades() < advancedFilter.getTradesMax());
        }

        if (advancedFilter.getDistTpbTpsMin() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getDistTpbTps() >= advancedFilter.getDistTpbTpsMin());
        }
        if (advancedFilter.getDistTpbTpsMax() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getDistTpbTps() < advancedFilter.getDistTpbTpsMax());
        }

        if (advancedFilter.getResultMin() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getResult() >= advancedFilter.getResultMin());
        }
        if (advancedFilter.getResultMax() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getResult() < advancedFilter.getResultMax());
        }

        if (advancedFilter.getMaxOpenPositionsMin() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getMaxOpenPositions() >= advancedFilter.getMaxOpenPositionsMin());
        }
        if (advancedFilter.getMaxOpenPositionsMax() != null)
        {
            advancedPredicates.add(tradingMetrics1 -> tradingMetrics1.getMaxOpenPositions() < advancedFilter.getMaxOpenPositionsMax());
        }

        return advancedPredicates;
    }


    List<Predicate<RelevantTradingMetrics>> getBasicFilterPredicates()
    {
        List<Predicate<RelevantTradingMetrics>> basicPredicates = new ArrayList<>();

        Predicate<RelevantTradingMetrics> positiveProfit = tradingMetrics1 -> tradingMetrics1.getProfit() > basicFilter.getProfitAtLeast();
        basicPredicates.add(positiveProfit);

        Predicate<RelevantTradingMetrics> notSoRisckyDrawDown = tradingMetrics1 -> tradingMetrics1.getEquityDdPercentage() < basicFilter.getEquityDdPercentageLessThan();
        basicPredicates.add(notSoRisckyDrawDown);

        return basicPredicates;
    }

    List<RelevantTradingMetrics> applyPredicates(List<RelevantTradingMetrics> tradingMetrics, List<Predicate<RelevantTradingMetrics>> predicates)
    {
        for (Predicate<RelevantTradingMetrics> predicate : predicates)
        {
            tradingMetrics = tradingMetrics.stream().filter(predicate).collect(Collectors.toList());
        }

        return tradingMetrics;
    }

    public List<RelevantTradingMetrics> getFilteredTradingMetrics()
    {
        List<RelevantTradingMetrics> all = (List<RelevantTradingMetrics>) tradingMetricsRepository.findAll();
        return applyPredicates(all, getAdvancedFilterPredicates());
    }
}
