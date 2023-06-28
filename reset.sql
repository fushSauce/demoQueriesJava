DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS TradesPerson;
DROP TABLE IF EXISTS Specialty;
DROP TABLE IF EXISTS Job;
DROP TABLE IF EXISTS HasJob;

CREATE TABLE IF NOT EXISTS Person
(
    FName    VARCHAR(255),
    LName    VARCHAR(255),
    PhNo     VARCHAR(11),
    Email    VARCHAR(255),
    PayDet   VARCHAR(255),
    Location VARCHAR(255),
    PId INTEGER PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE IF NOT EXISTS Client
(
    CId INTEGER PRIMARY KEY AUTOINCREMENT,
    PId INTEGER,
    FOREIGN KEY (PId) REFERENCES Person (PId)
);

CREATE TABLE IF NOT EXISTS TradesPerson
(
    Rate         INTEGER,
    Availability VARCHAR(255),
    SId          INTEGER,
    PId          INTEGER,
    yearsExp     INTEGER,
    avgRating    INTEGER,
    TPId         INTEGER PRIMARY KEY AUTOINCREMENT,
    FOREIGN KEY (PId) REFERENCES Person (PId),
    FOREIGN KEY (SId) REFERENCES Specialty (SId)
);

CREATE TABLE IF NOT EXISTS Specialty
(
    SId        INTEGER PRIMARY KEY AUTOINCREMENT,
    Specialty  VARCHAR(255),
    Accreditor VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Job
(
    JId      INTEGER PRIMARY KEY AUTOINCREMENT,
    SId      INTEGER,
    CId      INTEGER,
    maxCost  INTEGER CHECK ( maxCost > 0 ),
    yearsExpRequired INTEGER CHECK (yearsExpRequired > 0),
    ratingRequired INTEGER,
    Location VARCHAR(255),
    Title    VARCHAR(255),
    JobInfo  VARCHAR(255),
    Invoice  VARCHAR(255),
    FOREIGN KEY (CId) REFERENCES Client(CId),
    FOREIGN KEY(SId) REFERENCES Specialty(SId)
);

CREATE TABLE IF NOT EXISTS HasJob
(
    TPId INTEGER,
    JId  INTEGER,
    PRIMARY KEY (JId, TPId),
    FOREIGN KEY (JId) REFERENCES Job (JId),
    FOREIGN KEY (TPId) REFERENCES TradesPerson (TPId)
);

INSERT INTO Job(JId,SId,CId,maxCost,yearsExpRequired,ratingRequired,Location,Title, JobInfo) VALUES
                                           (1,1,1,100,5,1,'Wellington','Plumbing asap','We have leaking wall.'),
                                           (2,1,1,200,6,2,'Auckland','Plumbing needed','Shower broken'),
                                           (3,2,2,300,7,3,'ChristChurch','Electrical needed','socket not functioning'),
                                           (4,3,2,400,8,4,'Napier','Carpentry needed','broken dry wall.');


INSERT INTO Person(PId,FName,LName,PhNo,Email,PayDet,Location) VALUES
                                                                   (1,'FName1','LName1','022123456','fname1@gmail.com','paydet1','Wellington'),
                                                                   (2,'FName2','LName2','022123457','fname2@gmail.com','paydet2','Auckland'),
                                                                   (3,'FName3','LName3','022123458','fname3@gmail.com','paydet3','ChristChurch'),
                                                                   (4,'FName4','LName4','022123459','fname4@gmail.com','paydet4','Napier');


INSERT INTO Client (CId,PId)
VALUES (1,1),
       (2,2);

INSERT INTO Specialty(SId,Specialty,Accreditor)
VALUES (1,'Plumbing','PGDB'),
       (2,'Electrical','EWRB'),
       (3,'Carpentry','LBP');

INSERT INTO TradesPerson (Rate, SId, PId,avgRating,yearsExp)
VALUES  (28,1,3,5,10),
        (30,2,4,3,5);


