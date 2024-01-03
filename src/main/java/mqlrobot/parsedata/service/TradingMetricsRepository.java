package mqlrobot.parsedata.service;

import mqlrobot.parsedata.model.RelevantTradingMetrics;
import org.springframework.data.repository.CrudRepository;

public interface TradingMetricsRepository extends CrudRepository<RelevantTradingMetrics, Long>
{

}
