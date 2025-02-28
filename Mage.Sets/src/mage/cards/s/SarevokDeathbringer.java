package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarevokDeathbringer extends CardImpl {

    public SarevokDeathbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of each player's end step, if no permanents left the battlefield this turn, that player loses X life, where X is Sarevok's power.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.EACH_PLAYER, new LoseLifeTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE),
                false, new InvertCondition(SarevokDeathbringerCondition.instance)
        ), new RevoltWatcher());

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private SarevokDeathbringer(final SarevokDeathbringer card) {
        super(card);
    }

    @Override
    public SarevokDeathbringer copy() {
        return new SarevokDeathbringer(this);
    }
}

enum SarevokDeathbringerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return RevoltWatcher.checkAny(game);
    }

    @Override
    public String toString() {
        return "if no permanents left the battlefield this turn";
    }
}
