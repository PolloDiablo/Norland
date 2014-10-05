package ca.casualt.norland.persistence;

import java.util.List;

import ca.casualt.norland.exportformat.StatsBundle;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;

/**
 * Database operations.
 * 
 * @author Tony
 */
public final class Operations {

    /**
     * Utility class.
     */
    private Operations() {
    }

    /**
     * Saves to the database, false if problems exist in doing this.
     * 
     * @param inputData
     *            the data bundle to save.
     * @return true, if successfully saved.
     */
    public static boolean saveToDatabase(final StatsBundle inputData) {
        if (inputData == null) {
            return false;
        }
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Transaction tran = datastore.beginTransaction();
        try {
            // Need to layer-in Entity creation.
            Entity statsBundle = Conversion.getNewStatsBundleEntity();
            datastore.put(statsBundle);
            // stats bundle will now have a valid key, that we can take the
            // level interactions and attach them to.
            List<Entity> levelInteractions = Conversion.getLevelInteractionEntities(
                    statsBundle.getKey(), inputData.getLoggedInteractions());
            datastore.put(levelInteractions);
            tran.commit();
            return true;
        } finally {
            if (tran.isActive()) {
                tran.rollback();
                return false;
            }
        }
    }
}
