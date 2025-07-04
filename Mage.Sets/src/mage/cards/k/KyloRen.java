package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author NinthWorld
 */
public final class KyloRen extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public KyloRen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Kylo Ren attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Whenever Kylo Ren attacks, it gets +1/+0 for each creature in your graveyard and you may tap target creature defending player controls.
        CardsInControllerGraveyardCount value = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
        Ability ability = new AttacksTriggeredAbility(new BoostSourceEffect(value, StaticValue.get(0), Duration.WhileOnBattlefield).setText("it gets +1/+0 for each creature in your graveyard"));
        ability.addEffect(new KyloRenTapTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private KyloRen(final KyloRen card) {
        super(card);
    }

    @Override
    public KyloRen copy() {
        return new KyloRen(this);
    }
}

class KyloRenTapTargetEffect extends TapTargetEffect {

    KyloRenTapTargetEffect() {
        super();
        this.staticText = "and you may tap target creature defending player controls";
    }

    private KyloRenTapTargetEffect(final KyloRenTapTargetEffect effect) {
        super(effect);
    }

    @Override
    public KyloRenTapTargetEffect copy() {
        return new KyloRenTapTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && permanent != null) {
            if (player.chooseUse(outcome, "Tap target creature defending player controls (" + permanent.getLogName() + ")", source, game)) {
                super.apply(game, source);
            }
        }
        return false;
    }
}
