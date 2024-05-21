package com.example.filmfestival.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.filmfestival.data.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "film_festival.db"
    ).addCallback(object : RoomDatabase.Callback() {
        private fun createSQLInsertStatement(fileName: String, tableSignature: String): String {
            val bufferedReader = app.assets.open("database/$fileName").bufferedReader()
            val insertStatement = buildString {
                append("INSERT INTO $tableSignature VALUES ")
                bufferedReader.forEachLine { line ->
                    append(line)
                }
            }
            return insertStatement
        }

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val filesToBeInserted = listOf(
                Pair("Actor_data_loader.txt", "Actor (actorId, name, photo)"),
                Pair("Award_data_loader.txt", "Award (awardId, name, details)"),
                Pair("Movie_data_loader.txt", "Movie (movieId, title, moviePoster, " +
                        "moviePhoto, year, duration, genre, description)"),
                Pair("MovieAwardCrossRef_data_loader.txt", "MovieAwardCrossRef (movieId, awardId)"),
//                Pair("MovieRoleCrossRef_data_loader.txt", "MovieRoleCrossRef (movieId, roleId)"),
                Pair("Role_data_loader.txt", "Role (roleId, movieId, actorId, starring)"),
                Pair("Show_data_loader.txt", "Show (showId, movieId, dateTime)"),
                Pair("TicketCrossRef_data_loader.txt", "TicketCrossRef (userId, showId)"),
                Pair("Trailer_data_loader.txt", "Trailer (trailerId, movieId, trailer)"),
                Pair("User_data_loader.txt", "User (userId, username, avatar)"),
                Pair("WatchlistCrossRef_data_loader.txt", "WatchlistCrossRef (userId, movieId)")
            )

            filesToBeInserted.forEach {
                val filename = it.first
                val tableSignature = it.second

                val insertStatement = createSQLInsertStatement(filename, tableSignature)
                db.execSQL(insertStatement)
            }
        }
    }).build()
    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDatabase) = database.movieDao()

    @Singleton
    @Provides
    fun provideUserDao(database: MovieDatabase) = database.userDao()

    @Singleton
    @Provides
    fun provideShowDao(database: MovieDatabase) = database.showDao()

    @Singleton
    @Provides
    fun provideTicketDao(database: MovieDatabase) = database.ticketDao()

    @Singleton
    @Provides
    fun provideWatchlistDao(database: MovieDatabase) = database.watchlistDao()
}