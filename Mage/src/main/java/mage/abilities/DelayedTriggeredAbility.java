package mage.abilities;

import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DelayedTriggeredAbility extends TriggeredAbilityImpl {

    private Duration duration;
    protected boolean triggerOnlyOnce;

    protected DelayedTriggeredAbility(Effect effect) {
        this(effect, Duration.EndOfGame);
    }

    protected DelayedTriggeredAbility(Effect effect, Duration duration) {
        this(effect, duration, true);
    }

    protected DelayedTriggeredAbility(Effect effect, Duration duration, boolean triggerOnlyOnce) {
        this(effect, duration, triggerOnlyOnce, false);
    }

    protected DelayedTriggeredAbility(Effect effect, Duration duration, boolean triggerOnlyOnce, boolean optional) {
        super(Zone.ALL, effect, optional);
        this.duration = duration;
        this.triggerOnlyOnce = triggerOnlyOnce;
    }

    protected DelayedTriggeredAbility(final DelayedTriggeredAbility ability) {
        super(ability);
        this.duration = ability.duration;
        this.triggerOnlyOnce = ability.triggerOnlyOnce;
    }

    @Override
    public abstract DelayedTriggeredAbility copy();

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public boolean getTriggerOnlyOnce() {
        return triggerOnlyOnce;
    }

    /**
     * This method is called as the ability is added to the game (not as the
     * ability triggers later)
     *
     * @param game
     */
    public void init(Game game) {
    }

    @Override
    public DelayedTriggeredAbility setTriggerPhrase(String triggerPhrase) {
        super.setTriggerPhrase(triggerPhrase);
        return this;
    }

    public boolean isInactive(Game game) {
        return false;
    }
}
