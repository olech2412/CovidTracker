package org.vaadin.covid.backend.dataProvider;

import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;
import org.vaadin.covid.backend.RequestedURLEnum;
import org.vaadin.covid.jpa.Brd;
import org.vaadin.covid.jpa.Status;
import org.vaadin.covid.jpa.bundesland.BundeslandBerlin;
import org.vaadin.covid.jpa.bundesland.BundeslandFreistaatSachsen;
import org.vaadin.covid.jpa.bundesland.BundeslandMV;
import org.vaadin.covid.jpa.landkreis.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Log4j2
public class DataProvider {

    public Object formattedData;

    public DataProvider(JSONObject data, RequestedURLEnum requestedURLEnum) throws JSONException {
        formattedData = null;
        log.debug("DataProvider set formatted data to null");
        checkProvidedData(data, requestedURLEnum);
    }

    private void checkProvidedData(JSONObject data, RequestedURLEnum requestedURLEnum) throws JSONException {
        switch (requestedURLEnum) {
            case blMV:
                formatBundeslandMVData(data);
                break;
            case blBer:
                formatBundeslandBerlinData(data);
                break;
            case blFS:
                formatBundeslandSachsenData(data);
                break;
            case status:
                formatStatusData(data);
                break;
            case brd:
                formatBRDData(data);
                break;
            case blMVlkVR:
                formatVRData(data);
                break;
            case blMVlkG:
                formatGData(data);
                break;
            case blMVlkLP:
                formatLPData(data);
                break;
            case blMVlkMS:
                formatMSData(data);
                break;
            case blMVlkKSR:
                formatKSRData(data);
                break;
            case blMVlkNWM:
                formatNWMData(data);
                break;
            case blMVlkR:
                formatRData(data);
                break;
            case blMVlkSN:
                formatSNData(data);
                break;
            default:
                log.error("No dataProviderEnum found");
        }

    }

    private void formatSNData(JSONObject data) throws JSONException {
        LandkreisSN landkreisSN = new LandkreisSN();
        landkreisSN.setCreationDate(LocalDate.now());
        landkreisSN.setCases(data.getInt("cases"));
        landkreisSN.setCasesPer100k(data.getDouble("cases_per_100k"));
        landkreisSN.setCasesPerPopulation(data.getDouble("cases_per_population"));
        landkreisSN.setCases7(data.getInt("cases7_lk"));
        landkreisSN.setDeath7(data.getInt("death7_lk"));
        landkreisSN.setCases7per100k(data.getDouble("cases7_per_100k"));

        log.info("SNDataProvider fetched SN from: " + LocalDate.now());
        formattedData = landkreisSN;

    }

    private void formatRData(JSONObject data) throws JSONException {
        LandkreisR landkreisR = new LandkreisR();
        landkreisR.setCreationDate(LocalDate.now());
        landkreisR.setCases(data.getInt("cases"));
        landkreisR.setCasesPer100k(data.getDouble("cases_per_100k"));
        landkreisR.setCasesPerPopulation(data.getDouble("cases_per_population"));
        landkreisR.setCases7(data.getInt("cases7_lk"));
        landkreisR.setDeath7(data.getInt("death7_lk"));
        landkreisR.setCases7per100k(data.getDouble("cases7_per_100k"));

        log.info("R data fetched from: " + LocalDate.now());
        formattedData = landkreisR;
    }

    private void formatNWMData(JSONObject data) throws JSONException {
        LandkreisNwm landkreisNwm = new LandkreisNwm();
        landkreisNwm.setCreationDate(LocalDate.now());
        landkreisNwm.setCases(data.getInt("cases"));
        landkreisNwm.setCasesPer100k(data.getDouble("cases_per_100k"));
        landkreisNwm.setCasesPerPopulation(data.getDouble("cases_per_population"));
        landkreisNwm.setCases7(data.getInt("cases7_lk"));
        landkreisNwm.setDeath7(data.getInt("death7_lk"));
        landkreisNwm.setCases7per100k(data.getDouble("cases7_per_100k"));

        log.info("NWM data fetched from: " + LocalDate.now());
        formattedData = landkreisNwm;
    }

