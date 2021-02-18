package com.tiggs.core;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OutputFileTest {

    @Test
    public void verifyValues() throws IOException {
        OutputFile outputFile = new OutputFile();
        ArrayList<String> valueList = new ArrayList();
        valueList.add("hdfs://localhost:9000/ElData/Testfile1.pdf");
        valueList.add("hdfs://localhost:9000/ElData/Testfile2.pdf");
        valueList.add("hdfs://localhost:9000/ElData/Testfile3.pdf");
        valueList.add("hdfs://localhost:9000/ElData/Testfile4.pdf");
        outputFile.createElsSummaryFile(valueList);
        File file = new File("ElsSearchSummary.txt");
        List<String> content = Files.readAllLines(file.toPath(), StandardCharsets.US_ASCII);
        String lastValueInList = "4." + valueList.get(valueList.size() - 1);
        String lastLineInFile = content.get(content.size() - 1);
        assertTrue(lastLineInFile.equals(lastValueInList));
    }

}
