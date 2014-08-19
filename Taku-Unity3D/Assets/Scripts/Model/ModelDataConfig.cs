using System.Collections;

public class ModelConfig
{
	//碰到金币时的加分
	public const int SCORE_HIT_COIN = 1;
	//碰到障碍物时的扣分
	public const int SCORE_HIT_BARRIER = 10;
	//超越前方人物的加分
	public const int SCORE_TRANSCEND = 100;
	//被追兵追上时的扣分
	public const int SCORE_CAUGHTED = 100;
	//逃离追兵时的加分
	public const int SCORE_ESCAPE = 200;
	//碰到障碍物时的减速加速度
	public const float DECELERATION_HIT_COIN = 0.01F;
	//碰到金币时的减速加速度
	public const float DECELERATION_HIT_BARRIER = 0.2F;
	//追上前面一个人之后，需要追上下一个人的时间限制
	public const int  MAX_TIME_TO_CATCH_NEXT = 30;
	//追上前面一个人之后，没有在规定时间（TimeToCatchNextPrey）内追上下一个人的扣分
	public const int SCORE_PENALTY_LOSE_TARGET = 200;
	//暴击模式下，超过被追的人的速度差值
	public const int MIN_DIFFERENCE_SPEED_TO_ESCAPE_IN_CRIT = 200;
	//暴击模式下的奖励
	public const int SCORE_IN_CRIT = 200;
	//有追兵时，需要奔跑的最少时间
	public const int MIN_TIME_TO_ESCAPE = 5;


}