    private void formatKSRData(JSONObject data) throws JSONException {
        LandkreisKfSR landkreisKfSR = new LandkreisKfSR();
        landkreisKfSR.setCreationDate(LocalDate.now());
        landkreisKfSR.setCases(data.getInt("cases"));
        landkreisKfSR.setCasesPer100k(data.getDouble("cases_per_100k"));
        landkreisKfSR.setCasesPerPopulation(data.getDouble("cases_per_population"));
        landkreisKfSR.setCases7(data.getInt("cases7_lk"));
        landkreisKfSR.setDeath7(data.getInt("death7_lk"));
        landkreisKfSR.setCases7per100k(data.getDouble("cases7_per_100k"));

        log.info("KSR data fetched from: " + LocalDate.now());
        formattedData = landkreisKfSR;
    }

    private void formatMSData(JSONObject data) throws JSONException {
        LandkreisMS landkreisMS = new LandkreisMS();
        landkreisMS.setCreationDate(LocalDate.now());
        landkreisMS.setCases(data.getInt("cases"));
        landkreisMS.setCasesPer100k(data.getDouble("cases_per_100k"));
        landkreisMS.setCasesPerPopulation(data.getDouble("cases_per_population"));
        landkreisMS.setCases7(data.getInt("cases7_lk"));
        landkreisMS.setDeath7(data.getInt("death7_lk"));
        landkreisMS.setCases7per100k(data.getDouble("cases7_per_100k"));

        log.info("MS data fetched from: " + LocalDate.now());
        formattedData = landkreisMS;
    }

    private void formatLPData(JSONObject data) throws JSONException {
        LandkreisLwP landkreisLwP = new LandkreisLwP();
        landkreisLwP.setCreationDate(LocalDate.now());
        landkreisLwP.setCases(data.getInt("cases"));
        landkreisLwP.setCasesPer100k(data.getDouble("cases_per_100k"));
        landkreisLwP.setCasesPerPopulation(data.getDouble("cases_per_population"));
        landkreisLwP.setCases7(data.getInt("cases7_lk"));
        landkreisLwP.setDeath7(data.getInt("death7_lk"));
        landkreisLwP.setCases7per100k(data.getDouble("cases7_per_100k"));

        log.info("LandkreisLwP fetched from: " + landkreisLwP.getCreationDate());
        formattedData = landkreisLwP;
    }

    private void formatGData(JSONObject data) throws JSONException {
        LandkreisG landkreisG = new LandkreisG();
        landkreisG.setCreationDate(LocalDate.now());
        landkreisG.setCases(data.getInt("cases"));
        landkreisG.setCasesPer100k(data.getDouble("cases_per_100k"));
        landkreisG.setCasesPerPopulation(data.getDouble("cases_per_population"));
        landkreisG.setCases7(data.getInt("cases7_lk"));
        landkreisG.setDeath7(data.getInt("death7_lk"));
        landkreisG.setCases7per100k(data.getDouble("cases7_per_100k"));

        log.info("GDataProvider fetched LandkreisG from: " + landkreisG.getCreationDate());
        formattedData = landkreisG;

    }

    private void formatVRData(JSONObject data) throws JSONException {
        LandkreisVR landkreisVR = new LandkreisVR();
        landkreisVR.setCreationDate(LocalDate.now());
        landkreisVR.setCases(data.getInt("cases"));
        landkreisVR.setCasesPer100k(data.getDouble("cases_per_100k"));
        landkreisVR.setCasesPerPopulation(data.getDouble("cases_per_population"));
        landkreisVR.setCases7(data.getInt("cases7_lk"));
        landkreisVR.setDeath7(data.getInt("death7_lk"));
        landkreisVR.setCases7per100k(data.getDouble("cases7_per_100k"));

        log.info("VRDataProvider fetched MV from: " + landkreisVR.getCreationDate());
        formattedData = landkreisVR;

    }

