package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class Portcullis extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterCreaturePermanent("there are two or more other creatures on the battlefield"),
            ComparisonType.MORE_THAN, 1, false
    );

    public Portcullis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever a creature enters the battlefield, if there are two or more other creatures on the battlefield, exile that creature. Return that card to the battlefield under its owner's control when Portcullis leaves the battlefield.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new PortcullisExileEffect(), StaticFilters.FILTER_PERMANENT_A_CREATURE,
                false, SetTargetPointer.PERMANENT
        ).withInterveningIf(condition));
    }

    private Portcullis(final Portcullis card) {
        super(card);
    }

    @Override
    public Portcullis copy() {
        return new Portcullis(this);
    }
}

class PortcullisExileEffect extends OneShotEffect {

    PortcullisExileEffect() {
        super(Outcome.Exile);
        this.staticText = "exile that creature. Return that card to the battlefield " +
                "under its owner's control when {this} leaves the battlefield";
    }

    private PortcullisExileEffect(final PortcullisExileEffect effect) {
        super(effect);
    }

    @Override
    public PortcullisExileEffect copy() {
        return new PortcullisExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creatureToExile = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent portcullis = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (portcullis != null
                && creatureToExile != null
                && controller != null) {
            UUID exileZoneId = CardUtil.getExileZoneId(game, creatureToExile.getId(), creatureToExile.getZoneChangeCounter(game));
            controller.moveCardsToExile(creatureToExile, source, game, true, exileZoneId, portcullis.getName());
            Effect returnEffect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
            returnEffect.setTargetPointer(new FixedTarget(creatureToExile.getId(), game.getState().getZoneChangeCounter(creatureToExile.getId())));
            DelayedTriggeredAbility delayedAbility = new PortcullisReturnToBattlefieldTriggeredAbility(new FixedTarget(portcullis, game), returnEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
        }
        return true;
    }
}

class PortcullisReturnToBattlefieldTriggeredAbility extends DelayedTriggeredAbility {

    protected final FixedTarget fixedTarget;

    public PortcullisReturnToBattlefieldTriggeredAbility(FixedTarget fixedTarget, Effect effect) {
        super(effect, Duration.OneUse);
        this.fixedTarget = fixedTarget;
    }

    private PortcullisReturnToBattlefieldTriggeredAbility(final PortcullisReturnToBattlefieldTriggeredAbility ability) {
        super(ability);
        this.fixedTarget = ability.fixedTarget.copy();
    }

    @Override
    public PortcullisReturnToBattlefieldTriggeredAbility copy() {
        return new PortcullisReturnToBattlefieldTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getFromZone().match(Zone.BATTLEFIELD)) {
            return (fixedTarget.getTarget().equals(event.getTargetId()));
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Return this card to the battlefield under its owner's control when {this} leaves the battlefield.";
    }
}
