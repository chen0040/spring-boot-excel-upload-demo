package com.github.chen0040.bootslingshot.utils;


import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 10/7/2016.
 */
public class DataTable implements Serializable {
    private DataColumnCollection dataColumns = new DataColumnCollection();
    private DataRowCollection dataRows = new DataRowCollection();
    private String name = "";
    private String projectId = "";
    private String id = "";

    public DataTable(){

    }

    public DataTable(List<String> columnNames, List<DataRow> rows) {
        for(int i=0; i < columnNames.size(); ++i){
            columns().add(columnNames.get(i));
        }
        dataRows.addAll(rows);
    }

    public DataTable(int columnCount) {
        for(int i=0; i < columnCount; ++i) {
            columns().add("c" + (i+1));
        }
    }

    public DataTable(String... columnNames){
        for(int i=0; i < columnNames.length; ++i){
            columns().add(columnNames[i]);
        }
    }


    public DataColumnCollection columns(){
        return dataColumns;
    }

    public void setDataColumns(List<DataColumn> columns){
        this.dataColumns = new DataColumnCollection(columns);
    }

    public List<DataColumn> getDataColumns(){
        return dataColumns.getColumns();
    }

    public List<DataRow> getDataRows(){
        return dataRows.getRows();
    }

    public void setDataRows(List<DataRow> rows){
        this.dataRows = new DataRowCollection(rows);
    }

    public DataRowCollection rows(){
        return dataRows;
    }

    public DataRow newRow(){
        return new DataRow(dataColumns.makeCopy());
    }

    public int rowCount() {
        return dataRows.rowCount();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if(id == null || id.equals("")){
            id = name;
        }
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataRow addRow(String label, String... values) {
        DataRow row = newRow();
        if(!StringUtils.isEmpty(label)) {
            row.setLabel(label);
        }

        double value;
        try{
            value = Double.parseDouble(label);
        }catch(NumberFormatException ex){
            value = 0;
        }

        row.setOutputValue(value);

        for(int i=0; i < values.length; ++i){
            row.cell(i, values[i]);
        }
        return rows().add(row);
    }

    public DataRow addRow(String label, List<String> values) {
        DataRow row = newRow();
        if(!StringUtils.isEmpty(label)) {
            row.setLabel(label);
        }

        double value;
        try{
            value = Double.parseDouble(label);
        }catch(NumberFormatException ex){
            value = 0;
        }

        row.setOutputValue(value);

        for(int i=0; i < values.size(); ++i){
            row.cell(i, values.get(i));
        }
        return rows().add(row);
    }

    public DataRow addRow(String label, Map<Integer, String> values) {
        DataRow row = newRow();
        if(!StringUtils.isEmpty(label)) {
            row.setLabel(label);
        }

        double value;
        try{
            value = Double.parseDouble(label);
        }catch(NumberFormatException ex){
            value = 0;
        }

        row.setOutputValue(value);

        for(Map.Entry<Integer, String> entry : values.entrySet()) {
            row.cell(entry.getKey(), entry.getValue());
        }
        return rows().add(row);
    }




    public DataRow row(int i) {
        return dataRows.row(i);
    }


    public boolean isEmpty() {
        return dataRows.isEmpty();
    }
}
