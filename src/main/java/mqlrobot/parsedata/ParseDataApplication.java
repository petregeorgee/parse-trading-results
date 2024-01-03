package mqlrobot.parsedata;

import mqlrobot.parsedata.excel.ExcelReader;
import mqlrobot.parsedata.model.RelevantTradingMetrics;
import mqlrobot.parsedata.service.TradingMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

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

    @Bean
    public CommandLineRunner demo(TradingMetricsRepository repository)
    {
        return (args) ->
        {
//             repository.save(new RelevantTradingMetrics(75.0, 30.0, 10.0, 150.0, 20.0, 8.0, 3000.0, 1.5, 3.0, false));
//             repository.save(new RelevantTradingMetrics(75.0, 30.0, 10.0, 150.0, 20.0, 8.0, 3000.0, 1.5, 3.0, false));
//             repository.save(new RelevantTradingMetrics(120.0, 60.0, 20.0, 250.0, 40.0, 15.0, 6000.0, 2.5, 7.0, true));
        };
    }
}
