package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseCardTypeEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllOfChosenCardTypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Alex-Vasile
 */
public class StennParanoidPartisan extends CardImpl {

    public StennParanoidPartisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        addSubType(SubType.HUMAN, SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Stenn, Paranoid Partisan enters the battlefield, choose a card type other than creature or land.
        List<CardType> cardTypesOther = Arrays.stream(CardType.values()).collect(Collectors.toList());
        cardTypesOther.remove(CardType.CREATURE);
        cardTypesOther.remove(CardType.LAND);
        this.addAbility(new AsEntersBattlefieldAbility(
                new ChooseCardTypeEffect(Outcome.Benefit, cardTypesOther)
                        .setText("choose a card type other than creature or land")
        ));

        // Spells you cast of the chosen type cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostReductionAllOfChosenCardTypeEffect(new FilterCard("Spells you cast of the chosen type"), 1, true)
        ));

        // {1}{W}{U}: Exile Stenn. Return it to the battlefield under its owner’s control at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(), new ManaCostsImpl<>("{1}{W}{U}")));
    }

    private StennParanoidPartisan(final StennParanoidPartisan card) {
        super(card);
    }

    @Override
    public StennParanoidPartisan copy() {
        return new StennParanoidPartisan(this);
    }
}
