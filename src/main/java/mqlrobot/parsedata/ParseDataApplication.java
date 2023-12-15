package mqlrobot.parsedata;

import mqlrobot.parsedata.excel.ExcelReader;
import mqlrobot.parsedata.model.RelevantTradingMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class ParseDataApplication
{

    @Autowired
    private ExcelReader excelReader;

    public static void main(String[] args)
    {
        ConfigurableApplicationContext run = SpringApplication.run(ParseDataApplication.class, args);
//        DataManager bean = run.getBean(DataManager.class);
//        bean.storePositiveTradingMetricsResultsToDiskFromFile("C:\\Users\\pegeorge\\Downloads\\run2.xlsx");
    }

}
