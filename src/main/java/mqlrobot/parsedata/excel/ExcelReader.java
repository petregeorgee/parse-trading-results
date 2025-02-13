package mqlrobot.parsedata.excel;

import mqlrobot.parsedata.model.TradingMetrics;
 import mqlrobot.parsedata.utils.Utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelReader {

    @Autowired
    protected Utils utils;

    public List<TradingMetrics> getTradingMetricsFromFile(String filePath) {
        List<TradingMetrics> tradingMetricsList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isFirstRow = true;

            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Skip the first row (headers)
                }

                TradingMetrics tradingMetrics = new TradingMetrics();
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

    private void setFieldValue(TradingMetrics tradingMetrics, int cellIndex, String cellValue) {
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
                tradingMetrics.setProfitFactor(Double.parseDouble(cellValue));
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
                tradingMetrics.setDistBS(Double.parseDouble(cellValue));
                break;
            case 11:
                tradingMetrics.setDistTpbTps(Double.parseDouble(cellValue));
                break;
            case 12:
                tradingMetrics.setVolumeValue(Double.parseDouble(cellValue));
                break;
            case 13:
                tradingMetrics.setMultiplier(Double.parseDouble(cellValue));
                break;
            case 14:
                tradingMetrics.setMaxOpenPositions(Double.parseDouble(cellValue));
                break;
            case 15:
                tradingMetrics.setMaxOpenCloseAll(Boolean.parseBoolean(cellValue));
                break;
            default:
                // Ignore if the cellIndex is out of bounds
        }
    }
}

