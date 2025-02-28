package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class KaronaFalseGod extends CardImpl {

    public KaronaFalseGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of each player's upkeep, that player untaps Karona, False God and gains control of it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(TargetController.EACH_PLAYER, new KaronaFalseGodUntapGetControlEffect(), false));

        // Whenever Karona attacks, creatures of the creature type of your choice get +3/+3 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new KaronaFalseGodEffect(), false));
    }

    private KaronaFalseGod(final KaronaFalseGod card) {
        super(card);
    }

    @Override
    public KaronaFalseGod copy() {
        return new KaronaFalseGod(this);
    }
}

class KaronaFalseGodUntapGetControlEffect extends OneShotEffect {

    KaronaFalseGodUntapGetControlEffect() {
        super(Outcome.GainControl);
        this.staticText = "that player untaps {this} and gains control of it";
    }

    private KaronaFalseGodUntapGetControlEffect(final KaronaFalseGodUntapGetControlEffect effect) {
        super(effect);
    }

    @Override
    public KaronaFalseGodUntapGetControlEffect copy() {
        return new KaronaFalseGodUntapGetControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player newController = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (newController != null && controller != null && sourceObject != null && sourceObject.equals(sourcePermanent)) {
            sourcePermanent.untap(game);
            game.informPlayers(newController.getLogName() + " untaps " + sourceObject.getIdName());
            // remove old control effects of the same player
            for (ContinuousEffect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
                if (effect instanceof GainControlTargetEffect) {
                    UUID checkId = (UUID) effect.getValue("KaronaFalseGodSourceId");
                    UUID controllerId = (UUID) effect.getValue("KaronaFalseGodControllerId");
                    if (source.getSourceId().equals(checkId) && newController.getId().equals(controllerId)) {
                        effect.discard();
                    }
                }
            }
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, newController.getId());
            effect.setValue("KaronaFalseGodSourceId", source.getSourceId());
            effect.setValue("KaronaFalseGodControllerId", newController.getId());
            effect.setTargetPointer(new FixedTarget(sourcePermanent.getId(), game));
            effect.setText("and gains control of it");
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class KaronaFalseGodEffect extends OneShotEffect {

    KaronaFalseGodEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "creatures of the creature type of your choice get +3/+3 until end of turn";
    }

    private KaronaFalseGodEffect(final KaronaFalseGodEffect effect) {
        super(effect);
    }

    @Override
    public KaronaFalseGodEffect copy() {
        return new KaronaFalseGodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Choice typeChoice = new ChoiceCreatureType(game, source);
            if (!controller.choose(Outcome.BoostCreature, typeChoice, game)) {
                return false;
            }
            game.informPlayers(controller.getLogName() + " has chosen " + typeChoice.getChoiceKey());
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(SubType.byDescription(typeChoice.getChoiceKey()).getPredicate());
            game.addEffect(new BoostAllEffect(3, 3, Duration.EndOfTurn, filter, false), source);
            return true;
        }
        return false;
    }
}
