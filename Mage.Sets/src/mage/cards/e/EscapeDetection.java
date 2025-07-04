package mage.cards.e;

import mage.ObjectColor;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author grimreap124
 */
public final class EscapeDetection extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("blue creature you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public EscapeDetection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Freerunning--Return a blue creature you control to its owner's hand.
        this.addAbility(new FreerunningAbility(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter))));

        // Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private EscapeDetection(final EscapeDetection card) {
        super(card);
    }

    @Override
    public EscapeDetection copy() {
        return new EscapeDetection(this);
    }
}
