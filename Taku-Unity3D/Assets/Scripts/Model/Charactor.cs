using System.Collections;

/**
 * 角色模型基类
 * */
public class Charactor
{	
		public Charactor (int location, int state, int speed)
		{
				this.location = location;
				this.state = state;
				this.speed = speed;
		}

		/**
	 	* 人物在跑到上的位置
	 	*/
		protected int location = 0;
		/**
	 	 人物当前的状态
	 	*/
		protected int state = 0;
		/**
	 	* 记录人物当前速度
	 	*/
		protected int speed = 0;
		/**
	 	* 记录人物最大速度
	 	*/
		protected int maxSpeed = 0;
		/**
	 	* 记录人物跑步平均速度
	 	*/
		protected int averageSpeed = 0;
		/**
	 	* 记录一局游戏的总时长
	 	*/
		protected int totalTime = 0;
		/**
	 	* 记录一局游戏的总分数
	 	*/
		protected int totalScore = 0;
	
		public int Location {
				get{ return location;}
				set{ location = value;}
		}
	
		public int State {
				get{ return state;}
				set{ state = value;}
		}

		public int Speed {
				get{ return speed;}
				set{ speed = value;}
		}

		public int MaxSpeed {
				get{ return maxSpeed;}
				set{ maxSpeed = value;}
		}
	
		public int AverageSpeed {
				get{ return averageSpeed;}
				set{ averageSpeed = value;}
		}
	
		public int TotalTime {
				get{ return totalTime;}
				set{ totalTime = value;}
		}
	
		public int TotalScore {
				get{ return totalScore;}
				set{ totalScore = value;}
		}

		/**
		 * 模型状态
	 	* */
		public enum STATE
		{		/**
				*向左
				**/
				LEFT,
				/**
				 * 向右
				 **/
				RIGHT,
				/**
		 		* 奔跑
		 		**/
				RUN,
				/**
		 		* 跳跃
		 		**/
				JUMP,
				/**
		 		* 站立
		 		**/
				STAND
		}
	
}