    private void formatBRDData(JSONObject data) throws JSONException {
        Brd brd = new Brd();
        brd.setActivenew(data.getInt("AnzAktivNeu"));
        brd.setActivetotally(data.getInt("AnzAktiv"));
        brd.setCasesnew(data.getInt("AnzFallNeu"));
        brd.setCasestotally(data.getInt("AnzFall"));
        brd.setDeathsnew(data.getInt("AnzTodesfallNeu"));
        brd.setDeathstotally(data.getInt("AnzTodesfall"));
        brd.setCases7days(data.getInt("AnzFall7T"));
        brd.setRecoveredtotally(data.getInt("AnzGenesen"));
        brd.setIncidence7days(data.getDouble("Inz7T"));
        brd.setRevorednew(data.getInt("AnzGenesenNeu"));
        brd.setCreationDate(LocalDate.now());

        log.info("BRDDataProvider fetched BRD from: " + brd.getCreationDate());
        formattedData = brd;
    }

    private void formatStatusData(JSONObject data) throws JSONException {
        Status status = new Status();
        status.setStatus(data.getString("Status"));
        status.setCreationDate(LocalDate.now());
        status.setLastUpdate(data.getString("Timestamp_txt"));

        log.info("StatusDataProvider fetched Status from: " + status.getCreationDate());
        formattedData = status;
    }

    private void formatBundeslandMVData(JSONObject data) throws JSONException {
        BundeslandMV bundeslandMV = new BundeslandMV();
        bundeslandMV.setCreationDate(LocalDate.now());
        bundeslandMV.setCases(data.getInt("Fallzahl"));
        bundeslandMV.setCases7(data.getInt("cases7_bl"));
        bundeslandMV.setCases7per100k(data.getDouble("cases7_bl_per_100k"));
        bundeslandMV.setCases100k(data.getDouble("faelle_100000_EW"));
        bundeslandMV.setDeath(data.getInt("Death"));
        bundeslandMV.setDeath7(data.getInt("death7_bl"));
        LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(data.getString("Aktualisierung"))), TimeZone.getDefault().toZoneId());
        bundeslandMV.setLastUpdate(triggerTime);

        log.info("BundeslandMVDataProvider fetched BLMV from: " + bundeslandMV.getCreationDate());
        formattedData = bundeslandMV;
    }

    private void formatBundeslandBerlinData(JSONObject data) throws JSONException {
        BundeslandBerlin bundeslandBerlin = new BundeslandBerlin();
        bundeslandBerlin.setCreationDate(LocalDate.now());
        bundeslandBerlin.setCases(data.getInt("Fallzahl"));
        bundeslandBerlin.setCases7(data.getInt("cases7_bl"));
        bundeslandBerlin.setCases7per100k(data.getDouble("cases7_bl_per_100k"));
        bundeslandBerlin.setCases100k(data.getDouble("faelle_100000_EW"));
        bundeslandBerlin.setDeath(data.getInt("Death"));
        bundeslandBerlin.setDeath7(data.getInt("death7_bl"));
        LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(data.getString("Aktualisierung"))), TimeZone.getDefault().toZoneId());
        bundeslandBerlin.setLastUpdate(triggerTime);

        log.info("BundeslandBerlinDataProvider fetched BLBer from: " + bundeslandBerlin.getCreationDate());
        formattedData = bundeslandBerlin;
    }

    private void formatBundeslandSachsenData(JSONObject data) throws JSONException {
        BundeslandFreistaatSachsen bundeslandFreistaatSachsen = new BundeslandFreistaatSachsen();
        bundeslandFreistaatSachsen.setCreationDate(LocalDate.now());
        bundeslandFreistaatSachsen.setCases(data.getInt("Fallzahl"));
        bundeslandFreistaatSachsen.setCases7(data.getInt("cases7_bl"));
        bundeslandFreistaatSachsen.setCases7per100k(data.getDouble("cases7_bl_per_100k"));
        bundeslandFreistaatSachsen.setCases100k(data.getDouble("faelle_100000_EW"));
        bundeslandFreistaatSachsen.setDeath(data.getInt("Death"));
        bundeslandFreistaatSachsen.setDeath7(data.getInt("death7_bl"));
        LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(data.getString("Aktualisierung"))), TimeZone.getDefault().toZoneId());
        bundeslandFreistaatSachsen.setLastUpdate(triggerTime);

        log.info("BundeslandSachsenDataProvider fetched BLMV from: " + bundeslandFreistaatSachsen.getCreationDate());
        formattedData = bundeslandFreistaatSachsen;
    }

    public Object getFormattedData() {
        return formattedData;
    }
}