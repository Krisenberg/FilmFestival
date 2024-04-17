-- Cleanup
DROP TABLE IF EXISTS "Actor";
DROP TABLE IF EXISTS "Award";
DROP TABLE IF EXISTS "Cast";
DROP TABLE IF EXISTS "Movie";
DROP TABLE IF EXISTS "Show";
DROP TABLE IF EXISTS "Ticket";
DROP TABLE IF EXISTS "Trailer";
DROP TABLE IF EXISTS "User";
DROP TABLE IF EXISTS "Watchlist";

-- Create tables
CREATE TABLE "Actor" (
	"ID"	INTEGER NOT NULL,
	"Name"	TEXT NOT NULL,
	"Photo"	TEXT,
	PRIMARY KEY("ID")
);

CREATE TABLE "Award" (
	"ID"	INTEGER NOT NULL,
	"MovieID"	INTEGER NOT NULL,
	"Name"	TEXT NOT NULL,
	"Description"	TEXT,
	FOREIGN KEY("MovieID") REFERENCES "Movie"("ID"),
	PRIMARY KEY("ID")
);

CREATE TABLE "Cast" (
	"MovieID"	INTEGER NOT NULL,
	"ActorID"	INTEGER NOT NULL,
	"Starring"	TEXT NOT NULL,
	FOREIGN KEY("ActorID") REFERENCES "Actor"("ID"),
	FOREIGN KEY("MovieID") REFERENCES "Movie"("ID"),
	PRIMARY KEY("MovieID","ActorID")
)

CREATE TABLE "Movie" (
	"ID"	INTEGER NOT NULL,
	"Title"	TEXT NOT NULL,
	"MoviePoster"	TEXT NOT NULL,
	"MoviePhoto"	TEXT NOT NULL,
	"Year"	INTEGER NOT NULL,
	"Duration"	INTEGER NOT NULL,
	"Genre"	TEXT NOT NULL,
	"Description"	TEXT NOT NULL,
	PRIMARY KEY("ID")
);

CREATE TABLE "Show" (
	"ID"	INTEGER NOT NULL,
	"MovieID"	INTEGER NOT NULL,
	"DateTime"	TEXT NOT NULL,
	PRIMARY KEY("ID"),
	FOREIGN KEY("MovieID") REFERENCES "Movie"("ID")
);

CREATE TABLE "Ticket" (
	"UserID"	INTEGER NOT NULL,
	"ShowID"	INTEGER NOT NULL,
	PRIMARY KEY("UserID","ShowID"),
	FOREIGN KEY("ShowID") REFERENCES "Show"("ID"),
	FOREIGN KEY("UserID") REFERENCES "User"("ID")
);

CREATE TABLE "Trailer" (
	"ID"	INTEGER NOT NULL,
	"MovieID"	INTEGER NOT NULL,
	"Trailer"	TEXT NOT NULL,
	FOREIGN KEY("MovieID") REFERENCES "Movie"("ID"),
	PRIMARY KEY("ID")
);

CREATE TABLE "User" (
	"ID"	INTEGER NOT NULL,
	"Username"	TEXT NOT NULL UNIQUE,
	"Password"	TEXT NOT NULL,
	"Avatar"	TEXT,
	PRIMARY KEY("ID")
);

CREATE TABLE "Watchlist" (
	"UserID"	INTEGER NOT NULL,
	"MovieID"	INTEGER NOT NULL,
	PRIMARY KEY("UserID","MovieID"),
	FOREIGN KEY("UserID") REFERENCES "User"("ID"),
	FOREIGN KEY("MovieID") REFERENCES "Movie"("ID")
);

