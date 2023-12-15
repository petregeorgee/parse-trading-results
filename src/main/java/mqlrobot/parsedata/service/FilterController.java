package mqlrobot.parsedata.service;

import mqlrobot.parsedata.model.filter.AdvancedTradingMetricsFilter;
import mqlrobot.parsedata.model.filter.BasicTradingMetricsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class FilterController
{

    @Autowired
    private BasicTradingMetricsFilter basicTradingMetricsFilter;

    @Autowired
    private AdvancedTradingMetricsFilter tradingFilter;

    @GetMapping("/basicDataFilter")
    @ResponseBody
    public BasicTradingMetricsFilter getBasicDataFilter(){
        return basicTradingMetricsFilter;
    }

    @GetMapping("/advancedDataFilter")
    @ResponseBody
    public AdvancedTradingMetricsFilter getAdvancedDataFilter(){
        return tradingFilter;
    }

    @PutMapping("/positiveDataFilter")
    @ResponseBody
    public void changePositiveDataFilter(@RequestBody BasicTradingMetricsFilter newTradingSavingFilter){
        basicTradingMetricsFilter.setProfitAtLeast(newTradingSavingFilter.getProfitAtLeast());
        basicTradingMetricsFilter.setEquityDdPercentageLessThan(newTradingSavingFilter.getEquityDdPercentageLessThan());
    }

    @PutMapping("/advancedDataFilter")
    @ResponseBody
    public void changeAdvancedDataFilter(@RequestBody AdvancedTradingMetricsFilter newTradingFilter){
        tradingFilter.setProfitMin(newTradingFilter.getProfitMin());
        tradingFilter.setTradesMin(newTradingFilter.getTradesMin());
        tradingFilter.setDistTpbTpsMin(newTradingFilter.getDistTpbTpsMin());
        tradingFilter.setResultMin(newTradingFilter.getResultMin());
        tradingFilter.setEquityDdPercentageMax(newTradingFilter.getEquityDdPercentageMax());
        tradingFilter.setMaxOpenPositionsMax(newTradingFilter.getMaxOpenPositionsMax());
    }



}
