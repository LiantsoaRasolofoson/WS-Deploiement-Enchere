package com.example.enchere.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.enchere.modele.Commission;
import com.example.enchere.modele.Compte;
import com.example.enchere.modele.Enchere;
import com.example.enchere.modele.EnchereVendu;
import com.example.enchere.modele.Notifications;
import com.example.enchere.modele.Offre;
import com.example.enchere.modele.Utilisateur;
import com.example.enchere.repository.CommissionRepository;
import com.example.enchere.repository.CompteRepository;
import com.example.enchere.repository.EnchereRepository;
import com.example.enchere.repository.EnchereVenduRepository;
import com.example.enchere.repository.NotificationsRepository;
import com.example.enchere.repository.OffreRepository;

/**
 * Create by Weslei Dias.
 **/
@Component
public class NotificationService {

    @Autowired
    private EnchereRepository enchereRepository;

    @Autowired
    private EnchereVenduRepository enchereVenduRepository;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private CommissionRepository commissionRepository;


    public static final String REST_API_KEY = "MWRlNDVlOWUtYmNiMC00NDE1LTlhY2ItZTM4OWQ1YTVhMzk2";
    public static final String APP_ID = "a041247f-373d-40a0-bda9-0dbc7756deec";



    public static void sendMessageToAllUsers(String message) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization",
                    "Basic "+REST_API_KEY);//REST API
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \""+ APP_ID +"\","
                    +   "\"included_segments\": [\"All\"],"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"contents\": {\"en\": \""+ message +"\"}"
                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            jsonResponse = mountResponseRequest(con, httpResponse);
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException {
        String jsonResponse;
        if (  httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        return jsonResponse;
    }

    public void finEnchere(){
        List <Enchere> liste = enchereRepository.getAllNonTermine();
        System.out.println("Eeeeee "+liste.size());
        for( Enchere enchere : liste ){
            if( enchere.isTerminated() == true ){
                String n = "Votre enchere dont le nom est "+enchere.getNom()+" est terminee";
                Notifications notif = new Notifications(0, enchere.getIdUtilisateur(), n, 0);
                notificationsRepository.save(notif);
                Offre max = offreRepository.getOffreMax(enchere.getIdEnchere());
                if( max != null ){
                    Compte compteGagnant = compteRepository.getCompte(max.getIdUtilisateur());
                    double solde = compteGagnant.getSolde()-max.getPrixOffre();
                    compteGagnant.setSolde(solde);
                    compteRepository.save(compteGagnant);

                    Compte fournisseur = compteRepository.getCompte(enchere.getIdUtilisateur());
                    Commission c = commissionRepository.getCommission();
                    double soldes = (max.getPrixOffre()*c.getTaux())/100;
                    fournisseur.setSolde(soldes);
                    compteRepository.save(fournisseur);

                    EnchereVendu ev = new EnchereVendu(max.getIdEnchere(), max.getIdOffre());
                    enchereVenduRepository.save(ev);
                }
                this.sendMessageToUser(n, enchere.getIdUtilisateur());
                List <Utilisateur> users = offreRepository.getAllOffre(enchere.getIdEnchere());
                System.out.println("Eto size "+users.size());
                for( Utilisateur u : users ){
                    this.sendMessageToUser(n, u.getIdUtilisateur());
                }
            }
        }
    }

    public static void sendMessageToUser(String message, int userId) {
        try {

            RestTemplate restTemplate = new RestTemplate();

            String jsonResponse;

            
            /*URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization","Basic "+REST_API_KEY);
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \""+ APP_ID +"\","
                    +   "\"include_external_user_ids\": [\""+ userId +"\"],"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"contents\": {\"en\": \""+ message +"\"}"
                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            jsonResponse = mountResponseRequest(con, httpResponse);
            System.out.println("jsonResponse:\n" + jsonResponse);*/

      
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization","Basic "+REST_API_KEY);
            headers.set("accept","application/json");
            headers.set("content-type","application/json");

            Map<String,Object> data = new HashMap<>();
            data.put("app_id",APP_ID);
            data.put("included_segments",Arrays.asList("Subscribed Users"));
            data.put("include_external_user_ids",userId);

            Map<String,String> contents = new HashMap<>();
            contents.put("en",message);
            
            Map<String,String> headings = new HashMap<>();
            headings.put("en","Enchère terminée");

            data.put("contents",contents);
            data.put("headings",headings);
            data.put("name","INTERNAL_CAMPAIGN_NAME");


            HttpEntity <Map<String,Object>> request = new HttpEntity<>(data,headers);
            ResponseEntity response = restTemplate.postForEntity("https://onesignal.com/api/v1/notifications", request, Object.class);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    
}
