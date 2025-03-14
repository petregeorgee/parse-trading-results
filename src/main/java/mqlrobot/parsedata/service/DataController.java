package mqlrobot.parsedata.service;

import mqlrobot.parsedata.DataManager;
import mqlrobot.parsedata.RoboStaticDataManager;
import mqlrobot.parsedata.model.RelevantTradingMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
public class DataController
{

    @Autowired
    private DataManager dataManager;
    @Autowired
    private RoboStaticDataManager dataManagerForRoboStatic;

    @PostMapping("/newExcel")
    public void storeDataFromNewExcelFile(@RequestParam("file") MultipartFile multipartFile) throws IOException
    {
        InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());
        String path = Paths.get("tmp").toAbsolutePath() + "\\"+ UUID.randomUUID() +"_tmp.xlsx";

        try (FileOutputStream fos = new FileOutputStream(path))
        {
            fos.write(multipartFile.getBytes());
        }

        dataManager.storePositiveTradingMetricsResultsToDiskFromFile(path);
    }

    @PostMapping("/fromFolder")
    public void storeDataFromFolder() throws IOException
    {
        String path = Paths.get("tmp").toAbsolutePath().toString();

        dataManager.processExcelFiles(path);
    }

    @PostMapping("/roboStaticFromFolder")
    public void storeDataRoboStaticFromFolder() throws IOException
    {
        String path = Paths.get("tmp").toAbsolutePath().toString();

        dataManagerForRoboStatic.processExcelFiles(path);
    }

    @GetMapping("/filteredData")
    @ResponseBody
    public List<RelevantTradingMetrics> getFilteredData(){
        return dataManager.getFilteredTradingMetrics();
    }
}
