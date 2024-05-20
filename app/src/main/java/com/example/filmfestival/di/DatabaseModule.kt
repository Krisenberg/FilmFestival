package com.example.filmfestival.di

import android.app.Application
import android.content.Context
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.filmfestival.MyApp
import com.example.filmfestival.data.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
//            val actorDataLoadStatement = buildString {
//                append("INSERT INTO Actor (actorId, name, photo) VALUES ")
//                bufferedReader.forEachLine { line ->
//                    append(line)
//                }
//            }
//            db.execSQL(actorDataLoadStatement)
//            bufferedReader.
//            db.execSQL("INSERT INTO Actor (actorId, name, photo) " +
//                    "VALUES " +
//                    "(1,\"Timothée Chalamet\",\"https://bi.im-g.pl/im/0b/53/1d/z30748939ICR,Timothee-Chalamet.jpg\"), " +
//                    "(2,\"Zendaya\",\"https://fwcdn.pl/ppo/60/35/1546035/451218.2.jpg\"), " +
//                    "(3,\"Rebecca Ferguson\",\"https://media.themoviedb.org/t/p/w500/lJloTOheuQSirSLXNA3JHsrMNfH.jpg\"), " +
//                    "(4,\"Javier Bardem\",\"https://m.media-amazon.com/images/M/MV5BMTY1NTc4NTYzMF5BMl5BanBnXkFtZTcwNDIwOTY1NA@@._V1_.jpg\"), " +
//                    "(5,\"Josh Brolin\",\"https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Josh_Brolin_SDCC_2014.jpg/1200px-Josh_Brolin_SDCC_2014.jpg\"), " +
//                    "(6,\"Austin Butler\",\"https://m.media-amazon.com/images/M/MV5BZTNjZjMwMDAtNjliNy00NGNiLTgyNmQtOTA5NDRjNGU4YjRhXkEyXkFqcGdeQXVyNjY4MDI3NQ@@._V1_.jpg\"), " +
//                    "(7,\"Florence Pugh\",\"https://fwcdn.pl/fph/34/81/10003481/1190521_1.3.jpg\"), " +
//                    "(8,\"Dave Bautista\",\"https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Dave_Bautista_Photo_Op_GalaxyCon_Minneapolis_2019.jpg/640px-Dave_Bautista_Photo_Op_GalaxyCon_Minneapolis_2019.jpg\"), " +
//                    "(9,\"Daniel Craig\",\"https://fwcdn.pl/ppo/60/35/1546035/451218.2.jpg\")")

//        addMigrations(MovieDatabase.migration1To2)

//    createFromAsset("database/movies.db")

//    @Singleton
//    @Provides
//    fun provideAwardDao(database: MovieDatabase) = database.awardDao()
//
//    @Singleton
//    @Provides
//    fun provideCastDao(database: MovieDatabase) = database.castDao()
//
    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDatabase) = database.movieDao()

    @Singleton
    @Provides
    fun provideUserDao(database: MovieDatabase) = database.userDao()
//
//    @Singleton
//    @Provides
//    fun provideShowDao(database: MovieDatabase) = database.showDao()
//
//    @Singleton
//    @Provides
//    fun provideTicketDao(database: MovieDatabase) = database.ticketDao()
//
//    @Singleton
//    @Provides
//    fun provideTrailerDao(database: MovieDatabase) = database.trailerDao()
//
    @Singleton
    @Provides
    fun provideWatchlistDao(database: MovieDatabase) = database.watchlistDao()
}