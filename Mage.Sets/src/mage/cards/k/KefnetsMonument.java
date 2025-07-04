
package mage.cards.k;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE;

/**
 * @author fireshoes
 */
public final class KefnetsMonument extends CardImpl {

    private static final FilterCard filter = new FilterCard("Blue creature spells");

    static {
        filter.add(Predicates.and(new ColorPredicate(ObjectColor.BLUE), CardType.CREATURE.getPredicate()));
    }

    public KefnetsMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);

        // Blue creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast a creature spell, target creature an opponent controls doesn't untap during its controller's next untap step.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DontUntapInControllersNextUntapStepTargetEffect(),
                StaticFilters.FILTER_SPELL_A_CREATURE, false
        );
        ability.addTarget(new TargetPermanent(FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private KefnetsMonument(final KefnetsMonument card) {
        super(card);
    }

    @Override
    public KefnetsMonument copy() {
        return new KefnetsMonument(this);
    }
}
