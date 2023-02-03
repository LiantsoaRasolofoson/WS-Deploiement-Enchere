CREATE OR REPLACE VIEW V_Utilisateur AS (
    SELECT
        Utilisateur.*, Genre.valeur 
    FROM Utilisateur JOIN Genre ON Utilisateur.idGenre = Genre.idGenre
);

CREATE OR REPLACE VIEW V_Compte AS (
    SELECT 
        V_Utilisateur.nom, 
        V_Utilisateur.prenom, 
        V_Utilisateur.contact, 
        V_Utilisateur.adresse, 
        V_Utilisateur.valeur as genre, 
        V_Utilisateur.dateDeNaissance,
        Compte.*
    FROM V_Utilisateur JOIN Compte ON V_Utilisateur.idUtilisateur = Compte.idUtilisateur
);


CREATE OR REPLACE VIEW V_Enchere AS (
    SELECT 
        Enchere.*, 
        Categorie.valeur as categorie, 
        Commission.taux, 
        status(Enchere.idEnchere) as statusEnchere,
        Utilisateur.nom as nomVendeur,
        Utilisateur.prenom as prenomVendeur
    FROM 
        Enchere JOIN Categorie ON Enchere.idCategorie = Categorie.idCategorie
        JOIN Commission ON Commission.idCommission = Enchere.idCommission
        JOIN Utilisateur ON Utilisateur.idUtilisateur = Enchere.idUtilisateur
);

CREATE OR REPLACE VIEW V_Offre AS (
    SELECT 
        Offre.*,
        V_Enchere.nom, 
        V_Enchere.descriptions, 
        V_Enchere.prixEnchere, 
        V_Enchere.categorie, 
        V_Enchere.dateEnchere,
        V_Enchere.nomVendeur,
        V_Enchere.prenomVendeur,
        V_Utilisateur.nom as nomUser , 
        V_Utilisateur.prenom, 
        V_Utilisateur.email, 
        V_Utilisateur.contact, 
        V_Utilisateur.adresse, 
        V_Utilisateur.valeur as genre,
        V_Utilisateur.dateDeNaissance
    FROM Offre JOIN V_Enchere ON V_Enchere.idEnchere = Offre.idEnchere
    JOIN V_Utilisateur ON V_Utilisateur.idUtilisateur = Offre.idUtilisateur
);

CREATE OR REPLACE VIEW V_Rechargement AS (
    SELECT Rechargement.*, Compte.numero 
    FROM Rechargement JOIN Compte ON Rechargement.idCompte = Compte.idCompte
);

CREATE OR REPLACE VIEW V_Stat AS (
   SELECT idCategorie, categorie, count(idCategorie) 
    FROM V_Enchere WHERE idEnchere IN (SELECT idEnchere FROM EnchereVendu) GROUP BY idCategorie, categorie
);

CREATE OR REPLACE VIEW gagnant AS (
    SELECT
        Offre.idOffre,
        Utilisateur.idUtilisateur,
        EnchereVendu.idEnchere,
        Utilisateur.nom,
        Utilisateur.prenom,
        Offre.prixOffre,
        Offre.dateOffre
    FROM
        EnchereVendu JOIN Offre ON EnchereVendu.idOffre = Offre.idOffre
        JOIN Utilisateur ON Utilisateur.idUtilisateur = Offre.idUtilisateur
);

