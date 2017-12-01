package uk.ac.qub.eeecs.game.cardDemo;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

/**
 * Created by nshah on 27/11/2017.
 */

public class Hero extends Sprite {

    private final int MAX_HEALTH = 30;
    private final int MAX_MANA = 10;

    private int currentHealth;
    private int currentMana;
    private int manaLimit;

    public Hero(GameScreen gameScreen){
        super(gameScreen);
        currentHealth = 30;
        manaLimit = 1;
        currentMana = manaLimit;
    }

    public void IncrementMangaLimit(){
        if (manaLimit < MAX_MANA){
            manaLimit++;
        }
    }

    public void IncrementManga(){
        if(currentMana < manaLimit){
            currentMana++;
        }
    }

    public void TakeDamage(int DamageDealt){
        currentHealth -= DamageDealt;
        if(currentHealth < 0){
            currentHealth = 0;
        }
    }
}
