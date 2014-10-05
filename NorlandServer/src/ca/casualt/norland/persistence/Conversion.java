package ca.casualt.norland.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.casualt.norland.exportformat.CharacterType;
import ca.casualt.norland.exportformat.KillObject;
import ca.casualt.norland.exportformat.LevelAttempt;
import ca.casualt.norland.exportformat.LevelInteraction;

import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Convert to/from database format.
 * 
 * @author Tony
 */
public final class Conversion {

    /**
     * Hang everything off of this.
     */
    private static final Key ROOTPARENT = KeyFactory.createKey("ROOTKIND", "ROOTNAME");

    /**
     * Stats bundle kind.
     */
    private static final String KIND_STATSBUNDLE = "Kind_StatsBundle";

    /**
     * Level interaction kind.
     */
    private static final String KIND_LEVELINTERACTION = "Kind_LevelInteraction";

    /**
     * Key, for logged interactions listing.
     */
    private static final String KEY_STATSBUNDLE_CREATEDTIME = "Key_SB_CreatedTime";

    /**
     * Creates a stats bundle entity, with which to hang other kinds of things.
     * 
     * @return the entity with a current time field filled in.
     */
    static Entity getNewStatsBundleEntity() {
        Entity ent = new Entity(KIND_STATSBUNDLE, ROOTPARENT);
        ent.setProperty(KEY_STATSBUNDLE_CREATEDTIME, System.currentTimeMillis());
        return ent;
    }

    /**
     * Level Attempts.
     */
    private static final String KEY_LEVELINTERACTION_ATTEMPTS = "Key_LI_Attempts";

    /**
     * Time End.
     */
    private static final String KEY_LEVELINTERACTION_TIMEEND = "Key_LI_TimeEnd";

    /**
     * Time Start.
     */
    private static final String KEY_LEVELINTERACTION_TIMESTART = "Key_LI_TimeStart";

    /**
     * User Settings.
     */
    private static final String KEY_LEVELINTERACTION_USERSETTINGS = "Key_LI_UserSettings";

    /**
     * Get the level interaction entities, if any.
     * 
     * @param parentKey
     *            the parent to hang from.
     * @return the list of interactions.
     */
    static List<Entity> getLevelInteractionEntities(final Key parentKey,
            final List<LevelInteraction> interactions) {
        List<Entity> toReturn = new ArrayList<Entity>();
        if (interactions != null) {
            for (LevelInteraction li : interactions) {
                Entity toAdd = new Entity(KIND_LEVELINTERACTION, parentKey);
                // level attempts never null.
                List<LevelAttempt> lAttempts = li.getLevelAttempts();
                List<EmbeddedEntity> lAttemptsToSave = new ArrayList<EmbeddedEntity>();
                for (LevelAttempt la : lAttempts) {
                    lAttemptsToSave.add(toEmbeddedEntity(la));
                }
                toAdd.setProperty(KEY_LEVELINTERACTION_ATTEMPTS, lAttemptsToSave);
                toAdd.setProperty(KEY_LEVELINTERACTION_TIMEEND, li.getTimeEnd());
                toAdd.setProperty(KEY_LEVELINTERACTION_TIMESTART, li.getTimeStart());
                toAdd.setProperty(KEY_LEVELINTERACTION_USERSETTINGS,
                        toEmbeddedEntity(li.getUserSettings()));
                toReturn.add(toAdd);
            }
        }
        return toReturn;
    }

    /**
     * Kills.
     */
    private static final String KEY_LEVELATTEMPT_KILLS = "Key_LA_Kills";

    /**
     * Time End.
     */
    private static final String KEY_LEVELATTEMPT_TIMEEND = "Key_LA_TimeEnd";

    /**
     * Time Start.
     */
    private static final String KEY_LEVELATTEMPT_TIMESTTART = "Key_LA_TimeStart";

    /**
     * @param toConvert
     *            the LevelAttempt to convert to an embedded entity.
     * @return the resulting entity.
     */
    private static EmbeddedEntity toEmbeddedEntity(final LevelAttempt toConvert) {
        EmbeddedEntity embed = new EmbeddedEntity();
        KillObject kills = toConvert.getKills();
        embed.setProperty(KEY_LEVELATTEMPT_KILLS, toEmbeddedEntity(kills));
        embed.setProperty(KEY_LEVELATTEMPT_TIMEEND, toConvert.getTimeEnd());
        embed.setProperty(KEY_LEVELATTEMPT_TIMESTTART, toConvert.getTimeStart());
        return embed;
    }

    /**
     * @param toConvert
     * @return
     */
    private static EmbeddedEntity toEmbeddedEntity(final KillObject toConvert) {
        EmbeddedEntity embed = new EmbeddedEntity();
        for (Entry<CharacterType, Integer> ent : toConvert.getKillCounts().entrySet()) {
            embed.setProperty(ent.getKey().name(), ent.getValue());
        }
        return embed;
    }

    /**
     * @param toConvert
     * @return
     */
    private static EmbeddedEntity toEmbeddedEntity(final Map<String, ?> toConvert) {
        EmbeddedEntity embed = new EmbeddedEntity();
        for (Entry<String, ?> ent : toConvert.entrySet()) {
            embed.setProperty(ent.getKey(), ent.getValue());
        }
        return embed;
    }
}
