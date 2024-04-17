package com.example.filmfestival.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmfestival.models.Award
import com.example.filmfestival.models.Cast
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.Ticket
import com.example.filmfestival.models.Trailer
import com.example.filmfestival.models.Watchlist

@Database(
    entities = [
        Award::class,
        Cast::class,
        Movie::class,
        Show::class,
        Ticket::class,
        Trailer::class,
        Watchlist::class
    ],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun awardDao(): AwardDao
    abstract fun castDao(): CastDao
    abstract fun movieDao(): MovieDao
    abstract fun showDao(): ShowDao
    abstract fun ticketDao(): TicketDao
    abstract fun trailerDao(): TrailerDao
    abstract fun watchlistDao(): WatchlistDao

}