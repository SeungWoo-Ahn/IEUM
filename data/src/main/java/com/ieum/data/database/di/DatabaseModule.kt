package com.ieum.data.database.di

import android.content.Context
import androidx.room.Room
import com.ieum.data.database.IeumDatabase
import com.ieum.data.database.dao.PostDao
import com.ieum.data.database.util.PostTypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesIeumDatabase(
        @ApplicationContext context: Context,
    ): IeumDatabase =
        Room
            .databaseBuilder(
                context = context,
                klass = IeumDatabase::class.java,
                name = "ieum_database.db"
            )
            .addTypeConverter(PostTypeConverters())
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    @Singleton
    fun providesPostDao(db: IeumDatabase): PostDao = db.postDao()
}