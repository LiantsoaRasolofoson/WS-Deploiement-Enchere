INSERT INTO Genre(valeur) VALUES ('Homme');
INSERT INTO Genre(valeur) VALUES ('Femme');

INSERT INTO Utilisateur VALUES 
(DEFAULT, 'RAKOTO', 'Fara', 'Rakoto@gmail.com', '123LAZA', '0331246544', 'B112Faravohitra', 1, '1990-10-12');
INSERT INTO Utilisateur VALUES 
(DEFAULT, 'ANDRIAMALALA', 'Benja', 'Benja@gmail.com', '00012FA', '0321524633', 'TER103Antanimena', 2, '1990-10-12');
INSERT INTO Utilisateur VALUES 
(DEFAULT, 'RAKOTO', 'Jean', 'Jean@gmail.com', 'jean123', '0324496385', 'Lot 116 II A Nanisana', 1, '1990-10-12');

INSERT INTO Compte VALUES (DEFAULT, 1, 'COMPTE01', '2022-05-14', 21000);
INSERT INTO Compte VALUES (DEFAULT, 2, 'COMPTE02', '2022-09-23', 150000);
INSERT INTO Compte VALUES (DEFAULT, 3, 'COMPTE03', '2022-09-23', 350000);

INSERT INTO Categorie VALUES (DEFAULT, 'electronique');
INSERT INTO Categorie VALUES (DEFAULT, 'decoration');

INSERT INTO Rechargement VALUES (DEFAULT, 1, 1000, '2023-01-09');
INSERT INTO Rechargement VALUES (DEFAULT, 2, 15500, '2023-01-10');

INSERT INTO RechargementValide VALUES (1, '2023-01-09');

INSERT INTO Commission VALUES (DEFAULT, 10, '2022-05-12');

INSERT INTO Enchere(nom,descriptions,prixEnchere,idUtilisateur,idCommission,idCategorie,dateEnchere,duree )
VALUES ('Vase','En porceline venant d''Afrique', 100000,1,1,2, '2023-01-13 15:00:00', '2:00:00');
INSERT INTO Enchere(nom,descriptions,prixEnchere,idUtilisateur,idCommission,idCategorie,dateEnchere,duree ) 
VALUES ('Appareil photo','lourde', 175000,2,1,1, '2023-01-14 15:00:00', '2:00:00');

INSERT INTO Admins VALUES (DEFAULT, 'ANDRIAMBELO', 'Liantsoa', 'Liantsoa@gmail.com', '1234LI');

INSERT INTO Offre VALUES (DEFAULT, 1, 3, 120000, '2023-01-13 15:15:00');

--INSERT INTO Offre VALUES (DEFAULT, 2, 1, 100000, '2023-01-15 15:15:00');
 
INSERT INTO EnchereVendu(idEnchere, idOffre) VALUES (1, 1);

-- INSERT INTO EnchereVendu(idEnchere, idOffre) VALUES (2, 2);

-- INSERT INTO ImageEnchere(idImageEnchere, nomImage, format, idEnchere) VALUES (1, 'IMG01', 'PNG', 1);
-- INSERT INTO ImageEnchere(idImageEnchere, nomImage, format, idEnchere) VALUES (2, 'IMG03', 'JPG', 2);
