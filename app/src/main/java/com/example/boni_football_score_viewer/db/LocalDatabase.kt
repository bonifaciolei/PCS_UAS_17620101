package com.example.boni_football_score_viewer.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.boni_football_score_viewer.model.Events
import org.jetbrains.anko.db.*

const val DATABASE_NAME = "SavedScore.db"
class LocalDatabase(context: Context)
    : ManagedSQLiteOpenHelper(context, DATABASE_NAME, null,1) {
    companion object {
        private var instance : LocalDatabase? = null
        @Synchronized
        fun getInstances(context: Context) : LocalDatabase {
           return when (instance) {
                null -> {
                    instance = LocalDatabase(context)
                    instance as LocalDatabase
                } else -> instance as LocalDatabase
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Events.TABLE_SAVED_MATCH, true,
                Events.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Events.ID_EVENT to TEXT + UNIQUE,
                Events.HOME_TEAM to TEXT,
                Events.AWAY_TEAM to TEXT,
                Events.HOME_SCORE to TEXT,
                Events.AWAY_SCORE to TEXT,
                Events.DATE_EVENT to TEXT,
                Events.HOME_BADGE to TEXT,
                Events.AWAY_BADGE to TEXT,
                Events.STADIUM to TEXT,
                Events.STADIUM_COUNTRY to TEXT
                )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Events.TABLE_SAVED_MATCH, true)
    }

}

val Context.database: LocalDatabase
    get() = LocalDatabase.getInstances(applicationContext)