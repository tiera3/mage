package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RegalImperiosaur extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.DINOSAUR, "Dinosaurs");

    public RegalImperiosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Other Dinosaurs you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private RegalImperiosaur(final RegalImperiosaur card) {
        super(card);
    }

    @Override
    public RegalImperiosaur copy() {
        return new RegalImperiosaur(this);
    }
}
