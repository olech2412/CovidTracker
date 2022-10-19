package org.vaadin.covid;

import com.vaadin.flow.server.PWA;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.vaadin.covid.backend.DataCaller;
import org.vaadin.covid.backend.RequestedURLEnum;
import org.vaadin.covid.backend.dataProvider.DataProvider;
import org.vaadin.covid.jpa.Brd;
import org.vaadin.covid.jpa.Status;
import org.vaadin.covid.jpa.bundesland.BundeslandBerlin;
import org.vaadin.covid.jpa.bundesland.BundeslandFreistaatSachsen;
import org.vaadin.covid.jpa.bundesland.BundeslandMV;
import org.vaadin.covid.jpa.landkreis.*;
import org.vaadin.covid.repository.*;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EntityScan
@EnableJpaRepositories
@EnableScheduling
@Log4j2
public class CovidTrackerApplication {

    @Autowired
    BundeslandMVRepository bundeslandMVRepository;
    @Autowired
    BundeslandBerlinRepository bundeslandBerlinRepository;
    @Autowired
    BundeslandFreistaatSachsenRepository bundeslandFreistaatSachsenRepository;
    @Autowired
    BrdRepository brdRepository;
    @Autowired
    LandkreisGRepository landkreisGRepository;
    @Autowired
    LandkreisKfSRRepository landkreisKfSRRepository;
    @Autowired
    LandkreisLwPRepository landkreisLwPRepository;
    @Autowired
    LandkreisMSRepository landkreisMSRepository;
    @Autowired
    LandkreisNwmRepository landkreisNwmRepository;
    @Autowired
    LandkreisRRepository landkreisRRepository;
    @Autowired
    LandkreisSNRepository landkreisSNRepository;
    @Autowired
    LandkreisVRRepository landkreisVRRepository;
    @Autowired
    StatusRepository statusRepository;
    DataCaller dataCaller;

    /**
     * The main method makes it possible to run the application as a plain Java
     * application which starts embedded web server via Spring Boot.
     *
     * @param args
     *            command line parameters
     */
    public static void main(String[] args) {
        SpringApplication.run(CovidTrackerApplication.class, args);
    }

    /**
     * Fetches Data every 4 hours and on startup.
     */
    @Scheduled(fixedRate = 14400000)
    public void onApplicationReadyEvent() throws JSONException, IOException, InterruptedException {

        if(statusRepository.findAllByCreationDate(LocalDate.now()).isEmpty() && LocalTime.now().getHour() >= 4){
            try{
                dataCaller = new DataCaller(RequestedURLEnum.status);
                Status status = (Status) dataCaller.callData();
                if(!status.getStatus().equals("OK")){
                    log.error("Status is not OK");
                }
                statusRepository.save(status);
            }catch (Exception e){
                log.error("Error while fetching data from Status because of: " + e.getMessage());
            }
            try {
                dataCaller = new DataCaller(RequestedURLEnum.blMV);
                BundeslandMV bundeslandMV = (BundeslandMV) dataCaller.callData();
                bundeslandMVRepository.save(bundeslandMV);
            }catch (Exception e){
                log.error("Error while fetching data from MV because of: " + e.getMessage());
            }
            try{
                dataCaller = new DataCaller(RequestedURLEnum.blBer);
                BundeslandBerlin bundeslandBerlin = (BundeslandBerlin) dataCaller.callData();
                bundeslandBerlinRepository.save(bundeslandBerlin);
            }catch (Exception e){
                log.error("Error while fetching data from Berlin because of: " + e.getMessage());
            }
            try{
                dataCaller = new DataCaller(RequestedURLEnum.blFS);
                BundeslandFreistaatSachsen bundeslandFreistaatSachsen = (BundeslandFreistaatSachsen) dataCaller.callData();
                bundeslandFreistaatSachsenRepository.save(bundeslandFreistaatSachsen);
            }catch (Exception e){
                log.error("Error while fetching data from Sachsen because of: " + e.getMessage());
            }
            try{
                dataCaller = new DataCaller(RequestedURLEnum.brd);
                Brd brd = (Brd) dataCaller.callData();
                brdRepository.save(brd);
            }catch (Exception e){
                log.error("Error while fetching data from BRD because of: " + e.getMessage());
            }
            try{
                dataCaller = new DataCaller(RequestedURLEnum.blMVlkVR);
                LandkreisVR landkreisVR = (LandkreisVR) dataCaller.callData();
                landkreisVRRepository.save(landkreisVR);
            }catch (Exception e){
                log.error("Error while fetching data from VR because of: " + e.getMessage());
            }
            try{
                dataCaller = new DataCaller(RequestedURLEnum.blMVlkG);
                LandkreisG landkreisG = (LandkreisG) dataCaller.callData();
                landkreisGRepository.save(landkreisG);
            }catch (Exception e){
                log.error("Error while fetching data from G because of: " + e.getMessage());
            }
            try{
                dataCaller = new DataCaller(RequestedURLEnum.blMVlkKSR);
                LandkreisKfSR landkreisKfSR = (LandkreisKfSR) dataCaller.callData();
                landkreisKfSRRepository.save(landkreisKfSR);
            }catch (Exception e){
                log.error("Error while fetching data from KfSR because of: " + e.getMessage());
            }
            try{
                dataCaller = new DataCaller(RequestedURLEnum.blMVlkLP);
                LandkreisLwP landkreisLwP = (LandkreisLwP) dataCaller.callData();
                landkreisLwPRepository.save(landkreisLwP);
            }catch (Exception e){
                log.error("Error while fetching data from LwP because of: " + e.getMessage());
            }
            try{
                dataCaller = new DataCaller(RequestedURLEnum.blMVlkMS);
                LandkreisMS landkreisMS = (LandkreisMS) dataCaller.callData();
                landkreisMSRepository.save(landkreisMS);
            }catch (Exception e){
                log.error("Error while fetching data from MS because of: " + e.getMessage());
            }
            try{
                dataCaller = new DataCaller(RequestedURLEnum.blMVlkNWM);
                LandkreisNwm landkreisNwm = (LandkreisNwm) dataCaller.callData();
                landkreisNwmRepository.save(landkreisNwm);
            }catch (Exception e){
                log.error("Error while fetching data from Nwm because of: " + e.getMessage());
            }
            try{
                dataCaller = new DataCaller(RequestedURLEnum.blMVlkR);
                LandkreisR landkreisR = (LandkreisR) dataCaller.callData();
                landkreisRRepository.save(landkreisR);
            }catch (Exception e){
                log.error("Error while fetching data from R because of: " + e.getMessage());
            }
            try{
                dataCaller = new DataCaller(RequestedURLEnum.blMVlkSN);
                LandkreisSN landkreisSN = (LandkreisSN) dataCaller.callData();
                landkreisSNRepository.save(landkreisSN);
            }catch (Exception e){
                log.error("Error while fetching data from SN because of: " + e.getMessage());
            }


        }else{
            log.debug("Data already exists");
        }
    }
}