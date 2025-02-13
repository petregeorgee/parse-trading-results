package mqlrobot.parsedata.excel;

import mqlrobot.parsedata.model.robostatic.RoboStaticTradingMetrics;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoboStaticExcelReader extends ExcelReader{

    public List<RoboStaticTradingMetrics> getTradingMetricsFromFileX(String filePath) {
        List<RoboStaticTradingMetrics> tradingMetricsList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isFirstRow = true;

            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Skip the first row (headers)
                }

                RoboStaticTradingMetrics tradingMetrics = new RoboStaticTradingMetrics();
                int cellIndex = 0;

                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            setFieldValue(tradingMetrics, cellIndex, cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                setFieldValue(tradingMetrics, cellIndex, cell.getDateCellValue() + "");
                            } else {
                                setFieldValue(tradingMetrics, cellIndex, cell.getNumericCellValue() + "");
                            }
                            break;
                        case BOOLEAN:
                            setFieldValue(tradingMetrics, cellIndex, cell.getBooleanCellValue() + "");
                            break;
                        case FORMULA:
                            setFieldValue(tradingMetrics, cellIndex, cell.getCellFormula() + "");
                            break;
                        default:
                            setFieldValue(tradingMetrics, cellIndex, " ");
                    }
                    cellIndex++;
                }

                tradingMetricsList.add(tradingMetrics);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        utils.deleteAfterFiveSeconds(filePath);

        return tradingMetricsList;
    }

    private void setFieldValue(RoboStaticTradingMetrics tradingMetrics, int cellIndex, String cellValue) {
        switch (cellIndex) {
            case 0:
                tradingMetrics.setPass(Double.parseDouble(cellValue));
                break;
            case 1:
                tradingMetrics.setResult(Double.parseDouble(cellValue));
                break;
            case 2:
                tradingMetrics.setProfit(Double.parseDouble(cellValue));
                break;
            case 3:
                tradingMetrics.setExpectedPayoff(Double.parseDouble(cellValue));
                break;
            case 4:
                double x;
                try {
                    x = Double.parseDouble(cellValue);
                } catch (NumberFormatException e){
                    x = 0.0;
                }
                tradingMetrics.setProfitFactor(x);
                break;
            case 5:
                tradingMetrics.setRecoveryFactor(Double.parseDouble(cellValue));
                break;
            case 6:
                tradingMetrics.setSharpeRatio(Double.parseDouble(cellValue));
                break;
            case 7:
                tradingMetrics.setCustom(cellValue);
                break;
            case 8:
                tradingMetrics.setEquityDdPercentage(Double.parseDouble(cellValue));
                break;
            case 9:
                tradingMetrics.setTrades(Double.parseDouble(cellValue));
                break;
            case 10:
                tradingMetrics.setVolumeValue(Double.parseDouble(cellValue));
                break;
            case 11:
                tradingMetrics.setTpValue(Double.parseDouble(cellValue));
                break;
            case 12:
                tradingMetrics.setSlValue(Double.parseDouble(cellValue));
                break;
            case 13:
                tradingMetrics.setMultiplier(Double.parseDouble(cellValue));
                break;
            default:
                // Ignore if the cellIndex is out of bounds
        }
    }
}
