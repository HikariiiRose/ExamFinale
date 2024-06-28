package ren.marinay.epictodolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ren.marinay.epictodolist.database.achievements.IncompleteAchievementModel
import ren.marinay.epictodolist.database.achievements.IncompleteAchievementsDao
import ren.marinay.epictodolist.database.challenges.ChallengeModel
import ren.marinay.epictodolist.database.challenges.ChallengesDao
import ren.marinay.epictodolist.database.notifications.NotificationModel
import ren.marinay.epictodolist.database.notifications.NotificationsDao
import ren.marinay.epictodolist.database.products.IncompleteProductModel
import ren.marinay.epictodolist.database.products.IncompleteProductsDao
import ren.marinay.epictodolist.database.skills.SkillModel
import ren.marinay.epictodolist.database.skills.SkillsDao
import ren.marinay.epictodolist.database.tasks.completed.CompletedTaskModel
import ren.marinay.epictodolist.database.tasks.completed.CompletedTasksDao
import ren.marinay.epictodolist.database.tasks.current.CurrentTaskModel
import ren.marinay.epictodolist.database.tasks.current.CurrentTasksDao
import ren.marinay.epictodolist.database.tasks.repeatable.RepeatableTaskModel
import ren.marinay.epictodolist.database.tasks.repeatable.RepeatableTasksDao

@Database(
    entities = [
        IncompleteAchievementModel::class,
        ChallengeModel::class,
        NotificationModel::class,
        IncompleteProductModel::class,
        SkillModel::class,
        CompletedTaskModel::class,
        CurrentTaskModel::class,
        RepeatableTaskModel::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(
    IntListConverter::class,
    BoolListConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun incompleteAchievementsDao(): IncompleteAchievementsDao
    abstract fun challengesDao(): ChallengesDao
    abstract fun notificationsDao(): NotificationsDao
    abstract fun incompleteProductsDao(): IncompleteProductsDao
    abstract fun skillsDao(): SkillsDao
    abstract fun completedTasksDao(): CompletedTasksDao
    abstract fun currentTasksDao(): CurrentTasksDao
    abstract fun repeatableTasksDao(): RepeatableTasksDao

    companion object {
        var instance: AppDatabase? = null

        
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "epic_db")
                .allowMainThreadQueries()
                .addMigrations(
                    object : Migration(3, 4) {
                        override fun migrate(database: SupportSQLiteDatabase) {
                            database.execSQL(
                                "ALTER TABLE repeatable_tasks ADD COLUMN notCreateIfExists INTEGER DEFAULT 1 NOT NULL"
                            )
                        }
                    }
                )
                .build()
            return instance!!
        }
    }
}