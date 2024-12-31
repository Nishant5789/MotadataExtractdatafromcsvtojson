package org.example;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/java/org/example/Benchmark.csv");

        List<Rule> resultList = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {

            String headerLine = reader.readLine();
            System.out.println(headerLine);
            String[] headers = headerLine.split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");

                Map<String, String> rowMap = new HashMap<>();

                for (int i = 0; i < headers.length; i++) {
                    rowMap.put(headers[i], i < columns.length ? columns[i] : "");  // Handle missing columns
                }


                // Extract only the required fields and map them to ResultObject
                RuleContext rulecontext = new RuleContext();
                rulecontext.setRuleCheckCategory(rowMap.get("Rule configuration"));
                rulecontext.setRuleCheckType(rowMap.get("Rule Check in"));
                rulecontext.setCondition(rowMap.get("conditions"));
                rulecontext.setResultPattern(rowMap.get("result.pattern"));
//                rulecontext.setOccurence(Integer.parseInt(rowMap.get("occurence")));
                rulecontext.setOperator(rowMap.get("operator"));

                RuleControl ruleControl1 = new RuleControl();
                RuleControl ruleControl2 = new RuleControl();

                ExtractCISControlColumns Ciscontrolobject1 = new ExtractCISControlColumns(rowMap.get("CIS Controls1"));
                ExtractCISControlColumns Ciscontrolobject2 = new ExtractCISControlColumns(rowMap.get("CIS Controls2"));

                ruleControl1.setRuleControlVersion(rowMap.get("CIS Safeguards 1 (v8)"));
                ruleControl1.setRuleControlName(Ciscontrolobject1.getControl());
                ruleControl1.setRuleControlDescription(Ciscontrolobject1.getDescription());

                ruleControl2.setRuleControlVersion(rowMap.get("CIS Safeguards 1 (v7)"));
                ruleControl2.setRuleControlName(Ciscontrolobject2.getControl());
                ruleControl2.setRuleControlDescription(Ciscontrolobject2.getDescription());

                String igValue12 = rowMap.get("v8 IG2");
                String igValue13 = rowMap.get("v8 IG3");

                String igValue22 = rowMap.get("v7 IG2");
                String igValue23 = rowMap.get("v7 IG3");


                List<String> igList1 = Arrays.asList(igValue12,igValue13);
                List<String> igList2 = Arrays.asList(igValue22,igValue23);

                ruleControl1.setRuleControlIg(igList1);
                ruleControl2.setRuleControlIg(igList2);

                Rule result = new Rule();
                result.setRuleContext(rulecontext);
                result.setRuleControls(Arrays.asList(ruleControl1,ruleControl2));
                result.setRuleName(rowMap.get("Title"));
                result.setRuleCategory(rowMap.get("Assessment Status"));
                result.setRuleAutoRemediation(rowMap.get("Remediation Procedure"));
                result.setRuleDescription(rowMap.get("Description"));
                result.setRuleSeverity(rowMap.get("Rule Severity"));
                result.setRuleImpact(rowMap.get("Impact Statement"));
                result.setRuleDefaultValue(rowMap.get("Default Value"));
                result.setRuleReferences(rowMap.get("References"));
                result.setRuleAdditionalInformation(rowMap.get(""));

                resultList.add(result);
            }

            resultList.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error while reading the CSV: " + e.getMessage());
            e.printStackTrace();
        }



    }
}