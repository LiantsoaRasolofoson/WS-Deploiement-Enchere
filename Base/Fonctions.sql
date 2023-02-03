-- CREATE OR REPLACE FUNCTION status(id integer) RETURNS VARCHAR AS $$
--     DECLARE
--         dates timestamp;
--         fin timestamp;
--         temoin integer;
--     BEGIN
--         SELECT dateEnchere,dateEnchere+duree INTO dates,fin FROM Enchere WHERE idEnchere = id;
--         SELECT count(*) INTO temoin FROM EnchereVendu WHERE idEnchere = id;
--         IF now() > dates 
--         THEN
--             IF now() < fin
--             THEN
--                 RETURN 'En Cours';
--             ELSE
--                 IF temoin = 0
--                 THEN
--                     RETURN 'Non-Vendu';
--                 ELSE
--                     RETURN 'Vendu'; 
--                 END IF; 
--             END IF; 
--         ELSE
--             RETURN 'Non demarrer';
--         END IF;
--     END;
-- $$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION status(id integer) RETURNS VARCHAR AS $$
    DECLARE
        dates timestamp;
        fin timestamp;
        temoin integer;
    BEGIN
        SELECT dateEnchere,dateEnchere+duree INTO dates,fin FROM Enchere WHERE idEnchere = id;
        SELECT count(*) INTO temoin FROM EnchereVendu WHERE idEnchere = id;
        IF now() > dates 
        THEN
            IF now() < fin
            THEN
                RETURN 'En Cours';
            ELSE
                RETURN 'Termine';
            END IF; 
        ELSE
            RETURN 'Non demarrer';
        END IF;
    END;
$$ LANGUAGE plpgsql;
