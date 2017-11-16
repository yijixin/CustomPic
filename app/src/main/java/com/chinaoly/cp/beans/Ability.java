package com.chinaoly.cp.beans;

/**
 * @author Created by yijixin at 2017/11/14
 */
public class Ability {

    public static final String[] abilitys
            = {"击杀", "生存", "助攻", "物理",
            "魔法", "防御", "金钱"};

    //每个能力的值，范围0~100，单位%
    /**
     * 击杀
     */
    private int kill;

    /**
     * 生存
     */
    private int survival;

    /**
     * 助攻
     */
    private int assist;

    /**
     * 物理
     */
    private int ad;

    /**
     * 魔法
     */
    private int ap;

    /**
     * 防御
     */
    private int defense;

    /**
     * 金钱
     */
    private int money;

    public Ability(int kill, int survival, int assist, int ad, int ap, int defense, int money) {
        this.kill = kill;
        this.survival = survival;
        this.assist = assist;
        this.ad = ad;
        this.ap = ap;
        this.defense = defense;
        this.money = money;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getSurvival() {
        return survival;
    }

    public void setSurvival(int survival) {
        this.survival = survival;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public int getAd() {
        return ad;
    }

    public void setAd(int ad) {
        this.ad = ad;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
