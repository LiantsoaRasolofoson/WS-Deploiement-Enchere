create database enchere;
create user enchere with password 'enchere';
alter database enchere owner to enchere;

CREATE TABLE Genre (
  idGenre SERIAL NOT NULL, 
  valeur  varchar(255) NOT NULL,
  PRIMARY KEY (idGenre)
);

CREATE TABLE Utilisateur (
  idUtilisateur SERIAL NOT NULL, 
  nom           varchar(255) NOT NULL, 
  prenom        varchar(255) NOT NULL, 
  email         varchar(255) NOT NULL, 
  motDePasse    varchar(255) NOT NULL, 
  contact       varchar(255) NOT NULL, 
  adresse       varchar(255) NOT NULL, 
  idGenre       int4 NOT NULL, 
  dateDeNaissance date not null,
  PRIMARY KEY (idUtilisateur)
);

CREATE TABLE Token(
  idToken SERIAL NOT NULL,
  idUtilisateur int4,
  tokenValues varchar(255),
  dateExpiration timestamp,
  PRIMARY KEY (idToken)
);
ALTER TABLE Token ADD FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(idUtilisateur);

CREATE TABLE Compte (
  idCompte      SERIAL NOT NULL, 
  idUtilisateur int4 NOT NULL, 
  numero        varchar(255) NOT NULL, 
  dateCompte    date NOT NULL, 
  solde         real NOT NULL, 
  PRIMARY KEY (idCompte)
);

CREATE TABLE Categorie (
  idCategorie SERIAL NOT NULL, 
  valeur      varchar(255) NOT NULL,
  PRIMARY KEY (idCategorie)
);

CREATE TABLE Rechargement (
  idRechargement   SERIAL NOT NULL, 
  idCompte         int4 NOT NULL, 
  montant          real NOT NULL, 
  dateRechargement date NOT NULL, 
  PRIMARY KEY (idRechargement)
);

CREATE TABLE RechargementValide (
  idRechargement int4 NOT NULL, 
  dateValidation date NOT NULL
);

CREATE TABLE Enchere (
  idEnchere    SERIAL NOT NULL,
  nom  varchar(255) NOT NULL,
  descriptions varchar(255) NOT NULL,
  prixEnchere real NOT NULL,
  idUtilisateur  int4 NOT NULL,
  idCommission int4 NOT NULL,
  idCategorie int4 NOT NULL,
  dateEnchere  TIMESTAMP NOT NULL, 
  duree        time(6) NOT NULL, 
  PRIMARY KEY (idEnchere)
);

CREATE TABLE Offre (
  idOffre       SERIAL NOT NULL, 
  idEnchere     int4 NOT NULL, 
  idUtilisateur int4 NOT NULL, 
  prixOffre     real NOT NULL, 
  dateOffre     TIMESTAMP NOT NULL, 
  PRIMARY KEY (idOffre)
);

CREATE TABLE Admins (
  idAdmins       SERIAL NOT NULL,
  nom        varchar(255) NOT NULL, 
  prenom     varchar(255) NOT NULL, 
  email      varchar(255) NOT NULL, 
  motDePasse varchar(255) NOT NULL
);

CREATE TABLE Commission (
  idCommission   SERIAL NOT NULL, 
  taux           REAL NOT NULL, 
  dateCommission date NOT NULL, 
  PRIMARY KEY (idCommission)
);

CREATE TABLE EnchereVendu (
  idEnchere int4 NOT NULL, 
  idOffre   int4 NOT NULL
);

CREATE TABLE ImageEnchere (
  idImageEnchere SERIAL NOT NULL, 
  nomImage       TEXT NOT NULL, 
  format         varchar(255) NOT NULL, 
  idEnchere      int4 NOT NULL, 
  PRIMARY KEY (idImageEnchere)
);

ALTER TABLE Utilisateur ADD FOREIGN KEY (idGenre) REFERENCES Genre (idGenre);
ALTER TABLE Compte ADD FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur (idUtilisateur);
ALTER TABLE Rechargement ADD FOREIGN KEY (idCompte) REFERENCES Compte (idCompte);
ALTER TABLE RechargementValide ADD FOREIGN KEY (idRechargement) REFERENCES Rechargement (idRechargement);
ALTER TABLE Enchere ADD FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur (idUtilisateur);
ALTER TABLE Enchere ADD FOREIGN KEY (idCommission) REFERENCES Commission (idCommission);
ALTER TABLE Enchere ADD FOREIGN KEY (idCategorie) REFERENCES Categorie (idCategorie);
ALTER TABLE Offre ADD FOREIGN KEY (idEnchere) REFERENCES Enchere (idEnchere);
ALTER TABLE Offre ADD FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur (idUtilisateur);
ALTER TABLE Enchere ADD FOREIGN KEY (idCommission) REFERENCES Commission (idCommission);
ALTER TABLE EnchereVendu ADD FOREIGN KEY (idEnchere) REFERENCES Enchere (idEnchere);
ALTER TABLE EnchereVendu ADD FOREIGN KEY (idOffre) REFERENCES Offre (idOffre);
ALTER TABLE ImageEnchere ADD FOREIGN KEY (idEnchere) REFERENCES Enchere (idEnchere);


CREATE TABLE Notifications(
  idNotifications SERIAL PRIMARY KEY,
  idUtilisateur INT4 NOT NULL,
  notifications varchar(255) NOT NULL,
  etat INT4 default 0
);
ALTER TABLE Notifications ADD FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(idUtilisateur);
