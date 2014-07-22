#pragma strict

//追上前面的人之后，出现新追击的人的距离
var DistanceOfNewPrey : float = 600;
//追上前面的人之后的分数奖励
var ScoreCatchPrey : int = 100;
//追上前面一个人之后，需要追上下一个人的时间限制
var TimeToCatchNextPrey : int = 30;
//追上前面一个人之后，没有在规定时间（TimeToCatchNextPrey）内追上下一个人的扣分
var ScoreLooseNextPrey : int = 200;
//暴击模式下，超过被追的人的速度差值
var SpeedBurstThanPrey : int = 200;
//暴击模式下的奖励
var ScoreBurst : int = 200;
//有追兵时，需要奔跑的最少时间
var TimeToEscapeChaser : int = 5;
//被追兵追上时的扣分
var ScoreCaughtByChaser : int = 100;
//逃离追兵时的加分
var ScoreEscapeChaser : int = 200;
//碰到障碍物时的减速比例
var DecelerationOnBarrier : float = 0.2;
//碰到障碍物时的扣分
var ScoreOnBarrier : int = 10;
//碰到金币时的减速比例
var DecelerationOnCurrency : float = 0.01;
//碰到金币时的加分
var ScoreOnCurrency : int = 1;