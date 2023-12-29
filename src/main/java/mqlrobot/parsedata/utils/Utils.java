package mqlrobot.parsedata.utils;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class Utils
{
    public void deleteAfterFiveSeconds(String path)
    {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            try
            {
                Files.deleteIfExists(Path.of(path));
            } catch (IOException e)
            {

            }
        },5,  TimeUnit.SECONDS);
    }
}
