#pragma strict

public enum SwipeDirection 
{
	Null = 0,//no swipe detected
	Duck = 1,//swipe down detected
	Jump = 2,//swipe up detected
	Right = 3,//swipe right detected
	Left = 4//swipe left detected
}

//Constants
private var fSensitivity : float = 15;

//VARIABLES
//distance calculation
private var fInitialX : float;
private var fInitialY : float;
private var fFinalX : float;
private var fFinalY : float;

private var inputX : float;	
private var inputY : float;
private var slope : float;
private var fDistance : float;
private var iTouchStateFlag : int;
private var sSwipeDirection : SwipeDirection;
private var sLastSwipeDirection : SwipeDirection;

private var bGravityFirstTime : boolean = true;
private var fPrevGravityX : float = 0.0f;
private var fCurrGravityX : float = 0.0f;

private var hBluetoothScript : BluetoothScript;

function Start ()
{
	fInitialX = 0.0f;
	fInitialY = 0.0f;
	fFinalX = 0.0f;
	fFinalY = 0.0f;
	
	inputX = 0.0f;
	inputY = 0.0f;
	
	iTouchStateFlag = 0;
	sSwipeDirection = SwipeDirection.Null;
	hBluetoothScript = this.GetComponent(BluetoothScript) as BluetoothScript;
}

function Update()
{    
    //fPrevGravityX = fCurrGravityX;
    //fCurrGravityX = Input.acceleration.x;
    //
    if(!Application.isEditor)
    {
    	//sSwipeDirection = swipeDirection();
    }
    else
    {
    	if (iTouchStateFlag == 0 && Input.GetMouseButtonDown(0))
		{		
			fInitialX = Input.mousePosition.x;
			fInitialY = Input.mousePosition.y;
			
			sSwipeDirection = SwipeDirection.Null;
			iTouchStateFlag = 1;
		}
		
		if (iTouchStateFlag == 1)
		{
			fFinalX = Input.mousePosition.x;
			fFinalY = Input.mousePosition.y;
			
			sSwipeDirection = swipeDirection();
			
			if (sSwipeDirection != SwipeDirection.Null)
				iTouchStateFlag = 2;
		}
		
		if (iTouchStateFlag == 2 || Input.GetMouseButtonUp(0))
		{
			iTouchStateFlag = 0;
		}
    }
}

public function getSwipeDirection():SwipeDirection
{
	if(!Application.isEditor)
	{
		return swipeDirection();
	}
	else 
	{
		if (sSwipeDirection != SwipeDirection.Null)
		{
			var etempSwipeDirection = sSwipeDirection;
			sSwipeDirection = SwipeDirection.Null;
			return etempSwipeDirection;
		}
		else
			return SwipeDirection.Null;
	}
}

public function getCurrGravityX():float
{
    return fCurrGravityX;
}

function OnGUI(){
    //GUI.Box(Rect(0, 200, 100, 30), fCurrGravityX.ToString());
}

private function swipeDirection()
{
	inputX = fFinalX - fInitialX;
	inputY = fFinalY - fInitialY;
	slope = inputY / inputX;
	
	fDistance = Mathf.Sqrt( Mathf.Pow((fFinalY-fInitialY), 2) + Mathf.Pow((fFinalX-fInitialX), 2) );
	
	//if (fDistance <= (Screen.width/fSensitivity))
	//{
	    if(!Application.isEditor){
	    	return hBluetoothScript.GetDirection();
	    	/*if(hBluetoothScript.GetLeftRight() == -1)
	    	{
	    		return SwipeDirection.Left;
	    	}
	    	else if(hBluetoothScript.GetLeftRight() == 1)
	    	{
	    		return SwipeDirection.Right;
	    	}
	        /*if(fCurrGravityX < -0.2f){
	            return SwipeDirection.Left;
	        }else if(fCurrGravityX > 0.2f){
                return SwipeDirection.Right;
	        }*/
	    }
	//    return SwipeDirection.Null;
	//}
	
	if (inputX >= 0 && inputY > 0 && slope > 1)
	{		
		return SwipeDirection.Jump;
	}
	else if (inputX <= 0 && inputY > 0 && slope < -1)
	{
		return SwipeDirection.Jump;
	}
	else if (inputX > 0 && inputY >= 0 && slope < 1 && slope >= 0)
	{
	    return SwipeDirection.Right;
	}
	else if (inputX > 0 && inputY <= 0 && slope > -1 && slope <= 0)
	{
	    return SwipeDirection.Right;
	}
	else if (inputX < 0 && inputY >= 0 && slope > -1 && slope <= 0)
	{
	    return SwipeDirection.Left;
	}
	else if (inputX < 0 && inputY <= 0 && slope >= 0 && slope < 1)
	{
	    return SwipeDirection.Left;
	}
	else if (inputX >= 0 && inputY < 0 && slope < -1)
	{
		return SwipeDirection.Duck;
	}
	else if (inputX <= 0 && inputY < 0 && slope > 1)
	{
	    return SwipeDirection.Duck;
	}
	
	return SwipeDirection.Null;	
}

