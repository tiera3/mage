

package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author LevelX2
 */
public class ReshuffledBoosterDraftSwissTournamentType extends TournamentType {

    public ReshuffledBoosterDraftSwissTournamentType() {
        this.name = "Booster Draft Swiss (Reshuffled)";
        this.maxPlayers = 16;
        this.minPlayers = 4;
        this.numBoosters = 3;
        this.draft = true;
        this.limited = true;
        this.cubeBooster = false;
        this.elimination = false;
        this.isReshuffled = true;
    }

}
