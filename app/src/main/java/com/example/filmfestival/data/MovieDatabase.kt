package com.example.filmfestival.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.Award
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Role
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.crossRefs.TicketCrossRef
import com.example.filmfestival.models.Trailer
import com.example.filmfestival.models.User
import com.example.filmfestival.models.crossRefs.MovieAwardCrossRef
import com.example.filmfestival.models.crossRefs.WatchlistCrossRef

@Database(
    entities = [
        Actor::class,
        Award::class,
        MovieAwardCrossRef::class,
        Movie::class,
        Role::class,
        Show::class,
        TicketCrossRef::class,
        Trailer::class,
        User::class,
        WatchlistCrossRef::class
    ],
    version = 2,
    autoMigrations = [
//        AutoMigration(from = 1, to = 2),
//        AutoMigration(from = 2, to = 3)
    ]
)
abstract class MovieDatabase: RoomDatabase() {

//    abstract fun awardDao(): AwardDao
//    abstract fun castDao(): CastDao
    abstract fun movieDao(): MovieDao
//    abstract fun showDao(): ShowDao
//    abstract fun ticketDao(): TicketDao
//    abstract fun trailerDao(): TrailerDao
    abstract fun watchlistDao(): WatchlistDao
    abstract fun userDao(): UserDao
    abstract fun filmFestivalDao(): FilmFestivalDao

    companion object {
        val migration1To2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "INSERT INTO Actor (actorId, name, photo) " +
                            "VALUES (10,\"Adam Smith\",\"examplelink\")"
                )
            }
        }
    }

}