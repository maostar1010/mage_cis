
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import static mage.filter.StaticFilters.FILTER_ANOTHER_TARGET_CREATURE;

/**
 *
 * @author Loki
 */
public final class TideforceElemental extends CardImpl {

    public TideforceElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {U}, {tap}: You may tap or untap another target creature.
        Ability ability = new SimpleActivatedAbility(
                new MayTapOrUntapTargetEffect(), 
                new ColoredManaCost(ColoredManaSymbol.U));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
        // Landfall - Whenever a land you control enters, you may untap Tideforce Elemental.
        this.addAbility(new LandfallAbility(new UntapSourceEffect(), true));
    }

    private TideforceElemental(final TideforceElemental card) {
        super(card);
    }

    @Override
    public TideforceElemental copy() {
        return new TideforceElemental(this);
    }
}