-- Insert data
INSERT INTO "Actor"("ID","Name","Photo")
VALUES
    (1,"Timothée Chalamet","https://bi.im-g.pl/im/0b/53/1d/z30748939ICR,Timothee-Chalamet.jpg"),
    (2,"Zendaya","https://fwcdn.pl/ppo/60/35/1546035/451218.2.jpg"),
    (3,"Rebecca Ferguson","https://media.themoviedb.org/t/p/w500/lJloTOheuQSirSLXNA3JHsrMNfH.jpg"),
    (4,"Javier Bardem","https://m.media-amazon.com/images/M/MV5BMTY1NTc4NTYzMF5BMl5BanBnXkFtZTcwNDIwOTY1NA@@._V1_.jpg"),
    (5,"Josh Brolin","https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Josh_Brolin_SDCC_2014.jpg/1200px-Josh_Brolin_SDCC_2014.jpg"),
    (6,"Austin Butler","https://m.media-amazon.com/images/M/MV5BZTNjZjMwMDAtNjliNy00NGNiLTgyNmQtOTA5NDRjNGU4YjRhXkEyXkFqcGdeQXVyNjY4MDI3NQ@@._V1_.jpg"),
    (7,"Florence Pugh","https://fwcdn.pl/fph/34/81/10003481/1190521_1.3.jpg"),
    (8,"Dave Bautista","https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Dave_Bautista_Photo_Op_GalaxyCon_Minneapolis_2019.jpg/640px-Dave_Bautista_Photo_Op_GalaxyCon_Minneapolis_2019.jpg");

INSERT INTO "Movie" ("ID","Title","MoviePoster","MoviePhoto","Year","Duration","Genre","Description",)
VALUES
    (1,"Dune: Part Two","https://image.tmdb.org/t/p/original/5aUVLiqcW0kFTBfGsCWjvLas91w.jpg",
        "https://ca-times.brightspotcdn.com/dims4/default/c04179b/2147483647/strip/true/crop/2700x1580+0+0/resize/1200x702!/quality/75/?url=https%3A%2F%2Fcalifornia-times-brightspot.s3.amazonaws.com%2Fdc%2F0c%2F962ffd064d33a015a95c54b07c80%2Fdun2-27986r-jpv3.jpg",
        2024,166,"Action",
        "Paul Atreides unites with Chani and the Fremen while seeking revenge against the conspirators who destroyed his family."),
    (2,"Poor Things","https://media.themoviedb.org/t/p/w440_and_h660_face/jV3c2fsBNCJgcesxdNM9O0lwwdT.jpg",
        "https://inreview.com.au/wp-content/uploads/2023/10/Adelaide-Film-Festival-Poor-Things.jpg",
        2023,141,"Drama",
        "An account of the fantastical evolution of Bella Baxter, a young woman brought back to life by the brilliant and unorthodox scientist Dr. Godwin Baxter.");

INSERT INTO "User" ("ID","Username","Password","Avatar",)
VALUES
    (1,"Adam","adam","https://exumag.com/images/storychief/oscars_2d5cb98749d3a7b50369e1c4c64a5a39.jpg"),
    (2,"Stud","stud",NULL);

INSERT INTO "Award" ("ID","MovieID","Name","Description")
VALUES
    (1,1,"Don LaFontaine Award for Best Voice Over","2023 Nominee"),
    (2,1,"HCA Award","2023 Nominee - Most Anticipated Film");

INSERT INTO "Cast" ("MovieID","ActorID","Starring")
VALUES
    (1,1,"Paul Atreides"),
    (1,2,"Chani"),
    (1,3,"Jessica"),
    (1,4,"Stilgar"),
    (1,5,"Gurney Halleck"),
    (1,6,"Feyd-Rautha"),
    (1,7,"Princess Irulan"),
    (1,8,"Beast Rabban");

INSERT INTO "Show" ("ID","MovieID","DateTime")
VALUES
    (1,1,"2024-07-16T16:45"),
    (2,1,"2024-07-16T18:30"),
    (3,1,"2024-07-18T17:00"),
    (4,1,"2024-07-19T18:15"),
    (5,1,"2024-07-19T20:00");

INSERT INTO "Ticket" ("UserID","ShowID")
VALUES
    (1,2),
    (2,5);

INSERT INTO "Trailer" ("ID","MovieID","Trailer")
VALUES
    (1,1,"https://www.youtube.com/watch?v=0UtYt6Vg2nY");

INSERT INTO "Watchlist" ("UserID","MovieID")
VALUES
    (1,1),
    (1,2);

