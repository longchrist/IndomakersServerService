package com.idm.dao;

import com.idm.connection.dbConnection;
import com.idm.model.masterPriceMod;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class masterPriceAO {
    private Connection conn = null;

    public String getAllMasterPrice(){
        String jsonResponse = "";

        Statement stmt = null;
        ResultSet rs;

        int priceId = 0;
        int pricePerUnit = 0;
        int salesPrice = 0;
        String startDate = "";
        String endDate = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRICE = new JSONArray();

        try {
            conn = dbConnection.getConnection();

            stmt = conn.createStatement();
            String query = "SELECT price_id, price_per_unit, sales_price, start_date, end_date, is_active FROM tb_master_price";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                priceId = rs.getInt("PRICE_ID");
                pricePerUnit = rs.getInt("PRICE_PER_UNIT");
                salesPrice = rs.getInt("SALES_PRICE");
                startDate = rs.getString("START_DATE");
                endDate = rs.getString("END_DATE");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_PRICE = new JSONObject();

                DATA_PRICE.put("PRICE_ID", new Integer(priceId));
                DATA_PRICE.put("PRICE_PER_UNIT", new Integer(pricePerUnit));
                DATA_PRICE.put("SALES_PRICE", new Integer(salesPrice));
                DATA_PRICE.put("START_DATE", new String(startDate));
                DATA_PRICE.put("END_DATE", new String(endDate));
                DATA_PRICE.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_PRICE.put(DATA_PRICE);
            }

            JSONObjectRoot.put("DATA_MASTER_PRICE", DATA_MASTER_PRICE);
            jsonResponse += JSONObjectRoot.toString();

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String getMasterPrice(masterPriceMod MPM){
        String jsonResponse = "";

        ResultSet rs;

        int priceId = 0;
        int pricePerUnit = 0;
        int salesPrice = 0;
        String startDate = "";
        String endDate = "";
        String isActive = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRICE = new JSONArray();

        try {
            conn = dbConnection.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("SELECT price_id, price_per_unit, sales_price, start_date, end_date, is_active FROM tb_master_price WHERE price_id = ?");
            ps.setInt(1, MPM.getPriceId());
            rs = ps.executeQuery();

            while (rs.next()) {
                priceId = rs.getInt("PRICE_ID");
                pricePerUnit = rs.getInt("PRICE_PER_UNIT");
                salesPrice = rs.getInt("SALES_PRICE");
                startDate = rs.getString("START_DATE");
                endDate = rs.getString("END_DATE");
                isActive = rs.getString("IS_ACTIVE");

                JSONObject DATA_PRICE = new JSONObject();

                DATA_PRICE.put("PRICE_ID", new Integer(priceId));
                DATA_PRICE.put("PRICE_PER_UNIT", new Integer(pricePerUnit));
                DATA_PRICE.put("SALES_PRICE", new Integer(salesPrice));
                DATA_PRICE.put("START_DATE", new String(startDate));
                DATA_PRICE.put("END_DATE", new String(endDate));
                DATA_PRICE.put("IS_ACTIVE", new String(isActive));

                DATA_MASTER_PRICE.put(DATA_PRICE);
            }

            JSONObjectRoot.put("DATA_MASTER_PRICE", DATA_MASTER_PRICE);
            jsonResponse += JSONObjectRoot.toString();

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public String saveMasterPrice(masterPriceMod MPM) throws JSONException {
        String jsonResponse = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRICE = new JSONArray();

        boolean result = false;
        String messageResult = "";

        String responseGeneratedKeys = "";

        try {
            conn = dbConnection.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("INSERT INTO tb_master_price (price_per_unit, sales_price, start_date, end_date, is_active) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPM.getPricePerUnit());
            ps.setInt(2, MPM.getSalesPrice());
            ps.setString(3, MPM.getStartDate());
            ps.setString(4, MPM.getEndDate());
            ps.setString(5, MPM.getIsActive());

            if(ps.executeUpdate() > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()){
                    responseGeneratedKeys = rs.getString(1);
                }
                result = true;
                messageResult = "Success add price data.";
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PRICE = new JSONObject();

        DATA_PRICE.put("RESULT", new Boolean(result));
        DATA_PRICE.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PRICE.put(DATA_PRICE);

        JSONObjectRoot.put("DATA_MASTER_PRICE", DATA_MASTER_PRICE);
        jsonResponse += JSONObjectRoot.toString();

        return responseGeneratedKeys;
    }

    public String updateMasterPrice(masterPriceMod MPM) throws JSONException{
        String jsonResponse = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRICE = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            conn = dbConnection.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("UPDATE tb_master_price SET price_per_unit = ?, sales_price = ?, start_date = ?, end_date = ? WHERE price_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPM.getPricePerUnit());
            ps.setInt(2, MPM.getSalesPrice());
            ps.setString(3, MPM.getStartDate());
            ps.setString(4, MPM.getEndDate());
            ps.setInt(5, MPM.getPriceId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success update price data.";
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PRICE = new JSONObject();

        DATA_PRICE.put("RESULT", new Boolean(result));
        DATA_PRICE.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PRICE.put(DATA_PRICE);

        JSONObjectRoot.put("DATA_MASTER_PRICE", DATA_MASTER_PRICE);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }

    public String deleteMasterPrice(masterPriceMod MPM) throws JSONException{
        String jsonResponse = "";

        JSONObject JSONObjectRoot = new JSONObject();
        JSONArray DATA_MASTER_PRICE = new JSONArray();

        boolean result = false;
        String messageResult = "";

        try {
            conn = dbConnection.getConnection();

            PreparedStatement ps = this.conn.prepareStatement("DELETE FROM tb_master_price WHERE price_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, MPM.getPriceId());

            if(ps.executeUpdate() > 0){
                result = true;
                messageResult = "Success delete price data.";
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            messageResult = ""+e.getMessage();
        }

        JSONObject DATA_PRICE = new JSONObject();

        DATA_PRICE.put("RESULT", new Boolean(result));
        DATA_PRICE.put("MESSAGE", new String(messageResult));
        DATA_MASTER_PRICE.put(DATA_PRICE);

        JSONObjectRoot.put("DATA_MASTER_PRICE", DATA_MASTER_PRICE);
        jsonResponse += JSONObjectRoot.toString();

        return jsonResponse;
    }
}
