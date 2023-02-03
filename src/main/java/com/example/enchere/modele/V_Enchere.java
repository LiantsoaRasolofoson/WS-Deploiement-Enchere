package com.example.enchere.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.springframework.http.HttpStatus;

import com.example.enchere.connexion.ConnexionPostgreSQL;
import com.example.enchere.exeption.RessourceException;
import com.example.enchere.retour.ErrorRetour;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "v_enchere")
public class V_Enchere {

    @Id
    @Column(name = "idenchere")
    private int idEnchere;

    @Column(name = "nom")
    private String nom;

    @Column(name = "descriptions")
    private String descriptions;

    @Column(name = "prixenchere")
    private double prixEnchere;

    @Column(name = "idutilisateur")
    private int idUtilisateur;

    @Column(name = "idcommission")
    private int idCommission;

    @Column(name = "idcategorie")
    private int idCategorie;

    @Column(name = "dateenchere")
    private LocalDateTime dateEnchere;

    @Column(name = "duree")
    private Time duree;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "taux")
    private double taux;

    @Column(name = "statusenchere")
    private String statusEnchere;

    @Column(name = "nomvendeur")
    private String nomVendeur;

    @Column(name = "prenomvendeur")
    private String prenomVendeur;

    @Transient
    private List <ImageEnchere> images;

    @Transient
    private ImageEnchere oneImage;

    public List <V_Enchere> getAll(String condition)throws Exception{
        List <V_Enchere> listes = new ArrayList<V_Enchere>();
        Connection connexion = null;
        Statement st = null;
        try{
            connexion = new ConnexionPostgreSQL().getConnect();
            st = connexion.createStatement();
            ResultSet result = st.executeQuery("SELECT * FROM V_Enchere WHERE 1=1 "+condition);
            while( result.next() ){
                V_Enchere v = new V_Enchere();
                v.setIdEnchere(result.getInt("idenchere"));
                v.setNom(result.getString("nom"));
                v.setDescriptions(result.getString("descriptions"));
                v.setPrixEnchere(result.getDouble("prixenchere"));
                v.setIdUtilisateur(result.getInt("idutilisateur"));
                v.setIdCommission(result.getInt("idcommission"));
                v.setIdCategorie(result.getInt("idcategorie"));
                v.setDateEnchere(result.getTimestamp("dateenchere").toLocalDateTime());
                v.setDuree(result.getTime("duree"));
                v.setCategorie(result.getString("categorie"));
                v.setTaux(result.getDouble("taux"));
                v.setStatusEnchere(result.getString("statusenchere"));
                v.setNomVendeur(result.getString("nomvendeur"));
                v.setPrenomVendeur(result.getString("prenomvendeur"));
                listes.add(v);
            }
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(st != null ){
                st.close();
            }
            if(connexion != null ){
                connexion.close();
            }
        }
        return listes;
    }
	

    public ImageEnchere oneImages()throws Exception{
        ImageEnchere img = new ImageEnchere();
        Connection connexion = null;
        Statement st = null;
        try{
            connexion = new ConnexionPostgreSQL().getConnect();
            st = connexion.createStatement();
            ResultSet result = st.executeQuery("SELECT * FROM ImageEnchere WHERE idenchere="+this.idEnchere+" LIMIT 1");
            while( result.next() ){
				img.setIdImageEnchere(result.getInt("idimageenchere"));
				img.setNomImage(result.getString("nomimage"));
				img.setFormat(result.getString("format"));
				img.setIdEnchere(idEnchere);
            }
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(st != null ){
                st.close();
            }
            if(connexion != null ){
                connexion.close();
            }
        }
        return img;
    }

	public List <ImageEnchere> allImages()throws Exception{
        List <ImageEnchere> listes = new ArrayList<ImageEnchere>();
        Connection connexion = null;
        Statement st = null;
        try{
            connexion = new ConnexionPostgreSQL().getConnect();
            st = connexion.createStatement();
            ResultSet result = st.executeQuery("SELECT * FROM ImageEnchere WHERE idenchere="+this.idEnchere);
            while( result.next() ){
				ImageEnchere img = new ImageEnchere();
				img.setIdImageEnchere(result.getInt("idimageenchere"));
				img.setNomImage(result.getString("nomimage"));
				img.setFormat(result.getString("format"));
				img.setIdEnchere(idEnchere);
                listes.add(img);
            }
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(st != null ){
                st.close();
            }
            if(connexion != null ){
                connexion.close();
            }
        }
        System.out.println("Eto anh "+listes.size());
        return listes;
    }

    public void isEncherable(){
        if( this.statusEnchere.equals("En Cours") == false ){
            throw new RessourceException(new ErrorRetour("Vous ne pouvez pas enchérir sur cette enchère",HttpStatus.BAD_REQUEST.value()));
        }
    }
}
