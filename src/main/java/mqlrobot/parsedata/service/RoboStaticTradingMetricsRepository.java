package mqlrobot.parsedata.service;

import mqlrobot.parsedata.model.RelevantTradingMetrics;
import mqlrobot.parsedata.model.robostatic.RoboStaticRelevantTradingMetrics;
import org.springframework.data.repository.CrudRepository;

public interface RoboStaticTradingMetricsRepository extends CrudRepository<RoboStaticRelevantTradingMetrics, Long>
{

}
