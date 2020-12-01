package nitros.yatranslator.di;


import nitros.yatranslator.App;
import nitros.yatranslator.model.room.IRoomCache;
import nitros.yatranslator.model.room.RoomCache;
import nitros.yatranslator.model.room.data.Database;

import androidx.annotation.NonNull;
import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {


    @Singleton
    @Provides
    public Database getDatabase(App app) {
        Database room =  Room.databaseBuilder(app, Database.class, "translateDataBase.db").build();
        return room;
    }

    @Singleton
    @Provides
    public IRoomCache getBase(Database database) {

        return new RoomCache(database);
    }


}
